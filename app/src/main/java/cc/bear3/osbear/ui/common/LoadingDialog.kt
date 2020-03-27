package cc.bear3.osbear.ui.common

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import cc.bear3.osbear.databinding.DialogLoadingBinding
import kotlinx.android.synthetic.main.dialog_loading.*

/**
 * Description:
 * Author: TT
 * Since: 2020-03-24
 */
class LoadingDialog : DialogFragment(){
    var binding: DialogLoadingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DialogLoadingBinding.inflate(inflater, container, false)

        this.binding = binding

        return binding.root
    }

    override fun show(manager: FragmentManager, hint: String?) {
        updateHint(hint)

        val tag = LoadingDialog::class.java.simpleName
        super.show(manager, tag)
    }

    fun show(manager: FragmentManager) {
        updateHint(null)

        val tag = LoadingDialog::class.java.simpleName
        super.show(manager, tag)
    }

    fun show(manager: FragmentManager, stringResId: Int) {
        updateHint(stringResId)

        val tag = LoadingDialog::class.java.simpleName
        super.show(manager, tag)
    }

    fun updateHint(stringResId: Int) {
        binding?.let {
            var hint : String? = null
            try {
                hint = getString(stringResId)
            } catch (e : Resources.NotFoundException) {
            }

            updateHint(hint)
        }
    }

    fun updateHint(hint: String?) {
        binding?.hint?.let {
            it.text = hint

            if (hint.isNullOrEmpty()) {
                it.visibility = View.GONE
            } else {
                it.visibility = View.VISIBLE
            }
        }
    }
}