import java.io.IOException;
import java.net.*;

import static java.lang.Thread.sleep;

public class Sender implements  Runnable{

    private static DatagramSocket socket;
    private static int port;
    private static InetAddress group;

    public Sender(DatagramSocket _socket, int _port, InetAddress _group) {
        socket = _socket;
        port = _port;
        group = _group;
    }

    @Override
    public void run() {
        DatagramPacket pack = null;
        String msg = "I'm alive";
        while (true) {
            pack = new DatagramPacket(msg.getBytes(), msg.length(), new InetSocketAddress(group,port));
            try {
                socket.send(pack);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
