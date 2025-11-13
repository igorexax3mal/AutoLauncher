package com.appcarestudio.autolaucher.feature.items.ui.base;/*
package com.appcarestudio.fanserials.feature.fanserial.ui.base;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.appbar.AppBarLayout;


import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import kotlin.Pair;

public abstract class BaseFragment<VB extends ViewDataBinding, VM extends BaseViewModel> extends Fragment {


    //public ViewModelProvider.Factory viewModelFactory = new ViewModelProvider.Factory().;
    protected VM model;

    public View rootView;
    public LayoutInflater inflater;
    private boolean isBack;
    private ProgressDialog progressDialog;

    VB dataBinding;

    public VB getDataBinding() {
        return dataBinding;
    }

    public boolean isUseDataBinding() {
        return true;
    }

    public boolean isBack() {
        return isBack;
    }

    public void setBack(boolean back) {
        isBack = back;
    }

    @LayoutRes
    public abstract int getLayoutRes();

    public BaseActivity getBaseActivity() {
        return (BaseActivity) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        initDagger();
        initViewModel();
        if (model != null) {
           // if(!model.isCreated()){
          //      model.setCreated(true);
                model.onCreateview();
         //   }
        }

    }

    public void setupViews() {
        if (model != null) {
            model.setupViews();
        }
    }

    public boolean isNextEnabled() {
        return false;
    }

    private static final int SPEECH_REQUEST_CODE = 101;

    // Create an intent that can start the Speech Recognizer activity
    private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
// This starts the activity and populates the intent with the speech text.
        startActivityForResult(intent, SPEECH_REQUEST_CODE);
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

 */
/*   public int getBackIcon() {
        return R.drawable.ic_arrow_back;
    }*//*


    public boolean isShowLogo() {
        return false;
    }

    public boolean isShowSearch() {
        return true;
    }

    public void onNextPressed() {

    }

    Handler handler = new Handler();

    public void setupToolbar() {
        LinearLayout toolBar = null;
      */
/*  if (rootView != null) {
            toolBar = rootView.findViewById(R.id.toolbar);
        }*//*



    */
/*    model.getOnShowErrorLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String string) {
                if (string != null) {
                    if (!string.isEmpty()) {
                        Utils.Companion.exceptionToast(requireActivity(), string);
                    }

                }
            }
        });*//*


        model.getOnBackClickLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    onBackPressed();
                }
            }
        });
       */
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




        }*//*

    }



    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        try {
            rootView = inflater.inflate(getLayoutRes(), null, false);
            if (isUseDataBinding()) {
                dataBinding = DataBindingUtil.bind(rootView);
                dataBinding.setLifecycleOwner(this);
            }

            setModel();
            if (savedInstanceState != null) {
                getSavedBundle(savedInstanceState);
            }
            model.getHideBackBtnLiveData().postValue(isBack());
            model.getHideNextBtnLiveData().postValue(isNextEnabled());

            setupViews();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return rootView;
    }

    public void getSavedBundle(@NonNull Bundle savedInstanceState) {

    }

    private void baseSubscribe() {


        model.getShowPageLiveData().observe(getViewLifecycleOwner(), new Observer<Pair<BaseFragment<?, ?>, Boolean>>() {
            @Override
            public void onChanged(Pair<BaseFragment<?, ?>, Boolean> baseFragmentBooleanPair) {
                if (baseFragmentBooleanPair != null) {
                    model.getShowPageLiveData().setValue(null);
                    getBaseActivity().showPage(baseFragmentBooleanPair.getFirst(), baseFragmentBooleanPair.getSecond());
                }
            }
        });

        model.getPushPageLiveData().observe(getViewLifecycleOwner(), new Observer<BaseFragment<?, ?>>() {
            @Override
            public void onChanged(BaseFragment<?, ?> baseFragment) {
                if (baseFragment != null) {
                    model.getPushPageLiveData().setValue(null);
                    getBaseActivity().pushPage(baseFragment);
                }
            }
        });
        model.getPopPageFragmentLiveData().observe(getViewLifecycleOwner(), new Observer<BaseFragment<?, ?>>() {
            @Override
            public void onChanged(BaseFragment<?, ?> baseFragment) {
                if (baseFragment != null) {
                    model.getPopPageFragmentLiveData().setValue(null);
                    getBaseActivity().popPage(baseFragment);
                }
            }
        });


        model.getPushPageWithoutStackLiveData().observe(getViewLifecycleOwner(), new Observer<BaseFragment<?, ?>>() {
            @Override
            public void onChanged(BaseFragment<?, ?> baseFragment) {
                if (baseFragment != null) {
                    model.getPushPageWithoutStackLiveData().setValue(null);
                    getBaseActivity().pushPageWithoutStack(baseFragment);
                }
            }
        });

        model.getPopPageLiveData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean baseFragment) {
                if (baseFragment != null) {
                    model.getPopPageLiveData().setValue(null);
                    getBaseActivity().popPage();
                }
            }
        });


        model.getAddPageLiveData().observe(getViewLifecycleOwner(), new Observer<BaseFragment<?, ?>>() {
            @Override
            public void onChanged(BaseFragment<?, ?> baseFragment) {
                if (baseFragment != null) {
                    model.getAddPageLiveData().setValue(null);
                    getBaseActivity().addPage(baseFragment);
                }
            }
        });

   */
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
        });*//*

    }

    private void initViewModel() {
        Class<VM> vmClass = (Class<VM>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];

        model = new ViewModelProvider(this).get(vmClass);
      //  model = new ViewModelProvider(this, ViewModelProvider.Factory.from()).get(vmClass);
    }

    public abstract void subscribe();

    public abstract void setModel();

    */
/**
     * add inside for fragment with viewModel "App.getInstance().getAppComponent().inject(this);"
     *//*

    public abstract void initDagger();

    public void onFragmentResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        model.onViewCreated();
        model.getOnShowToastLiveData().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        });

        model.getOnShowAlertDialogLiveData().observe(getViewLifecycleOwner(), new Observer<Pair<String, String>>() {
            @Override
            public void onChanged(Pair<String, String> stringStringPair) {
                if (stringStringPair != null) {
                */
/*    model.getOnShowAlertDialogLiveData().setValue(null);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage(stringStringPair.getSecond());
                    builder.setTitle(stringStringPair.getFirst());
                    builder.setPositiveButton(R.string.ok_button, null);
                    builder.show();*//*

                }
            }
        });

        model.getOnShowProgressLiveData().observe(getViewLifecycleOwner(), show -> {
            if (show) {
                showProgressDialog();
            } else hideProgressDialog();
        });

        model.getFinishActivityLiveData().observe(getViewLifecycleOwner(), isFinish -> {
            if (isFinish) {
                if (getActivity() != null) {
                    getActivity().finish();
                }
            }
        });
       */
/* model.getOpenTabLiveData().observe(getViewLifecycleOwner(), bottomTab -> {
            if (bottomTab != null) {
                getBaseActivity().openTab(bottomTab);
                model.getOpenTabLiveData().postValue(null);
            }
        });*//*


        baseSubscribe();
        subscribe();

    }

    @Override
    public void onDestroy() {
        model.onDestroy();
        super.onDestroy();
    }


    public void showProgressDialog() {
        hideProgressDialog();
        try {
            progressDialog = new ProgressDialog(requireContext());
            progressDialog.setTitle("please_wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onBackPressed() {
        getBaseActivity().onBackPressed();
    }


    boolean isBottomNavigationEnabled = true;


    public boolean isBottomNavigationEnabled() {
        return isBottomNavigationEnabled;
    }

    public void setupBottomNavigationView() {
        if (getActivity() != null) {
          //  if (getActivity() instanceof MainActivity) {
                //   ((MainActivity) getActivity()).setBottomNavigationEnabled(isBottomNavigationEnabled());
          //  }
        }
    }


    public AppBarLayout getAppBarLayout() {
        if (getActivity() != null) {
            //   return ((BaseActivityOld) getActivity()).getAppBarLayout();
        }
        return null;
    }


    public void setupUnauthBottomBar() {
        // boolean isVisible = model.isVisibleUnauthBottomBar() && !AppPref.getInstance().isLoggedIn();
        boolean isVisible = false;
       // getBaseActivity().model.isVisibleUnauthBottomBar().postValue(new Pair<Boolean, Boolean>(isVisible, false));
    }

    @Override
    public void onResume() {
        //  getMainActivity().setOnBackPressedListener(getOnBackPressedListener());
        super.onResume();
        setupToolbar();
        setupUnauthBottomBar();
        setupBottomNavigationView();
        model.onResume();
    }


}
*/
