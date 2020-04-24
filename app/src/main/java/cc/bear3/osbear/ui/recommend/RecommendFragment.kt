package cc.bear3.osbear.ui.recommend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import cc.bear3.osbear.R
import cc.bear3.osbear.databinding.FragmentMainTabBinding
import cc.bear3.osbear.ui.TodoFragment
import cc.bear3.osbear.ui.ViewPagerAdapter
import cc.bear3.osbear.ui.common.BaseFragment

/**
 * Description:
 * Author: TT
 * Since: 2020-03-30
 */
class RecommendFragment : BaseFragment() {
    companion object {
        fun newInstance() : RecommendFragment {
            return RecommendFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMainTabBinding.inflate(inflater, container, false)

        subscribeUi(binding)

        return binding.root
    }

    private fun subscribeUi(binding: FragmentMainTabBinding) {
        val tabLayout = binding.tab
        val viewPager = binding.viewPager

        viewPager.adapter = ViewPagerAdapter<Fragment>(childFragmentManager).apply {
            val titles = listOf(
                getString(R.string.app_last_blog),
                getString(R.string.app_last_news),
                getString(R.string.app_last_post)
            )

            val fragments = listOf(
                TodoFragment.newInstance(),
                TodoFragment.newInstance(),
                TodoFragment.newInstance()
            )

            setFragments(titles, fragments)
        }

        tabLayout.setupWithViewPager(viewPager)
    }
}