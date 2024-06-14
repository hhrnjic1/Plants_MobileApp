package ba.unsa.etf.rma.rma_projekat

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream
import java.lang.reflect.Type

class Converters {

    private val gson = Gson()

    @TypeConverter
    fun fromMedicinskaKoristList(value: List<MedicinskaKorist>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toMedicinskaKoristList(value: String): List<MedicinskaKorist>? {
        val type = object : TypeToken<List<MedicinskaKorist>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromProfilOkusaBiljke(value: ProfilOkusaBiljke?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toProfilOkusaBiljke(value: String): ProfilOkusaBiljke? {
        return gson.fromJson(value, ProfilOkusaBiljke::class.java)
    }

    @TypeConverter
    fun fromKlimatskiTipList(value: List<KlimatskiTip>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toKlimatskiTipList(value: String): List<KlimatskiTip>? {
        val type = object : TypeToken<List<KlimatskiTip>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromZemljisteList(value: List<Zemljiste>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toZemljisteList(value: String): List<Zemljiste>? {
        val type = object : TypeToken<List<Zemljiste>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromStringList(value: List<String>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String): List<String>? {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): String? {
        if (bitmap == null) return null

        // Scale bitmap
        val scaledBitmap = scaleBitmap(bitmap)

        // Compress bitmap to JPEG
        val outputStream = ByteArrayOutputStream()
        scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        val byteArray = outputStream.toByteArray()

        // Encode to Base64
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    @TypeConverter
    fun toBitmap(encodedString: String?): Bitmap? {
        if (encodedString.isNullOrEmpty()) return null

        // Decode Base64
        val byteArray = Base64.decode(encodedString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    private fun scaleBitmap(bitmap: Bitmap): Bitmap {
        var width = bitmap.width
        var height = bitmap.height

        val ratioBitmap = width.toFloat() / height.toFloat()
        val ratioMax = 200.toFloat() / 200.toFloat()

        var finalWidth = 200
        var finalHeight = 200
        if (ratioMax > ratioBitmap) {
            finalWidth = (200.toFloat() * ratioBitmap).toInt()
        } else {
            finalHeight = (200.toFloat() / ratioBitmap).toInt()
        }

        return Bitmap.createScaledBitmap(bitmap, finalWidth, finalHeight, true)
    }
}


