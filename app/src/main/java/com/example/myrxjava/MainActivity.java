package com.example.myrxjava;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.myrxjava.api.DataItem;
import com.example.myrxjava.api.FAGResponse;
import com.example.myrxjava.api.MainViewModel;
import com.example.myrxjava.api.ViewModelComponent;
import com.example.myrxjava.databinding.ActivityMainBinding;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private final CompositeDisposable disposable = new CompositeDisposable();
    ProgressDialog dialog;


    @Inject
    MainViewModel viewModel;

    ActivityMainBinding mainBinding;
    MutableLiveData<FAGResponse> liveData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }


        ViewModelComponent component = DaggerViewModelComponent.create();
        component.inject(this);


//        viewModel = ViewModelProviders.of
        mainBinding.about.setOnClickListener(v -> {
            about();
        });


        if (liveData == null)
            getDataFromServer();

    }

    void getDataFromServer() {


        disposable.add(viewModel.getProgress().subscribe(aBoolean -> {
            if (aBoolean) initDialog(this);
            else if (dialog != null) dialog.dismiss();
        }));

        viewModel.getFaq().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<FAGResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        disposable.add(d);
                    }

                    @Override
                    public void onSuccess(@NonNull FAGResponse fagResponse) {
                        liveData = new MutableLiveData<>();
                        liveData.setValue(fagResponse);
                        viewModel.setLiveData(liveData);
                        initLiveData();


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (disposable != null)
            disposable.clear();

    }

    void initLiveData() {
        viewModel.getLiveData().observe(this,
                fagResponse -> {
                    setData(fagResponse.getData());
                });
    }

    void initDialog(Context context) {
        dialog = new ProgressDialog(context);
        dialog.setTitle("Please wait");
        dialog.show();
    }


    void setData(List<DataItem> list) {
        mainBinding.speedMeter.setSpeed(Float.parseFloat(list.get(0).getValue()));
        int[] ints = Utils.splitToComponentTimes(BigDecimal.valueOf(Long.parseLong(
                list.get(0).getTimeUntilUpdate())));
        mainBinding.update.setText(String.format("Time until Update %s:%s",
                ints[0], ints[1]));
        mainBinding.speedMeter.setMeterType(list.get(0).getValueClassification());

        List<DataItem> newList = new ArrayList<>();
        newList.add(list.get(1));
        newList.add(list.get(7));
        newList.add(list.get(list.size() - 1));
        Adapter adapter = new Adapter(newList);
        mainBinding.recycler.setAdapter(adapter);
        OverScrollDecoratorHelper.setUpOverScroll(mainBinding.recycler, OverScrollDecoratorHelper.ORIENTATION_VERTICAL);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mainBinding.scroll.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                if (scrollY == 0)
                    mainBinding.myAppbar.setElevation(0f);
                else mainBinding.myAppbar.setElevation(10f);
            });
        }


    }

Tight Coupled7 6
    void about() {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("About me")
                .setMessage("Developed By Mohammad Ahmadi\ndevfarsi@yahoo.com")
                .setIcon(R.drawable.ic_android_black_24dp).create();
        dialog.show();

    }

}