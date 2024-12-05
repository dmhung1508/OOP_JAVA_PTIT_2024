package com.mycompany.UI;
import com.mycompany.model.Transaction;
import com.mycompany.model.Cinema;
import com.mycompany.model.Movie;
import com.mycompany.model.Feedback;
import com.mycompany.database.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.bson.Document;

public class AdminPanel extends JFrame {
    private static final Color BACKGROUND_COLOR = new Color(245, 245, 250);
    private static final Color PRIMARY_COLOR = new Color(52, 152, 219);
    private static final Color SECONDARY_COLOR = new Color(44, 62, 80);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);

    private MovieDatabase movieDatabase;
    private TransactionHistory transactionHistory;
    private AccountManager accountManager;
    private CinemaManager cinemaManager;
    private FeedbackDatabase feedbackDatabase;

    private JTable movieTable, transactionTable, accountTable, cinemaTable, feedbackTable;
    private JTabbedPane tabbedPane;

    public AdminPanel() throws MalformedURLException {
        initializeDatabase();
        setupFrame();
        createUI();
    }

    private void initializeDatabase() {
        movieDatabase = new MovieDatabase();
        transactionHistory = new TransactionHistory();
        accountManager = new AccountManager();
        cinemaManager = new CinemaManager();
        feedbackDatabase = new FeedbackDatabase();
    }

    private void setupFrame() throws MalformedURLException {
        setTitle("Cinema Management System");
        setSize(1100, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(BACKGROUND_COLOR);
        String imageUrl = "https://i.ibb.co/HVRfYtz/picture-1-1505503146.png"; // Đổi thành URL ảnh của bạn
        URL url = new URL(imageUrl);
        ImageIcon icon = new ImageIcon(url);
        setIconImage(icon.getImage());
    }

    private void createUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Cinema Management", SwingConstants.CENTER);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(SECONDARY_COLOR);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        tabbedPane = createStyledTabbedPane();
        tabbedPane.addTab("Movie Management", createMovieManagementPanel());
        tabbedPane.addTab("Transaction History", createTransactionHistoryPanel());
        tabbedPane.addTab("Account Management", createAccountManagementPanel());
        tabbedPane.addTab("Cinema Management", createCinemaManagementPanel());
        tabbedPane.addTab("Feedback Management", createFeedbackManagementPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
    }

    private JTabbedPane createStyledTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(REGULAR_FONT);
        tabbedPane.setBackground(BACKGROUND_COLOR);
        tabbedPane.setForeground(SECONDARY_COLOR);
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            switch (selectedIndex) {
                case 1:
                    refreshTransactionTable();
                    break;
                case 2:
                    refreshAccountTable();
                    break;
                case 3:
                    refreshCinemaTable();
                    break;
                case 4:
                    refreshFeedbackTable();
                    break;
            }
        });
        return tabbedPane;
    }

    private JPanel createMovieManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        movieTable = createStyledTable(new String[]{"ID", "Title", "Genre", "Duration", "Release Date", "Status", "Image Path", "Director", "Description", "Main Actors", "Show Dates", "Cinemas"});
        JScrollPane scrollPane = new JScrollPane(movieTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        createMovieContextMenu();

        JPanel buttonPanel = createMovieButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshMovieTable();
        return panel;
    }

    private void createMovieContextMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editItem = new JMenuItem("Edit Movie");
        editItem.addActionListener(e -> showEditMovieDialog());
        popupMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete Movie");
        deleteItem.addActionListener(e -> showDeleteMovieDialog());
        popupMenu.add(deleteItem);

        movieTable.setComponentPopupMenu(popupMenu);

        movieTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private JPanel createMovieButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton addButton = createStyledButton("Add Movie", e -> showAddMovieDialog());
        buttonPanel.add(addButton);

        return buttonPanel;
    }

    private void showAddMovieDialog() {
        JDialog dialog = createMovieDialog("Add Movie", true);
        JPanel inputPanel = createMovieInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createDialogButtonPanel(inputPanel, dialog, true);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showEditMovieDialog() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a movie to edit.", "No movie selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = createMovieDialog("Edit Movie", true);
        JPanel inputPanel = createMovieInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        populateMovieFields(inputPanel, selectedRow);

        JPanel buttonPanel = createDialogButtonPanel(inputPanel, dialog, false);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JDialog createMovieDialog(String title, boolean modal) {
        JDialog dialog = new JDialog(this, title, modal);
        dialog.setSize(800, 800);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        return dialog;
    }

    private JPanel createDialogButtonPanel(JPanel inputPanel, JDialog dialog, boolean isAdd) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areMovieFieldsValid(inputPanel)) {
                try {
                    saveMovie(inputPanel, isAdd);
                    refreshMovieTable();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = createStyledButton("Cancel", e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private void populateMovieFields(JPanel inputPanel, int selectedRow) {
        // Truy xuất các thành phần theo chỉ số
        JTextField idField = (JTextField) inputPanel.getComponent(1);
        JTextField titleField = (JTextField) inputPanel.getComponent(3);
        JTextField genreField = (JTextField) inputPanel.getComponent(5);
        JTextField durationField = (JTextField) inputPanel.getComponent(7);
        JTextField releaseDateField = (JTextField) inputPanel.getComponent(9);
        JTextField statusField = (JTextField) inputPanel.getComponent(11);
        JTextField imagePathField = (JTextField) inputPanel.getComponent(13);
        JTextField directorField = (JTextField) inputPanel.getComponent(15);
        JTextField descriptionField = (JTextField) inputPanel.getComponent(17);
        JTextField mainActorsField = (JTextField) inputPanel.getComponent(19);
        JTextField showDateField = (JTextField) inputPanel.getComponent(21);
        JPanel cinemaPanel = (JPanel) inputPanel.getComponent(22); // Adjusted index for cinemaPanel
        JCheckBox[] cinemaCheckBoxes = (JCheckBox[]) cinemaPanel.getClientProperty("checkBoxes");
    
        // Điền dữ liệu từ bảng vào các trường
        idField.setText((String) movieTable.getValueAt(selectedRow, 0));
        titleField.setText((String) movieTable.getValueAt(selectedRow, 1));
        genreField.setText((String) movieTable.getValueAt(selectedRow, 2));
        durationField.setText(String.valueOf(movieTable.getValueAt(selectedRow, 3)));
        releaseDateField.setText((String) movieTable.getValueAt(selectedRow, 4));
        statusField.setText((String) movieTable.getValueAt(selectedRow, 5));
        imagePathField.setText((String) movieTable.getValueAt(selectedRow, 6));
        directorField.setText((String) movieTable.getValueAt(selectedRow, 7));
        descriptionField.setText((String) movieTable.getValueAt(selectedRow, 8));
        mainActorsField.setText((String) movieTable.getValueAt(selectedRow, 9));
        showDateField.setText((String) movieTable.getValueAt(selectedRow, 10));
    
        // Điền dữ liệu checkbox rạp chiếu
        ArrayList<String> selectedCinemas;
        Object value = movieTable.getValueAt(selectedRow, 11);
        if (value instanceof ArrayList<?>) {
            selectedCinemas = (ArrayList<String>) value;
        } else if (value instanceof String) {
            selectedCinemas = new ArrayList<>(Arrays.asList(((String) value).split(",\\s*")));
        } else {
            selectedCinemas = new ArrayList<>();
        }

        for (JCheckBox checkBox : cinemaCheckBoxes) {
            checkBox.setSelected(selectedCinemas.contains(checkBox.getText()));
        }
    }
    
    
    
    
    

    private void saveMovie(JPanel inputPanel, boolean isAdd) {
        JTextField idField = (JTextField) inputPanel.getComponent(1);
        JTextField titleField = (JTextField) inputPanel.getComponent(3);
        JTextField genreField = (JTextField) inputPanel.getComponent(5);
        JTextField durationField = (JTextField) inputPanel.getComponent(7);
        JTextField releaseDateField = (JTextField) inputPanel.getComponent(9);
        JTextField statusField = (JTextField) inputPanel.getComponent(11);
        JTextField imagePathField = (JTextField) inputPanel.getComponent(13);
        JTextField directorField = (JTextField) inputPanel.getComponent(15);
        JTextField descriptionField = (JTextField) inputPanel.getComponent(17);
        JTextField mainActorsField = (JTextField) inputPanel.getComponent(19);
        JTextField showDateField = (JTextField) inputPanel.getComponent(21);
        JPanel cinemaPanel = (JPanel) inputPanel.getComponent(22); // Adjusted index for cinemaPanel
        JCheckBox[] cinemaCheckBoxes = (JCheckBox[]) cinemaPanel.getClientProperty("checkBoxes");

        List<String> selectedCinemas = Arrays.stream(cinemaCheckBoxes)
                .filter(JCheckBox::isSelected)
                .map(JCheckBox::getText)
                .collect(Collectors.toList());

        if (isAdd) 
        {
            movieDatabase.addMovie(
                    idField.getText(),
                    titleField.getText(),
                    selectedCinemas,
                    Arrays.asList(showDateField.getText().split(",")),
                    genreField.getText(),
                    imagePathField.getText(),
                    directorField.getText(),
                    descriptionField.getText(),
                    Integer.parseInt(durationField.getText()),
                    releaseDateField.getText(),
                    mainActorsField.getText()
            );
            CinemaManager cm = new CinemaManager();
            MovieDatabase mvdb = new MovieDatabase();
            SeatsDatabase sdtb = new SeatsDatabase();
            ArrayList<Cinema> arl_cinema = new ArrayList<>(cm.getAllCinemas());
            List<String> l_date = Arrays.asList(showDateField.getText().split(","));
            ArrayList<String> arl_date = new ArrayList<>(l_date);
            for(String j: arl_date)
            {
                String[] temp = j.trim().split("\\s+");
                for(Cinema k: arl_cinema)
                {
                    for(String v: k.getShowHours())
                    {
                        String seatname = titleField.getText() + " / " + temp[1] + " / " 
                        + k.getName() + " / " + v;
                        sdtb.addSeats(seatname);
                    }
                    
                }

            }
             
        }
        else {
            movieDatabase.updateMovie(
                    idField.getText(),
                    titleField.getText(),
                    selectedCinemas,
                    Arrays.asList(showDateField.getText().split(",")),
                    genreField.getText(),
                    imagePathField.getText(),
                    directorField.getText(),
                    descriptionField.getText(),
                    Integer.parseInt(durationField.getText()),
                    releaseDateField.getText(),
                    mainActorsField.getText()
            );
        }
    }

    private void showDeleteMovieDialog() {
        int selectedRow = movieTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a movie to delete.", "No movie selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String movieId = (String) movieTable.getValueAt(selectedRow, 0);

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the movie with ID: " + movieId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                movieDatabase.deleteMovie(movieId);
                refreshMovieTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting movie: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel createMovieInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Mảng chứa nhãn
        String[] labels = {"ID:", "Title:", "Genre:", "Duration:", "Release Date:", "Status:", 
                           "Image Path:", "Director:", "Description:", "Main Actors:", "Show Dates:"};
    
        // Duyệt qua các nhãn và tạo JTextField
        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; // Cột đầu tiên: JLabel
            gbc.gridy = i; // Dòng thứ i
            gbc.anchor = GridBagConstraints.WEST;
            JLabel label = new JLabel(labels[i]);
            label.setFont(REGULAR_FONT);
            panel.add(label, gbc);
    
            gbc.gridx = 1; // Cột thứ hai: JTextField
            gbc.fill = GridBagConstraints.HORIZONTAL;
            JTextField textField = new JTextField(20);
            textField.setFont(REGULAR_FONT);
            panel.add(textField, gbc);
        }
    
        // Panel chứa danh sách rạp chiếu
        List<Cinema> cinemas = cinemaManager.getAllCinemas();
        JCheckBox[] cinemaCheckBoxes = new JCheckBox[cinemas.size()];
        JPanel cinemaPanel = new JPanel(new GridLayout(cinemas.size(), 1));
        for (int i = 0; i < cinemas.size(); i++) {
            cinemaCheckBoxes[i] = new JCheckBox(cinemas.get(i).getName());
            cinemaPanel.add(cinemaCheckBoxes[i]);
        }
        cinemaPanel.putClientProperty("checkBoxes", cinemaCheckBoxes); // Lưu danh sách checkbox
    
        gbc.gridx = 0; 
        gbc.gridy = labels.length; // Đặt dưới cùng của các thành phần trước
        gbc.gridwidth = 2; 
        panel.add(cinemaPanel, gbc);
    
        return panel;
    }
    
    

    private JPanel createAccountManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        accountTable = createStyledTable(new String[]{"Username", "Password", "Email", "Role"});
        JScrollPane scrollPane = new JScrollPane(accountTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        createAccountContextMenu();

        JPanel buttonPanel = createAccountButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshAccountTable();
        return panel;
    }

    private void createAccountContextMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editItem = new JMenuItem("Edit Account");
        editItem.addActionListener(e -> showEditAccountDialog());
        popupMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete Account");
        deleteItem.addActionListener(e -> showDeleteAccountDialog());
        popupMenu.add(deleteItem);

        accountTable.setComponentPopupMenu(popupMenu);

        accountTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private JPanel createAccountButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton addButton = createStyledButton("Add Account", e -> showAddAccountDialog());
        buttonPanel.add(addButton);

        return buttonPanel;
    }
    private void saveAccount(JPanel inputPanel, boolean isAdd) {
        JTextField usernameField = (JTextField) inputPanel.getComponent(1);
        JPasswordField passwordField = (JPasswordField) inputPanel.getComponent(3);
        JTextField emailField = (JTextField) inputPanel.getComponent(5);
        JTextField roleField = (JTextField) inputPanel.getComponent(7);

        if (isAdd) {
            accountManager.createAccount(usernameField.getText(), new String(passwordField.getPassword()), emailField.getText());
        } else {
            accountManager.updateAccount(usernameField.getText(), new String(passwordField.getPassword()), emailField.getText(), roleField.getText());
        }
    }
    private JPanel createAccountButtonPanel(JPanel inputPanel, JDialog dialog, boolean isAdd) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areAccountFieldsValid(inputPanel)) {
                try {
                    saveAccount(inputPanel, isAdd);
                    refreshAccountTable();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = createStyledButton("Cancel", e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private void showAddAccountDialog() {
        JDialog dialog = createAccountDialog("Add Account", true);
        JPanel inputPanel = createAccountInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createAccountButtonPanel(inputPanel, dialog, true);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showEditAccountDialog() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an account to edit.", "No account selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = createAccountDialog("Edit Account", true);
        JPanel inputPanel = createAccountInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        populateAccountFields(inputPanel, selectedRow);

        JPanel buttonPanel = createAccountButtonPanel(inputPanel, dialog, false);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JDialog createAccountDialog(String title, boolean modal) {
        JDialog dialog = new JDialog(this, title, modal);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        return dialog;
    }

    private void populateAccountFields(JPanel inputPanel, int selectedRow) {
        JTextField usernameField = (JTextField) inputPanel.getComponent(1);
        JPasswordField passwordField = (JPasswordField) inputPanel.getComponent(3);
        JTextField emailField = (JTextField) inputPanel.getComponent(5);
        JTextField roleField = (JTextField) inputPanel.getComponent(7);

        usernameField.setText((String) accountTable.getValueAt(selectedRow, 0));
        passwordField.setText((String) accountTable.getValueAt(selectedRow, 1));
        emailField.setText((String) accountTable.getValueAt(selectedRow, 2));
        roleField.setText((String) accountTable.getValueAt(selectedRow, 3));
    }

    private void showDeleteAccountDialog() {
        int selectedRow = accountTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an account to delete.", "No account selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String username = (String) accountTable.getValueAt(selectedRow, 0);

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the account: " + username + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                accountManager.deleteAccount(username);
                refreshAccountTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting account: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel createAccountInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] labels = {"Username:", "Password:", "Email:", "Role:"};
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JTextField emailField = new JTextField(20);
        JTextField roleField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel usernameLabel = new JLabel(labels[0]);
        usernameLabel.setFont(REGULAR_FONT);
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel(labels[1]);
        passwordLabel.setFont(REGULAR_FONT);
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel emailLabel = new JLabel(labels[2]);
        emailLabel.setFont(REGULAR_FONT);
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        panel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel roleLabel = new JLabel(labels[3]);
        roleLabel.setFont(REGULAR_FONT);
        panel.add(roleLabel, gbc);

        gbc.gridx = 1;
        panel.add(roleField, gbc);

        return panel;
    }

    private JPanel createTransactionHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        transactionTable = createStyledTable(new String[]{"Transaction ID", "Username", "Amount", "Timestamp", "Seats", "Movie Title", "Movie Date", "Is Paid"});
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createCinemaManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        cinemaTable = createStyledTable(new String[]{"Cinema Name", "Show Hours"});
        JScrollPane scrollPane = new JScrollPane(cinemaTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        createCinemaContextMenu();
        JPanel buttonPanel = createCinemaButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshCinemaTable();
        return panel;
    }

    private void createCinemaContextMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem editItem = new JMenuItem("Edit Cinema");
        editItem.addActionListener(e -> showEditCinemaDialog());
        popupMenu.add(editItem);

        JMenuItem deleteItem = new JMenuItem("Delete Cinema");
        deleteItem.addActionListener(e -> showDeleteCinemaDialog());
        popupMenu.add(deleteItem);

        cinemaTable.setComponentPopupMenu(popupMenu);

        cinemaTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }
    private void saveCinema(JPanel inputPanel, boolean isAdd) {
        JTextField nameField = (JTextField) inputPanel.getComponent(1);
        JTextField showHoursField = (JTextField) inputPanel.getComponent(3);

        List<String> showHours = Arrays.asList(showHoursField.getText().split(","));

        if (isAdd) 
        {
            cinemaManager.addCinema(nameField.getText(), showHours);
            CinemaManager cm = new CinemaManager();
            MovieDatabase mvdb = new MovieDatabase();
            SeatsDatabase sdtb = new SeatsDatabase();
            ArrayList<Cinema> arl_cinema = new ArrayList<>(cm.getAllCinemas());
            ArrayList<Movie> arl_movie = new ArrayList<>(mvdb.getAllMovies());
            for(Movie i: arl_movie)
            {
                String mvname = i.getTitle();

                ArrayList<String> arl_date = new ArrayList<>(i.getShowDates());
                for(String j: arl_date)
                {
                    String[] temp = j.split("\\s+");
                    String[] hours = showHoursField.getText().split(",");
                    for(String k: hours)
                    {
                        String seatname = i.getTitle() + " / " + temp[1] + " / " + nameField.getText() + " / " + k;
                        sdtb.addSeats(seatname);
                    }

                }
        }
        } else {
            cinemaManager.updateCinema(nameField.getText(), showHours);
        }
    }
    
    private JPanel createCinemaButtonPanel(JPanel inputPanel, JDialog dialog, boolean isAdd) {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = createStyledButton("Save", e -> {
            if (areCinemaFieldsValid(inputPanel)) {
                try {
                    saveCinema(inputPanel, isAdd);
                    refreshCinemaTable();
                    dialog.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        buttonPanel.add(saveButton);

        JButton cancelButton = createStyledButton("Cancel", e -> dialog.dispose());
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }
    private JPanel createCinemaButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton addButton = createStyledButton("Add Cinema", e -> showAddCinemaDialog());
        buttonPanel.add(addButton);

        return buttonPanel;
    }

    

    private void showAddCinemaDialog() {
        JDialog dialog = createCinemaDialog("Add Cinema", true);
        JPanel inputPanel = createCinemaInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createCinemaButtonPanel(inputPanel, dialog, true);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }


    private void showEditCinemaDialog() {
        int selectedRow = cinemaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a cinema to edit.", "No cinema selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = createCinemaDialog("Edit Cinema", true);
        JPanel inputPanel = createCinemaInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        populateCinemaFields(inputPanel, selectedRow);

        JPanel buttonPanel = createCinemaButtonPanel(inputPanel, dialog, false);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JDialog createCinemaDialog(String title, boolean modal) {
        JDialog dialog = new JDialog(this, title, modal);
        dialog.setSize(400, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        return dialog;
    }

    private void populateCinemaFields(JPanel inputPanel, int selectedRow) {
        JTextField nameField = (JTextField) inputPanel.getComponent(1);
        JTextField showHoursField = (JTextField) inputPanel.getComponent(3);
        
        
        nameField.setText((String) cinemaTable.getValueAt(selectedRow, 0));
        Object value = cinemaTable.getValueAt(selectedRow, 1);
        if (value instanceof List<?>) {
            showHoursField.setText(String.join(", ", (List<String>) value));
        } else if (value instanceof String) {
            showHoursField.setText((String) value);
        } else {
            showHoursField.setText("");
        }
    }

    private void showDeleteCinemaDialog() {
        int selectedRow = cinemaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a cinema to delete.", "No cinema selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String cinemaName = (String) cinemaTable.getValueAt(selectedRow, 0);

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the cinema: " + cinemaName + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                cinemaManager.deleteCinema(cinemaName);
                refreshCinemaTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting cinema: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel createCinemaInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] labels = {"Cinema Name:", "Show Hours:"};
        JTextField nameField = new JTextField(20);
        JTextField showHoursField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel nameLabel = new JLabel(labels[0]);
        nameLabel.setFont(REGULAR_FONT);
        panel.add(nameLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel showHoursLabel = new JLabel(labels[1]);
        showHoursLabel.setFont(REGULAR_FONT);
        panel.add(showHoursLabel, gbc);

        gbc.gridx = 1;
        panel.add(showHoursField, gbc);

        return panel;
    }

    private JPanel createFeedbackManagementPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BACKGROUND_COLOR);

        feedbackTable = createStyledTable(new String[]{"Movie", "User", "Feedback", "Status"});
        JScrollPane scrollPane = new JScrollPane(feedbackTable);
        scrollPane.getViewport().setBackground(Color.WHITE);
        panel.add(scrollPane, BorderLayout.CENTER);

        createFeedbackContextMenu();

//        JPanel buttonPanel = createFeedbackButtonPanel();
//        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshFeedbackTable();
        return panel;
    }

    private void createFeedbackContextMenu() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem deleteItem = new JMenuItem("Delete Feedback");
        deleteItem.addActionListener(e -> showDeleteFeedbackDialog());
        popupMenu.add(deleteItem);

        feedbackTable.setComponentPopupMenu(popupMenu);

        feedbackTable.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
    }

    private JPanel createFeedbackButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(BACKGROUND_COLOR);

        JButton addButton = createStyledButton("Add Feedback", e -> showAddFeedbackDialog());
        buttonPanel.add(addButton);

        return buttonPanel;
    }

    private void showAddFeedbackDialog() {
        JDialog dialog = createFeedbackDialog("Add Feedback", true);
        JPanel inputPanel = createFeedbackInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createDialogButtonPanel(inputPanel, dialog, true);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void showEditFeedbackDialog() {
        int selectedRow = feedbackTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a feedback to edit.", "No feedback selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = createFeedbackDialog("Edit Feedback", true);
        JPanel inputPanel = createFeedbackInputPanel();
        dialog.add(inputPanel, BorderLayout.CENTER);

        populateFeedbackFields(inputPanel, selectedRow);

        JPanel buttonPanel = createDialogButtonPanel(inputPanel, dialog, false);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JDialog createFeedbackDialog(String title, boolean modal) {
        JDialog dialog = new JDialog(this, title, modal);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());
        return dialog;
    }

    private void populateFeedbackFields(JPanel inputPanel, int selectedRow) {
        JTextField movieField = (JTextField) inputPanel.getComponent(1);
        JTextField userField = (JTextField) inputPanel.getComponent(3);
        JTextArea feedbackField = (JTextArea) inputPanel.getComponent(5);
        JTextField statusField = (JTextField) inputPanel.getComponent(7);

        movieField.setText((String) feedbackTable.getValueAt(selectedRow, 0));
        userField.setText((String) feedbackTable.getValueAt(selectedRow, 1));
        feedbackField.setText((String) feedbackTable.getValueAt(selectedRow, 2));
        statusField.setText((String) feedbackTable.getValueAt(selectedRow, 3));
    }

    private void showDeleteFeedbackDialog() {
        int selectedRow = feedbackTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a feedback to delete.", "No feedback selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String movie = (String) feedbackTable.getValueAt(selectedRow, 0);
        String user = (String) feedbackTable.getValueAt(selectedRow, 1);

        int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the feedback for movie: " + movie + " by user: " + user + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            try {
                feedbackDatabase.deleteFeedback(movie, user);
                refreshFeedbackTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error deleting feedback: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private JPanel createFeedbackInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        String[] labels = {"Movie:", "User:", "Feedback:", "Status:"};
        JTextField movieField = new JTextField(20);
        JTextField userField = new JTextField(20);
        JTextArea feedbackField = new JTextArea(5, 20);
        feedbackField.setLineWrap(true);
        feedbackField.setWrapStyleWord(true);
        JScrollPane feedbackScrollPane = new JScrollPane(feedbackField);
        JTextField statusField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel movieLabel = new JLabel(labels[0]);
        movieLabel.setFont(REGULAR_FONT);
        panel.add(movieLabel, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(movieField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel userLabel = new JLabel(labels[1]);
        userLabel.setFont(REGULAR_FONT);
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        panel.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel feedbackLabel = new JLabel(labels[2]);
        feedbackLabel.setFont(REGULAR_FONT);
        panel.add(feedbackLabel, gbc);

        gbc.gridx = 1;
        panel.add(feedbackScrollPane, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel statusLabel = new JLabel(labels[3]);
        statusLabel.setFont(REGULAR_FONT);
        panel.add(statusLabel, gbc);

        gbc.gridx = 1;
        panel.add(statusField, gbc);

        return panel;
    }

    private void refreshMovieTable() {
        DefaultTableModel model = (DefaultTableModel) movieTable.getModel();
        model.setRowCount(0);
        List<Movie> movies = movieDatabase.getAllMovies();
        for (Movie movie : movies) {
            model.addRow(new Object[]{
                    movie.getId(),
                    movie.getTitle(),
                    movie.getGenre(),
                    movie.getDuration(),
                    movie.getReleaseDate(),
                    "available",
                    movie.getImagePath(),
                    movie.getDirector(),
                    movie.getDescription(),
                    movie.getMainActors(),
                    String.join(", ", movie.getShowDates()),
                    movie.getCinemas() // Giữ nguyên kiểu List<String>
            });
        }
    }
    
    

    private void refreshTransactionTable() {
        DefaultTableModel model = (DefaultTableModel) transactionTable.getModel();
        model.setRowCount(0);
        int id = 1;
        for (Transaction transaction : transactionHistory.getAllTransactions()) {
            model.addRow(new Object[]{
                    id++,
                    transaction.getUsername(),
                    transaction.getAmount(),
                    transaction.getTimestamp(),
                    transaction.getSeats(),
                    transaction.getMovieTitle(),
                    transaction.getMovieDate(),
                    transaction.getIsPaid()
            });
        }
    }

    private void refreshAccountTable() {
        DefaultTableModel model = (DefaultTableModel) accountTable.getModel();
        model.setRowCount(0);
        List<Document> accounts = accountManager.getAllAccounts();
        for (Document account : accounts) {
            model.addRow(new Object[]{
                    account.getString("username"),
                    account.getString("password"),
                    account.getString("email"),
                    account.getString("role")
            });
        }
    }

    private void refreshCinemaTable() {
        DefaultTableModel model = (DefaultTableModel) cinemaTable.getModel();
        model.setRowCount(0);
        List<Cinema> cinemas = cinemaManager.getAllCinemas();
        for (Cinema cinema : cinemas) {
            model.addRow(new Object[]{
                    cinema.getName(),
                    String.join(", ", cinema.getShowHours())
            });
        }
    }

    private void refreshFeedbackTable() {
        DefaultTableModel model = (DefaultTableModel) feedbackTable.getModel();
        model.setRowCount(0);
       List<Feedback> feedbacks = feedbackDatabase.getAllFeedbacks();
       for (Feedback feedback : feedbacks) {
           model.addRow(new Object[]{
                   feedback.getMovie(),
                   feedback.getUser(),
                   feedback.getFeedback(),
                   feedback.getStatus()
           });
       }
    }

    private boolean areMovieFieldsValid(JPanel panel) {
        JTextField idField = (JTextField) panel.getComponent(1);
        JTextField titleField = (JTextField) panel.getComponent(3);
        JTextField genreField = (JTextField) panel.getComponent(5);
        JTextField durationField = (JTextField) panel.getComponent(7);
        JTextField releaseDateField = (JTextField) panel.getComponent(9);
        JTextField statusField = (JTextField) panel.getComponent(11);
        JTextField imagePathField = (JTextField) panel.getComponent(13);
        JTextField directorField = (JTextField) panel.getComponent(15);
        JTextField descriptionField = (JTextField) panel.getComponent(17);
        JTextField mainActorsField = (JTextField) panel.getComponent(19);
        JTextField showDateField = (JTextField) panel.getComponent(21);

        if (idField.getText().isEmpty() || titleField.getText().isEmpty() ||
                genreField.getText().isEmpty() || durationField.getText().isEmpty() ||
                releaseDateField.getText().isEmpty() || statusField.getText().isEmpty() ||
                imagePathField.getText().isEmpty() || directorField.getText().isEmpty() ||
                descriptionField.getText().isEmpty() || mainActorsField.getText().isEmpty() ||
                showDateField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "All fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        try {
            Integer.parseInt(durationField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(panel, "Duration must be a valid number.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean areAccountFieldsValid(JPanel panel) {
        JTextField usernameField = (JTextField) panel.getComponent(1);
        JPasswordField passwordField = (JPasswordField) panel.getComponent(3);
        JTextField emailField = (JTextField) panel.getComponent(5);
        JTextField roleField = (JTextField) panel.getComponent(7);

        if (usernameField.getText().isEmpty() || passwordField.getPassword().length == 0 ||
                emailField.getText().isEmpty() || roleField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "All fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean areCinemaFieldsValid(JPanel panel) {
        JTextField nameField = (JTextField) panel.getComponent(1);
        JTextField showHoursField = (JTextField) panel.getComponent(3);

        if (nameField.getText().isEmpty() || showHoursField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "All fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
    

    private boolean areFeedbackFieldsValid(JPanel panel) {
        JTextField movieField = (JTextField) panel.getComponent(1);
        JTextField userField = (JTextField) panel.getComponent(3);
        JTextArea feedbackField = (JTextArea) panel.getComponent(5);
        JTextField statusField = (JTextField) panel.getComponent(7);

        if (movieField.getText().isEmpty() || userField.getText().isEmpty() ||
                feedbackField.getText().isEmpty() || statusField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(panel, "All fields must be filled out.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private JTable createStyledTable(String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);
        table.setFont(REGULAR_FONT);
        table.setRowHeight(30);
        table.setBackground(Color.WHITE);
        table.setSelectionBackground(new Color(200, 220, 255));
        table.getTableHeader().setBackground(SECONDARY_COLOR);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setResizingAllowed(false);
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder(0, 0, 1, 0));
        table.getTableHeader().setOpaque(false);
        table.setShowGrid(false);
        table.setFillsViewportHeight(true);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        return table;
    }

    private JButton createStyledButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.setFont(REGULAR_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(listener);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminPanel app = null;
            try {
                app = new AdminPanel();
            } catch (MalformedURLException ex) {
                Logger.getLogger(AdminPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            app.setVisible(true);
        });
    }
}