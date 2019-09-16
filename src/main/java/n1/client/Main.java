package n1.client;

/**
 * @author haya
 */
public class Main {
    public static void main(String[] args) throws Exception {
        new EchoClient( "127.0.0.1", 8888 ).start();
    }
}
