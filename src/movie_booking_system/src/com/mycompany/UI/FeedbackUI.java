
package com.mycompany.UI;
import com.mycompany.model.Movie;
import com.mycompany.model.Feedback;
import com.mycompany.database.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class FeedbackUI 
{
    private JFrame fb = new JFrame("Give us your feedback");
    public FeedbackUI()
    {
        
    }
    public void SendFeedBack(JFrame myFrame, Movie moviee, String usrn)
    {
        
        fb.setSize(600, 400);
        fb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fb.setLayout(null);
        JLabel label = new JLabel("Feedback");
        label.setBounds(200, 5, 400, 50);
        label.setFont(new Font("MV Boli", Font.PLAIN, 50));
        JTextField urfb = new JTextField();
        urfb.setBounds(20, 60, 550, 150);
        urfb.setFont(new Font("Arial", Font.PLAIN, 20));
        JButton send = new JButton("Send");
        send.setBounds(400, 230, 100, 30);
        send.setFont(new Font("Arial", Font.PLAIN, 14));
        send.setBackground(Color.decode("#FFCCFF"));
        
        send.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                Feedback Fb = new Feedback(usrn, moviee.getTitle(), urfb.getText());
                FeedbackDatabase fbdtb = new FeedbackDatabase();
                Utils utils = new Utils();
                fbdtb.addFeedback(Fb.getMovie(), Fb.getUser(), Fb.getFeedback(), utils.classifyFeedback(Fb.getFeedback()));
                fb.dispose();
                EachMovie em2 = new EachMovie();
                em2.showEachMovie(moviee, usrn);
//                EachMovie.EachMovie(moviee, usrn);
                myFrame.dispose();
            }
            
        });
        fb.add(label);
        fb.add(urfb);
        fb.add(send);
        fb.getContentPane().setBackground(Color.decode("#99CCFF"));
        fb.setVisible(true);
    }
}