package ba.unsa.etf.rma.rma_projekat

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TrefleDAO(val context: Context? = null) {
    private val api_key = "i6onuid2-5mJofP1RnuwGVoInzOlc2LqhdjCozLk34w"
    suspend fun getImage(plant : Biljka) : Bitmap{
        return withContext(Dispatchers.IO) {
            try{
                //first we take a scientific name from the pant
                var imeBiljke = plant.naziv
                var scientificName = ""
                scientificName = extractContentInBrackets(imeBiljke)

                //then we get a image_url form api call
                var response = ApiAdapter.retrofit.searchScientificName(api_key, scientificName)
                val responseBody = response.body()
                val imageUrl = responseBody?.data?.get(0)?.imageUrl

                //set the defaulte picture if call is not responsive or incorrect
                if (imageUrl != null && context != null) {
                    Glide.with(context).asBitmap().load(imageUrl).submit().get()
                }else {
                    BitmapFactory.decodeResource(context?.resources, R.drawable.default_picture)
                }
            }catch (e:Exception){
                BitmapFactory.decodeResource(context?.resources, R.drawable.default_picture)
            }
        }
    }
    suspend fun fixData(plant : Biljka) : Biljka{
        return withContext(Dispatchers.IO){
            //Make new plant
            var returnPlant = Biljka(
                plant.naziv,
                plant.porodica,
                plant.medicinskoUpozorenje,
                plant.medicinskeKoristi,
                plant.profilOkusa,
                plant.jela,
                plant.klimatskiTipovi,
                plant.zemljisniTipovi
            )

            //Take a scientific name from the plant
            var imeBiljke = returnPlant.naziv
            var scientificName = ""
            scientificName = extractContentInBrackets(imeBiljke)

            //Get family name and replace if needed
            var scientificResponse = ApiAdapter.retrofit.searchScientificName(api_key, scientificName)
            val scientificResponseBody = scientificResponse.body()
            var family_name = scientificResponseBody?.data?.get(0)?.family
            if(returnPlant.porodica != family_name){
                if (family_name != null) {
                    returnPlant.porodica = family_name
                }
            }

            //Get plant id and do the search by ID, then check if it is edible
            val id = scientificResponseBody?.data?.get(0)?.id
            if (id != null) {
                val idResponse = ApiAdapter.retrofit.searchById(id, api_key)
                val idResponseBody = idResponse.body()
                val edible = idResponseBody?.data?.main_species?.edible
                if (edible == false) {
                    val subString = "NIJE JESTIVO"
                    if (!returnPlant.medicinskoUpozorenje.contains(subString)) {
                        returnPlant.medicinskoUpozorenje = returnPlant.medicinskoUpozorenje + " " + subString
                        returnPlant.jela = emptyList()
                    }
                }

            //Fixing if plant is toxic
                val toxicity = idResponseBody?.data?.main_species?.specifications?.toxicity
                if(toxicity != null){
                    val subString = "TOKSIČNO"
                    if (!returnPlant.medicinskoUpozorenje.contains(subString)) {
                        returnPlant.medicinskoUpozorenje = returnPlant.medicinskoUpozorenje + " " + subString
                    }
                }

            //Fixing soil_texture if needed
                val soil_texture = idResponseBody?.data?.main_species?.growth?.soil_texture
                var soilList : ArrayList<Zemljiste> = ArrayList()
                if(soil_texture != null){
                    if(soil_texture == 1 || soil_texture == 2){
                        soilList.add(Zemljiste.GLINENO)
                    }
                    else if(soil_texture == 3 || soil_texture == 4){
                        soilList.add(Zemljiste.PJESKOVITO)
                    }
                    else if(soil_texture == 5 || soil_texture == 6){
                        soilList.add(Zemljiste.ILOVACA)
                    }
                    else if(soil_texture == 7 || soil_texture == 8){
                        soilList.add(Zemljiste.CRNICA)
                    }
                    else if(soil_texture == 9){
                        soilList.add(Zemljiste.SLJUNOVITO)
                    }
                    else if(soil_texture == 10){
                        soilList.add(Zemljiste.KRECNJACKO)
                    }
                returnPlant.zemljisniTipovi = soilList
                }

            //Fixing climate type if needed
                val light = idResponseBody?.data?.main_species?.growth?.light
                val atmosperic_humidity = idResponseBody?.data?.main_species?.growth?.atmospheric_humidity
                var change = false
                var climateList = ArrayList<KlimatskiTip>()
                if(light in 6..9 && atmosperic_humidity in 1..5){
                    climateList.add(KlimatskiTip.SREDOZEMNA)
                    change = true;
                }
                if(light in 8..10 && atmosperic_humidity in 7..10){
                    climateList.add(KlimatskiTip.TROPSKA)
                    change = true;
                }
                if(light in 6..9 && atmosperic_humidity in 5..8){
                    climateList.add(KlimatskiTip.SUBTROPSKA)
                    change = true;
                }
                if(light in 4..7 && atmosperic_humidity in 3..7){
                    climateList.add(KlimatskiTip.UMJERENA)
                    change = true;
                }
                if(light in 7..9 && atmosperic_humidity in 1..2){
                    climateList.add(KlimatskiTip.SUHA)
                    change = true;
                }
                if(light in 0..5 && atmosperic_humidity in 3..7){
                    climateList.add(KlimatskiTip.PLANINSKA)
                    change = true;
                }
                if(change){
                    returnPlant.klimatskiTipovi = climateList
                }
            }
            return@withContext returnPlant
        }
    }

    private fun extractContentInBrackets(input: String): String {
        val regex = Regex("\\(([^)]+)\\)")
        val matchResult = regex.find(input)
        return matchResult?.groups?.get(1)?.value ?: ""
    }
}
