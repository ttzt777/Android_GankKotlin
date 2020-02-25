package cc.bear3.gank.ui.today.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cc.bear3.gank.data.GankData
import cc.bear3.gank.databinding.ItemTodayGankBinding
import cc.bear3.gank.ui.common.BaseAdapter

/**
 * Description:
 * Author: TT
 * Since: 2020-02-25
 */
class TodayGankCategoryAdapter :
    BaseAdapter<GankData, TodayGankCategoryAdapter.TodayGankViewHolder>() {

    override fun onCreateCustomViewHolder(parent: ViewGroup, viewType: Int): TodayGankViewHolder {
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
    ) : RecyclerView.ViewHolder(binding.root) {

        private val viewModel = TodayGankCategoryItemViewModel()

        init {
            binding.viewModel = viewModel
        }

        fun bind(data: GankData) {
            viewModel.updateData(data)
        }
    }
}
