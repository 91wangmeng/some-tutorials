package synchronized_tutorials;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 杭州蓝诗网络科技有限公司 版权所有 © Copyright 2019<br>
 *
 * @Description: <br>
 * @Project: some-tutorials <br>
 * @CreateDate: Created in 2019/6/12 16:49 <br>
 * @Author: <a href="wangmengmeng@quannengkuaiche.com">wmm</a>
 */
@Slf4j
public class SynchronizedTutorials {
    private static final Executor EXECUTOR = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {

//        normalWithNormal();
        staticWithStatic();


    }

    /*
    普通同步方法A执行时间:2019-06-12 17:12:29|执行线程:pool-1-thread-1
    普通同步方法C执行时间:2019-06-12 17:12:29|执行线程:pool-1-thread-3
    普通同步方法D执行时间:2019-06-12 17:12:34|执行线程:pool-1-thread-4
    普通同步方法B执行时间:2019-06-12 17:12:34|执行线程:pool-1-thread-2
    由上诉结果可知,同一个类的不同同步静态方法之间互斥,而同步静态方法和同步普通方法之间则不互斥
    */
    private static void staticWithStatic() {
        NormalMethod normalMethodA = new SynchronizedTutorials.NormalMethod();
        EXECUTOR.execute(normalMethodA::normalA);
        EXECUTOR.execute(normalMethodA::normalB);
        EXECUTOR.execute(NormalMethod::normalC);
        EXECUTOR.execute(NormalMethod::normalD);
    }

    /*
     普通同步方法A执行时间:2019-06-12 17:06:54|执行线程:pool-1-thread-1
     普通同步方法A执行时间:2019-06-12 17:06:54|执行线程:pool-1-thread-3
     普通同步方法B执行时间:2019-06-12 17:06:59|执行线程:pool-1-thread-2
     普通同步方法B执行时间:2019-06-12 17:06:59|执行线程:pool-1-thread-4
     由上诉结果可知,普通同步方法,同一个对象调用不同的同步方法两两互斥,而不同对象调用同一个同步方法不互斥
     */

    private static void normalWithNormal() {
        NormalMethod normalMethodA = new SynchronizedTutorials.NormalMethod();
        EXECUTOR.execute(normalMethodA::normalA);
        EXECUTOR.execute(normalMethodA::normalB);
        NormalMethod normalMethodB = new SynchronizedTutorials.NormalMethod();
        EXECUTOR.execute(normalMethodB::normalA);
        EXECUTOR.execute(normalMethodB::normalB);
    }

    static class NormalMethod {
        synchronized void normalA() {
            log.info("普通同步方法A执行时间:{}|执行线程:{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), Thread.currentThread().getName());
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        synchronized void normalB() {
            log.info("普通同步方法B执行时间:{}|执行线程:{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), Thread.currentThread().getName());
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        static synchronized void normalC() {
            log.info("普通同步方法C执行时间:{}|执行线程:{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), Thread.currentThread().getName());
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        static synchronized void normalD() {
            log.info("普通同步方法D执行时间:{}|执行线程:{}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), Thread.currentThread().getName());
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}

