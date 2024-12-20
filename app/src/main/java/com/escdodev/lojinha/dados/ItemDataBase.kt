package com.escdodev.lojinha.dados


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase: RoomDatabase(){

    abstract fun itemDao(): ItemDao

    companion object{
        fun abrirBancoDeDados(context: Context): ItemDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ItemDatabase::class.java, "item.db"
            ).build()
        }
    }
}
