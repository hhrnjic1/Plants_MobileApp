package ba.unsa.etf.rma.rma_projekat

import com.google.gson.annotations.SerializedName

data class BiljkaSearchClassID(
    @SerializedName("main_species") val main_species: MainSpecies?
)
