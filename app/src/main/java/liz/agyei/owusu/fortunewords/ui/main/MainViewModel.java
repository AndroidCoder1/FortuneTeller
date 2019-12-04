package liz.agyei.owusu.fortunewords.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import liz.agyei.owusu.fortunewords.data.models.Fortune;
import liz.agyei.owusu.fortunewords.data.repository.FortuneRepository;

public class MainViewModel extends ViewModel {

    private FortuneRepository repository;

    public MainViewModel(FortuneRepository repository) {
        this.repository = repository;
    }

    public LiveData<Fortune> getFortuneWords(){
        return this.repository.getFortunes();
    }

    public LiveData<Boolean> isProgressShowing(){
        return repository.isProgressShowing();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.compositeDisposable.clear();
    }
}
