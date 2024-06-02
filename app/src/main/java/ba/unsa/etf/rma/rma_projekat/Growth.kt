package ba.unsa.etf.rma.rma_projekat

import com.google.gson.annotations.SerializedName

data class Growth(
    @SerializedName("soil_texture") val soil_texture: Int?
)
