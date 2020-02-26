package cc.bear3.gank.ui.common

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Description: Fragment的基类，需要满足更布局必须是ViewGroup
 * Author: TT
 * Since: 2020-02-19
 */
abstract class BaseFragment : Fragment() {

    // 原来布局 根布局下的View集合
    val contentViews = mutableListOf<View>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        assert(view is ViewGroup) {throw IllegalAccessException("Fragment root(view) must be ViewGroup!!!")}
    }
}