import javax.swing.*;
import java.io.IOException;
import java.net.*;

public class Main {
    public static void main(String args[]){
        int port = 55555;
        int Multicast_port = 50000;
        DatagramSocket Datagramsocket = null;
        MulticastSocket socket = null;
        InetAddress groupIP = null;
        InetAddress LocalIp = null;
        try {
            groupIP = InetAddress.getByName(args[0]);
            LocalIp = InetAddress.getByName(args[1]);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Введен некорректный IP. Используйте следующий формат: " +"\n" +
                    "'x.x.x.x' где x от 0 до 255 для IPv4\n" +
                    "Или 'х.х.х.х.х.х.х.х' где х шестнадцатеричное 16-битное число, \nкоторое состоит из 4 символов в шестнадцатеричной системе для IPv6\n");
        }
        try {
            socket = new MulticastSocket(Multicast_port);
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
        try {
            Datagramsocket = new DatagramSocket(port, InetAddress.getByName(args[1]));
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        Runnable sender = new Sender(Datagramsocket,Multicast_port,groupIP);
            new Thread(sender).start();
            Runnable receiver = new Receiver(socket,port, LocalIp);
            new Thread(receiver).start();
    }
}
