package n2.server;

/**
 * @author haya
 */
public class Main {
    public static void main(String[] args) throws Exception {
        new HttpServer( 8888 ).start();
        System.out.println("!!");
    }
}
