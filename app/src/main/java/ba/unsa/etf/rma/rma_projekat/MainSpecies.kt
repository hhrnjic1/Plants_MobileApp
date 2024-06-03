package ba.unsa.etf.rma.rma_projekat

import com.google.gson.annotations.SerializedName

data class MainSpecies(
    @SerializedName("edible") val edible : Boolean?,
    @SerializedName("specifications") val specifications: Specifications?,
    @SerializedName("growth") val growth: Growth?,
    @SerializedName("flower") val flower: Flower?
)
