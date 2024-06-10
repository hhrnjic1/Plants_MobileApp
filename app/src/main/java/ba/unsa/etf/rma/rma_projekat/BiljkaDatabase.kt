package ba.unsa.etf.rma.rma_projekat

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Biljka::class, BiljkaBitmap::class], version = 1/*, exportSchema = false*/)
abstract class BiljkaDatabase : RoomDatabase() {

    abstract fun biljkaDAO(): BiljkaDAO

    companion object {
        @Volatile
        private var INSTANCE: BiljkaDatabase? = null

        fun getDatabase(context: Context): BiljkaDatabase {
            if(INSTANCE == null){
                synchronized(BiljkaDatabase::class){
                    INSTANCE = buildRoomDB(context)
                }
            }
            return INSTANCE!!
        }

        private fun buildRoomDB(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BiljkaDatabase::class.java,
            "biljke-db"
        ).build()
    }
}