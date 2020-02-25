package cc.bear3.gank.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cc.bear3.gank.R
import cc.bear3.gank.databinding.FragmentContainerBinding
import cc.bear3.gank.ui.common.BaseFragment
import cc.bear3.gank.ui.today.TodayGankFragment

/**
 * Description:
 * Author: TT
 * Since: 2020-01-29
 */
class ContainerFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContainerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tab
        val viewPager = binding.viewPager

        viewPager.adapter = ViewPagerAdapter<Fragment>(childFragmentManager).apply {
            val titles = listOf(
                getString(R.string.app_tab_today),
                getString(R.string.app_tab_reading),
                getString(R.string.app_tab_review),
                getString(R.string.app_tab_profile)
            )
            val fragments = listOf<Fragment>(
                TodayGankFragment.newInstance(),
                TodayGankFragment.newInstance(),
                TodayGankFragment.newInstance(),
                TodayGankFragment.newInstance()
            )
            setFragments(titles, fragments)
        }
        tabLayout.setupWithViewPager(viewPager)

        return binding.root
    }
}