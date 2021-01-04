package pua.rpc.framework.transport.netty;

import pua.rpc.framework.common.utils.LogUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import pua.rpc.framework.serialization.ProtostuffUtil;
import pua.rpc.framework.common.utils.LogUtils;

public class ProtobufEncoder  extends MessageToByteEncoder {
    private Class<?> genericClass;
    @Override
    protected void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        LogUtils.info(this,"Will send message out,prepare to encode");
        if (genericClass.isInstance(in)) {
            byte[] data = ProtostuffUtil.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }

    public void setGenericClass(Class<?> genericClass) {
        this.genericClass = genericClass;
    }
}
