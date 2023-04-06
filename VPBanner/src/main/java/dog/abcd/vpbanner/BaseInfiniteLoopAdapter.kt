package dog.abcd.vpbanner

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewbinding.ViewBinding

abstract class BaseInfiniteLoopAdapter<T, K : ViewBinding>(
    private val clazz: Class<K>,
) : Adapter<RecyclerView.ViewHolder>() {

    private val data: MutableList<T> = ArrayList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<T>) {
        this.data.clear()
        this.data.addAll(list)
        if (list.size > 1) {
            this.data.add(0, list.last())
            this.data.add(list.first())
        }
        notifyDataSetChanged()
    }

    fun getList(): List<T> {
        return data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BaseViewHolder(createBinding(parent.context, parent))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseViewHolder<*>) {
            bindView(holder as BaseViewHolder<K>, data[position])
        }
    }

    abstract fun bindView(holder: BaseViewHolder<K>, item: T)

    private fun createBinding(context: Context, parent: ViewGroup): K {
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        return method.invoke(null, LayoutInflater.from(context), parent, false) as K
    }
}