package com.mycompany.UI;
import com.mycompany.database.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ChatInterface extends JFrame 
{
    private JTextArea chatHistory;
    private JTextField inputField;
    private JButton sendButton;
    private List<String> chatLog; 
    public ChatInterface()
    {
        
    }
    public void showChatInterface() 
    {
        setTitle("Chat System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 245, 245));
        chatHistory = new JTextArea();
        chatHistory.setEditable(false);
        chatHistory.setFont(new Font("Arial", Font.PLAIN, 16));
        chatHistory.setLineWrap(true);
        chatHistory.setWrapStyleWord(true);
        chatHistory.setBackground(new Color(230, 230, 250));
        chatHistory.setForeground(new Color(50, 50, 50));
        JScrollPane scrollPane = new JScrollPane(chatHistory);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));
        inputPanel.add(inputField, BorderLayout.CENTER);

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.BOLD, 16));
        sendButton.setBackground(new Color(100, 149, 237));
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);
        chatLog = new ArrayList<>();
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        setVisible(true);
    }

    private void sendMessage() 
    {
        Utils utils = new Utils();
        String userMessage = inputField.getText().trim();
        if (!userMessage.isEmpty()) {
            chatLog.add("You: " + userMessage);
            appendMessage("You: " + userMessage, new Color(60, 179, 113));
//            String systemMessage = 
            chatLog.add("Chatbot: " + utils.chat(userMessage));
            appendMessage("Chatbot: " + utils.chat(userMessage), new Color(255, 69, 0));
            inputField.setText("");
        }
    }

    private void appendMessage(String message, Color color) {
        chatHistory.append(message + "\n");
        chatHistory.setCaretPosition(chatHistory.getDocument().getLength());
    }

    
}