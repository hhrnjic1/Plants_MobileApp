package ba.unsa.etf.rma.rma_projekat

import com.google.gson.annotations.SerializedName

data class BiljkaSearchClass(
    @SerializedName("id") val id: Int,
    @SerializedName("common_name") val commonName: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("scientific_name") val scientificName: String,
    @SerializedName("year") val year: Int,
    @SerializedName("bibliography") val bibliography: String,
    @SerializedName("author") val author: String,
    @SerializedName("status") val status: String,
    @SerializedName("rank") val rank: String,
    @SerializedName("family_common_name") val familyCommonName: String,
    @SerializedName("genus_id") val genusId: Int,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("synonyms") val synonyms: List<String>,
    @SerializedName("genus") val genus: String,
    @SerializedName("family") val family: String,
    @SerializedName("links") val links: Links,
    @SerializedName("edible") val edible: Boolean?,
    @SerializedName("main_species") val main_species: MainSpecies
)

