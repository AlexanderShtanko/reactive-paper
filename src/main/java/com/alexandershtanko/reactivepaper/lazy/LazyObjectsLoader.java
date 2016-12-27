package com.alexandershtanko.reactivepaper.lazy;


import android.util.Log;

import com.alexandershtanko.reactivepaper.BuildConfig;
import com.alexandershtanko.reactivepaper.RxPaper;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 19/10/2016.
 *         Copyright Ostrovok.ru
 */

public abstract class LazyObjectsLoader {
    private static final String TAG = LazyObjectsLoader.class.getSimpleName();
    private BehaviorSubject<LazyTask> taskSubject = BehaviorSubject.create();
    private Subscription subscription = null;

    public void start() {
        subscription = taskSubject.asObservable().onBackpressureBuffer().map(task -> {
            task.setResult(load(task.book, task.key));
            return task;
        }).subscribeOn(Schedulers.io()).onErrorResumeNext(throwable -> {
            return Observable.just(null);
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(task ->
        {
            if (task != null)
                task.getOnCompleteAction().call(task.getResult());
        }, logOnError());

    }

    private Action1<Throwable> logOnError() {
        return new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                if(BuildConfig.DEBUG)
                    Log.e(TAG,"",throwable);
            }
        };
    }

    public void stop() {
        subscription.unsubscribe();
    }

    public abstract <T> RxPaper.PaperObject<T> load(String book, String key);

    public <T> void loadAsync(String book, String key, Action1<RxPaper.PaperObject<T>> onCompleteAction) {
        LazyTask<T> lazyTask = new LazyTask<>();
        lazyTask.setBook(book);
        lazyTask.setKey(key);
        lazyTask.setOnCompleteAction(onCompleteAction);

        taskSubject.onNext(lazyTask);
    }


    private class LazyTask<T> {

        private String book;
        private String key;
        private Action1<RxPaper.PaperObject<T>> onCompleteAction;
        private RxPaper.PaperObject<T> result;

        public void setBook(String book) {
            this.book = book;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public void setOnCompleteAction(Action1<RxPaper.PaperObject<T>> onCompleteAction) {
            this.onCompleteAction = onCompleteAction;
        }

        public RxPaper.PaperObject<T> getResult() {
            return result;
        }

        public void setResult(RxPaper.PaperObject<T> result) {
            this.result = result;
        }

        public Action1<RxPaper.PaperObject<T>> getOnCompleteAction() {
            return onCompleteAction;
        }
    }


}
