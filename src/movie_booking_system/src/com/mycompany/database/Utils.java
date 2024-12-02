package com.mycompany.database;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;

public class Utils {
    public BufferedImage getQR(int amount, String addInfo) {
        try {
            // URL API
            URL url = new URL("https://oop.dinhmanhhung.net/get_QR");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Dữ liệu POST
            String jsonInput = String.format("""
                {
                    "amount": %d,
                    "addInfo": "%s",
                    "key": "dmhung1508"
                }
            """, amount, addInfo);

            // Gửi yêu cầu
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Kiểm tra mã phản hồi
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Đọc ảnh trả về
                InputStream inputStream = connection.getInputStream();
                BufferedImage image = ImageIO.read(inputStream);
                connection.disconnect();
                return image;
                
            } else {
                System.err.println("Error: HTTP response code " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkTicketExists(String ticket) {
        try {
            // URL API
            URL url = new URL("http://oop.dinhmanhhung.net/ticket");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Dữ liệu POST
            String jsonInput = String.format("""
                {
                    "ticket": "%s",
                    "key": "dmhung1508"
                }
            """, ticket);

            // Gửi yêu cầu
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Kiểm tra mã phản hồi
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Đọc phản hồi
                InputStream inputStream = connection.getInputStream();
                StringBuilder response = new StringBuilder();
                int ch;
                while ((ch = inputStream.read()) != -1) {
                    response.append((char) ch);
                }
                connection.disconnect();

                // Kiểm tra giá trị "exists"
                return response.toString().contains("\"exists\":true");
            } else {
                System.err.println("Error: HTTP response code " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean sendEmail(String tenKhachHang, String tenPhim, String ngayChieu, String gioChieu, String soGhe, String emailKhachHang) {
        try {
            // URL API
            URL url = new URL("http://oop.dinhmanhhung.net/send_mail");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Dữ liệu POST
            String jsonInput = String.format("""
                {
                    "key": "dmhung1508",
                    "ten_khach_hang": "%s",
                    "ten_phim": "%s",
                    "ngay_chieu": "%s",
                    "gio_chieu": "%s",
                    "so_ghe": "%s",
                    "email_khanh_hang": "%s"
                }
            """, tenKhachHang, tenPhim, ngayChieu, gioChieu, soGhe, emailKhachHang);

            // Gửi yêu cầu
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Kiểm tra mã phản hồi
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.disconnect();
                return true;
            } else {
                System.err.println("Error: HTTP response code " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public String chat(String userInput) {
        try {
            // URL API
            URL url = new URL("http://oop.dinhmanhhung.net/suggest-movie");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Dữ liệu POST
            String jsonInput = String.format("""
                {
                    "user_input": "%s"
                }
            """, userInput);

            // Gửi yêu cầu
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Kiểm tra mã phản hồi
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Đọc phản hồi với InputStreamReader để xử lý UTF-8
                InputStreamReader reader = new InputStreamReader(connection.getInputStream(), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(reader);
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                bufferedReader.close();
                connection.disconnect();

                // Trả về suggested_feedback
                String responseString = response.toString();
                if (responseString.contains("\"suggested_feedback\":\"")) {
                    String x = "";
                    for(int i= 23;i<=responseString.length()-3;i++)
                    {
                        if(responseString.charAt(i)!= '\\')
                        {
                              x = x +responseString.charAt(i);
                    }
                    }
                    
                    return x;
//                    return responseString.split("\"suggested_feedback\":\"")[1].split("\"")[0];
                } else {
                    System.err.println("Error: suggested_feedback not found in the response");
                }
            } else {
                System.err.println("Error: HTTP response code " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
            return "Có lỗi xảy ra khi xử lý yêu cầu.";
        }
        return null;
    }

    public String classifyFeedback(String feedback) {
        try {
            // URL API
            URL url = new URL("http://oop.dinhmanhhung.net/classify-feedback");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Dữ liệu POST
            String jsonInput = String.format("""
                {
                    "feedback": "%s"
                }
            """, feedback);

            // Gửi yêu cầu
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonInput.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Kiểm tra mã phản hồi
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Đọc phản hồi
                InputStream inputStream = connection.getInputStream();
                StringBuilder response = new StringBuilder();
                int ch;
                while ((ch = inputStream.read()) != -1) {
                    response.append((char) ch);
                }
                connection.disconnect();

                // Trả về classification
                return response.toString().split("\"classification\":\"")[1].split("\"")[0];
            } else {
                System.err.println("Error: HTTP response code " + responseCode);
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    
}