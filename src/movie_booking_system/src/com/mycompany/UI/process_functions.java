package com.mycompany.UI;
import com.mycompany.model.Movie;
import com.mycompany.database.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.io.*;
import javax.imageio.ImageIO;
public class process_functions 
{
    public process_functions()
    {
        
    }
    
    public boolean check_is_valid(String s)
    {
        if(s.trim().length()<1) return false;
        return true;
    }
    public JLabel processing_image(String link, int x, int y, int w, int h)
    {
        ImageIcon movie = new ImageIcon(link);
        Image scaledImage = movie.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH); 
        ImageIcon resizedImage = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(resizedImage);
        imageLabel.setBounds(x, y, w, h);
        return imageLabel;
    }
    public JLabel processing_image_from_url(String url, int x, int y, int w, int h) 
    {
        try {

            ImageIcon movie = new ImageIcon(new URL(url));
            Image scaledImage = movie.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH); 
            ImageIcon resizedImage = new ImageIcon(scaledImage);
            JLabel imageLabel = new JLabel(resizedImage);
            imageLabel.setBounds(x, y, w, h);
            return imageLabel;
        } catch (Exception e) {
            e.printStackTrace();
            JLabel errorLabel = new JLabel("Failed to load image");
            errorLabel.setBounds(x, y, w, h);
            errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
            errorLabel.setVerticalAlignment(SwingConstants.CENTER);
            return errorLabel;
        }
    }
    public JLabel processing_label(String name, int x, int y, int w, int h)
    {
        JLabel movie_label = new JLabel(name);
        movie_label.setBounds(x,y,w,h);
        movie_label.setFont(new Font("Arial", Font.PLAIN, 14));
        movie_label.setForeground(Color.WHITE);
        return movie_label;
    }
    public JLabel function_day(String day, int x, int y, int w, int h)
    {
        JLabel today = new JLabel(day);
        today.setForeground(Color.WHITE);
        today.setFont(new Font("Arial", Font.BOLD, 16));
        today.setBounds(x, y, w, h);
        return today;
    }
    public JButton function_date(String date, int x, int y, int w, int h)
    {
        JButton day1 = new JButton(date);
        day1.setBounds(x, y, w, h);
        day1.setFont(new Font("Arial", Font.BOLD, 12));
        day1.setBackground(Color.WHITE);
        return day1;
    }
    public JPanel setLine(int x, int y, int w, int h)
    {
        JPanel line = new JPanel();
        line.setLayout(null);
        line.setBounds(x, y, w, h);
        line.setBackground(Color.GRAY);
        return line;
    }    
    public JButton setButtonTime(String time, int x, int y, int w, int h)
    {
        JButton time1des1 = new JButton(time);
        time1des1.setFont(new Font("Arial", Font.BOLD, 12));
        time1des1.setBackground(Color.WHITE);
        time1des1.setBounds(x, y, w, h);
        return time1des1;
    }
    public String createID(String s)
    {
        String getFirstLetter = "";
        String[] words = s.toLowerCase().split("\\s+");
        for(String i: words)
        {
            char j = i.charAt(0);
            getFirstLetter = getFirstLetter + j;
        }
        return getFirstLetter;
    }
    
    public String createIDTicket(String username, Movie moviee)
    {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append("User");
        sb.append(username);
        sb.append("Movie");
        sb.append(createID(moviee.getTitle()));
        sb.append("ID");
        for(int i=0;i<4;i++)
        {
            char letter = (char) ('A' + random.nextInt(26));  
            sb.append(letter);
        }
        return sb.toString();
    }
    public BufferedImage croppedImage(BufferedImage image, String name_image, int x, int y, int w, int h) throws IOException
    { 
//        ImageIO.write(image, "png", name_image);
        BufferedImage croppedImage = image.getSubimage(x, y, w, h);
        File outputFile = new File(name_image);
        ImageIO.write(croppedImage, "png", outputFile);
        return croppedImage;
    }
   public JLabel resizeQR(BufferedImage image, int x, int y, int w, int h)
   {
        ImageIcon movie = new ImageIcon(image);
        Image scaledImage = movie.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH); 
        ImageIcon resizedImage = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(resizedImage);
        imageLabel.setBounds(x, y, w, h);
        return imageLabel;
   }

}
