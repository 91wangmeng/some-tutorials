package lambda;

import lombok.extern.slf4j.Slf4j;

/**
 * @Copyright: Zhejiang Lishi Technology Co., Ltd  2018 <br/>
 * @Desc: <br/>
 * @ProjectName: some-tutorials <br/>
 * @Date: 2018/12/29 10:49 <br/>
 * @Author: <a href="wangmengmeng@lswooks.com">汪萌萌</a>
 */
@Slf4j
public class LambdaTutorials {
    public static void main(String[] args) {
        functionInterface();
    }

    private static void functionInterface() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                log.info("do something");
            }
        };
        new Thread(runnable).start();
        new Thread(() -> log.info("do something")).start();
    }
}
