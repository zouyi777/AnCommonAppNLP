package org.zouyi.common.net;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface RetrofitService {
    //只使用了Retrofit
    @GET("/hello")
    Call<String> getHello();

    //使用了Retrofit+RxJava
    @GET("/hello")
    Observable<String> getHelloRx();

    //使用了Retrofit+RxJava的Get请求
    @GET("/hello_get")
    Observable<String> getHelloRxGet(@Query("username")String username);

    //使用了Retrofit+RxJava的Post请求
    @POST("/hello_post")
    Observable<String> getHelloRxPost(@Query("username")String username);

    //使用了Retrofit+RxJava的Post请求
    @FormUrlEncoded
    @POST("/hello_post_form")
    Observable<String> getHelloRxPostForm(@Field("username")String username);

    //使用了Retrofit+RxJava的Post请求古诗预测接口
    @FormUrlEncoded
    @POST("/venala2ancnt")
    Observable<String> getVenala2ancnt(@Field("seedwords")String seedwords);
}
