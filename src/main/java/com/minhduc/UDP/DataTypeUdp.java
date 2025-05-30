package com.minhduc.UDP;

import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.minhduc.Main.host;

public class DataTypeUdp {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()){
            InetAddress address = InetAddress.getByName(host);
            String message = ";B21DCCN256;xDmgDzEe";
            byte[] req1 = message.getBytes();
            DatagramPacket packet1 = new DatagramPacket(req1, req1.length, address, 2207);
            socket.send(packet1);

            byte[] res1 = new byte[4096];
            DatagramPacket packet2 = new DatagramPacket(res1, res1.length);
            socket.receive(packet2);
            String str = new String(packet2.getData(), 0, packet2.getLength());
            System.out.println(str);

            String[] arr = str.split(";");
            String requestId = arr[0];
            String[] arr2 = arr[1].split(",");

            Arrays.sort(arr2, (a, b) -> {
                return Integer.parseInt(a) - Integer.parseInt(b);
            });
            message = requestId + ";" + String.valueOf(arr2[arr2.length - 1]) + "," + String.valueOf(arr2[0]);
            System.out.println(message);
            byte[] res2 = message.getBytes();
            DatagramPacket packet3 = new DatagramPacket(res2, res2.length, address, 2207);
            socket.send(packet3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
