package liz.agyei.owusu.fortunewords.data.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;
import liz.agyei.owusu.fortunewords.data.api.RetrofitFortuneInterface;
import liz.agyei.owusu.fortunewords.data.models.Fortune;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FortuneRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    FortuneRepository repository;

    RetrofitFortuneInterface api;

    FortunesDataSource dataSource;

    @Test
    public void getFortunes() {
        TestScheduler testScheduler = new TestScheduler();
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> testScheduler);

        api = spy(mock(RetrofitFortuneInterface.class, RETURNS_DEEP_STUBS));
        repository = spy(FortuneRepository.getInstance(api));
        dataSource = spy(new FortunesDataSource(api, new CompositeDisposable()));
        Observable<Fortune> obs = api.getFortunes().toObservable();
        TestObserver<Fortune> testObserver = TestObserver.create();
        obs.subscribe(testObserver);
        testScheduler.triggerActions();
        assertTrue(dataSource.getFortunes() != null);

    }

    @Test
    public void isProgressShowing() {

        api = spy(mock(RetrofitFortuneInterface.class, RETURNS_DEEP_STUBS));
        repository = spy(FortuneRepository.getInstance(api));

        Observer<Fortune> fortuneObserver = mock(Observer.class);
        repository.getFortunes().observeForever(fortuneObserver);

        MutableLiveData<Fortune> fortunes =  new MutableLiveData<>();
        Fortune fortune = new Fortune();
        ArrayList<String> fortuneList = new ArrayList<>();
        fortuneList.add("When I start, I begin");
        fortuneList.add("     -Elizabeth");
        fortune.setFortuneList(fortuneList);
        fortunes.postValue(fortune);
        when(repository.getFortunes()).thenReturn(fortunes);
        assertTrue(repository.isProgressShowing().getValue());

    }
}