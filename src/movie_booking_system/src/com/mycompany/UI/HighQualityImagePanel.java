package com.mycompany.UI;
import com.mycompany.database.Utils;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HighQualityImagePanel extends JPanel {
    private BufferedImage image;

    public HighQualityImagePanel(int amount, String content) 
    {
        try 
        {
            
            Utils utils = new Utils();
            image = utils.getQR(amount, content);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        if (image != null) 
        {
           
            Graphics2D g2d = (Graphics2D) g;

            // Bật các chế độ tăng chất lượng
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Resize ảnh theo kích thước panel
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            g2d.drawImage(image, 0, 0, 120, 120, this);
        }
        else
        {
            System.out.println("null");
        }
    }


}