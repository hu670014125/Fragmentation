package me.yokeyword.demo2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import me.yokeyword.demo2.fragments.HomeFragment
import me.yokeyword.fragmentation.Fragmentation
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity

class MultipleFragmentActivity : SwipeBackActivity() {
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootFragment = FrameLayout(this)
        rootFragment.id = 0x0000231
        setContentView(rootFragment)
        if (findFragment(HomeFragment::class.java) ==null){
            loadRootFragment(rootFragment.id, HomeFragment.newInstance())
        }

    }
    override fun onCreateFragmentAnimator(): FragmentAnimator {
        // 设置横向(和安卓4.x动画相同)
        return DefaultHorizontalAnimator()
    }
}
