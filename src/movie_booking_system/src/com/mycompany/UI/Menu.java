package com.mycompany.UI;

import com.mycompany.model.Movie;
import com.mycompany.database.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import static com.mycompany.UI.process_functions.*;
import java.util.Collections;

public class Menu 
{

    private JFrame myFrame;
    private process_functions pf = new process_functions();
    public Menu() 
    {
        
    }

    public void show_Menu(String username) throws IOException 
    {
        initializeFrame();
        myFrame.add(createLeftPanel(username));
        myFrame.add(createRightPanel(username));
        myFrame.setVisible(true);
    }

    private void initializeFrame() 
    {
        myFrame = new JFrame("Movie Ticket System");
        myFrame.setSize(1150, 750);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);
    }

    private JPanel createLeftPanel(String username) 
    {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 165, 750);
        leftPanel.setBackground(Color.decode("#CCCCCC"));

        leftPanel.add(createUserInfoPanel(username));
        leftPanel.add(createMenuPanel());

        return leftPanel;
    }

    private JPanel createUserInfoPanel(String username) 
    {
        JPanel userInfoPanel = new JPanel();
        userInfoPanel.setLayout(null);
        userInfoPanel.setBounds(0, 30, 150, 200);
        userInfoPanel.setBackground(Color.decode("#CCCCCC"));
        
        userInfoPanel.add(pf.processing_image("images//user.png", 10, 0, 120, 120));

        JLabel usernameLabel = new JLabel(username);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        usernameLabel.setBounds(10, 130, 100, 50);
        userInfoPanel.add(usernameLabel);

        return userInfoPanel;
    }

    private JPanel createMenuPanel() 
    {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBounds(0, 150, 150, 500);
        menuPanel.setBackground(Color.decode("#CCCCCC"));

        menuPanel.add(createButton("Menu", 100, e -> showMainMenu()));
        menuPanel.add(createButton("Send Feedback", 160, e -> sendFeedback()));
        menuPanel.add(createButton("Logout", 220, e -> logout()));
        menuPanel.add(createButton("Asking Chatbot", 400, e -> showChatbot()));

        return menuPanel;
    }

    private JButton createButton(String text, int y, ActionListener action) 
    {
        JButton button = new JButton(text);
        button.setBounds(5, y, 160, 30);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(Color.decode("#333333"));
        button.setForeground(Color.WHITE);
        button.addActionListener(action);
        return button;
    }

    private void showMainMenu()
    {
        
    }

    private void sendFeedback() 
    {
        
    }

    private void logout() 
    {
        myFrame.dispose();
        Login login = new Login();
        login.Login_Interface();
    }

    private void showChatbot() 
    {
        ChatInterface chatInterface = new ChatInterface();
        chatInterface.showChatInterface();
    }

    private JPanel createRightPanel(String username) throws IOException 
    {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(null);
        rightPanel.setBounds(166, 0, 985, 750);
        rightPanel.setBackground(Color.decode("#000000"));
        JLabel moviesLabel = new JLabel("Movies");
        moviesLabel.setBounds(20, 10, 100, 50);
        moviesLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        moviesLabel.setForeground(Color.WHITE);
        rightPanel.add(moviesLabel);
        rightPanel.add(createMovieListPanel(username));
        return rightPanel;
    }

    private JScrollPane createMovieListPanel(String username) throws IOException 
    {
        MovieDatabase movieDatabase = new MovieDatabase();
        ArrayList<Movie> movies = new ArrayList<>(movieDatabase.getAllMovies());
        Collections.sort(movies);
        JPanel movieListPanel = new JPanel();
        movieListPanel.setLayout(null);
        movieListPanel.setPreferredSize(new Dimension(985, ((movies.size() + 2) / 3) * 300));
        movieListPanel.setBackground(Color.decode("#000000"));
        int movieWidth = 150;
        int movieHeight = 250;
        int gapX = 150;
        int gapY = 30;
        int xOffset = 60;
        int yOffset = 20;
        for (int i = 0; i < movies.size(); i++) 
        {
            Movie movie = movies.get(i);
            int col = i % 3;
            int row = i / 3;
            int x = xOffset + col * (movieWidth + gapX);
            int y = yOffset + row * (movieHeight + gapY);
            movieListPanel.add(pf.processing_image_from_url(movie.getImagePath(), x, y, movieWidth, movieHeight - 50));
            movieListPanel.add(pf.processing_label(movie.getTitle(), x, y + movieHeight - 40, movieWidth + gapX / 2, 30));
            JButton detailsButton = new JButton("More details");
            detailsButton.setBounds(x + movieWidth / 8, y + movieHeight - 10, 120, 30);
            detailsButton.setBackground(Color.decode("#FFFF00"));
            detailsButton.setFont(new Font("Arial", Font.BOLD, 12));
            detailsButton.addActionListener(e -> showMovieDetails(movie, username));
            movieListPanel.add(detailsButton);
        }

        JScrollPane scrollPane = new JScrollPane(movieListPanel);
        scrollPane.setBounds(0, 80, 1000, 650);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        return scrollPane;
    }

    private void showMovieDetails(Movie movie, String username) 
    {
        
        EachMovie eachMovie = new EachMovie();
        eachMovie.showEachMovie(movie, username);
        myFrame.dispose();
    }
}
