package com.mycompany.UI;
import com.mycompany.model.Cinema;
import com.mycompany.model.Movie;
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
import static com.mycompany.UI.process_functions.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
public class Receipt 
{
    private JFrame myFrame = new JFrame("Movie Ticket System");
    public static ArrayList<JButton> save_button = new ArrayList<>();
    public static ArrayList<JButton> save_day = new ArrayList<>();
    public static ArrayList<String> save_choosen_day = new ArrayList<>();
    public static ArrayList<String> save_choosen_time = new ArrayList<>();
    public static ArrayList <JButton> save_buyButton = new ArrayList<>();
    public static Map<JButton, String> buttonToCinemaMap = new LinkedHashMap<>();
    public static ArrayList<String> selectedCinema = new ArrayList<>();

    public Receipt() {
    }
    
    public void getReceipt(Movie moviee, String usrn)
    {
        myFrame = new JFrame("Movie Ticket System");
        myFrame.setSize(1150, 750);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);

        myFrame.add(left_Panel(moviee, myFrame, usrn));
        right_Panel(moviee, usrn);
//        myFrame.add(right_Panel(moviee, usrn));
        myFrame.setVisible(true);
    }
    public JPanel left_Panel(Movie moviee, JFrame myFrame, String usrn)
    {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0,0,165, 750);
        // anh user
        JPanel sub0 = new JPanel();
        sub0.setLayout(null);
        sub0.setBounds(0, 30, 150, 200);
        ImageIcon originalImage = new ImageIcon("images//user.png");
        Image scaledImage = originalImage.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH); 
        ImageIcon resizedImage = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(resizedImage);
        imageLabel.setBounds(10, 0, 120, 120);
        sub0.add(imageLabel);
        JLabel username = new JLabel(usrn);
        username.setFont(new Font("Arial", Font.PLAIN, 20));
        username.setBounds(10, 130, 100, 50);
        sub0.add(username);
        sub0.setBackground(Color.decode("#CCCCCC"));
        leftPanel.add(sub0);
        
        // Bound Menu
        JPanel sub1 = new JPanel();
        sub1.setLayout(null);
        sub1.setBounds(0, 150, 150, 500);
        // Menu Button
        JButton menu = new JButton("Menu");
        menu.setBounds(5, 100, 160, 30);
        menu.setFont(new Font("Arial", Font.BOLD, 14));
        menu.setBackground(Color.decode("#333333"));
        menu.setForeground(Color.WHITE);
        menu.addActionListener(new ActionListener(){
            final String usrn1 = usrn;
            @Override
            public void actionPerformed(ActionEvent e) {
                
                try {
                    Menu m = new Menu();
                    m.show_Menu(usrn1);
                    myFrame.dispose();
//                    Menu.show_Menu(usrn1);
                } catch (IOException ex) {
                    Logger.getLogger(Receipt.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        // FeedbackUI Button
        JButton sendFeedback = new JButton("Send Feedback");
        sendFeedback.setBounds(5, 160, 160, 30);
        sendFeedback.setFont(new Font("Arial", Font.BOLD, 14));
        sendFeedback.setBackground(Color.decode("#333333"));
        sendFeedback.setForeground(Color.WHITE);
        sendFeedback.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                FeedbackUI fback = new FeedbackUI();
                fback.SendFeedBack(myFrame, moviee, usrn);

            }
            
        });
        // Logout Button
        JButton logout = new JButton("Logout");
        logout.setBounds(5, 220, 160, 30);
        logout.setFont(new Font("Arial", Font.BOLD, 14));
        logout.setBackground(Color.decode("#333333"));
        logout.setForeground(Color.WHITE);
        logout.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Login li = new Login();
                li.Login_Interface();
                myFrame.dispose();
            }
            
        });
        sub1.add(menu);
        sub1.add(sendFeedback);
        sub1.add(logout);
        sub1.setBackground(Color.decode("#CCCCCC"));
        leftPanel.add(sub1);
        leftPanel.setBackground(Color.decode("#CCCCCC"));
        return leftPanel;
    }
    
    
    public void right_Panel(Movie moviee, String usrn) 
    {
        CinemaManager cm = new CinemaManager();
        ArrayList<Cinema> arl_cinema = new ArrayList<>(cm.getAllCinemas());
        process_functions pf = new process_functions();
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(151, 0, 1000, 750);
        rightPanel.setBackground(Color.decode("#000000"));

        // Left of right_Panel
        JPanel lPanel = new JPanel();
        lPanel.setBounds(0, 0, 300, 750);
        lPanel.setBackground(Color.BLACK);
        lPanel.add(pf.processing_image_from_url(moviee.getImagePath(), 10, 20, 150, 200));
        lPanel.add(pf.processing_label(moviee.getTitle(), 680, 190, 180, 50));
        rightPanel.add(lPanel);

        // Right of right_Panel
        JPanel rPanel = new JPanel();
        rPanel.setBounds(301, 0, 700, 750);
        rPanel.setBackground(Color.DARK_GRAY);
        rPanel.setLayout(null);

        JPanel sub0 = new JPanel();
        sub0.setLayout(null);
        sub0.setBackground(Color.BLACK);
        int x_day = 10, y_day = 10, w_day = 100, h_day = 20;
        int x_date = 10, y_date = 50, w_date = 50, h_date = 30;

        for (String i : moviee.getShowDates()) 
        {
            String[] day = i.split("\\s+");
            sub0.add(pf.function_day(day[0], x_day, y_day, w_day, h_day));
            JButton date = pf.function_date(day[1], x_date, y_date, w_date, h_date);
            sub0.add(date);
            save_day.add(date);
            x_day += 110;
            x_date += 110;
        }

        sub0.setPreferredSize(new Dimension(Math.max(700, x_date), 150));
        JScrollPane dayScrollPane = new JScrollPane(sub0);
        dayScrollPane.setBounds(0, 0, 700, 150);
        dayScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        dayScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        // chinh kich thuoc cua scroll bar
        JScrollBar verticalScrollBar = dayScrollPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = dayScrollPane.getHorizontalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(4, 0)); 
        horizontalScrollBar.setPreferredSize(new Dimension(2, 4)); 
        // chinh mau 
        verticalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.YELLOW; 
                this.trackColor = Color.GRAY; 
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return super.createDecreaseButton(orientation);
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return super.createIncreaseButton(orientation);
            }
        });

        horizontalScrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = Color.black; 
                this.trackColor = Color.black; 
            }
        });
        rPanel.add(dayScrollPane);

        JPanel sub1 = new JPanel();
        sub1.setLayout(null);
        sub1.setBackground(Color.BLACK);
        int y_des = 10, w_des = 300, h_des = 30;
        int y_time = 50, w_time = 100, h_time = 50, y_line = 140;

        for (Cinema i : arl_cinema) {
            JLabel cinemaLabel = new JLabel(i.getName());
            cinemaLabel.setBounds(10, y_des, w_des, h_des);
            cinemaLabel.setForeground(Color.WHITE);
            sub1.add(cinemaLabel);
            y_des += 140;

            int x_time = 10;
            for (String j : i.getShowHours()) {
                JButton time = pf.setButtonTime(j, x_time, y_time, w_time, h_time);
                sub1.add(time);
                save_button.add(time);
                buttonToCinemaMap.put(time, i.getName());
                x_time += 120;
            }
            sub1.add(pf.setLine(10, y_line, 650, 1));
            y_line += 140;
            y_time += 150;
        }
        

        sub1.setPreferredSize(new Dimension(700, Math.max(650, y_time)));
        JScrollPane cinemaScrollPane = new JScrollPane(sub1);
        cinemaScrollPane.setBounds(0, 150, 700, 500);
        cinemaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        cinemaScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        rPanel.add(cinemaScrollPane);

        rightPanel.add(rPanel);

        JPanel selectPanel = new JPanel();
        selectPanel.setLayout(null);
        selectPanel.setBounds(0, 600, 700, 150);
        selectPanel.setBackground(Color.black);
        rPanel.add(selectPanel);
        
        JButton selectButton = new JButton("Select");
        selectButton.setBounds(300, 65, 100, 30);
        selectButton.setBackground(Color.YELLOW);
        selectButton.setFont(new Font("Arial", Font.BOLD, 12));
        selectButton.setVisible(false);
        
        selectButton.addActionListener(new ActionListener() 
            {
                final String name1 = moviee.getTitle();
                final String usrn1 = usrn;
                @Override
                public void actionPerformed(ActionEvent e) 
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
                                    seatname = name1 + " / " + v + " / " + i + " / " + j;
                                }
                            }
                        }
                    }
                    System.out.println("seatname = " + seatname);
                    
                    SeatUI seat = new SeatUI();
                    seat.chooseSeat(moviee, name1, usrn1);
                    myFrame.dispose();
                }
            });

        Runnable checkAndShowNewButton = () -> {
            boolean buttonSelected = save_button.stream()
                    .anyMatch(btn -> btn.getBackground() == Color.YELLOW);
            boolean daySelected = save_day.stream()
                    .anyMatch(btn -> btn.getBackground() == Color.YELLOW);

            if (buttonSelected && daySelected) {
                selectButton.setVisible(true); 
            } else {
                selectButton.setVisible(false); 
            }
        };

        
        for (JButton btn : save_button) 
        {
            btn.addActionListener(e -> 
            {
                for (JButton b : save_button) 
                {
                    b.setBackground(Color.WHITE);
                    save_choosen_time.remove(b.getText());
                    selectedCinema.remove(buttonToCinemaMap.get(b));
                }
                btn.setBackground(Color.YELLOW);
                save_choosen_time.add(btn.getText());
                selectedCinema.add(buttonToCinemaMap.get(btn));
                System.out.println(buttonToCinemaMap.get(btn));
                checkAndShowNewButton.run();
            });
        }

       
        for (JButton btn : save_day) {
            btn.addActionListener(e -> {
                for (JButton b : save_day) {
                    b.setBackground(Color.WHITE);
                    save_choosen_day.remove(b.getText());
                    
                }
                btn.setBackground(Color.YELLOW);
                save_choosen_day.add(btn.getText());
                
                checkAndShowNewButton.run();
            });
        }

        selectPanel.add(selectButton);
        

        myFrame.add(rightPanel);
    }


    
}

