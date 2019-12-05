package me.yokeyword.demo2

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import dalvik.system.DexClassLoader
import me.yokeyword.demo2.fragments.HomeFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity
import java.io.File
import java.util.*


class MultipleFragmentActivity : SwipeBackActivity() {
    private var mDexClassLoader:DexClassLoader?=null
    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootFragment = FrameLayout(this)
        rootFragment.id = 0x0000231
        setContentView(rootFragment)
        val dexOutputDir2: File = getDir("dex2", Context.MODE_PRIVATE)
        this.mDexClassLoader = DexClassLoader("/data/user/0/me.yokeyword.demo2/app_dex/Generated_-1290568858.jar", dexOutputDir2.absolutePath, null, super.getClassLoader())

        if (findFragment(HomeFragment::class.java) ==null){
            val newInstance = HomeFragment.newInstance()
//            loadRootFragment(rootFragment.id, HomeFragment.newInstance())
        }
//        val startTime = System.currentTimeMillis()
//        val fragment = DexMakerUtils.generateClass<Fragment>(this,DemoFragment::class.java, "com.huqs.dex.MockFragment_huqs234")
//        println("---------->fragment:$fragment"+"------耗时:"+(System.currentTimeMillis()- startTime))
        val fg = classLoader.loadClass("com.huqs.dex.MockFragment_huqs234").constructors[0].newInstance(123) as Fragment

        println("-------》fg："+fg)

        supportFragmentManager.apply {
            beginTransaction().replace(rootFragment.id,fg).commitAllowingStateLoss()
        }

    }

    override fun getClassLoader(): ClassLoader {
      if (mDexClassLoader != null){
          return mDexClassLoader!!
      }else{
          return super.getClassLoader()
      }
    }
    fun setWho(fragment:Fragment,tag:String){
        var tempClass:Class<*> = fragment.javaClass
        while (tempClass.name.toLowerCase() != "java.lang.object") {
            try {
                val declaredField = tempClass.getDeclaredField("mWho")
                println("---------->mWho:"+declaredField)
                declaredField.isAccessible=true
                declaredField.set(fragment,tag)
                return
            }catch (e: Exception){
                tempClass = tempClass.superclass as Class<*>
            }
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        // 设置横向(和安卓4.x动画相同)
        return DefaultHorizontalAnimator()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
