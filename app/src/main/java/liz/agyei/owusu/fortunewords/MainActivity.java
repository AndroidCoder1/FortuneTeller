package liz.agyei.owusu.fortunewords;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import liz.agyei.owusu.fortunewords.data.api.RetrofitFortuneClient;
import liz.agyei.owusu.fortunewords.data.repository.FortuneRepository;
import liz.agyei.owusu.fortunewords.databinding.MainActivityBinding;
import liz.agyei.owusu.fortunewords.ui.main.MainViewModel;

public class MainActivity extends AppCompatActivity {
    MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Binding to layout and attaching to ViewModel and Lifecycle
        MainActivityBinding binding =  DataBindingUtil.setContentView(this, R.layout.main_activity);
        mainViewModel = new MainViewModel(FortuneRepository.getInstance(new RetrofitFortuneClient().getClient()));
        binding.setViewmodel(mainViewModel);
        binding.setLifecycleOwner(this);

    }

}
