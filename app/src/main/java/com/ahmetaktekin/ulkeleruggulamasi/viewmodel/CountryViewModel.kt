package com.ahmetaktekin.ulkeleruggulamasi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ahmetaktekin.ulkeleruggulamasi.model.Country
import com.ahmetaktekin.ulkeleruggulamasi.service.CountryDatabase
import kotlinx.coroutines.launch
import java.util.*

class CountryViewModel(application:Application) : BaseViewModel(application) {
    //room access yapacağımız için veritabanına erişeceğimiz için böyle tanımlamalar yaptık
    val countryLiveData = MutableLiveData<Country>()

    fun getDataFromRoom(uuid: Int){
        launch {
            val dao = CountryDatabase(getApplication()).countryDao()
            val country = dao.getOnlyCountry(uuid)
            countryLiveData.value = country
        }
        
    }

}