package com.jiandanlangman.recyclerviewpager.util

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.support.v4.view.ViewPager
import android.view.MotionEvent
import com.jiandanlangman.recyclerviewpager.widget.RecyclerViewPager

class ViewPagerAutoScrollHelper(private val viewPager: ViewPager, isPauseWhenTouch: Boolean) {

    companion object {
        fun hold(viewPager: ViewPager, isPauseWhenTouch: Boolean) = ViewPagerAutoScrollHelper(viewPager, isPauseWhenTouch)
    }

    private var timeMillis = 0L

    private val handler = Handler(Looper.getMainLooper()) {
        if (timeMillis > 0) {
            viewPager.setCurrentItem(if (viewPager.currentItem >= (viewPager.adapter?.count
                            ?: 0) - 1) 0 else viewPager.currentItem + 1, true)
            it.target.sendEmptyMessageDelayed(200, timeMillis)
        }
        true
    }


    init {
        if (isPauseWhenTouch) {
            if (viewPager is RecyclerViewPager)
                viewPager.setDispatchTouchListener { dispatchTouchEvent(it) }
            else dispatchTouchEventFromViewPager()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun dispatchTouchEventFromViewPager() {
        viewPager.setOnTouchListener { v, event ->
            dispatchTouchEvent(event)
            if (event.action == MotionEvent.ACTION_UP)
                v.performClick()
            false
        }
    }

    private fun dispatchTouchEvent(ev: MotionEvent) {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> onPause()
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> onResume()
        }
    }

    fun startAutoScroll(timeMillis: Long) {
        if (timeMillis > 0L) {
            this.timeMillis = timeMillis
            onResume()
        }
    }

    fun stopAutoScroll() {
        onPause()
        timeMillis = 0L
    }

    fun onResume() {
        handler.removeMessages(200)
        handler.sendEmptyMessageDelayed(200, timeMillis)
    }

    fun onPause() = handler.removeMessages(200)
}