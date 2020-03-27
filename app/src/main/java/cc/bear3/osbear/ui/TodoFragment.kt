package cc.bear3.osbear.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import cc.bear3.osbear.R
import cc.bear3.osbear.databinding.FragmentTodoBinding
import cc.bear3.osbear.ui.common.BaseFragment

/**
 * Description:
 * Author: TT
 * Since: 2020-03-23
 */
class TodoFragment : BaseFragment() {
    companion object {
        fun newInstance() : TodoFragment {
            return TodoFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTodoBinding.inflate(inflater, container, false)

        binding.text.setOnClickListener {
            findNavController().navigate(R.id.login_fragment)
        }

        return binding.root
    }
}