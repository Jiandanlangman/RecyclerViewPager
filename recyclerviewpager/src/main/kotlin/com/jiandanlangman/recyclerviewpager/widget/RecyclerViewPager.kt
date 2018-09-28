package com.jiandanlangman.recyclerviewpager.widget

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

class RecyclerViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    constructor(context: Context) : this(context, null)

    private var dispatchTouchListener: ((MotionEvent) -> Unit)? = null

    @Deprecated("", ReplaceWith("throw RuntimeException(\"Method setAdapter(PagerAdapter) is deprecated, please call the method setAdapter(Adapter<ViewHolder>)\")"))
    override fun setAdapter(adapter: PagerAdapter?) {
        throw RuntimeException("Method setAdapter(PagerAdapter) is deprecated, please call the method setAdapter(Adapter<ViewHolder>)")
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        dispatchTouchListener?.invoke(ev)
        return super.dispatchTouchEvent(ev)
    }

    fun setAdapter(adapter: Adapter<*>?) = super.setAdapter(adapter)

    fun getCurrentItemView(): View? = findViewWithTag(currentItem)

    internal fun setDispatchTouchListener(listener: (ev: MotionEvent) -> Unit) {
        this.dispatchTouchListener = listener
    }

    open class ViewHolder(val itemView: View)

    abstract class Adapter<VH : ViewHolder> : PagerAdapter() {

        private val viewHolders = HashMap<Int, ArrayList<VH>>()
        private val positionMappings = HashMap<Int, VH>()

        abstract fun getItemCount(): Int

        abstract fun onCreateViewHolder(container: ViewGroup, viewType: Int): VH

        abstract fun onBindViewHolder(viewHolder: VH, position: Int)

        final override fun getCount() = getItemCount()

        open fun getItemViewType(position: Int) = 0

        final override fun isViewFromObject(view: View, `object`: Any) = view === `object`

        final override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val viewType = getItemViewType(position)
            if (viewHolders[viewType] == null)
                viewHolders[viewType] = ArrayList()
            val viewHolder = if (viewHolders[viewType]!!.isNotEmpty()) viewHolders[viewType]!!.removeAt(0) else onCreateViewHolder(container, viewType)
            onBindViewHolder(viewHolder, position)
            viewHolder.itemView.tag = position
            container.addView(viewHolder.itemView)
            positionMappings[position] = viewHolder
            return viewHolder.itemView
        }

        final override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
            viewHolders[getItemViewType(position)]!!.add(positionMappings.remove(position)!!)
        }

        final override fun getItemPosition(`object`: Any) = POSITION_NONE

    }

}