package com.ahmetaktekin.ulkeleruggulamasi.service

import com.ahmetaktekin.ulkeleruggulamasi.model.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryAPI {
    //BURADA RETROFİTTRE NE İŞLEM YAPACAĞIMIZI SÖYLERİZ
    //GET-POST İŞLEMLERİ
    //VERİLERİ ÇEKERKEN GET VERİLERİ DEĞİŞTİRİRKEN POST
    //https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    // BASE_URL = https://raw.githubusercontent.com/  EXT = atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries():Single<List<Country>>
}