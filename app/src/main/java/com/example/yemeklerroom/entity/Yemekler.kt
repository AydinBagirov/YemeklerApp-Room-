package com.example.yemeklerroom.entity

import androidx.room.*
import org.jetbrains.annotations.NotNull

@Entity(tableName = "yemekler")
data class Yemekler(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("yemek_id") @NotNull var yemek_id: Int,
    @ColumnInfo("yemek_ad") @NotNull var yemek_ad: String,
    @ColumnInfo("yemek_fiyat") @NotNull var yemek_fiyat: Int
)
