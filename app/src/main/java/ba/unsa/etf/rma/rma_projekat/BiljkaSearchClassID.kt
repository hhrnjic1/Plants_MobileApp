package ba.unsa.etf.rma.rma_projekat

import com.google.gson.annotations.SerializedName

data class BiljkaSearchClassID(
    @SerializedName("main_species") val main_species: MainSpecies?,
    @SerializedName("common_name") val commonName: String?,
    @SerializedName("scientific_name") val scientificName: String?
)
