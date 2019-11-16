package me.yokeyword.demo2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import me.yokeyword.demo2.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnMultipleFragmentActivity.setOnClickListener {
            startActivity(Intent(this,MultipleFragmentActivity::class.java))
        }
    }
}
