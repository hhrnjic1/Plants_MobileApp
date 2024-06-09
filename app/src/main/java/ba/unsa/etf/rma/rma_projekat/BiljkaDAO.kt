package ba.unsa.etf.rma.rma_projekat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BiljkaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBiljka(biljka: Biljka): Boolean

    @Query("UPDATE biljka SET onlineChecked = :status WHERE id = :biljkaId")
    suspend fun updateBiljkaStatus(biljkaId: Int, status: Boolean): Int

    @Query("SELECT * FROM biljka WHERE onlineChecked = 1")
    suspend fun getAllCheckedBiljke(): List<Biljka>

    @Query("DELETE FROM biljka")
    suspend fun clearData()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addImage(biljkaBitmap: BiljkaBitmap): Long

    @Query("SELECT * FROM biljka")
    suspend fun getAllBiljkas(): List<Biljka>
}