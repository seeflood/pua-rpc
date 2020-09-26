package fubao.rpc.framework.transport.netty;

import fubao.rpc.framework.common.utils.LogUtils;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import fubao.rpc.framework.common.model.Invocation;
import fubao.rpc.framework.common.model.Response;
import fubao.rpc.framework.provider.RpcServer;
import io.netty.bootstrap.ServerBootstrap;

import java.io.IOException;

public class NettyRpcServer extends RpcServer {
    private EventLoopGroup boss;
    private EventLoopGroup worker;
    private ChannelFuture future;
    private ChannelOutboundHandler encoder;
    private ChannelInboundHandler decoder;

    @Override
    protected void close() {
        if (boss != null) {
            boss.shutdownGracefully();
        }
        if (worker != null) {
            worker.shutdownGracefully();
        }
    }

    @Override
    protected void start() throws InterruptedException {
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                ProtobufDecoder localDecoder=new ProtobufDecoder();
                localDecoder.setGenericClass(Invocation.class);
                ProtobufEncoder localEncoder=new ProtobufEncoder();
                localEncoder.setGenericClass(Response.class);
                pipeline.addLast(localDecoder); // 解码 RPC 请求
                pipeline.addLast(localEncoder); // 编码 RPC 响应
                pipeline.addLast(new ReflectionInvokeHandler(localServiceContext.getHandlerMap())); // 处理 RPC 请求
            }
        });
        bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

        future = bootstrap.bind(url.getHost(), Integer.valueOf(url.getPort())).sync();
        LogUtils.info(this,"Server started.host:"+url.getHost()+" port:"+url.getPort());
    }

    @Override
    protected void hang() throws IOException, InterruptedException {
        LogUtils.info(this,"Hang until close.");
        future.channel().closeFuture().sync();
    }

    public void setEncoder(ChannelOutboundHandler encoder) {
        this.encoder = encoder;
    }

    public void setDecoder(ChannelInboundHandler decoder) {
        this.decoder = decoder;
    }
}
