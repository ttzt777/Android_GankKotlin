package cc.bear3.osbear.utils

/**
 * Description:
 * Author: TT
 * Since: 2020-03-30
 */
internal inline fun Boolean.positive(block : () -> Unit) {
    if (this) {
        block()
    }
}

internal inline fun Boolean.negative(block: () -> Unit) {
    if (!this) {
        block()
    }
}