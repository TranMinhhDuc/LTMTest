package com.minhduc.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

import static com.minhduc.Main.host;

public class DataType2UDP {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()){
            InetAddress address = InetAddress.getByName(host);
            String message = ";B21DCCN256;MZTEBlTP";
            byte[] res1 = message.getBytes();
            DatagramPacket packet1 = new DatagramPacket(res1, res1.length, address, 2207);
            socket.send(packet1);

            byte[] req2 = new byte[4096];
            DatagramPacket packet2 = new DatagramPacket(req2, req2.length);
            socket.receive(packet2);

            String s = new String(packet2.getData(), 0, packet2.getLength());
            System.out.println(s);

            String[] data = s.split(";");
            String[] arr = data[1].split(",");
            Arrays.sort(arr, (a, b) -> {
                return Integer.parseInt(a) - Integer.parseInt(b);
            });

            String res2 = data[0] + ";"   + arr[arr.length - 2] + "," + arr[1];
            byte[] res3 = res2.getBytes();
            DatagramPacket packet3 = new DatagramPacket(res3, res3.length, address, 2207);
            socket.send(packet3);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
