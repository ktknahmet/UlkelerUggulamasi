package com.ahmetaktekin.ulkeleruggulamasi.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ahmetaktekin.ulkeleruggulamasi.model.Country
@Dao
interface CountryDAO {
    @Insert
    suspend fun insertAll(vararg countries:Country) :List<Long>
    //insert -> insert into gibi
    //suspend -> corutine içine çağırılır fonksiyonları durdurup devam ettirmeye çalıştıran fonk
    //vararg -> istediğimiz zaman istediğimiz kadar elemanı ekler
    //list<long> primary key döndürür
    @Query("Select * From Country")
    suspend fun getAllCountries() :List<Country>

    @Query("Select * From Country Where uuid=:countryId")
    suspend fun getOnlyCountry(countryId:Int) :Country

    @Query("Delete From Country")
    suspend fun deleteAllCountries()
}