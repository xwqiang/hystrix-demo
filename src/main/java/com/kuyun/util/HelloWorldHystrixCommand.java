package com.kuyun.util;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * Created by xuwuqiang on 2018/9/29.
 */

public class HelloWorldHystrixCommand extends HystrixCommand<String> {

    private final String name;

    public HelloWorldHystrixCommand(String name) {
        super(Setter.withGroupKey(
            //服务分组
            HystrixCommandGroupKey.Factory.asKey("OrderGroup"))
            //线程分组
            .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("OrderPool"))
            //线程池配置
            .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                .withCoreSize(10)
                .withKeepAliveTimeMinutes(5)
                .withMaxQueueSize(100)///最大线程数
                .withQueueSizeRejectionThreshold(100))
            .andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter()
                    .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.THREAD)
                    .withFallbackIsolationSemaphoreMaxConcurrentRequests(1000)//最多x个fallback请求。

                    .withCircuitBreakerEnabled(true)
                    .withCircuitBreakerRequestVolumeThreshold(100)//表示请求数至少达到多大才进行熔断计算
                    .withCircuitBreakerErrorThresholdPercentage(60)//熔断器关闭到打开阈值  熔断器错误比率阈值
                    .withCircuitBreakerSleepWindowInMilliseconds(3000)//熔断器打开到关闭的时间窗长度  半开的触发试探休眠时间
                    .withExecutionTimeoutInMilliseconds(1000)//超时时间配置
            )

        );
        this.name = name;
    }


    @Override
    protected String run() {
        System.out.println("running......");
        if (LocalDateTime.now().getMinute() == 11) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }
        }
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME) + " Hello " + name;
    }


    @Override
    protected String getFallback() {
        return "sorry fallback: " + name;
    }

}
