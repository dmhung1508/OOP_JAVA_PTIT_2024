package com.mycompany.UI;

import com.mycompany.database.*;
import javax.swing.*; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Login {

    private JFrame myFrame;
    private JTextField textUsername;
    private JPasswordField textPassword;
    private AccountManager accountManager = new AccountManager();
    public Login() 
    {
        this.myFrame = new JFrame("Movie Ticket System");
    }

    private void initializeFrame()
    {
        myFrame.setSize(1150, 750);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setLayout(null);
        myFrame.add(createLeftPanel());
        myFrame.add(createRightPanel());
        
        myFrame.setVisible(true);
    }

    private JPanel createLeftPanel() 
    {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(null);
        leftPanel.setBounds(0, 0, 500, 750);

        ImageIcon originalImage = new ImageIcon("images//its-movie-time-vector.jpg");
        Image scaledImage = originalImage.getImage().getScaledInstance(
        leftPanel.getWidth(), leftPanel.getHeight(), Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

        imageLabel.setBounds(0, 0, leftPanel.getWidth(), leftPanel.getHeight());
        leftPanel.add(imageLabel);
        leftPanel.setBackground(Color.decode("#CCCCCC"));
        return leftPanel;
    }

    
    private JPanel createRightPanel() 
    {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(4, 1));
        rightPanel.setBounds(530, 10, 580, 750);
        rightPanel.add(createHeaderPanel());
        rightPanel.add(createInputPanel());
        rightPanel.add(createLoginButtonPanel());
        rightPanel.add(createSignUpPanel());
        return rightPanel;
    }

    private JPanel createHeaderPanel() 
    {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(2, 1));
        ImageIcon ticketIcon = new ImageIcon("images//ticket.jpg");
        Image scaledImage = ticketIcon.getImage().getScaledInstance(153, 82, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));

        JLabel titleLabel = new JLabel("  Movie Ticket System");
        titleLabel.setFont(new Font("MV Boli", Font.BOLD, 48));

        headerPanel.add(imageLabel);
        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createInputPanel() 
    {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 1));
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        textUsername = new JTextField();
        textUsername.setFont(new Font("Arial", Font.PLAIN, 20));
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        textPassword = new JPasswordField();
        textPassword.setFont(new Font("Arial", Font.PLAIN, 20));
        inputPanel.add(usernameLabel);
        inputPanel.add(textUsername);
        inputPanel.add(passwordLabel);
        inputPanel.add(textPassword);
        return inputPanel;
    }

    private JPanel createLoginButtonPanel() 
    {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(null);
        JButton loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 20));
        loginButton.setBounds(-20, 40, 650, 50);
        loginButton.setBackground(Color.decode("#BB0000"));
        loginButton.addActionListener(new LoginButtonListener());

        buttonPanel.add(loginButton);
        return buttonPanel;
    }

    private JPanel createSignUpPanel() 
    {
        JPanel signUpPanel = new JPanel();
        signUpPanel.setLayout(new FlowLayout());
        JLabel askLabel = new JLabel("Don't have an account?               ");
        askLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton signUpButton = new JButton("Sign up here");
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setBorderPainted(false);
        signUpButton.setFocusPainted(false);
        signUpButton.setForeground(Color.decode("#BB0000"));
        signUpButton.setBackground(Color.decode("#EEEEEE"));
        signUpButton.addActionListener(new SignUpButtonListener());
        signUpPanel.add(askLabel);
        signUpPanel.add(signUpButton);
        return signUpPanel;
    }

    private class LoginButtonListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = textUsername.getText();
            String password = new String(textPassword.getPassword());

            
            try {
                if (accountManager.check_correct(username, password)) 
                {
                    
                    if (accountManager.check_admin(username)) 
                    {
                        
                        AdminPanel adminPanel = new AdminPanel();
                        adminPanel.setVisible(true);
                        myFrame.dispose();
                    } 
                    else 
                    {
                        JOptionPane.showMessageDialog(myFrame, "Login successfully!");
                        Menu menu = new Menu();
                        menu.show_Menu(username);
                        myFrame.dispose();
                    }
                } else {
                    JOptionPane.showMessageDialog(myFrame, "Wrong username or password!");
                }
            } catch (IOException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class SignUpButtonListener implements ActionListener 
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            myFrame.dispose();
            RegisterPage registerPage = new RegisterPage();
            registerPage.RegisterPage();
        }
    }

    public void Login_Interface() 
    {
        initializeFrame();
    }
}
