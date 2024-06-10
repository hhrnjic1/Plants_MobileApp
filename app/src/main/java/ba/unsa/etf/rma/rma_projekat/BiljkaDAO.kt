package ba.unsa.etf.rma.rma_projekat

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface BiljkaDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveBiljka(biljka: Biljka): Boolean

    @Update
    suspend fun fixOfflineBiljka(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addImage(idBiljke :Int ,biljkaBitmap: BiljkaBitmap): Boolean

    @Query("SELECT * FROM biljka")
    suspend fun getAllBiljkas(): List<Biljka>

    @Delete
    suspend fun clearData()
}