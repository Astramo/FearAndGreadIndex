package com.example.myrxjava.api;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.Module;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;

@Module
public class MainViewModel extends ViewModel {
    private ApiClass apiClass;
    private BehaviorSubject<Boolean> progress = BehaviorSubject.create();

    private MutableLiveData<FAGResponse> liveData;

    @Inject
    public MainViewModel(ApiClass apiClass) {
        this.apiClass = apiClass;
    }

    public MutableLiveData<FAGResponse> getLiveData() {
        return liveData;
    }

    public void setLiveData(MutableLiveData<FAGResponse> liveData) {
        this.liveData = liveData;
    }

    public Single<FAGResponse> getFaq() {
        progress.onNext(true);
        return apiClass.getFag()
                .doFinally(() -> progress.onNext(false));
    }


    public BehaviorSubject<Boolean> getProgress() {
        return progress;
    }


}
