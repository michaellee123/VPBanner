package dog.abcd.vpbanner

import android.os.Handler
import android.os.Looper

class InfiniteTimer(val delay: Long, private val run: () -> Unit) {

    val handler = Handler(Looper.getMainLooper())
    val runnable = Runnable {
        start()
        run()
    }

    fun start() {
        stop()
        handler.postDelayed(runnable, delay)
    }

    fun stop() {
        handler.removeCallbacks(runnable)
    }
}