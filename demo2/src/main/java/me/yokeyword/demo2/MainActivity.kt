package me.yokeyword.demo2

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.networktools.ping.NetworkTools
import kotlinx.android.synthetic.main.activity_main.*
import me.yokeyword.fragmentation.Fragmentation
import me.yokeyword.fragmentation.debug.DebugStackDelegate
import java.util.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    private var mDebugStackDelegate: DebugStackDelegate? = null
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDebugStackDelegate = DebugStackDelegate(this)
        mDebugStackDelegate?.onCreate(2)
        btnMultipleFragmentActivity.setOnClickListener {
            startActivity(Intent(this, MultipleFragmentActivity::class.java))
        }
        Fragmentation.builder()
                .stackViewMode(Fragmentation.BUBBLE)
                .debug(true)
                .install()
    }

}
