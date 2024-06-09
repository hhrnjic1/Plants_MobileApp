package ba.unsa.etf.rma.rma_projekat

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "biljka_bitmap",
    foreignKeys = [ForeignKey(
        entity = Biljka::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("biljkaId"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["biljkaId"])]
)
data class BiljkaBitmap(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "biljkaId") val biljkaId: Int,
    @ColumnInfo(name = "bitmap") val bitmap: Bitmap
)
