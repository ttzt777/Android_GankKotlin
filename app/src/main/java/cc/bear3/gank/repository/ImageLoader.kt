package cc.bear3.gank.repository

import android.content.Context
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Description:
 * Author: TT
 * Since: 2020-02-21
 */
object ImageLoader {
    fun loadImageUrl(
        context: Context,
        imageView: ImageView,
        url: String?,
        placeHolder: Int = 0,
        error: Int = 0
    ) {
        val options = RequestOptions().placeholder(placeHolder).error(error).apply{

        }

        Glide.with(context).load(url).apply(options).into(imageView)
    }

    fun loadImageUrl(
        fragment: Fragment,
        imageView: ImageView,
        url: String?,
        placeHolder: Int = 0,
        error: Int = 0
    ) {
        val options = RequestOptions().placeholder(placeHolder).error(error).apply{

        }

        Glide.with(fragment).load(url).apply(options).into(imageView)
    }
}