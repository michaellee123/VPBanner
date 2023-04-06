package dog.abcd.vpbanner.indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet

class CircleIndicator @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : BaseIndicator(context, attrs, defStyleAttr, defStyleRes) {
    var space = context?.resources?.displayMetrics?.scaledDensity?.times(8) ?: 0f
        set(value) {
            field = value
            invalidate()
        }
    var size = context?.resources?.displayMetrics?.scaledDensity?.times(8) ?: 0f
        set(value) {
            field = value
            invalidate()
        }
    var normalColor = Color.GRAY
        set(value) {
            field = value
            invalidate()
        }
    var selectedColor = Color.GREEN
        set(value) {
            field = value
            invalidate()
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = (size + space) * count - space
        val height = size
        setMeasuredDimension(width.toInt(), height.toInt())
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        var left = 0f
        for (i in 0 until count) {
            paint.color = if (i == position) selectedColor else normalColor
            val right = left + size
            canvas?.drawCircle((left + right) / 2, size / 2, size / 2, paint)
            left = right + space
        }
    }
}