package cc.bear3.gank.data

/**
 * Description: 闲读相关的数据类
 * Author: TT
 * Since: 2020-01-23
 */
data class ReadingPrimaryCategory(
    val _id: String?,
    val en_name: String?,
    val name: String?,
    val rank: Int
)

data class ReadingSummaryCategory(
    val _id: String?,
    val created_at: String?,
    val icon: String?,
    val id: String?,
    val title: String?
)

data class ReadingData(
    val _id: String?,
    val content: String?,
    val cover: String?,
    val crawled: Long,
    val created_at: String?,
    val deleted: Boolean,
    val published_at: String?,
    val raw: String?,
    val site: Site?,
    val title: String?,
    val uid: String?,
    val url: String?
) {
    data class Site(
        val cat_cn: String?,
        val cat_en: String?,
        val desc: String?,
        val feed_id: String?,
        val icon: String?,
        val id: String?,
        val name: String?,
        val subscribers: Int,
        val type: String?,
        val url: String?
    )
}