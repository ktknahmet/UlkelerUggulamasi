package com.ahmetaktekin.ulkeleruggulamasi.viewmodel

import android.app.Application

import android.widget.Toast
import androidx.lifecycle.MutableLiveData

import com.ahmetaktekin.ulkeleruggulamasi.model.Country
import com.ahmetaktekin.ulkeleruggulamasi.service.CountryAPIService
import com.ahmetaktekin.ulkeleruggulamasi.service.CountryDatabase
import com.ahmetaktekin.ulkeleruggulamasi.util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application):BaseViewModel(application) {
    private val countryAPIService = CountryAPIService()
    private val disposable = CompositeDisposable()
    private var customPreferences = CustomSharedPreferences(getApplication())
    private var refleshTime = 10 * 60 * 1000 * 1000 * 1000L //nanosaniyeyi alma 10dk verir nanosaniye cinsinden
    val countrise = MutableLiveData<List<Country>>()
    val countryError = MutableLiveData<Boolean>()
    val countryLoading = MutableLiveData<Boolean>()

    fun refleshData(){
     val updateTime = customPreferences.getTime()
        if(updateTime!=null && updateTime !=0L && System.nanoTime()-updateTime<refleshTime){
            //eğer aradaki fark 10 dakikayı geçtiyse verileri api'den çeksin
            getDataFromSqlite()
        }else{
            getDataFromAPI()
        }
    }
    private fun getDataFromSqlite(){
        launch {
            val countries = CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(),"SQLİTEDEN VERİLER GELDİ",Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromAPI(){
       countryLoading.value = true
        disposable.add(
            countryAPIService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object :DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                      storeInSqlite(t)
                        Toast.makeText(getApplication(),"APİDEN VERİLER GELDİ",Toast.LENGTH_SHORT).show()
                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value = false
                        countryError.value = true
                        e.printStackTrace()

                    }

                })
        )
    }
    private fun showCountries(countrylist :List<Country>){
        countrise.value = countrylist
        countryError.value = false
        countryLoading.value = false
    }
    private fun storeInSqlite(list:List<Country>){
        //alınan verileri sqlite kaydeder
        //couritineleri kullanırız
        launch {
         val dao = CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            val listLong = dao.insertAll(*list.toTypedArray())
            //elemanları tek tek alırız
            var i = 0
            while(i<list.size){
                list[i].uuid = listLong[i].toInt()
                //döndürülen longu uuid olarak tanımladık
                i += 1
            }
            showCountries(list)
        }
        customPreferences.saveTime(System.nanoTime())

    }
}