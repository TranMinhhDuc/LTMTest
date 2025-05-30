package com.minhduc.TCP;

import TCP.Product;

import java.io.*;
import java.net.Socket;

import static com.minhduc.Main.host;

public class ObjectStream {
    public static void main(String[] args) {
        String serverHost = "localhost"; // hoặc IP server
        int serverPort = 2209;
        String studentCode = "B21DCCN256";
        String qCode = "fI8IdbIn"; // mã câu hỏi

        try (
                Socket socket = new Socket(host, serverPort);
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ) {
            // a. Gửi chuỗi "studentCode;qCode"
            String message = studentCode + ";" + qCode;
            oos.writeObject(message);
            oos.flush();

            // b. Nhận đối tượng Product từ server
            Object obj = ois.readObject();
            if (!(obj instanceof Product)) {
                System.err.println("Server trả về không phải là đối tượng Product!");
                return;
            }
            Product product = (Product) obj;
            System.out.println("Nhận từ server: " + product);

            // c. Tính discount = tổng các chữ số phần nguyên của price
            int discount = calculateDiscount(product.getPrice());
            product.setDiscount(discount);
            System.out.println("Đã tính discount: " + discount);

            // Gửi lại đối tượng product đã cập nhật discount lên server
            oos.writeObject(product);
            oos.flush();

            // d. Đóng kết nối và kết thúc
            System.out.println("Đã gửi lại sản phẩm với discount lên server, kết thúc chương trình.");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static int calculateDiscount(double price) {
        int sum = 0;
        int integerPart = (int) price;
        while (integerPart > 0) {
            sum += integerPart % 10;
            integerPart /= 10;
        }
        return sum;
    }
}
