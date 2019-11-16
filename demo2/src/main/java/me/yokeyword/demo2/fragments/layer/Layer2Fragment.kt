package com.caih.cloudec.bridge.engine.demo2.layer

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlinx.android.synthetic.main.fragment_layer.*
import me.yokeyword.demo2.R
import me.yokeyword.demo2.base.BaseSwipeBackFragment
import me.yokeyword.demo2.fragments.HomeFragment
import me.yokeyword.fragmentation.SupportFragment
import java.util.*

class Layer2Fragment : BaseSwipeBackFragment() {
    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle = Bundle()): SupportFragment {
            val args = Bundle()
            val fragment = Layer2Fragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_layer)
        setTitle("Layer2")
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