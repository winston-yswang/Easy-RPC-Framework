package tech.wys.rpc.socket.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wys.rpc.RequestHandler;
import tech.wys.rpc.RpcServer;
import tech.wys.rpc.enumeration.RpcError;
import tech.wys.rpc.exception.RpcException;
import tech.wys.rpc.registry.ServiceRegistry;
import tech.wys.rpc.serializer.CommonSerializer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;


/**
 * @Author: wys
 * @Desc: Socket方式远程方法调用的提供者（服务端）
 * @Date: 2021/11/19
**/
public class SocketServer implements RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(SocketServer.class);

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;

    private final ExecutorService threadPool;   // 线程池
    private final ServiceRegistry serviceRegistry;  // 服务注册中心
    private CommonSerializer serializer;
    private RequestHandler requestHandler = new RequestHandler();

    public SocketServer(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        ArrayBlockingQueue<Runnable> workingQueue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_CAPACITY);
        ThreadFactory threadFactory = Executors.defaultThreadFactory();
        threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, // 核心线程池大小
                MAXIMUM_POOL_SIZE,  // 最大线程池大小
                KEEP_ALIVE_TIME,    // 空闲存活时间
                TimeUnit.SECONDS,   // 超时单位
                workingQueue,   // 阻塞队列
                threadFactory); // 线程工厂
    }


    @Override
    public void start(int port) {
        if(serializer == null) {
            logger.error("未设置序列化器");
            throw new RpcException(RpcError.SERIALIZER_NOT_FOUND);
        }
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器启动……");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {
                logger.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry, serializer));
            }
            threadPool.shutdown();
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生:", e);
        }

    }

    @Override
    public void setSerializer(CommonSerializer serializer) {
        this.serializer = serializer;
    }
}
