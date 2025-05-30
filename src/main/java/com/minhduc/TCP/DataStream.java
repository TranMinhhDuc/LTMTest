package com.minhduc.TCP;

import java.io.*;
import java.net.Socket;

import static com.minhduc.Main.host;

class DataStream {
    public static void main(String[] args) {
        String serverHost = "localhost"; // hoặc IP server thực tế
        int serverPort = 2207;

        String studentCode = "B21DCCN256";
        String qCode = "1pIWmaAf"; // mã câu hỏi từ đề bài

        try (
                Socket socket = new Socket(host, serverPort);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                DataInputStream dis = new DataInputStream(socket.getInputStream());
        ) {
            // a. Gửi chuỗi "studentCode;qCode"
            String sendStr = studentCode + ";" + qCode;
            dos.writeUTF(sendStr);
            dos.flush();

            // b. Nhận 2 số nguyên a và b từ server
            int a = dis.readInt();
            int b = dis.readInt();

            // c. Tính tổng và tích
            int sum = a + b;
            int product = a * b;

            // Gửi lần lượt tổng và tích lên server
            dos.writeInt(sum);
            dos.flush();

            dos.writeInt(product);
            dos.flush();

            // d. Đóng kết nối và kết thúc (tự động qua try-with-resources)
            System.out.println("Đã gửi tổng và tích: sum = " + sum + ", product = " + product);

        } catch (IOException e) {
            System.err.println("Lỗi kết nối hoặc trao đổi dữ liệu: " + e.getMessage());
        }
    }
}
