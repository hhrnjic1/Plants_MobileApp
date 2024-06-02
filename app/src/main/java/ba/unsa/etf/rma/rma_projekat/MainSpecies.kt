package ba.unsa.etf.rma.rma_projekat

import com.google.gson.annotations.SerializedName

data class MainSpecies(
    @SerializedName("edible") val edible : Boolean?,
    @SerializedName("specifications") val specifications: Specifications?
)
