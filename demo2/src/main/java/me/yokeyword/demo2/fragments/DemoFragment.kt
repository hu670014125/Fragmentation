package me.yokeyword.demo2.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlinx.android.synthetic.main.fragment_home.*
import me.yokeyword.demo2.R
import me.yokeyword.demo2.base.BaseSwipeBackFragment
import me.yokeyword.fragmentation.SupportFragment
import java.util.*



@SuppressLint("NewApi")
open class DemoFragment : BaseSwipeBackFragment() {
    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle = Bundle()): SupportFragment {
            val fragment = DemoFragment()
            fragment.arguments = bundle
            fragment.setStackTag("Demo${bundle.getInt("layer")}层")
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
        setTitle("Demo${arguments?.getInt("layer")}层")
        val random = Random()
        val ranColor = -0x1000000 or random.nextInt(0x00ffffff)
        setNavigationBarBackgroundColor(ranColor)
//        val textColor = -0x1000000 or random.nextInt(0x00ffffff)
//        setNavigationBarTextColor(textColor)
        Handler().postDelayed({
            val textColor = -0x1000000 or random.nextInt(0x00ffffff)
            setNavigationBarTextColor(textColor)
        },1000)



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_nav_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        toast(item.title)
        return super.onOptionsItemSelected(item)
    }

    override fun displayStatusBarDarkFont(): Boolean = true

    @SuppressLint("DefaultLocale")
    override fun onStart() {
        super.onStart()
//        println("-------------->tag:$tag")
        val layer = arguments?.getInt("layer")
        val bundle =  Bundle()
        bundle.putInt("layer",layer!!+1)
        btnNavigation.setOnClickListener {
            pushFragment(newInstance(bundle))
        }
        btnRedirect.setOnClickListener {
            pushRedirectFragment(newInstance())
        }
        btnReLaunch.setOnClickListener {
            startWithPopTo(newInstance(bundle),HomeFragment::class.java,true)
        }
        btnBack.setOnClickListener {
            popTo(HomeFragment::class.java,true)
        }

    }
}