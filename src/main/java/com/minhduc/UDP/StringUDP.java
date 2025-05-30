package com.minhduc.UDP;

import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import static com.minhduc.Main.host;

public class StringUDP {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket()){
            InetAddress address = InetAddress.getByName(host);

            String message = ";B21DCCN256;WKYQXoHl";
            byte[] res1 = message.getBytes();
            DatagramPacket packet1 = new DatagramPacket(res1, res1.length, address, 2208);
            socket.send(packet1);

            byte[] req1 = new byte[4096];
            DatagramPacket packet2 = new DatagramPacket(req1, req1.length);
            socket.receive(packet2);

            String str = new String(packet2.getData(), 0, packet2.getLength());
            String[] data = str.split(";");
            String[] arr = data[1].split("\\s+");
            List<String> result = new ArrayList<>();
            for (int i = 0; i < arr.length; i++) {
                String s = "";
                s += arr[i].substring(0, 1).toUpperCase();
                s += arr[i].substring(1, arr[i].length()).toLowerCase();
                result.add(s);
            }
            String res2 = data[0] + ";";
            for(String s: result) {
                System.out.println(s);
                res2 += s +" ";
            }

            res2.trim();
            byte[] res3 = res2.getBytes();
            DatagramPacket packet3 = new DatagramPacket(res3, res3.length, address, 2208);
            socket.send(packet3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
