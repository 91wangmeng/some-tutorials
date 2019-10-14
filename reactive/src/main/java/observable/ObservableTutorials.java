package observable;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.java.Log;


/**
 * 杭州蓝诗网络科技有限公司 版权所有 © Copyright 2019<br>
 *
 * @Description: <br>
 * @Project: some-tutorials <br>
 * @CreateDate: Created in 19.9.29 17:52 <br>
 * @Author: <a href="wangmengmeng@quannengkuaiche.com">wmm</a>
 */
@Log
public class ObservableTutorials {
    public static void main(String[] args) {
        Observable.just(1)
                .map(integer -> {
                    log.info("map-1:" + Thread.currentThread().getName());
                    return integer;
                })
                .subscribeOn(Schedulers.newThread())
                .map(integer -> {
                    log.info("map-2:" + Thread.currentThread().getName());
                    return integer;
                })
                .subscribeOn(Schedulers.io())
                .map(integer -> {
                    log.info("map-3:" + Thread.currentThread().getName());
                    return integer;
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.newThread())
                .subscribe(integer -> log.info("subscribe:" + Thread.currentThread().getName()));
    }
}
