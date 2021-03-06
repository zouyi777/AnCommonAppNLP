package org.zouyi.common.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.zouyi.common.BaseFragment;
import org.zouyi.common.R;
import org.zouyi.common.net.RetrofitService;
import org.zouyi.common.net.ToStringConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private EditText etMy;
    private Button btnRequest;
    private TextView tvDispaly;

    public static HomeFragment newInstance(String data) {
        Bundle args = new Bundle();
        args.putString("key", data);
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        view =inflater.inflate(R.layout.fragment_home, container, false);
        etMy = view.findViewById(R.id.editText);
        btnRequest = view.findViewById(R.id.btn_request);
        btnRequest.setOnClickListener(this);
        tvTitle.setText(getArguments().getString("key"));
        tvDispaly = view.findViewById(R.id.tv_dispaly);
        flContent.addView(view);
        return baseView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_request:
//                loadDataByRetrofit();
                loadDataByRetrofitRx();
                break;
            default:
                break;
        }
    }

    //使用Retrofit从服务器加载数据
    private void loadDataByRetrofit(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://120.24.147.111:80")
                .addConverterFactory(new ToStringConverterFactory())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        Call<String> call =  service.getHello();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                etMy.setText(response.body()+"");
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                etMy.setText(t.getMessage());
                Log.e("",t.getMessage());
            }
        });
    }

    //使用Retrofit+Rx从服务器加载数据
    private void loadDataByRetrofitRx(){
        String baseUrl = "http://120.24.147.111:80";
//        String baseUrl = "http://10.5.9.57:5003";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(new ToStringConverterFactory())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持RxJava
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        String userName = etMy.getText().toString();
//        Observable<String> observable =  service.getHelloRxGet(userName);
//        Observable<String> observable =  service.getHelloRxPost(userName);
        Observable<String> observable =  service.getVenala2ancnt(userName);
        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(),"","加载中……",
                true, false);
        //RxAndroid其实就是对RxJava的扩展。比如AndroidSchedulers主线程在RxJava中就没有，因此要使用的话就必须得引用RxAndroid
        observable.subscribeOn(Schedulers.io())//请求数据的事件发生在io线程
                  .observeOn(AndroidSchedulers.mainThread())//请求完成后在主线程更显UI
                  .subscribe(new Observer<String>() {//订阅
                    @Override
                    public void onCompleted() {
                        //所有事件都完成，可以做些操作。。。
                        progressDialog.dismiss();
                    }
                    @Override
                    public void onError(Throwable e) {
                        progressDialog.dismiss();
                        e.printStackTrace(); //请求过程中发生错误
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onNext(String book) {
                        tvDispaly.setText(book);
                    }
                });
    }
}
