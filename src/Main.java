import javax.swing.*;
import java.io.IOException;
import java.net.*;
import java.net.Inet4Address;
import java.net.Inet6Address;

public class Main {
    public static final int BUFFER_SIZE = 1000;
    public static void main(String args[]){
        int port = 55555;
        MulticastSocket socket = null;
        InetAddress groupIP = null;
        try {
            groupIP = InetAddress.getByName(args[0]);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Введен некорректный IP. Используйте следующий формат: " +"\n" +
                    "'x.x.x.x' где x от 0 до 255 для IPv4\n" +
                    "Или 'х.х.х.х.х.х.х.х' где х шестнадцатеричное 16-битное число, \nкоторое состоит из 4 символов в шестнадцатеричной системе для IPv6\n");
        }
        try {
            socket = new MulticastSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

            if (groupIP.isMulticastAddress()) {
                    try {
                        socket.joinGroup(groupIP);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            else {
                JOptionPane.showMessageDialog(null, "Entered IP is not a multicast address");
                System.exit(1);
            }
            Runnable sender = new Sender(socket,port,groupIP);
            new Thread(sender).start();
            Runnable receiver = new Receiver(socket,port,groupIP);
            new Thread(receiver).start();

    }
}
