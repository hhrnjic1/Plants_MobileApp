package ba.unsa.etf.rma.rma_projekat

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("plants/")
    suspend fun searchScientificName(@Query("token") apiKey : String, @Query("filter[scientific_name]") scientific_name : String) : Response<GetBiljkaResponse>

    @GET("plants/{id}/")
    suspend fun searchById(@Path("id") id : Int, @Query("token") apiKey: String) : Response<GetBiljkaResponseID>

    @GET("plants/search")
    suspend fun searchByFlowerColorNew(@Query("q") q : String, @Query("filter[flower_color]") flower : String, @Query("token") apiKey: String) : Response<GetBiljkaResponse>
}