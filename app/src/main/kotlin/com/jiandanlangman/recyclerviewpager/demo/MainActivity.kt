package com.jiandanlangman.recyclerviewpager.demo

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.jiandanlangman.recyclerviewpager.util.ViewPagerAutoScrollHelper
import com.jiandanlangman.recyclerviewpager.widget.RecyclerViewPager

class MainActivity : AppCompatActivity() {

    private lateinit var pagerAutoScrollHelper: ViewPagerAutoScrollHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        val viewPager = findViewById<RecyclerViewPager>(R.id.viewPager)
        val indexView = findViewById<TextView>(R.id.index)
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                indexView.text = "currentPosition:$position"
            }

        })
        val adapter = Adapter(ArrayList())
        viewPager.setAdapter(adapter)
        pagerAutoScrollHelper = ViewPagerAutoScrollHelper.hold(viewPager, true)
        pagerAutoScrollHelper.startAutoScroll(1000)
    }

    override fun onResume() {
        super.onResume()
        pagerAutoScrollHelper.onResume()
    }

    override fun onPause() {
        super.onPause()
        pagerAutoScrollHelper.onPause()
    }


    private inner class ViewHolder(itemView: ImageView) : RecyclerViewPager.ViewHolder(itemView)

    private inner class Adapter(private val datas: List<String>) : RecyclerViewPager.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(container: ViewGroup, viewType: Int): ViewHolder {
            return when (viewType) {
                0 -> ViewHolder(ImageView(this@MainActivity))
                else -> ViewHolder(ImageView(this@MainActivity))
            }
        }

        override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            if (getItemViewType(position) == 0)
                (viewHolder.itemView as ImageView).setImageResource(R.mipmap.ic_launcher_round)
        }

        override fun getItemCount() = 100

        override fun getItemViewType(position: Int) = position % 2

    }
}
