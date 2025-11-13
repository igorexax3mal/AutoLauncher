package com.appcarestudio.autolaucher.feature.items.ui.list

import android.os.Bundle
import com.appcarestudio.autolaucher.feature.items.R
import com.appcarestudio.autolaucher.feature.items.databinding.NavigationBinding


import com.appcarestudio.autolaucher.feature.items.ui.base.BaseFragment
import com.yandex.mapkit.MapKitFactory


import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentNavigation(override val layoutRes: Int = R.layout.navigation) :
    BaseFragment<NavigationBinding, FragmentNavigationViewModel>() {



    override fun subscribe() {

    }

    override fun setModel() {
        dataBinding?.model = model
    }


   // lateinit var newsAdapter : NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  MapKitFactory.initialize(requireContext())

       // setContentView(R.layout.activity_main)
     //   mapView = findViewById(R.id.mapview)
    }
    override fun setupViews() {
        super.setupViews()
     //  val  mapView = dataBinding?.mapview
       /*  newsAdapter = NewsAdapter(this.requireContext())
        dataBinding?.list?.adapter = newsAdapter

       newsAdapter!!.onAdapterItemClick = object : OnAdapterItemClick<Movie> {
            override fun onItemClick(item: Movie) {
                model?.openDetail(item)
            }
        }*/

    }

    override fun onStart() {
        super.onStart()
        //MapKitFactory.getInstance().onStart()
    //    dataBinding?.mapview?.onStart()
    }

    override fun onStop() {
    //    dataBinding?.mapview?.onStop()
      //  MapKitFactory.getInstance().onStop()
        super.onStop()
    }

 /*   fun updateAdapter(list: List<Movie>) {
        newsAdapter.dataList = list.toMutableList()
        newsAdapter.notifyDataSetChanged()
    }
*/
}