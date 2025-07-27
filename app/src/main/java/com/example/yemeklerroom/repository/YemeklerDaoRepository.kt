package com.example.yemeklerroom.repository

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.yemeklerroom.entity.Yemekler
import com.example.yemeklerroom.room.Veritabani
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class YemeklerDaoRepository(application: Application) {
    var yemeklerListesi = MutableLiveData<List<Yemekler>>()
    var vt: Veritabani
    init {
        vt = Veritabani.veritabaniErisim(application)!!
        yemeklerListesi = MutableLiveData()
    }

    fun yemekleriGetir():MutableLiveData<List<Yemekler>>{
        return yemeklerListesi
    }

    fun tumYemeklerAl(){
        val job: Job = CoroutineScope(Dispatchers.Main).launch {
            yemeklerListesi.value = vt.yemeklerDao().tumYemekleriGetir()
        }
    }

    fun yemekEkle(yemek_ad: String, yemek_fiyat: Int){
        val job: Job = CoroutineScope(Dispatchers.Main).launch {
            var yeniYemek = Yemekler(0, yemek_ad, yemek_fiyat)
            vt.yemeklerDao().ekleYemekler(yeniYemek)
        }
    }

    fun yemekSil(yemek_id: Int){
        val job: Job = CoroutineScope(Dispatchers.Main).launch {
            var silinenYemek = Yemekler(yemek_id, "",0)
            vt.yemeklerDao().silYemekler(silinenYemek)
        }
    }

    fun yemekGuncelle(yemek_id: Int, yemek_ad: String, yemek_fiyat: Int){
        val job: Job = CoroutineScope(Dispatchers.Main).launch {
            var guncellenYemek = Yemekler(yemek_id, yemek_ad, yemek_fiyat)
            vt.yemeklerDao().guncelleYemekler(guncellenYemek)
        }
    }
}