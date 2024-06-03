package ba.unsa.etf.rma.rma_projekat

import com.google.gson.annotations.SerializedName

data class GetBiljkaResponseID(
    @SerializedName("data") val data : BiljkaSearchClassID,
    @SerializedName("meta") val meta : Meta?
)
