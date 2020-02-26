package cc.bear3.gank.ui.common

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.IllegalStateException

private const val TYPE_DATA = 0x7FFF0000        // 数据默认
private const val TYPE_LOADING = 0x7FFF0010     // 加载
private const val TYPE_EMPTY = 0x7FFF0011       // 空状态
private const val TYPE_ERROR = 0x7FFF0012       // 错误状态
private const val TYPE_NO_MORE = 0x7FFF0013    // 没有更多 - 底部

private const val TYPE_HEADER_INIT_INDEX = 0x80000000
private const val TYPE_FOOTER_INIT_INDEX = 0xB0000000

enum class AdapterStatus {
    Null,               // 空，初始化的状态
    Loading,            // 加载中
    Empty,              // 空状态，显示该列表没有内容却有一个item展示
    Error,              // 错误状态
    Content             // 有数据的内容状态
}

/**
 * Description: RecyclerView 适配器基类
 * * 内容展示 *
 * -- Header... --
 * -- Content(Loading/Empty/Error/Content) --
 * -- Footer... --
 * -- NoMore --
 * Author: TT
 * Since: 2020-02-25
 */
abstract class BaseAdapter<T, VH : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // 状态
    var status = AdapterStatus.Null

    // 数据集合
    private val dataList = mutableListOf<T>()

    // Header View 集合
    private val mHeaderViews = SparseArray<View>()
    // Footer View 集合
    private val mFooterViews = SparseArray<View>()

    // 没有更多数据标记
    var noMoreData = false

    // 用于界面上固定的头部或者顶部，与Item中的逻辑无关
    private var mHeaderTypeIndex = TYPE_HEADER_INIT_INDEX
    private var mFooterTypeIndex = TYPE_FOOTER_INIT_INDEX

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

    // <editor-folder desc="头部和底部Api">
    open fun addHeaderView(vararg views: View) {
        var notify = false
        for (view in views) {
            if (mHeaderViews.indexOfValue(view) == -1) {
                mHeaderViews.put(getHeaderTypeIndex(), view)
                notify = true
            }
        }
        if (notify) {
            notifyDataSetChanged()
        }
    }

    open fun addFooterView(vararg views: View) {
        var notify = false
        for (view in views) {
            if (mFooterViews.indexOfValue(view) == -1) {
                mFooterViews.put(getFooterTypeIndex(), view)
                notify = true
            }
        }
        if (notify) {
            notifyDataSetChanged()
        }
    }

    open fun removeHeaderView(view: View) {
        val key = mHeaderViews.indexOfValue(view)
        if (key != -1) {
            mHeaderViews.remove(key)
            notifyDataSetChanged()
        }
    }

    open fun removeAllHeaderView() {
        mHeaderViews.clear()
    }

    open fun removeFooterView(view: View) {
        val key = mHeaderViews.indexOfValue(view)
        if (key != -1) {
            mHeaderViews.remove(key)
            notifyDataSetChanged()
        }
    }

    open fun removeAllFooterView() {
        mFooterViews.clear()
    }

    open fun getHeaderViewSize(): Int {
        return mHeaderViews.size()
    }

    open fun getFooterViewSize(): Int {
        return mFooterViews.size()
    }
    // </editor-folder>

    final override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when {
            mHeaderViews.get(viewType) != null -> {
                HeaderViewHolder(mHeaderViews.get(viewType))
            }
            mFooterViews.get(viewType) != null -> {
                FooterViewHolder(mFooterViews.get(viewType))
            }
            viewType == TYPE_LOADING -> onCreateLoadingViewHolder(inflater, parent)
            viewType == TYPE_EMPTY -> onCreateEmptyViewHolder(inflater, parent)
            viewType == TYPE_ERROR -> onCreateLErrorViewHolder(inflater, parent)
            viewType == TYPE_NO_MORE -> onCreateNoMoreViewHolder(inflater, parent)
            else -> onCreateCustomViewHolder(inflater, parent, viewType)
        }
    }

    final override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is HeaderViewHolder -> {}
            is FooterViewHolder -> {}
            is ALoadingViewHolder -> onBindLoadingViewHolder(holder)
            is AEmptyViewHolder -> onBindEmptyViewHolder(holder)
            is AErrorViewHolder -> onBindErrorViewHolder(holder)
            is ANoMoreViewHolder -> onBindNoMoreViewHolder(holder)
            else -> onBindCustomViewHolder(holder as VH, position)
        }
    }

    final override fun getItemCount(): Int {
        return getHeaderViewSize() + getContentCount() + getFooterViewSize() + getNoMoreCount()
    }

    final override fun getItemViewType(position: Int): Int {
        return when {
            isHeaderPosition(position) -> {
                mHeaderViews.keyAt(position)
            }
            isFooterPosition(position) -> {
                mFooterViews.keyAt(position)
            }
            else -> {
                when (status) {
                    AdapterStatus.Null -> TYPE_DATA     // 不会出现这种类型
                    AdapterStatus.Loading -> TYPE_LOADING
                    AdapterStatus.Empty -> TYPE_EMPTY
                    AdapterStatus.Error -> TYPE_ERROR
                    AdapterStatus.Content -> {
                        return if (noMoreData && position == itemCount - 1) {
                            TYPE_NO_MORE
                        } else {
                            var realPos = position - getHeaderViewSize()
                            val type = getCustomViewType(realPos)

                            check(type in 0..TYPE_DATA){IllegalStateException("View type must in 0 .. $TYPE_DATA")}

                            type
                        }
                    }
                }
            }
        }
    }

    protected abstract fun onCreateCustomViewHolder(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): VH

    protected abstract fun onBindCustomViewHolder(holder: VH, position: Int)

    protected open fun getCustomItemCount(): Int {
        return dataList.size
    }

    protected open fun getCustomViewType(position: Int): Int {
        return 0
    }

    protected open fun onCreateLoadingViewHolder(inflater: LayoutInflater, parent: ViewGroup) : ALoadingViewHolder {

    }

    protected open fun onCreateEmptyViewHolder(inflater: LayoutInflater, parent: ViewGroup) : AEmptyViewHolder {

    }

    protected open fun onCreateLErrorViewHolder(inflater: LayoutInflater, parent: ViewGroup) : AErrorViewHolder {

    }

    protected open fun onCreateNoMoreViewHolder(inflater: LayoutInflater, parent: ViewGroup) : ANoMoreViewHolder {

    }

    protected open fun onBindLoadingViewHolder(holder: ALoadingViewHolder) {

    }

    protected open fun onBindEmptyViewHolder(holder: AEmptyViewHolder) {

    }

    protected open fun onBindErrorViewHolder(holder: AErrorViewHolder) {

    }

    protected open fun onBindNoMoreViewHolder(holder: ANoMoreViewHolder) {

    }

    /**
     * 获取中间内容（Content/Loading/Error/Empty）的item个数
     */
    private fun getContentCount() :Int {
        var count = getCustomItemCount()

        return if (count == 0) {
            when (status) {
                AdapterStatus.Loading, AdapterStatus.Error, AdapterStatus.Empty -> ++count
                else -> count
            }
        } else {
            count
        }
    }

    /**
     * 获取底部NoMore的Item个数
     */
    private fun getNoMoreCount() : Int {
        return if (noMoreData && status == AdapterStatus.Content) {
            1
        } else {
            0
        }
    }

    private fun getHeaderTypeIndex(): Int {
        mHeaderTypeIndex++
        if (mHeaderTypeIndex >= TYPE_FOOTER_INIT_INDEX) {
            mHeaderTypeIndex = TYPE_HEADER_INIT_INDEX
        }
        return mHeaderTypeIndex.toInt()
    }

    private fun getFooterTypeIndex(): Int {
        mFooterTypeIndex++
        if (mFooterTypeIndex >= 0xFFFFFFFF) {
            mFooterTypeIndex = TYPE_FOOTER_INIT_INDEX
        }
        return mFooterTypeIndex.toInt()
    }

    private fun isHeaderPosition(position: Int): Boolean {
        return position < getHeaderViewSize()
    }

    private fun isFooterPosition(position: Int): Boolean {
        return position >= getHeaderViewSize() + getCustomItemCount()
    }

    abstract class ALoadingViewHolder(view: View) : RecyclerView.ViewHolder(view)

    abstract class AEmptyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    abstract class AErrorViewHolder(view: View) : RecyclerView.ViewHolder(view)

    abstract class ANoMoreViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class FooterViewHolder(view: View) : RecyclerView.ViewHolder(view)

    class DefaultALoadingViewHolder(view: View) : ALoadingViewHolder(view) {

    }

    class DefaultEmptyViewHolder(view: View): AEmptyViewHolder(view) {

    }

    class DefaultErrorViewHolder(view: View): AErrorViewHolder(view) {

    }

    class DefaultNoMoreViewHolder(view: View) : ANoMoreViewHolder(view) {

    }
}