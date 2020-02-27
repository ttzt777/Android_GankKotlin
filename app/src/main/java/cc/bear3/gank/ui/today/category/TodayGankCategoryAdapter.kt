package cc.bear3.gank.ui.today.category

import android.view.LayoutInflater
import android.view.ViewGroup
import cc.bear3.gank.data.GankData
import cc.bear3.baseadapter.AContentViewHolder
import cc.bear3.baseadapter.BaseAdapter
import cc.bear3.gank.databinding.ItemTodayGankBinding

/**
 * Description:
 * Author: TT
 * Since: 2020-02-25
 */
class TodayGankCategoryAdapter :
    cc.bear3.baseadapter.BaseAdapter<GankData, TodayGankCategoryAdapter.TodayGankViewHolder>() {

    override fun onCreateCustomViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        viewType: Int
    ): TodayGankViewHolder {
        return TodayGankViewHolder(
            ItemTodayGankBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindCustomViewHolder(holder: TodayGankViewHolder, position: Int) {
        holder.bind(getData(position))
    }

    class TodayGankViewHolder(
        binding: ItemTodayGankBinding
    ) : cc.bear3.baseadapter.AContentViewHolder(binding.root) {

        private val viewModel = TodayGankCategoryItemViewModel()

        init {
            binding.viewModel = viewModel
        }

        fun bind(data: GankData) {
            viewModel.updateData(data)
        }
    }
}
