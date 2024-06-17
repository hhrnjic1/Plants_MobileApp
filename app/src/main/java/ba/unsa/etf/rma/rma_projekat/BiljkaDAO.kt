package ba.unsa.etf.rma.rma_projekat

import android.graphics.Bitmap
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface BiljkaDAO {
        //Main functions
        @Transaction
        suspend fun saveBiljka(biljka: Biljka): Boolean {
                return insertBiljka(biljka) > 0
        }

        @Transaction
        suspend fun fixOfflineBiljka():Int {
                var numOfUpdates = 0
                var filterList = getBiljkeOnlineCheckedZero()
                for (item in filterList) {
                        var fixedBiljka = TrefleDAO().fixData(item)
                        fixedBiljka.id = item.id
                        fixedBiljka.onlineChecked = true;
                        updateBiljka(fixedBiljka)
                        numOfUpdates++
                }
                return numOfUpdates
        }

        @Transaction
        suspend fun addImage(biljkaId: Int, biljkaBitmap: Bitmap): Boolean {
                return insertImage(BiljkaBitmap(null, biljkaId,biljkaBitmap)) > 0
        }

        @Query("SELECT * FROM Biljka")
        suspend fun getAllBiljkas(): List<Biljka>

        @Query("DELETE FROM Biljka")
        suspend fun clearData()

        //Resto of functions
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertBiljka(biljka: Biljka):Long

        @Query("SELECT * FROM Biljka WHERE onlineChecked = 0")
        suspend fun getBiljkeOnlineCheckedZero(): List<Biljka>

        @Update
        suspend fun updateBiljka(biljka: Biljka)

        @Insert(onConflict = OnConflictStrategy.IGNORE )
        suspend fun insertImage(biljkaBit : BiljkaBitmap): Long

        @Query("SELECT bitmap FROM BiljkaBitmap WHERE idBiljke = :id")
        suspend fun getBitmapById(id: Int) : Bitmap
}