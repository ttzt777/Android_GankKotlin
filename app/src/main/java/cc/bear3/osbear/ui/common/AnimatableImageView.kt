package cc.bear3.osbear.ui.common

import android.content.Context
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView

/**
 * Description:
 * Author: TT
 * Since: 2020-03-20
 */
class AnimatableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr){

    override fun setImageDrawable(drawable: Drawable?) {
        stopSvgAnim()

        super.setImageDrawable(drawable)

        startSvgAnim()
    }

    override fun setImageResource(resId: Int) {
        stopSvgAnim()

        super.setImageResource(resId)

        startSvgAnim()
    }

    override fun onDetachedFromWindow() {
        stopSvgAnim()
        super.onDetachedFromWindow()
    }

    private fun startSvgAnim() {
        (drawable as? Animatable)?.start()
    }

    private fun stopSvgAnim() {
        (drawable as? Animatable)?.let {
            if (it.isRunning) {
                it.stop()
            }
        }
    }
}