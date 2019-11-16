package me.yokeyword.demo2.fragments

import android.os.Bundle
import android.view.*
import com.caih.cloudec.bridge.engine.demo2.layer.Layer1Fragment
import kotlinx.android.synthetic.main.fragment_home.*
import me.yokeyword.demo2.R
import me.yokeyword.demo2.base.BaseSwipeBackFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment

class HomeFragment : BaseSwipeBackFragment() {
    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle = Bundle()): SupportFragment {
            val fragment = HomeFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)
        setTitle("Home")
        setStackTag("Home")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnLayer.setOnClickListener {
            start(Layer1Fragment.newInstance())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.bottom_nav_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        btnNavigation.setOnClickListener {
          val bundle =  Bundle()
            bundle.putInt("layer",1)
            pushFragment(DemoFragment.newInstance(bundle))
        }
        btnRedirect.setOnClickListener {
            pushRedirectFragment(newInstance())
        }

    }
}