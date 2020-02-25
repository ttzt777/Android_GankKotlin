package cc.bear3.gank.ui.today.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cc.bear3.gank.data.GankData
import cc.bear3.gank.databinding.FragmentTodayGankCategoryBinding
import cc.bear3.gank.ui.common.BaseFragment

private const val ARG_DATA_LIST = "arg_data_list"

/**
 * Description:
 * Author: TT
 * Since: 2020-02-19
 */
class TodayGankCategoryFragment private constructor() : BaseFragment() {

    companion object {
        fun newInstance(dataList: ArrayList<GankData>?): TodayGankCategoryFragment {
            return TodayGankCategoryFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(ARG_DATA_LIST, dataList)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTodayGankCategoryBinding.inflate(inflater, container, false)

        val adapter = TodayGankCategoryAdapter()
        binding.gankList.adapter = adapter

        adapter.dataRefresh(arguments!!.getParcelableArrayList(ARG_DATA_LIST))

        return binding.root
    }
}