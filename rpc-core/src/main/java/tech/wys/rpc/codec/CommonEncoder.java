package tech.wys.rpc.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import tech.wys.rpc.entity.RpcRequest;
import tech.wys.rpc.enumeration.PackageType;
import tech.wys.rpc.serializer.CommonSerializer;


/**
 * @Author: wys
 * @Desc: 通用的编码拦截器，解码是针对入站数据，编码是针对出站数据
 *        MessageToByteEncoder 将一个消息编码下，编码成字节数组形式
 * @Date: 2021/11/17
**/
public class CommonEncoder extends MessageToByteEncoder {

    private static final int MAGIC_NUMBER = 0xCAFEBABE; // 协议标识符

    private final CommonSerializer serializer;

    public CommonEncoder(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        out.writeInt(MAGIC_NUMBER); // 写入协议标识符
        if (msg instanceof RpcRequest) {    // 写入数据包类型
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        out.writeInt(serializer.getCode()); // 写入序列化方式
        byte[] bytes = serializer.serialize(msg);   // 将msg序列化
        out.writeInt(bytes.length); // 写入msg序列化后的长度
        out.writeBytes(bytes);  // 写入msg序列化后的字符数组
    }
}
