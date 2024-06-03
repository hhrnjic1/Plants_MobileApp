package ba.unsa.etf.rma.rma_projekat

import com.google.gson.annotations.SerializedName

data class Flower(
    @SerializedName("color") val color: List<String>?
)
