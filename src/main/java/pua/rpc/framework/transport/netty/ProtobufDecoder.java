package pua.rpc.framework.transport.netty;

import pua.rpc.framework.common.utils.LogUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import pua.rpc.framework.serialization.ProtostuffUtil;
import pua.rpc.framework.common.utils.LogUtils;

import java.util.List;

public class ProtobufDecoder extends ByteToMessageDecoder {
    private Class<?> genericClass;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List out) throws Exception {
        LogUtils.info(this,"Get send message in,prepare to decode");
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);
        out.add(ProtostuffUtil.deserialize(data, genericClass));
    }

    public void setGenericClass(Class<?> genericClass) {
        this.genericClass = genericClass;
    }
}
