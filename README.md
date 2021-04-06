## PUA rpc

一个会PUA你的RPC框架，基于Netty实现，和Spring做了集成。

希望能通过RPC的方式将幸福传递到分布式系统的每一个角落。

使用示例参见\rpc\demo目录下的用例

### Feature
- 鼓励程序员

每次RPC调用会打日志，日志中随机加入一条PUA语录以鼓励程序员，比如“别人都在这里加班，你有什么脸下班”；比如调用成功时打印“你以为是因为你才成功的？就凭你也配？”,“年轻人不要贪图安逸，否则老了一事无成”,“我不会忘了你的，等我当上总监，继续罩着你”

示例日志：
```$xslt
proxy created
Prepare to send request.host:127.0.0.1 port:8000 哎呀，你写个代码怎么写的这么慢
Will send message out,prepare to encode 别急，我也在这里加班陪你们
Request was written into channel and flushed.Prepare to acquire semaphore. 你这写的是什么东西，毫无逻辑性和思考，能不能站在P9和P10的高度去看问题？
Get send message in,prepare to decode 我不管你怎么做的，那是你的事情，如果你这点事情都做不到，那么你存在的价值是什么？
Received response,requestId:303ab856-ba4a-4f88-8e07-159d14812551 你不要凡事都来问我，你自己的脑子呢？我怎么知道这个事怎么做？你自己看着办吧？
```
- 批评程序员

在RPC调用失败/出现报错/出现性能问题等不好的场合，会在日志中加入批评程序员的话，比如"公司这么悉心再陪你，安排你在这么关键的岗位，你怎么能犯这种低级错误，自己好好反省一下","你能力太差！都是因为你，这个case才没有完成！"

### TODO


- 给程序员打绩效

定期根据报错量、调用量、rt性能打绩效，如果报错量上涨/调用量上涨不够高会打3.25，如果报错量很少、调用量上涨较高、rt性能提升明显会打3.75。如果打3.75会在磁盘上生成一张“福.jpg”图片（考虑加入361排名策略）
- 支持自动代理，不需要显示调用RpcProxy取代理对象
- 支持zk
- 支持长连接、TCP拆包 
- client支持异步调用(返回Future,基于AQS实现自己的Future)
- client同步阻塞调用支持设置超时时间
- client支持Forking Cluster 并行调用多个服务器，只要一个成功即返回
- url自动获取
- 日志优化
- 支持压缩算法
- 支持自定义每个对象的序列化算法
