package liz.agyei.owusu.fortunewords.data.api;

import java.util.List;

import io.reactivex.Single;
import liz.agyei.owusu.fortunewords.data.models.Fortune;
import retrofit2.http.GET;

public interface RetrofitFortuneInterface {

    @GET("/fortune")
    Single<Fortune> getFortunes();
}
