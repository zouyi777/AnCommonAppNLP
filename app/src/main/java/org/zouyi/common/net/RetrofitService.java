package org.zouyi.common.net;

import retrofit2.Call;
import retrofit2.http.GET;
import rx.Observable;

public interface RetrofitService {
    //只使用了Retrofit
    @GET("/hello")
    Call<String> getHello();

    //使用了Retrofit+RxJava
    @GET("/hello")
    Observable<String> getHelloRx();
}
