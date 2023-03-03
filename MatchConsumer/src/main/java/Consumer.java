import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

public class Consumer {

    private static final String EXCHANGE_NAME = "swipeExchange";
    private static final String QUEUE_NAME = "matchQueue";
    private static final int QUEUE_SIZE = 10;
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final int PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {
        String host = args[0];
        int THREAD_NUM = Integer.parseInt(args[1]);
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(host);
        factory.setPort(PORT);
        factory.setUsername(USER);
        factory.setPassword(PASSWORD);
        Connection connection = factory.newConnection();
        Map<Integer, Set<Integer>> record = new ConcurrentHashMap<>();
        for (int i = 0; i < THREAD_NUM; i++) {
            Thread thread = new Thread(new ConsumerThread(EXCHANGE_NAME, QUEUE_NAME, connection, record, QUEUE_SIZE));
            thread.start();
        }
    }

}
