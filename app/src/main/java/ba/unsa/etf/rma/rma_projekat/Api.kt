package ba.unsa.etf.rma.rma_projekat

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("plants")
    suspend fun searchScientificName(@Query("token") apiKey : String, @Query("filter[scientific_name]") scientific_name : String) : Response<GetBiljkaResponse>
}