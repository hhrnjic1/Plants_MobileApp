package ba.unsa.etf.rma.rma_projekat

import com.google.gson.annotations.SerializedName

data class GetBiljkaResponse(
    @SerializedName("data") val data : List<BiljkaSearchClass>,
    /*@SerializedName("links") val links : List<Links>,
    @SerializedName("meta") val meta : List<Meta>*/
)
