## Toy RPC

自己实现的RPC框架，和Spring做了集成。仅用于学习。

使用示例参见toy\rpc\demo目录下的用例

### TODO

- 支持自动代理，不需要显示调用RpcProxy取代理对象
- 支持zk
- 支持长连接、TCP拆包 
- client支持异步调用(返回Future)
- client同步阻塞调用支持设置超时时间
- client支持Forking Cluster 并行调用多个服务器，只要一个成功即返回
- url自动获取
- 日志优化