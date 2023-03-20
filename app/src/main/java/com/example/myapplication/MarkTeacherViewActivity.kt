package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.adapter.ScreenSlidePagerAdapter

class MarkTeacherViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mark_teacher_view)

        val viewPager = findViewById<ViewPager2>(R.id.markStudentCarousel)
        viewPager.adapter = ScreenSlidePagerAdapter(this)

    }
}