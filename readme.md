hystrix demo.

测试：

```
ab -n 1000 -c 500 http://localhost:10080/test
```

FallbackIsolationSemaphoreMaxConcurrentRequests 可以调节处理fallback时的请求你数
