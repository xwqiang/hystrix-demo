import com.google.common.base.Stopwatch;
import com.kuyun.util.HelloWorldHystrixCommand;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by xuwuqiang on 2018/9/29.
 */
public class Test {

    static ExecutorService executorService = Executors.newCachedThreadPool();

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

        Stopwatch stopwatch = Stopwatch.createStarted();
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                try {
                    dorun();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
        System.out.println("timecost:" + stopwatch.stop());
    }

    public static void dorun() throws Exception {
        //异步调用,可自由控制获取结果时机,
        HelloWorldHystrixCommand command = new HelloWorldHystrixCommand("HLX");
        //get操作不能超过command定义的超时时间,默认:1秒
//        String result = future.get(100, TimeUnit.MILLISECONDS);
        Future<String> future = command.queue();
        String result = future.get();
        System.out.println("result=" + result);
        TimeUnit.MILLISECONDS.sleep(50);
    }

}
