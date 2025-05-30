package com.minhduc.TCP;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import static com.minhduc.Main.host;

public class ByteStream {
    public static void main(String[] args) {
        try (Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(host, 2206));
            socket.setSoTimeout(5000);

            OutputStream out = socket.getOutputStream();
            InputStream in = socket.getInputStream();

            String message = "B21DCCN256;pFZOZwqi";
            byte[] res1 = message.getBytes();
            out.write(res1);
            out.flush();

            byte[] res2 = new byte[1024];
            in.read(res2);
            String str = new String(res2).trim();
            System.out.println(str);
            String[] data = str.split("\\|");
            Long sum = 0L;
            for(String s : data) {
                sum += Long.parseLong(s);
            }
            message = String.valueOf(sum);
            byte[] res3 = message.getBytes();
            out.write(res3);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
