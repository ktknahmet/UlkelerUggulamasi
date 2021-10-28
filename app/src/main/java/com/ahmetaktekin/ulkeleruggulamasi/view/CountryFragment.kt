package com.ahmetaktekin.ulkeleruggulamasi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ahmetaktekin.ulkeleruggulamasi.R
import com.ahmetaktekin.ulkeleruggulamasi.util.downloadFromUrl
import com.ahmetaktekin.ulkeleruggulamasi.util.placeholderProgressBar
import com.ahmetaktekin.ulkeleruggulamasi.viewmodel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*


class CountryFragment : Fragment() {
private var countryUuid = 0
    private lateinit var  viewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let{
            countryUuid=CountryFragmentArgs.fromBundle(it).countryUUID
        }
        viewModel = ViewModelProviders.of(this).get(CountryViewModel::class.java)
        viewModel.getDataFromRoom(countryUuid)
        observerLiveData()
    }

    private fun observerLiveData(){
       viewModel.countryLiveData.observe(viewLifecycleOwner, Observer { country->
           country?.let {
               countryname.text = country.countryName
               countrycapital.text = country.countryCapital
               countrycurrently.text = country.countryCurrencly
               countryregion.text = country.countryRegion
               countrylanguage.text = country.countryLanguage
               context?.let{
                   imageView.downloadFromUrl(country.imageUrl, placeholderProgressBar(it))
               }
           }

       })
    }
}