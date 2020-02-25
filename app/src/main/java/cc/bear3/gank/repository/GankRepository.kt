package cc.bear3.gank.repository

import cc.bear3.gank.data.GankData
import cc.bear3.gank.http.HttpManager
import cc.bear3.gank.http.HttpObserver
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Description: 干货数据提供
 * Author: TT
 * Since: 2020-01-30
 */
class GankRepository {
    fun getTodayGankCategories(observer: Observer<List<String>>) {
        // todo 先从数据库检索有没有今天的数据
    }

    /**
     * 获取今日干货
     */
    fun getTodayGank(observer: HttpObserver<Map<String, List<GankData>>>) {
        HttpManager.gankApi.getTodayGank()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

}