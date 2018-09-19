import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Receiver implements Runnable {
    LocalTime LT;
    public static MulticastSocket socket;
    public static int port;
    public static InetAddress group;
    public static Map<InetAddress, LocalTime> alive;

    public Receiver(MulticastSocket _socket, int _port, InetAddress _group) {
        socket = _socket;
        port = _port;
        group = _group;
    }

    @Override
    public void run() {
        byte buf[] = new byte[1024];
        DatagramPacket pack = new DatagramPacket(buf, buf.length);
        alive = new HashMap<>();
        while (true) {
            try {
                socket.receive(pack);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Received data from: " + pack.getAddress().toString() +
                    ":" + pack.getPort() + " with length: " +
                    pack.getLength());
            System.out.write(pack.getData(), 0, pack.getLength());
            //if(!alive.containsKey(pack.getAddress())){
                alive.put(pack.getAddress(), LT.now());

            for (Map.Entry<InetAddress, LocalTime> tmp : alive.entrySet()) {
                if(LT.now().isAfter(tmp.getValue().plusMinutes(1))){
                    alive.remove(tmp.getKey(),tmp.getValue());
                    System.out.println("\nremoving...");
                }
            }

            for (Map.Entry<InetAddress, LocalTime> tmp : alive.entrySet()) {
                System.out.println("\n"+tmp.getKey().toString() + "  " + tmp.getValue().toString());
            }
        }
    }
}
