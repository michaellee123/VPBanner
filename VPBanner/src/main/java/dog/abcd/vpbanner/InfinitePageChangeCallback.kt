package dog.abcd.vpbanner

import androidx.viewpager2.widget.ViewPager2

class InfinitePageChangeCallback(val viewPager: ViewPager2) : ViewPager2.OnPageChangeCallback() {

    override fun onPageScrolled(
        position: Int,
        positionOffset: Float,
        positionOffsetPixels: Int
    ) {
        super.onPageScrolled(position, positionOffset, positionOffsetPixels)
        if (positionOffsetPixels == 0) {
            if (position == 0) {
                viewPager.setCurrentItem(viewPager.adapter!!.itemCount - 2, false)
            } else if (position == viewPager.adapter!!.itemCount - 1) {
                viewPager.setCurrentItem(1, false)
            }
        }
    }

    override fun onPageScrollStateChanged(state: Int) {
        super.onPageScrollStateChanged(state)
        if (state == 0) {
            viewPager.ignoreAllEvent = false
        }
    }
}