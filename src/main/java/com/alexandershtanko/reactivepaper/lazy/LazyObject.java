package com.alexandershtanko.reactivepaper.lazy;


import com.alexandershtanko.reactivepaper.RxPaper;

import rx.functions.Action1;

/**
 * @author Alexander Shtanko ab.shtanko@gmail.com
 *         Created on 19/10/2016.
 */

public class LazyObject<T> {
    private RxPaper.PaperObject<T> cachedObject;

    public LazyObjectsLoader getLazyObjectsLoader() {
        return lazyObjectsLoader;
    }

    private LazyObjectsLoader lazyObjectsLoader;
    private String book;
    private String key;

    public String getKey() {
        return key;
    }


    public LazyObject(String book, String key, LazyObjectsLoader lazyObjectsLoader) {
        this.key = key;
        this.book = book;
        this.lazyObjectsLoader = lazyObjectsLoader;
    }


    public RxPaper.PaperObject<T> getObject() {
        return lazyObjectsLoader.load(book, key);
    }

    public void getObjectAsync(Action1<RxPaper.PaperObject<T>> action) {
        lazyObjectsLoader.loadAsync(book, key, action);
    }

    public String getBook() {
        return book;
    }

    public RxPaper.PaperObject<T> pollCachedObject() {
        try {
            return cachedObject;
        } finally {
            cachedObject = null;
        }
    }

    public void cacheObject(RxPaper.PaperObject<T> object) {
        cachedObject = object;
    }
}
