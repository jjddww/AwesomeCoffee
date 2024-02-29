package com.jjddww.awesomecoffee.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jjddww.awesomecoffee.data.model.Advertisement


@Database(entities = [Advertisement::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
//    abstract fun advertisementDao(): AdvertisementDao
//
//    companion object{
//        @Volatile private var instance: AppDatabase? = null
//
//        fun getInstance(context: Context): AppDatabase {
//            return instance ?: synchronized(this) {
//                instance ?: buildDatabase(context).also { instance = it }
//            }
//        }
//
//        private fun buildDatabase(context: Context): AppDatabase {
//            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
//                .addCallback(
//                    object : RoomDatabase.Callback() {
//                        override fun onCreate(db: SupportSQLiteDatabase) {
//                            super.onCreate(db)
//                            val request = OneTimeWorkRequestBuilder<AdvertisementDatabaseWorker>()
//                                .setInputData(workDataOf(KEY_FILENAME to ADVERTISEMENT_DATA_FILE))
//                                .build()
//                            WorkManager.getInstance(context).enqueue(request)
//                        }
//                    }
//                )
//                .build()
//        }
//    }
}