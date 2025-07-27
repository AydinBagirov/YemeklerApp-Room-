package com.example.yemeklerroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.yemeklerroom.entity.Yemekler
import com.example.yemeklerroom.repository.YemeklerDaoRepository

class RecordPageViewModel(application: Application): AndroidViewModel(application) {
    var yemeklerListesi = MutableLiveData<List<Yemekler>>()
    var yRepo = YemeklerDaoRepository(application)

    init {
        yemeklerListesi = yRepo.yemekleriGetir()
    }

    fun yemekEkle(yemek_ad:String, yemek_fiyat: Int){
        yRepo.yemekEkle(yemek_ad, yemek_fiyat)
    }
}