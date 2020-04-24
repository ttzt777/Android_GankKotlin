package cc.bear3.osbear.app

import cc.bear3.baseadapter.ABaseAdapter
import cc.bear3.baseadapter.AContentViewHolder
import cc.bear3.osbear.http.HttpError
import cc.bear3.osbear.ui.common.BaseAdapter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener

class RefreshProxy<T> constructor(
    val layout: SmartRefreshLayout,
    val adapter: BaseAdapter<T, AContentViewHolder>
) : Disposable {

    private var enableRefresh = false
    private var enableLoadMore = false

    private var pageNum = 1
    private var pageNumBak = pageNum
    private var loadingFlag = false


    override fun dispose() {

    }

    fun enableRefresh(refreshListener: OnRefreshListener) {
        this.enableRefresh = true
        with(layout) {
            setEnableRefresh(enableRefresh)
            setOnRefreshListener(refreshListener)
        }
    }

    fun enableLoadMore(loadMoreListener: OnLoadMoreListener) {
        this.enableLoadMore = true
        with(layout) {
            setEnableRefresh(enableLoadMore)
            setOnLoadMoreListener(loadMoreListener)
        }
    }

    fun enableRefreshAndLoadMore(listener: OnRefreshLoadMoreListener) {
        enableRefresh = true
        enableLoadMore = true
        with(layout) {
            setEnableRefresh(enableRefresh)
            setEnableRefresh(enableLoadMore)
            setOnRefreshLoadMoreListener(listener)
        }
    }

    fun getPageNum(): Int {
        if (pageNum < 1) {
            pageNum = 1
        }
        return pageNum
    }

    fun isFirstPage(): Boolean {
        return getPageNum() == 1
    }

    fun autoRefresh(): Boolean {
        if (loadingFlag) {
            return false
        }

        layout.autoRefresh(0, 500, 1.0f, false)

        return true
    }

    /**
     *
     */
    fun resetParams() {
        pageNumBak = pageNum
        pageNum = 1
    }

    fun onLoading(block: () -> Unit) {
        if (loadingFlag) {
            return
        }
        loadingFlag = true
        if (adapter.status == ABaseAdapter.Status.Loading) {
            with(layout) {
                setEnableRefresh(false)
                setEnableLoadMore(false)
            }
        } else {
            layout.setEnableLoadMore(false)
            //            autoRefresh(runnable != null);
        }
        resetParams()
        block()
    }

    fun onReLoading(block: () -> Unit) {
        loadingFlag = false
        onLoading(block)
    }

    fun onRefresh(block: () -> Unit) {
        if (loadingFlag) {
            return
        }
        loadingFlag = true
        resetParams()
        block()
    }

    fun onLoadMore(block: () -> Unit) {
        if (loadingFlag) {
            return
        }
        pageNumBak = pageNum
        pageNum++
        loadingFlag = true
        block()
    }

    fun onFinish(
        error: HttpError,
        dataList: List<T>? = null,
        pageSize: Int = -1
    ) {
        var enableLoadMore = enableLoadMore
        adapter.dataRefresh(dataList)

        if (adapter.status == ABaseAdapter.Status.Null || adapter.status == ABaseAdapter.Status.Loading) {
            when (error) {
                HttpError.SUCCESS -> adapter.status = ABaseAdapter.Status.Content
                else -> adapter.status = ABaseAdapter.Status.Error
            }
        }

        // 数据处理及错误时PageNum归正
        if (error == HttpError.SUCCESS) {
            when {
                dataList.isNullOrEmpty() -> {
                    enableLoadMore = false
                    adapter.status = ABaseAdapter.Status.Empty
                }
                dataList.size < pageSize -> {
                    enableLoadMore = false
                }
            }
        } else {
            pageNum = pageNumBak
            if (adapter.status === ABaseAdapter.Status.Error) {
                enableLoadMore = false
            }
        }

        // 状态归正
        layout.finishRefresh()
        layout.finishLoadMore()

        layout.setEnableRefresh(enableRefresh)
        layout.setEnableLoadMore(enableLoadMore)

        loadingFlag = false
    }
}