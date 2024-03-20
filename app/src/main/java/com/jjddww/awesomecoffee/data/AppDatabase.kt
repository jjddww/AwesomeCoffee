package com.jjddww.awesomecoffee.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jjddww.awesomecoffee.data.dao.CartDao
import com.jjddww.awesomecoffee.data.model.Cart
import com.jjddww.awesomecoffee.utilities.DATABASE_NAME


@Database(entities = [Cart::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun cartDao(): CartDao

    companion object{
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }

        fun destroyInstance(){
            instance = null
        }
    }
}