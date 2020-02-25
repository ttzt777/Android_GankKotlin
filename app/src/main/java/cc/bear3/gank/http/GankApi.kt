package cc.bear3.gank.http

import cc.bear3.gank.data.GankData
import cc.bear3.gank.data.ReadingData
import cc.bear3.gank.data.ReadingPrimaryCategory
import cc.bear3.gank.data.ReadingSummaryCategory
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Description:
 * Author: TT
 * From: 2019/8/14
 * Last Version: 1.0.0
 * Last Change Time: 2019/8/14
 * ----------- History ---------
 * *-*
 * version:
 * description:
 * time: 2019/8/14
 * *-*
 */
interface GankApi {
    // <editor-folder desc="干货相关">
    /**
     * 获取最新一天的干货
     */
    @GET("today")
    fun getTodayGank(): Observable<ResponseModel<Map<String, List<GankData>>>>

    @GET("day/{year}/{month}/{day}")
    fun getSpecialDayGank(
        @Path("year") year: Int,
        @Path("month") month: Int,
        @Path("day") day: Int
    ): Observable<ResponseModel<Map<String, List<GankData>>>>

    @GET("random/data/{category}/{count}")
    fun getRandomGank(
        @Path("category") category: String,
        @Path("count") count: Int
    ) : Observable<ResponseModel<List<GankData>>>

    // </editor-folder>

    // <editor-folder desc="闲读相关">
    @GET("xiandu/categories")
    fun getReadingPrimaryCategories() : Observable<ResponseModel<List<ReadingPrimaryCategory>>>

    @GET("xiandu/category/{primaryCategory}")
    fun getReadingSummaryCategories(
        @Path("primaryCategory") primaryCategory: String
    ) : Observable<ResponseModel<List<ReadingSummaryCategory>>>

    @GET("xiandu/data/id/{id}/count/{size}/page/{page}")
    fun getReadingList(
        @Path("id") id: String,
        @Path("size") size: Int,
        @Path("page") page: Int
    ) : Observable<ResponseModel<List<ReadingData>>>

    // </editor-folder>

    // <editor-folder desc="发布">

    // </editor-folder>

    // <editor-folder desc="">

    // </editor-folder>
}