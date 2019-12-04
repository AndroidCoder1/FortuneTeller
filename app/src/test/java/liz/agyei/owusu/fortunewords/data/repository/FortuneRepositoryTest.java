package liz.agyei.owusu.fortunewords.data.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import liz.agyei.owusu.fortunewords.data.api.RetrofitFortuneInterface;

import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class FortuneRepositoryTest {

    @Mock
    FortuneRepository repository;

    @Mock
    RetrofitFortuneInterface api;


    @Test
    public void getFortunes() {
        doReturn(api.getFortunes()).when(repository).getFortunes();
    }

    @Test
    public void isProgressShowing() {
    }

    @Test
    public void getFortunesFromAPI() {
    }
}