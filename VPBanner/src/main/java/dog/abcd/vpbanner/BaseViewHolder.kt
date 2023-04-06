package dog.abcd.vpbanner

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<T: ViewBinding>(val bind: T) : RecyclerView.ViewHolder(bind.root)