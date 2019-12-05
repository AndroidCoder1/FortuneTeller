package liz.agyei.owusu.fortunewords;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import liz.agyei.owusu.fortunewords.data.models.Fortune;

import static org.junit.Assert.assertEquals;

public class MainActivityTest {


    Fortune fortune;

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        fortune = new Fortune();
        ArrayList<String> fortuneList = new ArrayList<>();
        fortuneList.add("The road to success");
        fortuneList.add(" is a hard road");
        fortuneList.add("        -Lisa Owusu");
        fortune.setFortuneList(fortuneList);
    }

    @After
    public void tearDown() throws Exception {
        fortune = null;
    }

    @Test
    public void isValidFortuneStringShowingInTextView() {
        assertEquals("The road to success\n is a hard road\n        -Lisa Owusu\n", fortune.toString());
    }

    @Test
    public void isTrueForProgressValueObserver() {
        MutableLiveData<Boolean> progressBarObserver = new MutableLiveData<>();
        progressBarObserver.postValue(true);
        assertEquals(true, progressBarObserver.getValue());
    }

    @Test
    public void isFalseForProgressValueObserver() {
        MutableLiveData<Boolean> progressBarObserver = new MutableLiveData<>();
        progressBarObserver.postValue(false);
        assertEquals(false, progressBarObserver.getValue());
    }

    @Test
    public void isValidValuesForFortuneObserver() {
        MutableLiveData<Fortune> fortuneMutableLiveData = new MutableLiveData<>();
        Fortune fortune = new Fortune();
        ArrayList<String> fortuneList = new ArrayList<>();
        fortuneList.add("When I start, I begin");
        fortuneList.add("     -Elizabeth");
        fortune.setFortuneList(fortuneList);

        fortuneMutableLiveData.postValue(fortune);
        assertEquals("When I start, I begin\n     -Elizabeth\n", fortuneMutableLiveData.getValue().toString());
    }


}