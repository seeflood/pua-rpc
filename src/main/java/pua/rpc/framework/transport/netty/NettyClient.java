package pua.rpc.framework.transport.netty;

import pua.rpc.framework.common.model.Invocation;
import pua.rpc.framework.common.model.Response;
import pua.rpc.framework.common.utils.LogUtils;
import pua.rpc.framework.consumer.Client;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import pua.rpc.framework.common.model.Invocation;
import pua.rpc.framework.common.model.Response;
import pua.rpc.framework.common.utils.LogUtils;
import pua.rpc.framework.consumer.Client;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

public class NettyClient implements Client {
    static class ResultWrapper {
        Response  r;
        Semaphore semaphore = new Semaphore(0);
    }

    private ChannelInboundHandler decoder;
    private ChannelOutboundHandler encoder;
    private Map<String, ResultWrapper> requestId2Result = new ConcurrentHashMap<>();

    @Override
    public Response send(Invocation invocation, String host, String port) throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(boss);
            bootstrap.channel(NioSocketChannel.class);
            Semaphore semaphore = new Semaphore(0);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ChannelPipeline pipeline = ch.pipeline();
                    ProtobufDecoder localDecoder = new ProtobufDecoder();
                    localDecoder.setGenericClass(Response.class);
                    ProtobufEncoder localEncoder = new ProtobufEncoder();
                    localEncoder.setGenericClass(Invocation.class);
                    pipeline.addLast(localDecoder); // 解码 RPC 请求
                    pipeline.addLast(localEncoder); // 编码 RPC 响应
                    pipeline.addLast(new SimpleChannelInboundHandler<Response>() {
                        @Override
                        protected void channelRead0(ChannelHandlerContext ctx, Response msg) throws Exception {
                            String requestId = msg.getRequestId();
                            LogUtils.info(this, "Received response,requestId:" + requestId);
                            ResultWrapper wrapper = requestId2Result.get(requestId);
                            if (wrapper != null) {
                                wrapper.r = msg;
                                wrapper.semaphore.release();
                            }
                        }
                    });
                }
            });

            ChannelFuture future = bootstrap.connect(host, Integer.parseInt(port)).sync();
//            prepare
            ResultWrapper wrapper = new ResultWrapper();
            requestId2Result.put(invocation.getRequestId(), wrapper);
//            write
            Channel channel = future.channel();
            channel.writeAndFlush(invocation).sync();
            LogUtils.info(this, "Request was written into channel and flushed.Prepare to acquire semaphore.");
//            read
            wrapper.semaphore.acquire();
//            短连接
            channel.closeFuture().sync();
            return wrapper.r;
        } finally {
            boss.shutdownGracefully();
        }
    }

    public void setDecoder(ChannelInboundHandler decoder) {
        this.decoder = decoder;
    }

    public void setEncoder(ChannelOutboundHandler encoder) {
        this.encoder = encoder;
    }
}
