package com.appcarestudio.autolaucher.feature.items.ui.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import java.lang.reflect.ParameterizedType


abstract class BaseFragment<VB : ViewDataBinding, VM : BaseViewModel> : Fragment() {
    //public ViewModelProvider.Factory viewModelFactory = new ViewModelProvider.Factory().;
    protected var model: VM? = null
   // private val model by viewModels<VM::class>()
    var rootView: View? = null
    var inflater: LayoutInflater? = null
    var isBack: Boolean = false
    private var progressDialog: ProgressDialog? = null

    var dataBinding: VB? = null

    val isUseDataBinding: Boolean
        get() = true

    @get:LayoutRes
    abstract val layoutRes: Int

    val baseActivity: BaseActivity<*, *>?
        get() = activity as BaseActivity<*, *>?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        //initDagger()
        initViewModel()
        if (model != null) {
            // if(!model.isCreated()){
            //      model.setCreated(true);
            model!!.onCreateview()
            //   }
        }
    }

    open fun setupViews() {
        if (model != null) {
            model!!.setupViews()
        }
    }

    val isNextEnabled: Boolean
        get() = false

    // Create an intent that can start the Speech Recognizer activity
    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        // This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }

    // This callback is invoked when the Speech Recognizer returns.
    // This is where you process the intent and extract the speech text from the intent.
    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val results: List<String>? = data!!.getStringArrayListExtra(
                RecognizerIntent.EXTRA_RESULTS
            )
            val spokenText = results!![0]
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    val isShowLogo: Boolean
        /*   public int getBackIcon() {
                   return R.drawable.ic_arrow_back;
               }*/
        get() = false

    val isShowSearch: Boolean
        get() = true

    fun onNextPressed() {
    }

    var handler: Handler = Handler()

    fun setupToolbar() {
        val toolBar: LinearLayout? = null


        /*  if (rootView != null) {
            toolBar = rootView.findViewById(R.id.toolbar);
        }*/


        /*    model.getOnShowErrorLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {
                if (string != null) {
                    if (!string.isEmpty()) {
                        Utils.Companion.exceptionToast(requireActivity(), string);
                    }

                }
            }
        });*/
        model!!.onBackClickLiveData.observe(this
        ) { aBoolean ->
            if (aBoolean) {
                onBackPressed()
            }
        }
        /* if (toolBar != null) {
            LayoutToolbarBinding toolbarBinding = DataBindingUtil.bind(toolBar);
            // val binding  : ToolbarBinding? = DataBindingUtil.bind(view)
            ToolbarViewModel modelToolbar = ViewModelProvider.AndroidViewModelFactory.getInstance(App.getInstance()).create(ToolbarViewModel.class);
            toolbarBinding.setViewModel(modelToolbar);
            modelToolbar.getOnBackClickLiveData().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {
                    if (aBoolean) {
                        onBackPressed();
                    }
                }
            });
            model.getTitleLiveData().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    modelToolbar.getTitleLiveData().postValue(s);
                }
            });

            model.getBackTitleLiveData().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    modelToolbar.getBackTitleLiveData().postValue(s);
                }
            });

            model.getHideBackBtnLiveData().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {

                    if (aBoolean) {
                        modelToolbar.getBackVisibilityLiveData().postValue(View.VISIBLE);


                    } else {

                        modelToolbar.getBackVisibilityLiveData().postValue(View.INVISIBLE);
                    }

                }
            });

            modelToolbar.getOnNextClickLiveData().observe(this, new Observer<Boolean>() {
                @Override
                public void onChanged(Boolean aBoolean) {

                    if (aBoolean) {
                        modelToolbar.getOnNextClickLiveData().setValue(false);
                        onNextPressed();
                    }
                }
            });




        }*/
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.inflater = inflater
        try {
            rootView = inflater.inflate(layoutRes, null, false)
            if (isUseDataBinding) {
                dataBinding = DataBindingUtil.bind(rootView!!)
                dataBinding!!.lifecycleOwner = this
            }

            setModel()
            if (savedInstanceState != null) {
                getSavedBundle(savedInstanceState)
            }
            model!!.hideBackBtnLiveData.postValue(isBack)
            model!!.hideNextBtnLiveData.postValue(isNextEnabled)

            setupViews()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return rootView
    }

    fun getSavedBundle(savedInstanceState: Bundle) {
    }

    private fun baseSubscribe() {
        model!!.showPageLiveData.observe(
            viewLifecycleOwner
        ) {
            if (it != null) {
                model!!.showPageLiveData.value = null
                baseActivity?.showPage(
                    it.first,
                    it.second
                )
            }
        }

        model!!.pushPageLiveData.observe(
            viewLifecycleOwner
        ) {
            if (it != null) {
                model!!.pushPageLiveData.setValue(null)
                baseActivity?.pushPage(it)
            }
        }
        model!!.popPageFragmentLiveData.observe(
            viewLifecycleOwner
        ) { value ->
            if (value != null) {
                model!!.popPageFragmentLiveData.setValue(null)
                baseActivity?.popPage(value)
            }
        }


        model!!.pushPageWithoutStackLiveData.observe(
            viewLifecycleOwner
        ) {
            if (it != null) {
                model!!.pushPageWithoutStackLiveData.setValue(null)
                baseActivity?.pushPageWithoutStack(it)
            }
        }

        model!!.popPageLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                model!!.popPageLiveData.setValue(null)
                baseActivity?.popPage()
            }
        }


        model!!.addPageLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                model!!.addPageLiveData.setValue(null)
                baseActivity?.addPage(it)
            }
        }

        /*     model.getBottomNavigationTabSelect().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (getBaseActivity() instanceof MainActivity){
                    if(integer!=0){
                        model.getBottomNavigationTabSelect().setValue(0);
                        ((MainActivity) getBaseActivity() ).model.getBottomNavigationTabSelect().postValue(integer);
                    }
                }
            }
        });*/
    }

    private fun initViewModel() {
        val vmClass =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<VM>
       // model by viewModels<vmClass>()
        model = ViewModelProvider(this).get<VM>(vmClass)
        setupModelArgs()
        //  model = new ViewModelProvider(this, ViewModelProvider.Factory.from()).get(vmClass);
    }

    open fun setupModelArgs(){

    }
    abstract fun subscribe()

    abstract fun setModel()

  /*  *//**
     * add inside for fragment with viewModel "App.getInstance().getAppComponent().inject(this);"
     *//*
    abstract fun initDagger()*/

    fun onFragmentResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model!!.onViewCreated()
        model!!.onShowToastLiveData.observe(
            viewLifecycleOwner
        ) { message: String? ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        model!!.onShowAlertDialogLiveData.observe(
            viewLifecycleOwner
        ) {
            if (it != null) {
                /*    model.getOnShowAlertDialogLiveData().setValue(null);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setMessage(stringStringPair.getSecond());
                        builder.setTitle(stringStringPair.getFirst());
                        builder.setPositiveButton(R.string.ok_button, null);
                        builder.show();*/
            }
        }

        model!!.onShowProgressLiveData.observe(
            viewLifecycleOwner
        ) { show: Boolean ->
            if (show) {
                showProgressDialog()
            } else hideProgressDialog()
        }

        model!!.finishActivityLiveData.observe(
            viewLifecycleOwner
        ) { isFinish: Boolean ->
            if (isFinish) {
                if (activity != null) {
                    requireActivity().finish()
                }
            }
        }

        /* model.getOpenTabLiveData().observe(getViewLifecycleOwner(), bottomTab -> {
     if (bottomTab != null) {
         getBaseActivity().openTab(bottomTab);
         model.getOpenTabLiveData().postValue(null);
     }
 });*/
        baseSubscribe()
        subscribe()
    }

    override fun onDestroy() {
        model!!.onDestroy()
        super.onDestroy()
    }


    fun showProgressDialog() {
        hideProgressDialog()
        try {
            progressDialog = ProgressDialog(requireContext())
            progressDialog!!.setTitle("please_wait")
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog!!.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onBackPressed() {
        baseActivity!!.onBackPressed()
    }


    var isBottomNavigationEnabled: Boolean = true


    fun setupBottomNavigationView() {
        if (activity != null) {
            //  if (getActivity() instanceof MainActivity) {
            //   ((MainActivity) getActivity()).setBottomNavigationEnabled(isBottomNavigationEnabled());
            //  }
        }
    }


    val appBarLayout: AppBarLayout?
        get() {
            if (activity != null) {
                //   return ((BaseActivityOld) getActivity()).getAppBarLayout();
            }
            return null
        }


    fun setupUnauthBottomBar() {
        // boolean isVisible = model.isVisibleUnauthBottomBar() && !AppPref.getInstance().isLoggedIn();
        val isVisible = false
        // getBaseActivity().model.isVisibleUnauthBottomBar().postValue(new Pair<Boolean, Boolean>(isVisible, false));
    }

    override fun onResume() {
        //  getMainActivity().setOnBackPressedListener(getOnBackPressedListener());
        super.onResume()
        setupToolbar()
        setupUnauthBottomBar()
        setupBottomNavigationView()
        model!!.onResume()
    }


    companion object {
        private const val SPEECH_REQUEST_CODE = 101
    }
}