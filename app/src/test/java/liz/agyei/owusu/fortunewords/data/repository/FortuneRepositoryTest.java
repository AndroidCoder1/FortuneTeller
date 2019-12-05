package liz.agyei.owusu.fortunewords.data.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;
import liz.agyei.owusu.fortunewords.TestLiveDataObserver;
import liz.agyei.owusu.fortunewords.data.api.RetrofitFortuneInterface;
import liz.agyei.owusu.fortunewords.data.models.Fortune;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class FortuneRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    FortuneRepository repository;

    RetrofitFortuneInterface api;

    FortunesDataSource dataSource;

    @Test
    public void testIfGetFortunesIsNotNull() {
        Fortune fortune = createFortuneObject();

        api = spy(mock(RetrofitFortuneInterface.class, RETURNS_DEEP_STUBS));
        repository = spy(FortuneRepository.getInstance(api));
        dataSource = spy(new FortunesDataSource(api, new CompositeDisposable()));

        doReturn(Single.just(fortune)).
                when(api).getFortunes();

        Observable<Fortune> obs = api.getFortunes().toObservable();

        TestObserver<Fortune> testObserver = obs.test();

        assertTrue(testObserver.values() != null);

        testObserver.dispose();

    }


    @Test
    public void testIfGetFortunesIsValid() {
        Fortune fortune = createFortuneObject();

        api = spy(mock(RetrofitFortuneInterface.class, RETURNS_DEEP_STUBS));
        repository = spy(FortuneRepository.getInstance(api));
        dataSource = spy(new FortunesDataSource(api, new CompositeDisposable()));

        doReturn(Single.just(fortune)).
                when(api).getFortunes();

        Observable<Fortune> obs = api.getFortunes().toObservable();

        TestObserver<Fortune> testObserver = obs.test();

        System.out.println(testObserver.values());

        assertEquals("When I start, I begin\n     -Elizabeth\n", testObserver.values().get(0).toString());

        testObserver.dispose();

    }

    private Fortune createFortuneObject() {
        Fortune fortune = new Fortune();
        ArrayList<String> fortuneList = new ArrayList<>();
        fortuneList.add("When I start, I begin");
        fortuneList.add("     -Elizabeth");
        fortune.setFortuneList(fortuneList);
        return fortune;
    }

    @Test
    public void testIsProgressShowingOnStartOfRequestAndHidingOnEndOfRequest() {

        TestScheduler testScheduler = new TestScheduler();
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> testScheduler);

        Fortune fortune = createFortuneObject();

        api = spy(mock(RetrofitFortuneInterface.class, RETURNS_DEEP_STUBS));
        repository = spy(FortuneRepository.getInstance(api));
        dataSource = spy(new FortunesDataSource(api, new CompositeDisposable()));


        TestLiveDataObserver<Boolean> progressObserver = new TestLiveDataObserver();
        repository.isProgressShowing().observeForever(progressObserver);
        doReturn(Single.just(fortune)).
                when(api).getFortunes();
        Observable<Fortune> obs = api.getFortunes().toObservable();
        obs.test().values();
        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS);
        assertEquals(progressObserver.getItems().get(0)  , true);
        assertEquals(progressObserver.getItems().get(1)  , false);
    }


    @Test
    public void testIsRequestRetried() {

        api = spy(mock(RetrofitFortuneInterface.class, RETURNS_DEEP_STUBS));
        repository = spy(FortuneRepository.getInstance(api));
        dataSource = spy(new FortunesDataSource(api, new CompositeDisposable()));

        TestObserver testObserver = new TestObserver();
        AtomicInteger atomicCounter = new AtomicInteger(0);
        Observable<Fortune> obs = api.getFortunes().toObservable();
        obs.error(() -> {
                    atomicCounter.incrementAndGet();
                    return null;
                })
                .retry((integer, throwable) -> integer < 4)
                .subscribe(testObserver);

        testObserver.assertNotComplete();
        testObserver.assertNoValues();
        assertTrue("should call 4 times", atomicCounter.get() == 4);
        testObserver.dispose();
    }
}