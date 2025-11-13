package com.appcarestudio.autolaucher.feature.items.ui.base

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


enum class ScreenState {
    NORMAL,
    LOADING,
    ERROR,
    EMPTY,
    FULL
}


abstract class BaseViewModel  constructor(): ViewModel() {


    val emptyStateVisibilityLiveData = MutableLiveData<Int>(View.GONE)
    val loadingStateVisibilityLiveData = MutableLiveData<Int>(View.GONE)
    val updateAdapterPositionLiveData = MutableLiveData<Int?>()
    val onBackClickLiveData = MutableLiveData<Boolean>()
    val onShowToastLiveData = MutableLiveData<String>()
    val onShowAlertDialogLiveData = MutableLiveData<Pair<String, String>>()
    val onShowProgressLiveData = MutableLiveData<Boolean>()
    val onShowLoadingInsideLiveData = MutableLiveData<Boolean>()
    val onShowErrorLiveData = MutableLiveData<String?>()
    val onDestroyLiveData = MutableLiveData<Boolean>()
    val finishActivityLiveData = MutableLiveData<Boolean>()
    val titleLiveData = MutableLiveData<String>()
    val backTitleLiveData = MutableLiveData<String>()
    val hideBackBtnLiveData = MutableLiveData<Boolean>()
    val hideNextBtnLiveData = MutableLiveData<Boolean>()
    val isHideActionBarLiveData = MutableLiveData<Boolean>(false)
    val showPageLiveData = MutableLiveData<Pair<BaseFragment<*, *>?, Boolean>?>()
    val pushPageLiveData = MutableLiveData<BaseFragment<*, *>?>()
    val pushPageWithoutStackLiveData = MutableLiveData<BaseFragment<*, *>?>()
    val popPageLiveData = MutableLiveData<Boolean>()
    val popPageFragmentLiveData = MutableLiveData<BaseFragment<*, *>?>()
    val addPageLiveData = MutableLiveData<BaseFragment<*, *>?>()

    public val bottomNavigationTabSelect: MutableLiveData<Int> = MutableLiveData<Int>(0)


    fun startLoading() {
        emptyStateVisibilityLiveData.postValue(View.GONE)
        loadingStateVisibilityLiveData.postValue(View.VISIBLE)
    }


    fun openTab(index: Int) {
        bottomNavigationTabSelect.postValue(index)
    }

    fun showError(error: String?) {
        onShowErrorLiveData.postValue(error)
    }

    fun stopLoading() {
        emptyStateVisibilityLiveData.postValue(View.GONE)
        loadingStateVisibilityLiveData.postValue(View.GONE)
    }

    fun showState(state: ScreenState) {
        when (state) {
            ScreenState.NORMAL -> {
                stopLoading()
            }

            ScreenState.LOADING -> {
                startLoading()
            }

            ScreenState.ERROR -> {
                stopLoading()
            }

            ScreenState.EMPTY -> {
                stopLoading()
                emptyStateVisibilityLiveData.postValue(View.VISIBLE)
            }

            ScreenState.FULL -> {
                stopLoading()
            }
        }
    }

    fun showPage(baseFragment: BaseFragment<*, *>?, isBack: Boolean = false) {
        showPageLiveData.postValue(Pair(baseFragment, isBack))
    }

    fun pushPage(baseFragment: BaseFragment<*, *>?) {
        pushPageLiveData.postValue(baseFragment)
    }

    fun pushPageWithoutStack(baseFragment: BaseFragment<*, *>?) {
        pushPageWithoutStackLiveData.postValue(baseFragment)
    }

    fun popPage() {
        popPageLiveData.postValue(true)
    }

    fun popPage(baseFragment: BaseFragment<*, *>?) {
        popPageFragmentLiveData.postValue(baseFragment)
    }

    fun addPage(baseFragment: BaseFragment<*, *>?) {
        addPageLiveData.postValue(baseFragment)
    }

    var isCreated = false

    //  val openTabLiveData = MutableLiveData<BottomNavView.BottomTab?>()
    open fun onCreateview() {

    }


    open fun onViewCreated() {}

    open fun onDestroy() {
        onShowProgressLiveData.value = false
        onDestroyLiveData.value = true
    }

    open fun onResume() {}

    open fun onBackButtonClick(v: View?) {
        onBackClickLiveData.postValue(true)
    }


    fun showProgressDialog() {
        onShowProgressLiveData.postValue(true)
    }

    fun hideProgressDialog() {
        onShowProgressLiveData.postValue(false)
    }

    open fun onRequestPermissionSuccess(requestCode: Int) {

    }

    open fun onRequestPermissionFail(requestCode: Int) {

    }

    open fun isVisibleUnauthBottomBar(): Boolean {
        return false
    }

    open fun setTitle(title: String?) {
        titleLiveData.postValue(title ?: "")
    }

    open fun setBackTitle(title: String?) {
        backTitleLiveData.postValue(title ?: "")
    }

    open fun onSearchText(searchText: String?) {

    }

    open fun setupViews() {

    }


}