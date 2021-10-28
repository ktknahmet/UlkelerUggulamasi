package com.ahmetaktekin.ulkeleruggulamasi.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ahmetaktekin.ulkeleruggulamasi.model.Country

@Database(entities = arrayOf(Country::class),version = 1)
abstract class CountryDatabase : RoomDatabase() {
    abstract  fun countryDao() : CountryDAO
    //sadece tek bir object oluşturmak istiyoruz
    //singelton = içinden tek bir object oluşturulan sınıf
    //eğer obje yoksa oluştururuz varsa var olan objeyi heryerden ulaşabiliyoruz
    //companion object yazarız ki heryerden objeye ulaşalım

    companion object {
        @Volatile private var instance : CountryDatabase? = null
        //@volatile olarak tanımlarsak diğer threadlerde bunu görebilir
        private val lock = Any()
        operator  fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also {
                //bunu yap üstüne alsodaki şeyleri yap
                instance=it
            }
        }

        private fun makeDatabase(context:Context) = Room.databaseBuilder(
            context.applicationContext,CountryDatabase::class.java,"countrydatabase"
        ).build()
    }
}