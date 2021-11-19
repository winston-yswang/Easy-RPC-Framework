package tech.wys.rpc.netty.server;


import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wys.rpc.RequestHandler;
import tech.wys.rpc.entity.RpcRequest;
import tech.wys.rpc.entity.RpcResponse;
import tech.wys.rpc.registry.DefaultServiceRegistry;
import tech.wys.rpc.registry.ServiceRegistry;

/**
 * @Author: wys
 * @Desc: Netty中处理RpcRequest的Handler
 * @Date: 2021/11/17
**/
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);
    private static RequestHandler requestHandler;
    private static ServiceRegistry serviceRegistry;

    static {
        requestHandler = new RequestHandler();
        serviceRegistry = new DefaultServiceRegistry();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try {
            logger.info("服务器收到请求：{}", msg);
            String interfaceName = msg.getInterfaceName();
            Object service = serviceRegistry.getService(interfaceName); // 获取对应服务实体
            Object result = requestHandler.handle(msg, service);    // 执行服务返回结果
            ChannelFuture future = ctx.writeAndFlush(RpcResponse.success(result, msg.getRequestId()));
            future.addListener(ChannelFutureListener.CLOSE);    // 关闭通道监听

        } finally {
            ReferenceCountUtil.release(msg);    // ByteBuf.release() 对象可回收
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("处理过程调用时有错误发送：");
        cause.printStackTrace();
        ctx.close();
    }
}
