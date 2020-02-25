package cc.bear3.gank.adapters

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import cc.bear3.gank.repository.ImageLoader

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, url: String?) {
    if (url.isNullOrEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
        ImageLoader.loadImageUrl(view.context, view, url)
    }
}

//@BindingAdapter("isGone")
//fun bindIsGone(view: View, gone: Boolean) {
//    view.visibility
//}