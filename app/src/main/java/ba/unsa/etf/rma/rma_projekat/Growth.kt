package ba.unsa.etf.rma.rma_projekat

import com.google.gson.annotations.SerializedName

data class Growth(
    @SerializedName("soil_texture") val soil_texture: Int?,
    @SerializedName("light") val light: Int?,
    @SerializedName("atmospheric_humidity") val atmospheric_humidity: Int?
)
