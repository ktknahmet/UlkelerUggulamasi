package com.ahmetaktekin.ulkeleruggulamasi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import com.ahmetaktekin.ulkeleruggulamasi.R
import androidx.recyclerview.widget.RecyclerView
import com.ahmetaktekin.ulkeleruggulamasi.model.Country
import com.ahmetaktekin.ulkeleruggulamasi.util.downloadFromUrl
import com.ahmetaktekin.ulkeleruggulamasi.util.placeholderProgressBar
import com.ahmetaktekin.ulkeleruggulamasi.view.FeedFragmentDirections

import kotlinx.android.synthetic.main.item_countryrow.view.*


class CountryAdapter(val countryList:ArrayList<Country>):RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder (var view:View):RecyclerView.ViewHolder(view){


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
       //oluşturduğumuz layout ile adapter birbirine bağlama işlemini yaparız
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_countryrow,parent,false)
        return CountryViewHolder(view)
    }

       override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {

           holder.view.Countryisim.text= countryList[position].countryName
           holder.view.CountryKita.text = countryList[position].countryRegion
           holder.view.setOnClickListener {
            val action = FeedFragmentDirections.actionFeedFragmentToCountryFragment()
                action.setCountryUUID(countryList[position].uuid)
               Navigation.findNavController(it).navigate(action)
           }
           holder.view.CountryResim.downloadFromUrl(countryList[position].imageUrl,
               placeholderProgressBar(holder.view.context))


    }

    override fun getItemCount(): Int {
       return countryList.size
    }
    fun updateCountryList(newCountryList:List<Country>){
        countryList.clear()
        countryList.addAll(newCountryList)
        notifyDataSetChanged()//adapteri yenilemek için kullandığımız method
    }

}

