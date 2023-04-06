package dog.abcd.vpbanner

import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import dog.abcd.vpbanner.indicator.BaseIndicator

var ViewPager2.infinitePageChangeCallback: InfinitePageChangeCallback?
    get() = getTag(R.id.infinite_page_change_callback) as? InfinitePageChangeCallback
    set(value) {
        infinitePageChangeCallback?.let { unregisterOnPageChangeCallback(it) }
        value?.let { registerOnPageChangeCallback(it) }
        setTag(R.id.infinite_page_change_callback, value)
    }

var ViewPager2.ignoreNextEvent: Boolean
    get() = getTag(R.id.ignore_next_event) as? Boolean ?: false
    set(value) {
        setTag(R.id.ignore_next_event, value)
    }

var ViewPager2.needIgnoreNextEvent: Boolean
    get() = getTag(R.id.need_ignore_next_event) as? Boolean ?: false
    set(value) {
        setTag(R.id.need_ignore_next_event, value)
    }

var ViewPager2.ignoreAllEvent: Boolean
    get() = getTag(R.id.ignore_all_event) as? Boolean ?: false
    set(value) {
        setTag(R.id.ignore_all_event, value)
    }

var ViewPager2.infiniteTimer: InfiniteTimer?
    get() = getTag(R.id.infinite_timer) as? InfiniteTimer
    set(value) {
        infiniteTimer?.stop()
        value?.start()
        setTag(R.id.infinite_timer, value)
    }

fun <T, K : ViewBinding> ViewPager2.setInfiniteAdapter(
    list: List<T>,
    clazz: Class<K>,
    viewBinder: BaseViewHolder<K>.(T) -> Unit
) {
    val onTouchListener = OnTouchListener { v, event ->
        v.performClick()
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                ignoreAllEvent = true
                ignoreNextEvent = true
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                ignoreAllEvent = false
            }
        }
        true
    }
    if (adapter == null) {
        adapter = object : BaseInfiniteLoopAdapter<T, K>(clazz) {
            override fun bindView(holder: BaseViewHolder<K>, item: T) {
                holder.itemView.setOnTouchListener(onTouchListener)
                viewBinder(holder, item)
            }
        }
    }
    (adapter as BaseInfiniteLoopAdapter<T, K>).setList(list)
    setCurrentItem(1, false)
    infinitePageChangeCallback = InfinitePageChangeCallback(this)
}

fun <T, K : ViewBinding> ViewPager2.asBanner(
    list: List<T>,
    clazz: Class<K>,
    delay: Long = 3000,
    viewBinder: BaseViewHolder<K>.(T) -> Unit
) {
    setInfiniteAdapter(list, clazz, viewBinder)
    infiniteTimer = InfiniteTimer(delay) {
        next()
    }
}

fun ViewPager2.next() {
    if (ignoreAllEvent) {
        return
    }
    if (ignoreNextEvent && needIgnoreNextEvent) {
        ignoreNextEvent = false
        return
    }
    setCurrentItem(minOf(itemCount, currentItem + 1), true)
}

fun ViewPager2.previous() {
    if (ignoreAllEvent) {
        return
    }
    if (ignoreNextEvent && needIgnoreNextEvent) {
        ignoreNextEvent = false
        return
    }
    setCurrentItem(maxOf(0, currentItem - 1), true)
}

fun ViewPager2.setIndicator(indicator: BaseIndicator) {
    indicator.bind(this)
}

val ViewPager2.itemCount: Int get() = adapter?.itemCount ?: 0