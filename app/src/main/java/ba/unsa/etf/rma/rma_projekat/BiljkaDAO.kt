package ba.unsa.etf.rma.rma_projekat

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BiljkaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBiljka(biljka: Biljka): Boolean

    @Query("UPDATE biljka SET onlineChecked = 1 WHERE id = (SELECT biljkaId FROM biljka_bitmap LIMIT 1)")
    suspend fun fixOfflineBiljka(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addImage(biljkaBitmap: BiljkaBitmap): Long

    @Query("SELECT * FROM biljka")
    suspend fun getAllBiljkas(): List<Biljka>

    @Query("DELETE FROM biljka")
    suspend fun clearData()
}