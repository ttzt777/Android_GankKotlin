package cc.bear3.osbear.ui.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Description:
 * Author: TT
 * Since: 2020-02-17
 */
abstract class BaseViewModel : ViewModel() {
    protected val pageState = MutableLiveData(PageState.Loading)

    protected val firstRequest = MutableLiveData(true)

    override fun onCleared() {
        super.onCleared()

    }
}