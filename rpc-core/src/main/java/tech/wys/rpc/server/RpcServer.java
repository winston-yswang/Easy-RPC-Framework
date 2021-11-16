package tech.wys.rpc.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.wys.rpc.registry.ServiceRegistry;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class RpcServer {

    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAXIMUM_POOL_SIZE = 50;
    private static final int KEEP_ALIVE_TIME = 60;
    private static final int BLOCKING_QUEUE_CAPACITY = 100;

    private final ExecutorService threadPool;   // 线程池
    private final ServiceRegistry serviceRegistry;  // 服务注册中心
    private RequestHandler requestHandler = new RequestHandler();

    public RpcServer(ServiceRegistry serviceRegistry) {
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

    public void start(int port) {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("服务器启动......");
            Socket socket;
            while ((socket = serverSocket.accept()) != null) {  // 等待客户端连接
                logger.info("消费者连接：{} : {}", socket.getInetAddress(), socket.getPort());
                // 将连接请求、处理器和注册中心一块打包交给线程池中的一个线程专门处理
                threadPool.execute(new RequestHandlerThread(socket, requestHandler, serviceRegistry));
            }
            threadPool.shutdown();  // 关闭线程池
        } catch (IOException e) {
            logger.error("服务器启动时有错误发生：", e);
        }
    }


}
