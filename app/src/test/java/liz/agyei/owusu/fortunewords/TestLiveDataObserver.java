package liz.agyei.owusu.fortunewords;

import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

public class TestLiveDataObserver<T> implements Observer<T> {

    private List<T> items = new ArrayList<>();

    @Override
    public void onChanged(T t) {
        items.add(t);
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> corridors) {
        this.items = corridors;
    }
}

