package ba.unsa.etf.rma.rma_projekat

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "BiljkaBitmap",
    foreignKeys = [ForeignKey(
        entity = Biljka::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("idBiljke"),
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["idBiljke"])]
)
data class BiljkaBitmap(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    @ColumnInfo(name = "idBiljke") val biljkaId: Int,
    @ColumnInfo(name = "bitmap") val bitmap: Bitmap
)
