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

            //Checking if plant is toxic
                val toxicity = idResponseBody?.data?.main_species?.specifications?.toxicity
                if(toxicity != null){
                    val subString = "TOKSIČNO"
                    if (!returnPlant.medicinskoUpozorenje.contains(subString)) {
                        returnPlant.medicinskoUpozorenje = returnPlant.medicinskoUpozorenje + " " + subString
                    }
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
