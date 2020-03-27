package cc.bear3.osbear.ui.common

import android.view.LayoutInflater
import android.view.ViewGroup
import cc.bear3.baseadapter.*
import cc.bear3.osbear.databinding.ItemDefaultEmptyBinding
import cc.bear3.osbear.databinding.ItemDefaultErrorBinding
import cc.bear3.osbear.databinding.ItemDefaultLoadingBinding
import cc.bear3.osbear.databinding.ItemDefaultNomoreBinding

/**
 * Description:
 * Author: TT
 * Since: 2020-02-28
 */
abstract class BaseAdapter<T, VH : AContentViewHolder> :ABaseAdapter<T, VH>() {

    override fun onViewAttachedToWindow(holder: AViewHolder) {
        if (holder is DefaultLoadingViewHolder) {
            val lottieView = holder.binding.loadLottie
//            if (!lottieView.isAnimating) {
//                lottieView.playAnimation()
//            }
        }
    }

    override fun onViewDetachedFromWindow(holder: AViewHolder) {
        if (holder is DefaultLoadingViewHolder) {
//            holder.binding.loadLottie.cancelAnimation()
        }
    }

    override fun onCreateLoadingViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ALoadingViewHolder {
        return DefaultLoadingViewHolder(
            ItemDefaultLoadingBinding.inflate(inflater, parent, false)
        )
    }

    override fun onCreateEmptyViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): AEmptyViewHolder {
        return DefaultEmptyViewHolder(
            ItemDefaultEmptyBinding.inflate(inflater, parent, false)
        )
    }

    override fun onCreateLErrorViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): AErrorViewHolder {
        return DefaultErrorViewHolder(
            ItemDefaultErrorBinding.inflate(inflater, parent, false)
        )
    }

    override fun onCreateNoMoreViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup
    ): ANoMoreViewHolder {
        return DefaultNoMoreViewHolder(
            ItemDefaultNomoreBinding.inflate(inflater, parent, false)
        )
    }

    override fun onBindLoadingViewHolder(holder: ALoadingViewHolder) {

    }

    override fun onBindEmptyViewHolder(holder: AEmptyViewHolder) {

    }

    override fun onBindErrorViewHolder(holder: AErrorViewHolder) {

    }

    override fun onBindNoMoreViewHolder(holder: ANoMoreViewHolder) {

    }

    class DefaultLoadingViewHolder(val binding: ItemDefaultLoadingBinding) :
        ALoadingViewHolder(binding.root)

    class DefaultEmptyViewHolder(val binding: ItemDefaultEmptyBinding) :
        AEmptyViewHolder(binding.root)

    class DefaultErrorViewHolder(val binding: ItemDefaultErrorBinding) :
        AErrorViewHolder(binding.root)

    class DefaultNoMoreViewHolder(val binding: ItemDefaultNomoreBinding) :
        ANoMoreViewHolder(binding.root)
}