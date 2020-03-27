package cc.bear3.osbear.ui.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import cc.bear3.osbear.R
import cc.bear3.osbear.databinding.LayoutTitleBarBinding

/**
 * Description:
 * Author: TT
 * Since: 2020-03-23
 */
class TitleBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private val binding = LayoutTitleBarBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        if (attrs != null) {
            val array = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
            binding.title.text = array.getString(R.styleable.TitleBar_bar_text)
            with(binding.back) {
                setImageResource(
                    array.getResourceId(
                        R.styleable.TitleBar_bar_back_res,
                        R.mipmap.ic_back_black
                    )
                )
                visibility = if (array.getBoolean(R.styleable.TitleBar_bar_back_gone, false)) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
            with(binding.other) {
                setImageResource(
                    array.getResourceId(
                        R.styleable.TitleBar_bar_other_res,
                        R.mipmap.ic_more_v_black
                    )
                )
                visibility = if (array.getBoolean(R.styleable.TitleBar_bar_other_gone, true)) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }

            array.recycle()
        } else {
            binding.other.visibility = View.GONE
        }
    }

    fun getTextView(): TextView {
        return binding.title
    }

    fun getBackView(): View {
        return binding.back
    }

    fun getOtherView(): View {
        return binding.other
    }
}