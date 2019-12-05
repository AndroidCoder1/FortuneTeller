package liz.agyei.owusu.fortunewords.data.repository;

import androidx.lifecycle.LiveData;

import io.reactivex.disposables.CompositeDisposable;
import liz.agyei.owusu.fortunewords.data.api.RetrofitFortuneInterface;
import liz.agyei.owusu.fortunewords.data.models.Fortune;

public class FortuneRepository {
    private static FortuneRepository instance;
    RetrofitFortuneInterface api;
    private FortunesDataSource fortunesDataSource;
    public CompositeDisposable compositeDisposable = new CompositeDisposable();


    private FortuneRepository(RetrofitFortuneInterface api) {
        this.api = api;
        getFortunesFromAPI(compositeDisposable);
    }


    public static FortuneRepository getInstance(RetrofitFortuneInterface api) {
        if(instance == null) {
            instance = new FortuneRepository(api);
        }
        return instance;
    }

    public LiveData<Fortune> getFortunes(){
        return fortunesDataSource.getFortunes();
    }

    public LiveData<Boolean> isProgressShowing(){
        return fortunesDataSource.getIsProgressShowing();
    }

    public void getFortunesFromAPI(CompositeDisposable compositeDisposable){
        fortunesDataSource = new FortunesDataSource(api, compositeDisposable);
        fortunesDataSource.getFortunesFromAPI();
    }
}
