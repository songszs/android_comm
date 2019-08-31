package com.zs.test.rxbus;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zs.R;
import com.zs.base.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-4-24 下午8:14
 * @description: mytest
 */
public class RxbusFragment extends BaseFragment {

    private static String TAG = "RxbusFragment";

    @Override
    protected int createViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle bundle) {
        super.initData(bundle);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initView(View view) {
        super.initView(view);


        Observable.create((ObservableOnSubscribe<String>) emitter -> {
            emitter.onNext("hello");
            emitter.onNext("world");
        }).subscribe(s -> Log.d(TAG, s));


        /*****拦截发送 dispose*******/
        //dispose拦截发送  如果调用了dispose 不在接受到上游事件 包括complete
        Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                emitter.onNext("test dispose");
                emitter.onNext("dispose");
                emitter.onNext("end dispose");
                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {

            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                this.disposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, s);
                if (s.equals("dispose")) {
                    disposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });


        /*****线程切换*******/
        //subscribeOn 表示订阅线程
        //observeOn 表示回调线程
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.e(TAG, "Observable thread is : " + Thread.currentThread().getName());
                emitter.onNext("test thread");
            }
        })
//                .subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, "Consumer1 thread is : " + Thread.currentThread().getName());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e(TAG, s);
                        Log.e(TAG, "Consumer2 thread is : " + Thread.currentThread().getName());
                    }
                });

        /*****操作符 map  进行数据转换*******/
        //map 操作符 对数据进行转换 把Response转换为model
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> emitter) throws Exception {

            }
        }).map(new Function<Response, UsrModel>() {
            @Override
            public UsrModel apply(Response response) throws Exception {
                return null;
            }
        }).doOnNext(new Consumer<UsrModel>() {
            @Override
            public void accept(UsrModel usrModel) throws Exception {

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UsrModel>() {
                    @Override
                    public void accept(UsrModel usrModel) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        /*****操作符 concat  合并发射器*******/
        /*concat 接受依次传入的Observable 如果前一个调用onComplete，则调用后一个的Observable*/
        /*比如网络请求，如果cache未命中则请求network*/
        Observable cache = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("test 1");
                emitter.onComplete();
                emitter.onNext("test 3");
            }
        });

        Observable network = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("test 2");
            }
        });

        Observable.concat(cache, network)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        Log.e(TAG, "concat result is : " + o);
                    }
                });

        /*****操作符 zip  进行Observable发射数据合并*******/
        Observable intOb = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
            }
        });

        Observable strOb = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("zip test");
            }
        });

        Observable.zip(strOb, intOb, new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String s, Integer integer) throws Exception {
                return s + integer;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        Log.e(TAG, "zip test is : " + o);
                    }
                });

        /*****操作符 flatMap   发射事件无序 把一个发射器中的单个事件 转换为单个可发射的集合 *******/
        /*****操作符  发射事件有序 把一个发射器中的单个事件 转换为单个可发射的集合 *******/
        Observable flatMapOb = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("flatMap 1");
                emitter.onNext("flatMap 2");
                emitter.onNext("flatMap 3");
            }
        });

        flatMapOb.flatMap(new Function<String, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(String s) throws Exception {
                List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + s);
                }
                //把单个事件转换为多个发射器的集合
                return Observable.fromIterable(list);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String o) throws Exception {
                Log.e(TAG, "flatMap test is : " + o);
            }
        });

        /*****操作符 distinct  去重 *******/
        Observable.just(1, 1, 2, 3, 4, 5, 5, 3, 4)
                .distinct().subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "distinct test is : " + integer);
            }
        });

        /*****操作符 filter  过滤 *******/
        Observable.just(1, 1, 2, 3, 4, 5, 5, 3, 4)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 3;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "filter test is : " + integer);
            }
        });

        /*****操作符 skip 跳过后 发送数据*******/
        Observable.just(1, 1, 2, 3, 4, 5, 5, 3, 4)
                .skip(2).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "filter test is : " + integer);
            }
        });

        /*****操作符 buffer  count 是buf大小  skip是每次从数据中取数据时跳过的步数 *******/
        Observable.just(1, 1, 2, 3, 4, 5, 5, 3, 4)
                .buffer(3, 2).subscribe(new Consumer<List<Integer>>() {
            @Override
            public void accept(List<Integer> integers) throws Exception {
                Log.e(TAG, "buffer test ====");

                for (Integer integer : integers) {
                    Log.e(TAG, "buffer test is : " + integer);
                }
            }
        });


        /*****操作符 timer  执行延时 *******/
        Observable.timer(1, TimeUnit.SECONDS).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {

            }
        });

        /*****操作符 interval 周期执行 initialDelay 初始延迟 activity使用关闭后仍在计时 需dispos掉*******/
        Observable.interval(3, 2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                });

        /*****操作符 debounce 去除执行频率过快的事件*******/
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                emitter.onNext("1");
                emitter.onNext("2");
                Thread.sleep(50);
                emitter.onNext("3");
                emitter.onNext("3.5");
                Thread.sleep(150);
                emitter.onNext("4");
                Thread.sleep(200);
                emitter.onNext("5");
            }
        }).debounce(100, TimeUnit.MILLISECONDS).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "debounce test is : " + s);
            }
        });

        /*****操作符 timeout 去除执行频率过快的事件*******/
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                execRequest();
                emitter.onNext("timeout test");
            }
        }).timeout(1, TimeUnit.SECONDS).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.e(TAG, "timeout is : " + s);

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "timeout test is : ", throwable);

            }
        });


        Observable.just(1,1,2,2,3,4).groupBy(new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) throws Exception {
                return integer;
            }
        });

    }
}
