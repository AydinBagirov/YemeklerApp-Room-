package com.example.yemeklerroom.room

import android.content.Context
import androidx.room.*
import com.example.yemeklerroom.entity.Yemekler

@Database(entities = [Yemekler::class], version = 1)
abstract class Veritabani: RoomDatabase() {
    abstract fun yemeklerDao(): YemeklerDao

    companion object{
        var INSTANCE: Veritabani? = null
        fun veritabaniErisim(context: Context): Veritabani?{
            if (INSTANCE == null){
                synchronized(Veritabani::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        Veritabani::class.java,
                        "yemekler.sqlite"
                    ).createFromAsset("yemekler.sqlite")
                        .build()
                }
            }
            return INSTANCE
        }
    }
}