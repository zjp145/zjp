package com.zhang.sqone.ceshi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zhang.sqone.R;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;

/**创建一个测试Rxjava的activity*/
public class RxJavaActivity extends AppCompatActivity {

    private Subscriber<String> subscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        Ceobservable2();
        Ceobservable();
        CeSubscriber();
        CeObserver();

    }
    /**创建被监听者的简单方式
     * */
    private void Ceobservable2() {
        //方式一
        Observable observable = Observable.just("我的天","真实坑","我去");
        //方式二
        String[] s = {"abc","123","asd"};
        Observable observable1 = Observable.from(s);
    }

    /**创建一个被监听者*/
    private void Ceobservable() {
        //创建一个被监听者   在被监听者上添加订阅
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
            //当创建订阅的时候就调用call方法
                //调用监听者的抽象类方法
                subscriber.onNext("张建鹏");
                subscriber.onNext("张杰");
                subscriber.onNext("517");
                //结束标志
                subscriber.onCompleted();
            }
        });
        //被监听者订阅监听者
        /**
         * subscribe（）方法做了：
         * 1.首先他调用了
         * subscribe.onstart();
         * 2.调用observable.call(subscruber);
         * 3.将传入的 Subscriber 作为 Subscription 返回。这是为了方便 unsubscribe().
         * */
        observable.subscribe(subscriber);
    }

    /**RxJava 还内置了一个实现了 Observer 的抽象类：Subscriber。 Subscriber 对 Observer 接口进行了一些扩展，但他们的基本使用方式是完全一样的*/
    private void CeSubscriber() {
         subscriber = new Subscriber<String>() {
            /*成功的回调*/
            @Override
            public void onCompleted() {

            }
            /*失败回调*/
            @Override
            public void onError(Throwable e) {

            }
            /*事件回调*/
            @Override
            public void onNext(String s) {

            }
        };
    }

    /**测试Rxjava（observer）方法*/
    private void CeObserver() {
        //首先创建一个观察者对象Observer
        Observer<String> observer = new Observer<String>() {
            //事件队列完结，Rxjava 不仅把每个事件单独处理 还会把它们看做一个队列，RXjava规定当不会有更新onNext（）
            //发出时需要触发onCompleted（）方法作为标志
            @Override
            public void onCompleted() {

            }
            /*当事件队列异常在事件处理过程中出异常时会被触发*/
            @Override
            public void onError(Throwable e) {

            }
            /*触发方法*/
            @Override
            public void onNext(String s) {

            }
        };
    }


}
