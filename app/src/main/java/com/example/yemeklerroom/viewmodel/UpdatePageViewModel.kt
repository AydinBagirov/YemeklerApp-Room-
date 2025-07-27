package com.example.yemeklerroom.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.yemeklerroom.entity.Yemekler
import com.example.yemeklerroom.repository.YemeklerDaoRepository

class UpdatePageViewModel(application: Application): AndroidViewModel(application) {
    var yemeklerListesi = MutableLiveData<List<Yemekler>>()
    var yRepo = YemeklerDaoRepository(application)

    init {
        yemeklerListesi = yRepo.yemekleriGetir()
    }

    fun yemekGuncelle(yemek_id: Int, yemek_ad: String, yemek_fiyat: Int){
        yRepo.yemekGuncelle(yemek_id, yemek_ad, yemek_fiyat)
    }
}