package com.minhduc;

import TCP.Product;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.InetSocketAddress;

public class TCPObject {
    public static void main(String[] args) {
        final String HOST = "203.162.10.109";
        final int PORT = 2209;
        final String studentCode = "B21DCCN256";
        final String qCode = "fI8IdbIn";

        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(HOST, PORT));
            socket.setSoTimeout(5000); // timeout 5s

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush(); // quan trọng để tránh deadlock
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            // Step a: Gửi chuỗi xác thực
            String auth = studentCode + ";" + qCode;
            oos.writeObject(auth);
            oos.flush();

            // Step b: Nhận đối tượng Product từ server
            Product product = (Product) ois.readObject();
            System.out.println("Nhận được sản phẩm: " + product.getName() + " - Giá: " + product.getPrice());

            // Step c: Tính tổng chữ số phần nguyên của price
            int sum = sumOfDigits((int) product.getPrice());
            product.setDiscount(sum);
            System.out.println("Gán discount = " + sum);

            // Step d: Gửi lại đối tượng đã chỉnh sửa
            oos.writeObject(product);
            oos.flush();

            System.out.println("Hoàn thành. Đã gửi lại product có discount.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm tính tổng các chữ số
    private static int sumOfDigits(int num) {
        int sum = 0;
        while (num != 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
}
