package cc.bear3.osbear.ui.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import cc.bear3.osbear.R

/**
 * Description: Fragment的基类，需要满足更布局必须是ViewGroup
 * Author: TT
 * Since: 2020-02-19
 */
abstract class BaseFragment : Fragment() {

    // 原来布局 根布局下的View集合
    private val contentViews = mutableListOf<View>()

    var loadingView: View? = null
    var errorView: View? = null

    private val loadingDialog : LoadingDialog by lazy {
        LoadingDialog()
    }

    private val params: ViewGroup.LayoutParams by lazy {
        ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkRootView()
    }

    @SuppressLint("InflateParams")
    fun showLoadingView() {
        hideErrorView()

        if (loadingView != null) {
            return
        }

        checkRootView()

        val viewGroup = view as ViewGroup
        val iterator = viewGroup.iterator()
        while (iterator.hasNext()) {
            contentViews.add(iterator.next())
            iterator.remove()
        }

        val loading = layoutInflater.inflate(R.layout.layout_default_loading, null)
        viewGroup.addView(loading, params)

//        val lottie = loading.findViewById<SvgImageView>(R.id.iv_loading_image)
//        if (!lottie.isAnimating) {
//            lottie.playAnimation()
//        }

        loadingView = loading
    }

    fun showErrorView() {
        hideLoadingView()

        if (errorView != null) {
            return
        }

        checkRootView()

        val viewGroup = view as ViewGroup
        val iterator = viewGroup.iterator()
        while (iterator.hasNext()) {
            contentViews.add(iterator.next())
            iterator.remove()
        }

        val error = layoutInflater.inflate(R.layout.layout_default_error, null)
        viewGroup.addView(error, params)

        errorView = error
    }

    fun hideLoadingView() {
        loadingView?.let{
            checkRootView()

//            val lottie = it.findViewById<LottieAnimationView>(R.id.iv_loading_image)
//            lottie.cancelAnimation()

            val viewGroup = view as ViewGroup
            viewGroup.removeView(it)

            val iterator = contentViews.iterator()
            while (iterator.hasNext()) {
                viewGroup.addView(iterator.next())
                iterator.remove()
            }

            loadingView = null
        }
    }

    fun hideErrorView() {
        errorView?.let{
            checkRootView()

            val viewGroup = view as ViewGroup
            viewGroup.removeView(it)

            val iterator = contentViews.iterator()
            while (iterator.hasNext()) {
                viewGroup.addView(iterator.next())
                iterator.remove()
            }

            errorView = null
        }
    }

    fun showContentView() {
        hideLoadingView()
        hideErrorView()
    }

    protected fun showLoadingDialog(string: String? = null) {
        loadingDialog.show(childFragmentManager, string)
    }

    protected fun showLoadingDialog(resId: Int) {
        loadingDialog.show(childFragmentManager, resId)
    }

    protected fun dismissLoadingDialog() {
        loadingDialog.dismiss()
    }

    protected fun popUp() {
        findNavController().navigateUp()
    }

    private fun checkRootView() {
        assert(view is ViewGroup) { throw IllegalAccessException("Fragment root(view) must be ViewGroup!!!") }
    }
}