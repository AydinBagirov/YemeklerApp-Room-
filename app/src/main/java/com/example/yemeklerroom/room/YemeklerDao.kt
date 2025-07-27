package com.example.yemeklerroom.room

import androidx.room.*
import com.example.yemeklerroom.entity.Yemekler

@Dao
interface YemeklerDao {
    @Query("SELECT * FROM yemekler")
    suspend fun tumYemekleriGetir(): List<Yemekler>

    @Insert
    suspend fun ekleYemekler(yemek: Yemekler)
    @Delete
    suspend fun silYemekler(yemek: Yemekler)

    @Update
    suspend fun guncelleYemekler(yemek: Yemekler)
}