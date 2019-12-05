package liz.agyei.owusu.fortunewords.data.models;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import liz.agyei.owusu.fortunewords.utils.Configs;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class FortuneTest {

    private List<String> fortunes = new ArrayList<>();

    @Mock
    Fortune fortune;

    @Before
    public void setUp() throws Exception {
        fortune = spy(mock(Fortune.class));
        fortunes = new ArrayList<>();
        fortunes.add("When I start, I begin");
        fortunes.add("     -Elizabeth");
        fortune.setFortuneList(fortunes);

        MockitoAnnotations.initMocks(this);
       // when(fortune.getFortuneList()).thenReturn(fortunes);
    }


    @Test
    public void testIfFortunesIsValid(){
        fortune = spy(mock(Fortune.class));
        fortunes = new ArrayList<>();
        fortunes.add("When I start, I begin");
        fortunes.add("     -Elizabeth");
        fortune.setFortuneList(fortunes);
        assertEquals(2, fortunes.size());
        assertEquals("When I start, I begin", fortunes.get(0));
        assertEquals("     -Elizabeth", fortunes.get(1));
    }

    @Test
    public void testIfToStringReturnsDefaultStringIfFortuneListIsNull(){
        List<String> fortunes = null;
        fortune = new Fortune();
        fortune.setFortuneList(fortunes);
        assertEquals(Configs.DEFAULT_ADVICE, fortune.toString());
    }

    @Test
    public void testIfToStringReturnsDefaultStringIfFortuneListIsNotNull(){
        fortune = new Fortune();
        fortunes = new ArrayList<>();
        fortunes.add("When I start, I begin");
        fortunes.add("     -Elizabeth");
        fortune.setFortuneList(fortunes);
        assertEquals("When I start, I begin\n     -Elizabeth\n", fortune.toString());
    }

}