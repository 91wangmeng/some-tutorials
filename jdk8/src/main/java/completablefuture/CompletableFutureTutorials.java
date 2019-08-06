package completablefuture;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @Copyright: Zhejiang Lishi Technology Co., Ltd  2019 <br/>
 * @Desc: <br/>
 * @ProjectName: some-tutorials <br/>
 * @Date: 2019/1/2 15:37 <br/>
 * @Author: <a href="wangmengmeng@lswooks.com">汪萌萌</a>
 */
@Slf4j
public class CompletableFutureTutorials {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int result = 0;
        for (int i = 0; i < 10; i++) {
            result += step();
        }
        log.info("消耗时间:{},结果:{}", System.currentTimeMillis() - start, result);


        start = System.currentTimeMillis();
        CompletableFuture<Integer>[] completableFutures = new CompletableFuture[10];
        for (int i = 0; i < 10; i++) {
            completableFutures[i] = CompletableFuture.supplyAsync(CompletableFutureTutorials::step);
        }
        List<Integer> collect = Arrays.stream(completableFutures)
                .map(CompletableFuture::join).collect(Collectors.toList());
        result = collect.stream()
                .mapToInt(value -> value)
                .sum();
        log.info("消耗时间:{},结果:{}", System.currentTimeMillis() - start, result);

        for (int i = 0; i < 10; i++) {
            completableFutures[i] = CompletableFuture.supplyAsync(CompletableFutureTutorials::step);
        }
        start = System.currentTimeMillis();
        CompletableFuture<List<Integer>> listCompletableFuture = CompletableFuture.allOf(completableFutures)
                .thenApply(aVoid ->
                        Arrays.stream(completableFutures)
                                .map(CompletableFuture::join).collect(Collectors.toList()));
        result = listCompletableFuture.join()
                .stream()
                .mapToInt(value -> value)
                .sum();
        log.info("消耗时间:{},结果:{}", System.currentTimeMillis() - start, result);

    }

    private static int step() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 1;
    }
}
