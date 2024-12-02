
package com.mycompany.UI;
import com.mycompany.model.Movie;
import com.mycompany.model.Feedback;
import com.mycompany.database.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.*;
import javax.swing.table.DefaultTableCellRenderer;
import static com.mycompany.UI.process_functions.*;

public class EachMovie 
{
    private JFrame myFrame = new JFrame("Movie Ticket System");;
    public EachMovie(){}
    public void showEachMovie(Movie moviee, String usrn)
    {
        myFrame.setSize(1150, 750);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);
        Receipt rc = new Receipt();
        myFrame.add(rc.left_Panel(moviee, myFrame, usrn));
        myFrame.add(right_Panel(myFrame, moviee, usrn));
        check_size_feedback(moviee);
        myFrame.setVisible(true);
    }
    private void check_size_feedback(Movie moviee)
    {
        FeedbackDatabase fbdtb = new FeedbackDatabase();
        ArrayList<Feedback> arl_feedback = new ArrayList<>(fbdtb.getFeedbacks(moviee.getTitle()));
        for(Feedback i: arl_feedback)
        {
            System.out.println(i.getUser());
            System.out.println(i.getFeedback());
        }
    }
    private JPanel right_Panel(JFrame myFrame, Movie moviee, String usrn)
    {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(151, 0, 1000, 750);
        rightPanel.setBackground(Color.decode("#000000"));
        process_functions pf = new process_functions();
        
        JPanel movie_infor = new JPanel();
        movie_infor.setLayout(null);
        movie_infor.setBounds(250, 20, 800, 300);
        movie_infor.setBackground(Color.black);
        JLabel movie_name = pf.processing_label("<html><b>Movie name:</b> " + moviee.getTitle() + "</html>", 10, 0, 800, 30);
        movie_name.setFont(new Font("Arial", Font.BOLD, 16));
        movie_name.setForeground(Color.WHITE);

        JLabel genre = pf.processing_label("<html><b>Genre:</b> " + moviee.getGenre() + "</html>", 10, 40, 800, 30);
        genre.setFont(new Font("Arial", Font.PLAIN, 14));
        genre.setForeground(Color.LIGHT_GRAY);

        JLabel director = pf.processing_label("<html><b>Director:</b> " + moviee.getDirector() + "</html>", 10, 80, 800, 30);
        director.setFont(new Font("Arial", Font.PLAIN, 14));
        director.setForeground(Color.LIGHT_GRAY);

        JLabel description = pf.processing_label("<html><b>Description:</b> " + moviee.getDescription() + "</html>", 10, 120, 800, 90);
        description.setFont(new Font("Arial", Font.ITALIC, 14));
        description.setForeground(Color.LIGHT_GRAY);

        JLabel duration = pf.processing_label("<html><b>Duration:</b> " + moviee.getDuration() + " minutes</html>", 10, 220, 800, 30);
        duration.setFont(new Font("Arial", Font.PLAIN, 14));
        duration.setForeground(Color.LIGHT_GRAY);

        JLabel premiere_date = pf.processing_label("<html><b>Release date:</b> " + moviee.getReleaseDate() + "</html>", 10, 260, 800, 30);
        premiere_date.setFont(new Font("Arial", Font.PLAIN, 14));
        premiere_date.setForeground(Color.LIGHT_GRAY);

        JLabel main_actor = pf.processing_label("<html><b>Main actors:</b> " + moviee.getMainActors() + "</html>", 10, 300, 800, 30);
        main_actor.setFont(new Font("Arial", Font.PLAIN, 14));
        main_actor.setForeground(Color.LIGHT_GRAY);

        movie_infor.add(movie_name);
        movie_infor.add(genre);
        movie_infor.add(director);
        movie_infor.add(description);
        movie_infor.add(duration);
        movie_infor.add(premiere_date);
        movie_infor.add(main_actor);
        rightPanel.add(movie_infor);
        
        JPanel btn = new JPanel();
        btn.setLayout(null);
        btn.setBounds(780, 620, 100, 40);
        JButton buy_button  = new JButton("Buy now");
        buy_button.setLayout(null);
        buy_button.setBounds(0, 0, 100, 40);
        buy_button.setBackground(Color.yellow);
        buy_button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Receipt rc = new Receipt();
                rc.getReceipt(moviee, usrn);
                myFrame.dispose();
            }
            
        });
        btn.add(buy_button);
        rightPanel.add(btn);
        
        FeedbackDatabase fbdtb = new FeedbackDatabase();
        ArrayList<Feedback> arl_feedback = new ArrayList<>(fbdtb.getFeedbacks(moviee.getTitle()));
        JPanel khung = new JPanel();
        khung.setLayout(null);
        khung.setBounds(40, 330, 600, 30);
        khung.setBackground(Color.pink);
        
        JLabel title = pf.processing_label("Review of viewers", 220, 0, 200, 30);
        khung.add(title);

        String[] columnNames = {"User", "Review", "Status"}; 
        String[][] data = new String[arl_feedback.size()+1][3];
        int count_positive = arl_feedback.size();

        for (int i = 0; i < arl_feedback.size(); i++) 
        {
            data[i][0] = arl_feedback.get(i).getUser();
            data[i][1] = arl_feedback.get(i).getFeedback();
            if(arl_feedback.get(i).getStatus().equals("Negative"))
            {
                data[i][2] = "0";
                count_positive --;
            }
            else data[i][2] = "1";
        }

        
        JTable reviewTable = new JTable(data, columnNames);
        reviewTable.setRowHeight(30);
        reviewTable.setFont(new Font("Arial", Font.PLAIN, 14));
        reviewTable.getTableHeader().setBackground(Color.LIGHT_GRAY);

        reviewTable.getColumnModel().getColumn(2).setMinWidth(0);
        reviewTable.getColumnModel().getColumn(2).setMaxWidth(0);
        reviewTable.getColumnModel().getColumn(2).setWidth(0);
        reviewTable.getColumnModel().getColumn(0).setPreferredWidth(100); 
        reviewTable.getColumnModel().getColumn(1).setPreferredWidth(500);
        DefaultTableCellRenderer reviewRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (column == 1) {
                    String status = (String) table.getValueAt(row, 2); 

                    if ("1".equals(status)) {
                        cell.setBackground(Color.WHITE); 
                        cell.setForeground(Color.BLUE);
                    } else if ("0".equals(status)) {
                        cell.setBackground(Color.WHITE); 
                        cell.setForeground(Color.BLACK);
                    } else {
                        cell.setBackground(Color.WHITE); 
                        cell.setForeground(Color.BLACK);
                    }
                } else {
                    cell.setBackground(Color.WHITE); 
                    cell.setForeground(Color.BLACK);
                }

                return cell;
            }
        };
        
        JPanel avt = new JPanel();
        avt.setLayout(null);
        avt.setBounds(40, 20, 200, 300);
        avt.setBackground(Color.black);
        avt.add(pf.processing_image_from_url(moviee.getImagePath(), 00, 0, 200, 250));
        JLabel reviews = pf.processing_label("Review (" + String.valueOf(count_positive) + " positive reviews)", 0, 255, 200, 30);
        avt.add(reviews);
        rightPanel.add(avt);
        
        reviewTable.getColumnModel().getColumn(1).setCellRenderer(reviewRenderer);
        JScrollPane chat = new JScrollPane(reviewTable);
        chat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chat.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        chat.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));  
        chat.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 8));  

        chat.setBounds(40, 360, 600, 350);

        rightPanel.add(khung);
        rightPanel.add(chat);

        return rightPanel;
    }
}
