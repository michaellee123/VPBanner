package dog.abcd.vpbanner.indicator

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.viewpager2.widget.ViewPager2

abstract class BaseIndicator @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) :
    View(context, attrs, defStyleAttr, defStyleRes) {

    val callback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            this@BaseIndicator.position = when (position) {
                0 -> maxOf(0, count - 1)
                count + 1 -> 0
                else -> maxOf(0, position - 1)
            }
        }
    }

    var position = 0
        set(value) {
            field = value
            invalidate()
        }

    val count: Int
        get() {
            var num = viewPager?.adapter?.itemCount ?: 0
            if (num > 3) {
                num -= 2
            }
            return num
        }

    private var viewPager: ViewPager2? = null
        set(value) {
            field = value
        }

    var paint: Paint = Paint()

    fun bind(viewPager: ViewPager2) {
        viewPager.unregisterOnPageChangeCallback(callback)
        viewPager.registerOnPageChangeCallback(callback)
        this.viewPager = viewPager
    }

    init {
        paint.isAntiAlias = true
    }

}