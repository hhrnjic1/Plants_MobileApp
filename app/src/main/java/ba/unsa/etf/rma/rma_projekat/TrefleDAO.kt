package ba.unsa.etf.rma.rma_projekat

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class TrefleDAO (var context : Context) {
    private val api_key = "i6onuid2-5mJofP1RnuwGVoInzOlc2LqhdjCozLk34w"
    suspend fun getImage(plant : Biljka) : Bitmap{
        return withContext(Dispatchers.IO) {
            try{
                //first we take a scientific name from the pant
                var imeBiljke = plant.naziv
                var scientificName = ""
                scientificName = extractContentInBrackets(imeBiljke)

                //then we get a image_url form api call
                //var imageUrl = ""
                var response = ApiAdapter.retrofit.searchScientificName(api_key, scientificName)
                val responseBody = response.body()
                val imageUrl = responseBody?.data?.get(0)?.imageUrl
                //set the defaulte picture if call is not responsive or incorrect
                if (imageUrl != null) {
                    Glide.with(context).asBitmap().load(imageUrl).submit().get()
                }else {
                    BitmapFactory.decodeResource(context.resources, R.drawable.default_picture)
                }
            }catch (e:Exception){
                BitmapFactory.decodeResource(context.resources, R.drawable.default_picture)
            }
        }
    }

    private fun extractContentInBrackets(input: String): String {
        val regex = Regex("\\(([^)]+)\\)")
        val matchResult = regex.find(input)
        return matchResult?.groups?.get(1)?.value ?: ""
    }
}
