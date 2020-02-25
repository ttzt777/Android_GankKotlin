package cc.bear3.gank.ui.today

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.observe
import cc.bear3.gank.data.GankData
import cc.bear3.gank.databinding.FragmentTodayGankBinding
import cc.bear3.gank.repository.GankRepository
import cc.bear3.gank.ui.ViewPagerAdapter
import cc.bear3.gank.ui.common.BaseFragment
import cc.bear3.gank.ui.today.category.TodayGankCategoryFragment

/**
 * Description:
 * Author: TT
 * Since: 2020-01-30
 */
class TodayGankFragment private constructor(): BaseFragment() {

    private val viewModel: TodayGankViewModel by lazy {
        TodayGankViewModel(GankRepository())
    }

    private val pagerAdapter: ViewPagerAdapter<BaseFragment> by lazy {
        ViewPagerAdapter<BaseFragment>(childFragmentManager)
    }

    companion object {
        fun newInstance(): TodayGankFragment {
            return TodayGankFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTodayGankBinding.inflate(inflater, container, false)

        initView(binding)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.requestTodayGank()
    }

    private fun initView(binding: FragmentTodayGankBinding) {
        val tabLayout = binding.tab
        val viewPager = binding.viewPager

        viewPager.adapter = pagerAdapter
        tabLayout.setupWithViewPager(viewPager)

        subscribeUi(binding)
    }

    private fun subscribeUi(binding: FragmentTodayGankBinding) {
        viewModel.titles.observe(viewLifecycleOwner) { result ->
            if (result.isNullOrEmpty()) {
                return@observe
            }

            val titles = listOf(*result.toTypedArray())
            val fragments = mutableListOf<TodayGankCategoryFragment>().apply {
                for (title in result) {
                    add(TodayGankCategoryFragment.newInstance(viewModel.ganks.value?.get(title) as? ArrayList<GankData>?))
                }
            }

            pagerAdapter.setFragments(titles, fragments)
        }
    }
}