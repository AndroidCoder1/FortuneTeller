import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.TestScheduler;
import liz.agyei.owusu.fortunewords.data.api.RetrofitFortuneInterface;
import liz.agyei.owusu.fortunewords.data.models.Fortune;
import liz.agyei.owusu.fortunewords.data.repository.FortuneRepository;
import liz.agyei.owusu.fortunewords.ui.main.MainViewModel;
import liz.agyei.owusu.fortunewords.utils.Configs;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.spy;

@RunWith(JUnit4.class)
public class MainViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    FortuneRepository repository;


    RetrofitFortuneInterface apiClient;

    private MainViewModel viewModel;

    @Mock
    LifecycleOwner lifecycleOwner;

    Lifecycle lifecycle;


    @Before
    public void setUp() throws Exception {
        RxJavaPlugins.reset();
        MockitoAnnotations.initMocks(this);
        lifecycle = new LifecycleRegistry(lifecycleOwner);
        viewModel = new MainViewModel(repository);
    }


    @Test
    public void testAfter10SecondsExceeded() throws Exception {

        TestScheduler testScheduler = new TestScheduler();
        RxJavaPlugins.setComputationSchedulerHandler(scheduler -> testScheduler);

        apiClient = spy(Mockito.mock(RetrofitFortuneInterface.class, RETURNS_DEEP_STUBS));
        repository = spy(FortuneRepository.getInstance(apiClient));
        viewModel = spy(new MainViewModel(repository));
        Observable<Fortune> obs = apiClient.getFortunes().toObservable();
        TestObserver<Fortune> testObserver = TestObserver.create();
        obs.subscribe(testObserver);
        testScheduler.advanceTimeBy(10, TimeUnit.SECONDS);
        assertEquals(viewModel.getFortuneWords().getValue().toString().trim(), Configs.DEFAULT_ADVICE);
    }


    @After
    public void tearDown() throws Exception {
        apiClient = null;
        viewModel = null;
        RxJavaPlugins.reset();
    }
}