package com.example.yemeklerroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.yemeklerroom.entity.Yemekler
import com.example.yemeklerroom.repository.YemeklerDaoRepository

class MainPageViewModel(application: Application): AndroidViewModel(application) {
    var yemeklerListesi = MutableLiveData<List<Yemekler>>()
    var yRepo = YemeklerDaoRepository(application)

    init {
        yemekGetir()
        yemeklerListesi = yRepo.yemekleriGetir()
    }

    fun yemekGetir(){
        yRepo.tumYemeklerAl()
    }

    fun yemekEkle(yemek_ad:String, yemek_fiyat: Int){
        yRepo.yemekEkle(yemek_ad, yemek_fiyat)
    }

    fun yemekSil(yemek_id: Int){
        yRepo.yemekSil(yemek_id)
    }

    fun yemekGuncelle(yemek_id: Int, yemek_ad: String, yemek_fiyat: Int){
        yRepo.yemekGuncelle(yemek_id, yemek_ad, yemek_fiyat)
    }
}