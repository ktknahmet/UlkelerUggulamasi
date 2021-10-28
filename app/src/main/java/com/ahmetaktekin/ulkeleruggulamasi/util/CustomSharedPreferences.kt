package com.ahmetaktekin.ulkeleruggulamasi.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class CustomSharedPreferences {
    companion object{
        private val PREFERENCESTIME="preferences_time"
        private var sharedPreferences :SharedPreferences?=null
        @Volatile private var instance : CustomSharedPreferences?=null
        private val lock=Any()
        operator fun invoke(context: Context) :CustomSharedPreferences =
            instance ?: synchronized(lock){
                instance ?: makeCustomSharedPreferences(context).also {
                    instance=it
                }
            }
        private fun makeCustomSharedPreferences(context:Context):CustomSharedPreferences{
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            return CustomSharedPreferences()

        }
    }
    fun saveTime(time:Long){
        sharedPreferences?.edit(commit = true){
            putLong(PREFERENCESTIME,time)
        }
    }
    fun getTime()= sharedPreferences?.getLong(PREFERENCESTIME,0)
}
//shared preferences null oluşturduk tek bir instance oldu obje varmı yokmu kontrol ettik
//eğer obje varsa instance döndürecek yoksa seknolije şekilde işlem yapılacak shared preferences oluşturulacak
