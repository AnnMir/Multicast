import java.io.IOException;
import java.net.*;

import static java.lang.Thread.sleep;

public class Sender implements  Runnable{

    public static final int BUFFER_SIZE = 1000;
    public static MulticastSocket socket;
    public static int port;
    public static InetAddress group;

    public Sender(MulticastSocket _socket, int _port, InetAddress _group) {
        socket = _socket;
        port = _port;
        group = _group;
    }

    @Override
    public void run() {
        DatagramPacket pack = null;
        String msg = "I'm alive";
        pack = new DatagramPacket(msg.getBytes(), msg.length(), group, port);
        //while (true) {
            try {
                sleep(10 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                socket.send(pack);
            } catch (IOException e) {
                e.printStackTrace();
            }
        //}
    }
}
