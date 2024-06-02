package ba.unsa.etf.rma.rma_projekat

import com.google.gson.annotations.SerializedName

data class Specifications(
    @SerializedName("toxicity") val toxicity : String?
)
