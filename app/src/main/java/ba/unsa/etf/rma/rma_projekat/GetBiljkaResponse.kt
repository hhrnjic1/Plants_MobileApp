package ba.unsa.etf.rma.rma_projekat

import com.google.gson.annotations.SerializedName

data class GetBiljkaResponse(
    @SerializedName("data") val data : ArrayList<BiljkaSearchClass>,
    @SerializedName("meta") val meta : Meta?
)
