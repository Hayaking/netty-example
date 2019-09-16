package n3.server;

/**
 * @author haya
 */
public class ServerMain {
    public static void main(String[] args) throws Exception {
        new ChatServer( 8888 ).start();
    }
}
