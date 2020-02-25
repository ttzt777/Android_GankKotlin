package cc.bear3.gank.ui.common

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Description:
 * Author: TT
 * Since: 2020-02-25
 */
abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val dataList = mutableListOf<T>()

    fun dataRefresh(targetList: List<T>?) {
        dataList.clear()
        targetList?.let {
            dataList.addAll(it)
        }

        notifyDataSetChanged()
    }

    fun dataMore(targetList: List<T>?) {
        if (targetList.isNullOrEmpty()) {
            return
        }

        dataList.addAll(targetList)

        notifyDataSetChanged()
    }

    fun getData(position: Int) : T {
        return dataList[position]
    }

    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return onCreateCustomViewHolder(parent, viewType)
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        onBindCustomViewHolder(holder as VH, position)
    }

    final override fun getItemCount(): Int {
        return getCustomItemCount()
    }

    final override fun getItemViewType(position: Int): Int {
        return getCustomViewType(position)
    }

    abstract fun onCreateCustomViewHolder(parent: ViewGroup, viewType: Int): VH

    abstract fun onBindCustomViewHolder(holder: VH, position: Int)

    open fun getCustomItemCount(): Int {
        return dataList.size
    }

    open fun getCustomViewType(position: Int): Int {
        return 0
    }
}