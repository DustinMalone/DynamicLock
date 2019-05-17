package com.example.mylock.DynamicLock.GalleryView

import android.content.Context
import android.graphics.Canvas
import android.os.Build
import android.os.Handler
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_FLING
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
import android.widget.LinearLayout


/**
 * Created by LCB on 2018/12/29.
 */
class GalleryRecyclerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : RecyclerView(context, attrs, defStyleAttr) {

    private var userScrolling = false
    private var mScrolling = false
//    private var scrollState = RecyclerView.SCROLL_STATE_IDLE
    //最后滑动时间
    private var lastScrollTime: Long = 0
    //handler
    private val mHandler = Handler()
    //是否支持缩放
    private var scaleViews = false
    //是否支持透明度
    private var alphaViews = false
    //方向  默认水平
    private var orientation = Orientation.HORIZONTAL

    private var childViewMetrics: ChildViewMetrics? = null
    //选中回调
    private var listener: OnViewSelectedListener? = null
    // 选中的位置position
    /**
     * 获取当前位置position
     * @return
     */
    var currentPosition: Int = 0
        private set
    //recycleview   LinearLayoutManager
    private var mLinearLayoutManager: LinearLayoutManager? = null

    private var listem: TouchDownListem? = null
    //缩放基数
    private var baseScale = 0.7f
    //缩放透明度
    private var baseAlpha = 0.7f

    private var scrolling: Boolean = false

    /**
     * 获取中间的view
     * @return
     */
    val centerView: View?
        get() = getChildClosestToLocation(centerLocation)

    private val centerLocation: Int
        get() = if (orientation == Orientation.VERTICAL) measuredHeight / 2 else measuredWidth / 2

    private val scrollOffset: Int
        get() =
            if (orientation == Orientation.VERTICAL) computeVerticalScrollOffset() else computeHorizontalScrollOffset()

    init {

        init()
    }

    private fun init() {
        setHasFixedSize(true)
        setOrientation(orientation)
        enableSnapping()
    }

    override fun onChildAttachedToWindow(child: View) {
        super.onChildAttachedToWindow(child)

        if (!scrolling && scrollState == RecyclerView.SCROLL_STATE_IDLE) {
            scrolling = true
            scrollToView(centerView)
            updateViews()
        }
    }

    private fun enableSnapping() {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeGlobalOnLayoutListener(this)
            }
        })

        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                updateViews()
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)


                /** if scroll is caused by a touch (scroll touch, not any touch)  */
                if (newState == SCROLL_STATE_TOUCH_SCROLL) {
                    /** if scroll was initiated already, it would probably be a tap  */
                    /** if scroll was not initiated before, this is probably a user scrolling  */
                    if (!mScrolling) {
                        userScrolling = true
                    }
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    /** if user is the one scrolling, snap to the view closest to center  */
                    if (userScrolling) {
                        scrollToView(centerView)
                    }

                    userScrolling = false
                    mScrolling = false

                    /** if idle, always check location and correct it if necessary, this is just an extra check  */
                    if (centerView != null && getPercentageFromCenter(centerView) > 0) {
                        scrollToView(centerView)
                    }

                    /** if idle, notify listeners of new selected view  */
                    notifyListener()
                } else if (newState == SCROLL_STATE_FLING) {
                    mScrolling = true
                }

//                scrollState = newState
            }
        })
    }

    /**
     * 通知回调并设置当前选中位置
     */
    private fun notifyListener() {
        val view = centerView
        val position = getChildAdapterPosition(view)
        /** if there is a listener and the index is not the same as the currently selected position, notify listener  */
        if (listener != null && position != currentPosition) {
            listener!!.onSelected(view, position)
        }
        currentPosition = position
    }

    /**
     * 设置方向 水平 or 竖直
     * @param orientation LinearLayoutManager.HORIZONTAL or LinearLayoutManager.VERTICAL
     */
    fun setOrientation(orientation: Orientation) {
        this.orientation = orientation
        childViewMetrics = ChildViewMetrics(orientation)
        mLinearLayoutManager = LinearLayoutManager(context, orientation.intValue(), false)
        layoutManager = mLinearLayoutManager
    }

    /**
     * 设置选择position
     * @param position
     */
    fun setSelectPosition(position: Int) {
        mLinearLayoutManager!!.scrollToPositionWithOffset(position, 0)
    }

    /**
     * 设置选中回调接口
     * @param listener the OnViewSelectedListener
     */
    fun setOnViewSelectedListener(listener: OnViewSelectedListener) {
        this.listener = listener
    }

    /**
     * 设置两边是否可以缩放
     * @param enabled
     */
    fun setCanScale(enabled: Boolean) {
        this.scaleViews = enabled
    }

    /**
     * 设置两边的透明度是否支持
     * @param enabled
     */
    fun setCanAlpha(enabled: Boolean) {
        this.alphaViews = enabled
    }


    /**
     * 设置基数缩放值
     * @param baseScale
     */
    fun setBaseScale(baseScale: Float) {
        this.baseScale = 1f - baseScale
    }

    /**
     * 设置基数透明度
     * @param baseAlpha
     */
    fun setBaseAlpha(baseAlpha: Float) {
        this.baseAlpha = 1f - baseAlpha
    }

    /**
     * 更新views
     */
    private fun updateViews() {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            setMarginsForChild(child)
            val percentage = getPercentageFromCenter(child)
            val scale = 1f - baseScale * percentage
            val alpha = 1f - baseAlpha * percentage
            //设置缩放
            if (scaleViews) {
                child.scaleX = scale
                child.scaleY = scale
            }
            //设置透明度
            if (alphaViews) {
                child.alpha = alpha
            }
        }
    }

    /**
     * Adds the margins to a childView so a view will still center even if it's only a single child
     * @param child childView to set margins for
     */
    private fun setMarginsForChild(child: View) {
        val lastItemIndex = layoutManager.itemCount - 1
        val childIndex = getChildAdapterPosition(child)

        var startMargin = 0
        var endMargin = 0
        var topMargin = 0
        var bottomMargin = 0

        if (orientation == Orientation.VERTICAL) {
            topMargin = if (childIndex == 0) centerLocation else 0
            bottomMargin = if (childIndex == lastItemIndex) centerLocation else 0
        } else {
            startMargin = if (childIndex == 0) centerLocation else 0
            endMargin = if (childIndex == lastItemIndex) centerLocation else 0
        }

        /** if sdk minimum level is 17, set RTL margins  */
        if (orientation == Orientation.HORIZONTAL && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            (child.getLayoutParams() as ViewGroup.MarginLayoutParams).marginStart = startMargin
            (child.getLayoutParams() as ViewGroup.MarginLayoutParams).marginEnd = endMargin
        }

        /** If layout direction is RTL, swap the margins   */
        if (ViewCompat.getLayoutDirection(child) == ViewCompat.LAYOUT_DIRECTION_RTL)
            (child.getLayoutParams() as ViewGroup.MarginLayoutParams).setMargins(endMargin, topMargin, startMargin, bottomMargin)
        else {
            (child.getLayoutParams() as ViewGroup.MarginLayoutParams).setMargins(startMargin, topMargin, endMargin, bottomMargin)
        }

        /** if sdk minimum level is 18, check if view isn't undergoing a layout pass (this improves the feel of the view by a lot)  */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (!child.isInLayout())
                child.requestLayout()
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        val currentTime = System.currentTimeMillis()

        /** if touch events are being spammed, this is due to user scrolling right after a tap,
         * so set userScrolling to true  */
        if (mScrolling && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            if (currentTime - lastScrollTime < MINIMUM_SCROLL_EVENT_OFFSET_MS) {
                userScrolling = true
            }
        }

        lastScrollTime = currentTime

        val location = if (orientation == Orientation.VERTICAL) event.y.toInt() else event.x.toInt()

        val targetView = getChildClosestToLocation(location)

        if (!userScrolling) {
            if (event.action == MotionEvent.ACTION_UP) {
                if (targetView !== centerView) {
                    scrollToView(targetView)
                    return true
                }
            }
        }

        return super.dispatchTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (listem != null)
                listem!!.onTouchDown()
        }
        val location = if (orientation == Orientation.VERTICAL) event.y.toInt() else event.x.toInt()
        val targetView = getChildClosestToLocation(location)
        return if (targetView !== centerView) {
            true
        } else super.onInterceptTouchEvent(event)
    }

    override fun onTouchEvent(e: MotionEvent): Boolean {
        if (e.action == MotionEvent.ACTION_DOWN) {
            if (listem != null)
                listem!!.onTouchDown()
        }
        return super.onTouchEvent(e)
    }


    fun setTouchDownlistem(listem: TouchDownListem) {
        this.listem = listem
    }

    interface TouchDownListem {
        fun onTouchDown()
    }

    override fun scrollToPosition(position: Int) {
        childViewMetrics!!.size(getChildAt(0))
        smoothScrollBy(childViewMetrics!!.size(getChildAt(0)) * position)
    }

    private fun getChildClosestToLocation(location: Int): View? {
        if (childCount <= 0)
            return null

        var closestPos = 9999
        var closestChild: View? = null

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            val childCenterLocation = childViewMetrics!!.center(child).toInt()
            val distance = childCenterLocation - location

            /** if child center is closer than previous closest, set it as closest child   */
            if (Math.abs(distance) < Math.abs(closestPos)) {
                closestPos = distance
                closestChild = child
            }
        }

        return closestChild
    }

    /**
     * Check if the view is correctly centered (allow for 10px offset)
     * @param child the child view
     * @return true if correctly centered
     */
    private fun isChildCorrectlyCentered(child: View): Boolean {
        val childPosition = childViewMetrics!!.center(child).toInt()
        return childPosition > centerLocation - 10 && childPosition < centerLocation + 10
    }

    /**
     * 滚动指定view
     * @param child
     */
    private fun scrollToView(child: View?) {
        if (child == null)
            return

        stopScroll()

        val scrollDistance = getScrollDistance(child)

        if (scrollDistance != 0)
            smoothScrollBy(scrollDistance)
    }

    private fun getScrollDistance(child: View): Int {
        val childCenterLocation = childViewMetrics!!.center(child).toInt()
        return childCenterLocation - centerLocation
    }

    private fun getPercentageFromCenter(child: View?): Float {
        val center = centerLocation.toFloat()
        val childCenter = childViewMetrics!!.center(child)

        val offSet = Math.max(center, childCenter) - Math.min(center, childCenter)
        val maxOffset = center + childViewMetrics!!.size(child)

        return offSet / maxOffset
    }

    fun smoothScrollBy(distance: Int) {
        if (orientation == Orientation.VERTICAL) {
            super.smoothScrollBy(0, distance)
            return
        }

        super.smoothScrollBy(distance, 0)
    }

    fun scrollBy(distance: Int) {
        if (orientation == Orientation.VERTICAL) {
            super.scrollBy(0, distance)
            return
        }

        super.scrollBy(distance, 0)
    }

    private fun scrollTo(position: Int) {
        val currentScroll = scrollOffset
        scrollBy(position - currentScroll)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler.removeCallbacksAndMessages(null)
    }

    /**
     * 绘制一个中间view
     * @param canvas
     */
    protected override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
    }


    private class ChildViewMetrics(private val orientation: Orientation) {

        fun size(view: View?): Int {
            return if (orientation == Orientation.VERTICAL) view!!.getHeight() else view!!.getWidth()

        }

        fun location(view: View?): Float {
            return if (orientation == Orientation.VERTICAL) view!!.getY() else view!!.getX()

        }

        fun center(view: View?): Float {
            return location(view) + size(view) / 2
        }
    }

    enum class Orientation private constructor(internal var value: Int) {
        HORIZONTAL(LinearLayout.HORIZONTAL),
        VERTICAL(LinearLayout.VERTICAL);

        fun intValue(): Int {
            return value
        }
    }

    /**
     * 中间view选中接口
     */
    interface OnViewSelectedListener {
        fun onSelected(view: View?, position: Int)
    }

    companion object {

        private val MINIMUM_SCROLL_EVENT_OFFSET_MS = 20
    }
}