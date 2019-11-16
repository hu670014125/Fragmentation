package me.yokeyword.demo2.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment
import com.gyf.immersionbar.ImmersionBar
import kotlinx.android.synthetic.main.toolbar.view.*
import me.yokeyword.demo2.R
import me.yokeyword.fragmentation.ISupportFragment
import java.lang.Exception


open class BaseSwipeBackFragment : SwipeBackFragment() {
    protected lateinit var rootView: LinearLayout
    protected lateinit var toolbar: View
    private var contentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.rootView = LinearLayout(this.context!!)
        this.rootView.orientation = LinearLayout.VERTICAL
        this.rootView.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        this.toolbar = LayoutInflater.from(this.context).inflate(R.layout.toolbar, null)
        this.rootView.addView(this.toolbar)

        // 设置toolbar
        if (activity is AppCompatActivity) {
            toolbar.toolbar.title = ""
            toolbar.toolbar.subtitle = ""
            (activity as AppCompatActivity).setSupportActionBar(toolbar.toolbar)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled())
            setHasOptionsMenu(true)
        }


        // 设置沉浸式
        ImmersionBar.with(this)
                .titleBar(toolbar)
                .statusBarDarkFont(displayStatusBarDarkFont(),0.5f)
                .init()

    }
    final  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        this.contentView?.let {
            this.rootView.addView(it)
        }
        return attachToSwipeBack(this.rootView)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                _mActivity.onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun setTitle(title:CharSequence) {
        this.toolbar.toolbar_title.text = title
    }

    protected fun setContentView(view: View) {
        this.contentView = view
    }

    protected fun setContentView(@LayoutRes layoutResID: Int) {
        this.contentView = LayoutInflater.from(this.context).inflate(layoutResID, null)
    }

    protected open fun displayHomeAsUpEnabled(): Boolean {
        return true
    }
    protected open fun displayStatusBarDarkFont(): Boolean {
        return false
    }

    /**
     * 设置toolbar 背景颜色
     * @param color ColorInt
     */
    fun setNavigationBarBackgroundColor(@ColorInt color:Int){
        this.toolbar.toolbar.setBackgroundColor(color)
    }

    fun pushFragment(fragment: ISupportFragment){
        super.start(fragment)
    }

    fun pushToFragment(fragment: ISupportFragment,include:Boolean = false){
//        super.startWithPopTo(fragment)
    }

    fun pushRedirectFragment(fragment: ISupportFragment){
        super.startWithPop(fragment)
    }

    fun pushReLaunchFragment(fragment: ISupportFragment){

    }

    fun popFragment(){
        super.pop()
    }

    fun popToFragment(fragment: ISupportFragment,include:Boolean = false){

    }

    /**
     * 通过反射设置Fragment中的tag字段
     * @param tag
     */
    @SuppressLint("DefaultLocale")
    fun setTag(tag:String){
        var tempClass:Class<*> = this.javaClass
        while (tempClass.name.toLowerCase() != "java.lang.object") {
            try {
                val declaredField = tempClass.getDeclaredField("mTag")
                declaredField.isAccessible=true
                declaredField.set(this,tag)
                return
            }catch (e: Exception){
                tempClass = tempClass.superclass as Class<*>
            }
        }
    }

    /**
     * 设置toolbar 字体颜色,包括：'返回键'、'标题'
     * @param color ColorInt
     */
//    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setNavigationBarTextColor(@ColorInt color:Int){
//        // 设置标题文字颜色
//        this.toolbar.toolbar_title.setTextColor(color)
//        // 设置返回键图标颜色
//        this.toolbar.toolbar.navigationIcon?.setTint(color)
//        // 设置toolbar更多menu的图标颜色
//        this.toolbar.toolbar.overflowIcon?.setTint(color)
//        val menuItems = mutableListOf<MenuItem>()
//
//        for (index in 0 until this.toolbar.toolbar.menu.size()){
//           val menu= toolbar.toolbar.menu.getItem(index);
//            menu.icon?.setTint(color)
//            val  spanString = SpannableString(menu.title)
//
//            spanString.setSpan(ForegroundColorSpan(color),0,spanString.length,0)
//            menu.title = spanString
//        }

    }


}

