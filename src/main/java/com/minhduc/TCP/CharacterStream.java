package com.minhduc.TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static com.minhduc.Main.host;

public class CharacterStream {
    public static void main(String[] args) {
        try (Socket socket = new Socket()){
            socket.connect(new InetSocketAddress(host, 2208));
            socket.setSoTimeout(5000);

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String message = "B21DCCN256;aQpIb32R";
            out.write(message);
            out.newLine();
            out.flush();

            String req = in.readLine();
            System.out.println(req);
            String[] arr = req.split(",");
            List<String> myList = new ArrayList<>();

            for(String s: arr) {
                if(s.endsWith(".edu")) {
                    myList.add(s);
                }

            }

            String s = "";

            for (String str: myList) {
                System.out.println(str);
                s += str + ", ";
            }

            s = s.substring(0, s.length() - 2);

            System.out.println(s);
            out.write(s);
            out.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
