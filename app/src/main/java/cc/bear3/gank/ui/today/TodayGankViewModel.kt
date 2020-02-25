package cc.bear3.gank.ui.today

import androidx.lifecycle.MutableLiveData
import cc.bear3.gank.data.GankData
import cc.bear3.gank.http.HttpError
import cc.bear3.gank.http.HttpObserver
import cc.bear3.gank.repository.GankRepository
import cc.bear3.gank.ui.common.BaseViewModel

/**
 * Description:
 * Author: TT
 * Since: 2020-02-17
 */
class TodayGankViewModel internal constructor(
    private val repository: GankRepository
) : BaseViewModel() {

    val titles = MutableLiveData<List<String>>()

    val ganks = MutableLiveData<Map<String, List<GankData>>>()

    internal fun requestTodayGank() {
        repository.getTodayGank(object : HttpObserver<Map<String, List<GankData>>>() {
            override fun onSuccess(data: Map<String, List<GankData>>) {
                val categories = mutableListOf<String>()

                for ((key, _) in data) {
                    categories.add(key)
                }

                ganks.value = data
                titles.value = categories
            }

            override fun onFailed(error: HttpError) {
            }
        })
    }
}