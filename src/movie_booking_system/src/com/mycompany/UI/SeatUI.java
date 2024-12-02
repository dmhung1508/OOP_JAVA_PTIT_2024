package com.mycompany.UI;
import com.mycompany.model.Movie;
import com.mycompany.model.Seats;
import com.mycompany.database.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.QuadCurve2D;
import java.util.*;
import javax.swing.*;


import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static com.mycompany.UI.Receipt.*;
public class SeatUI 
{
    private JFrame myFrame;
    public static ArrayList <String> save_seat = new ArrayList<>();
    private LinkedHashSet<String> save_set_seat = new LinkedHashSet<>();
    private String tos;
    public SeatUI()
    {
        tos = "";
        
    }
    public void chooseSeat(Movie moviee, String mvname, String usrn)
    {
        myFrame = new JFrame("Movie Ticket System");
        myFrame.setSize(1150, 750);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);
        Receipt rc = new Receipt();
        myFrame.add(rc.left_Panel(moviee, myFrame, usrn));
        myFrame.add(right_Panel(myFrame, mvname, moviee, usrn));
//        System.out.println(getSeatName());
        myFrame.setVisible(true);
    }
    public String getSeatName(String mvname)
    {
        String seatname = "";
        for(String v: save_choosen_day)
        {
            for(String i: selectedCinema)
            {
                if(i!=null)
                {
                    for(String j: save_choosen_time)
                    {
                        seatname = mvname + " / " + v + " / " + i + " / " + j;
                    }
                }
            }
        }
        
        return seatname;
    }
    private JPanel right_Panel(JFrame myFrame, String mvname, Movie moviee, String usrn)
    {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(151, 0, 1000, 750);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(0, 0, 1000, 200);
        topPanel.setBackground(Color.BLACK);
        CurveScreen screen = new CurveScreen();
        screen.setBackground(Color.BLACK);
        screen.setLayout(null);
        screen.setBounds(0,0,1000, 150);
        topPanel.add(screen);
        JLabel inf = new JLabel("Screen");
        inf.setBounds(430,150, 1000, 50);
        inf.setFont(new Font("Arial", Font.BOLD, 20));
        inf.setForeground(Color.WHITE);
        topPanel.add(inf);
        rightPanel.add(topPanel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(null);
        bottomPanel.setBounds(0, 200, 1000, 550);
        bottomPanel.setBackground(Color.BLACK);
        
        SeatsDatabase sdtb = new SeatsDatabase();
        
        ArrayList<Seats> arl_seats = new ArrayList<>(sdtb.getSeats(getSeatName(mvname)));
        Collections.sort(arl_seats);
        
        
        int x = 70, y = 10, w = 60, ht = 50;
        ArrayList<JButton> h = new ArrayList<>();
        JLabel H = new JLabel("H");
        JLabel G = new JLabel("G");
        H.setForeground(Color.decode("#FFCC33"));
        H.setFont(new Font("Arial", Font.BOLD, 16));
        H.setBounds(20, 20, 30, 30);
        G.setForeground(Color.decode("#FFCC33"));
        G.setFont(new Font("Arial", Font.BOLD, 16));
        G.setBounds(20, 90, 30, 30);
        bottomPanel.add(H);
        bottomPanel.add(G);
        rightPanel.add(bottomPanel);
        for(int i=1;i<=12;i++)
        {
            String name = arl_seats.get(i+11).getSeat();
            JButton SeatName = new JButton(name);
            h.add(SeatName);
            h.get(i-1).setBounds(x,y,w,ht);
            boolean ok = true;
            if(arl_seats.get(i+11).getStatus().equals("booked"))
            {
                ok = false;
            }
            if(ok) bottomPanel.add(h.get(i-1));
            else
            {
                JLabel BookedSeat = new JLabel(name, SwingConstants.CENTER);
                BookedSeat.setOpaque(true); 
                BookedSeat.setBackground(Color.RED);
                BookedSeat.setForeground(Color.WHITE);
                BookedSeat.setBounds(x, y, w, ht);
                BookedSeat.setFont(new Font("Arial", Font.BOLD, 12)); 
                bottomPanel.add(BookedSeat);
            }
            if(i==2 || i == 10) x = x + 30;
            x = x + w + 10;
            
            final int index = i;
            h.get(i-1).addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    h.get(index-1).setBackground(Color.YELLOW);
                    save_set_seat.add(String.format("H%02d", index));
//                    save_seat.add(String.format("H%02d", index));
                }
                
            });
        }

        x = 70; y = 80; w = 60; ht = 50;
        ArrayList<JButton> g = new ArrayList<>();
        for(int i=1;i<=12;i++)
        {
            String name = arl_seats.get(i-1).getSeat();
            JButton SeatName = new JButton(name);
            g.add(SeatName);
            g.get(i-1).setBounds(x,y,w,ht);
            boolean ok = true;
            if(arl_seats.get(i-1).getStatus().equals("booked"))
            {
                ok = false;
            }
            if(ok) bottomPanel.add(g.get(i-1));
            else
            {
                JLabel BookedSeat = new JLabel(name, SwingConstants.CENTER);
                BookedSeat.setOpaque(true); 
                BookedSeat.setBackground(Color.RED);
                BookedSeat.setForeground(Color.WHITE);
                BookedSeat.setBounds(x, y, w, ht);
                BookedSeat.setFont(new Font("Arial", Font.BOLD, 12)); 
                bottomPanel.add(BookedSeat);
            }
            if(i==2 || i == 10) x = x + 30;
            x = x + w + 10;
            
            final int index = i;
            g.get(i-1).addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) 
                {
                    g.get(index-1).setBackground(Color.YELLOW);
                    save_set_seat.add(String.format("G%02d", index));
//                    save_seat.add(String.format("G%02d", index));
                }
                
            });
        }
        
        for(int i=0;i<save_seat.size();i++)
        {
            tos = tos + ", "+save_seat.get(i);
        }
        JButton confirm = new JButton("CONFIRM");
        confirm.setBounds(420, 400, 100, 30);
        confirm.setFont(new Font("Arial", Font.BOLD, 12));
        confirm.setBackground(Color.WHITE);
        
        bottomPanel.add(confirm);
        
        confirm.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                save_seat = new ArrayList<>(save_set_seat);
                String content = String.valueOf(save_seat.size()) + " ticket(s)";
                JLabel total = new JLabel(content);
                
                total.setForeground(Color.WHITE);
                total.setBounds(600, 400, 100, 30);
                total.setFont(new Font("Arial", Font.BOLD, 16));
                bottomPanel.add(total);
                bottomPanel.revalidate();
                bottomPanel.setBounds(0, 200, 1000, 550);
                bottomPanel.repaint();
                
                PrintTicket pt = new PrintTicket();
                try {
                    pt.printTicket(mvname, moviee, usrn);
                    myFrame.dispose();
                } catch (IOException ex) {
                    Logger.getLogger(SeatUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                
        });

        JPanel legendPanel = new JPanel();
        legendPanel.setLayout(null);
        legendPanel.setBounds(150, 250, 200, 100); 
        legendPanel.setBackground(Color.BLACK);

        // Ô đỏ - Booked Seat
        JPanel redSquare = new JPanel();
        redSquare.setBounds(10, 10, 20, 20);
        redSquare.setBackground(Color.RED);

        JLabel redLabel = new JLabel("Booked Seat");
        redLabel.setBounds(40, 10, 150, 20);
        redLabel.setForeground(Color.WHITE);
        redLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Ô trắng - Empty Seat
        JPanel whiteSquare = new JPanel();
        whiteSquare.setBounds(10, 40, 20, 20);
        whiteSquare.setBackground(Color.WHITE);

        JLabel whiteLabel = new JLabel("Empty Seat");
        whiteLabel.setBounds(40, 40, 150, 20);
        whiteLabel.setForeground(Color.WHITE);
        whiteLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        legendPanel.add(redSquare);
        legendPanel.add(redLabel);
        legendPanel.add(whiteSquare);
        legendPanel.add(whiteLabel);

        bottomPanel.add(legendPanel);
        //        bottomPanel.add(note);
        return rightPanel;
    }
    private class CurveScreen extends JPanel 
    {
        @Override
        protected void paintComponent(Graphics g) 
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setStroke(new BasicStroke(8));
            g2d.setColor(Color.GRAY);

            QuadCurve2D curve = new QuadCurve2D.Float();
            curve.setCurve(20, 80, 360, 10, 975, 80); 
            g2d.draw(curve);
        }
    }
}
