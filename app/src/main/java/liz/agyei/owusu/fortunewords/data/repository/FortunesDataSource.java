package liz.agyei.owusu.fortunewords.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import liz.agyei.owusu.fortunewords.data.api.RetrofitFortuneInterface;
import liz.agyei.owusu.fortunewords.data.models.Fortune;
import liz.agyei.owusu.fortunewords.utils.Configs;

public class FortunesDataSource {

    RetrofitFortuneInterface api;
    CompositeDisposable compositeDisposable;
    private MutableLiveData<Fortune> _fortunes = new MutableLiveData<>();
    private MutableLiveData<Boolean> _isProgressShowing = new MutableLiveData<>();

    public FortunesDataSource(RetrofitFortuneInterface api, CompositeDisposable compositeDisposable){
        this.api = api;
        this.compositeDisposable = compositeDisposable;
    }

    public LiveData<Fortune> getFortunes() {
        return _fortunes;
    }

    public LiveData<Boolean> getIsProgressShowing() {
        return _isProgressShowing;
    }

    public void getFortunesFromAPI(){
        //Show ProgressDialog at the start of Activity
        _isProgressShowing.postValue(true);
        compositeDisposable.add(
            api.getFortunes()
                    .subscribeOn(Schedulers.io())
                    //Retry till we get valid response
                    .retry(throwable ->{
                        System.out.println(">>>"+throwable.getMessage());
                       return true;
                    } )
                    //Cancel the thread after 10 seconds and show default message
                    .timeout(10, TimeUnit.SECONDS)
                    .subscribe(fortunes -> {
                        System.out.println("Fortunes" + fortunes);
                        _isProgressShowing.postValue(false);
                        _fortunes.postValue(fortunes);
                    }, throwable -> {
                        System.out.println("Fortunes Error"+throwable.getMessage());
                        _isProgressShowing.postValue(false);
                        _fortunes.postValue(hardCodedFortuneAdvice());
                    })
        );
        //fortunes = _fortunes;
        //return fortunes;
    }

    private Fortune hardCodedFortuneAdvice(){
        Fortune fortune = new Fortune();
        ArrayList list = new ArrayList();
        list.add(Configs.DEFAULT_ADVICE);
        fortune.setFortuneList(list);
        return fortune;
    }



}
