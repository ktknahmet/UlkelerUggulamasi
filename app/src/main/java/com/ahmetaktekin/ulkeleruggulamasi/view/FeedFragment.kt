package com.ahmetaktekin.ulkeleruggulamasi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmetaktekin.ulkeleruggulamasi.R
import com.ahmetaktekin.ulkeleruggulamasi.adapter.CountryAdapter
import com.ahmetaktekin.ulkeleruggulamasi.viewmodel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {
    private lateinit var viewModel: FeedViewModel
    private val countryAdapter = CountryAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refleshData() //FeedViewModel sayfasındaki fonksiyon
        countryList.layoutManager = LinearLayoutManager(context) //alt alta göstermek için yazdık
        countryList.adapter = countryAdapter
//sayfayı yenilediğimiz zaman yapılacak işlemler
        swiperefleshLayout.setOnRefreshListener {
            countryList.visibility = View.GONE
            countryError.visibility = View.GONE
            countryLoading.visibility = View.VISIBLE
            viewModel.refleshData()
            swiperefleshLayout.isRefreshing = false
        }
        getDataLiveObserver()
    }


   private  fun getDataLiveObserver(){
        viewModel.countrise.observe(viewLifecycleOwner, Observer { countries ->
            countries?.let {
                countryList.visibility = View.VISIBLE
                countryAdapter.updateCountryList(countries)
            }
        })
        viewModel.countryError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if(it){
                  countryError.visibility = View.VISIBLE
                }else{
                    countryError.visibility = View.GONE
                }
            }

        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if(it){
                    countryLoading.visibility = View.VISIBLE
                    countryError.visibility = View.GONE
                    countryList.visibility = View.GONE
                }else{
                    countryLoading.visibility = View.GONE
                }
            }

        })
    }

}