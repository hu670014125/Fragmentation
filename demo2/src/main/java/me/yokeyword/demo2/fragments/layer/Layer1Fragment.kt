package com.caih.cloudec.bridge.engine.demo2.layer

import android.os.Bundle

import android.view.Menu
import android.view.MenuInflater
import kotlinx.android.synthetic.main.fragment_layer.*
import me.yokeyword.demo2.R
import me.yokeyword.demo2.base.BaseSwipeBackFragment
import me.yokeyword.demo2.fragments.HomeFragment
import me.yokeyword.fragmentation.SupportFragment
import java.util.*

class Layer1Fragment : BaseSwipeBackFragment() {
    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle = Bundle()): SupportFragment {
            val args = Bundle()
            val fragment = Layer1Fragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_layer)
        setTitle("Layer1")
        val random = Random()
        val ranColor = -0x1000000 or random.nextInt(0x00ffffff)
        setNavigationBarBackgroundColor(ranColor)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnGoToHome.setOnClickListener {
            popTo(HomeFragment::class.java,CheckBox.isChecked)
        }
        btnAdd.setOnClickListener {
            pushFragment(newInstance())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}