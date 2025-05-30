package com.minhduc.UDP;

import UDP.Product;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import java.util.Collection;

import static com.minhduc.Main.sendData;

public class ObjectUDP {
    private static int port = 2209;
    public static void main(String[] args) {
        try(DatagramSocket socket = new DatagramSocket()){
            String message = ";B21DCCN256;JmeKPMjS";
            byte[] data = message.getBytes();

            sendData(data, port, socket);

            System.out.println("Message sent");

            byte[] buffer = new byte[4096];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            byte[] received = packet.getData();
            byte[] requestIdByte = Arrays.copyOfRange(received, 0, 8);
            String requestId = new String(requestIdByte);

            System.out.println(requestId);

            ByteArrayInputStream bais = new ByteArrayInputStream(received, 8, received.length - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Product product = (Product) ois.readObject();

            System.out.println(product.getName());
            System.out.println(product.getQuantity());

            String[] names = product.getName().split(" ");
            String name = names[names.length - 1] + " ";

            for(int i = 1; i < names.length - 1; i ++) {
                name += names[i] + " ";
            }

            name += names[0];

            System.out.println(name);

            product.setName(name);

            StringBuilder quantity = new StringBuilder(String.valueOf(product.getQuantity()));
            quantity = quantity .reverse();

            product.setQuantity(Integer.parseInt(quantity.toString()));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(requestIdByte);
            ObjectOutputStream out = new ObjectOutputStream(baos);
            out.writeObject(product);
            out.flush();

            byte[] respone = baos.toByteArray();
            sendData(respone, 2209, socket);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
