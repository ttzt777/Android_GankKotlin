package cc.bear3.gank.ui.today.category

import androidx.lifecycle.MutableLiveData
import cc.bear3.gank.data.GankData
import cc.bear3.gank.utils.formatTime

/**
 * Description:
 * Author: TT
 * Since: 2020-02-25
 */
class TodayGankCategoryItemViewModel() {
    val title = MutableLiveData<String>()
    val time = MutableLiveData<String>()
    val imageUrl = MutableLiveData<String>()
    val linkUrl = MutableLiveData<String>()

    fun updateData(data: GankData) {
        title.value = data.desc

        time.value = formatTime(data.publishedAt)
//        time.value = data.publishedAt

        imageUrl.value = if ("福利" == data.type) {
            data.url
        } else {
            // todo 处理
            data.images?.get(0)
        }

        linkUrl.value = if ("福利" == data.type) {
            null
        } else {
            data.url
        }
    }
}