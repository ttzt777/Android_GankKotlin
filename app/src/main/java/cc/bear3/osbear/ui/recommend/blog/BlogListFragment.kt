package cc.bear3.osbear.ui.recommend.blog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.osbear.databinding.FragmentSrlRecyclerBinding
import cc.bear3.osbear.ui.common.BaseFragment

/**
 * Description:
 * Author: TT
 * Since: 2020-03-30
 */
class BlogListFragment : BaseFragment() {

    val viewModel : BlogListViewModel by lazy {
        BlogListViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSrlRecyclerBinding.inflate(inflater, container, false)

        subscribeUi(binding)

        return binding.root
    }

    private fun subscribeUi(binding: FragmentSrlRecyclerBinding) {

    }
}