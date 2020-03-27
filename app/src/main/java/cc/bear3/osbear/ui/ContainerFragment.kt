package cc.bear3.osbear.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cc.bear3.osbear.R
import cc.bear3.osbear.databinding.FragmentContainerBinding
import cc.bear3.osbear.ui.common.BaseFragment

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
                getString(R.string.app_tab_last),
                getString(R.string.app_tab_project),
                getString(R.string.app_tab_post),
                getString(R.string.app_tab_tweet),
                getString(R.string.app_tab_profile)
            )
            val fragments = listOf<Fragment>(
                TodoFragment.newInstance(),
                TodoFragment.newInstance(),
                TodoFragment.newInstance(),
                TodoFragment.newInstance(),
                TodoFragment.newInstance()
            )
            setFragments(titles, fragments)
        }
        tabLayout.setupWithViewPager(viewPager)

        return binding.root
    }
}