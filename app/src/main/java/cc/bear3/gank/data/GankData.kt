package cc.bear3.gank.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Description: 干货相关的数据类
 * Author: TT
 * Since: 2020-01-22
 */
@Parcelize
data class GankData(
    val _id: String?,
    val createdAt: String?,
    val desc: String?,
    val images: List<String>?,
    val publishedAt: String?,
    val source: String?,
    val type: String?,
    val url: String?,
    val used: Boolean,
    val who: String?
) : Parcelable