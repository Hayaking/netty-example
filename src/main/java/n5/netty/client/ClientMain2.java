package n5.netty.client;

/**
 * @author haya
 */
public class ClientMain2 {
    public static void main(String[] args) throws Exception {
        new ChatClient( 8888 ).start();
    }
}
