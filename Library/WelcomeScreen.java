package Library;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class WelcomeScreen extends JFrame {
    private LibraryController controller = new LibraryController();

    public WelcomeScreen() {
        setTitle("Library Management System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.CYAN);

        // Welcome label
        JLabel welcomeLabel = new JLabel("Welcome!!!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Login and Exit buttons
        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit!!!");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Signup section
        JLabel signupPrompt = new JLabel("Not a user? Click here to sign up:");
        signupPrompt.setAlignmentX(Component.CENTER_ALIGNMENT);
        JButton signupButton = new JButton("Signup");
        signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add action listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginScreen();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSignupScreen();
            }
        });

        // Add components to panel
        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(exitButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(signupPrompt);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(signupButton);

        // Add panel to frame
        getContentPane().add(panel);
    }

    private void showLoginScreen() {
        // Implement login screen
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(300, 200);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField cardNumberField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardNumber = cardNumberField.getText();
                String password = new String(passwordField.getPassword());
                // Implement login logic here
                // If login is successful, transition to the main application screen
            }
        });

        panel.add(new JLabel("Library Card Number:"));
        panel.add(cardNumberField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);

        loginFrame.getContentPane().add(panel);
        loginFrame.setVisible(true);
    }

    private void showSignupScreen() {
        // Implement signup screen
        JFrame signupFrame = new JFrame("Signup");
        signupFrame.setSize(300, 300);
        signupFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signupFrame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField firstNameField = new JTextField(20);
        JTextField lastNameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JButton signupButton = new JButton("Signup");

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String password = new String(passwordField.getPassword());
                if (!firstName.isEmpty() && !lastName.isEmpty() && !password.isEmpty()) {
                    String cardNumber = generateLibraryCardNumber();
                    User newUser = new User(firstName + " " + lastName, cardNumber);
                    // Save the password securely (this example does not handle password storage)
                    controller.addUser(newUser);
                    JOptionPane.showMessageDialog(signupFrame, "Signup successful! Your library card number is: " + cardNumber);
                    signupFrame.dispose();
                }
            }
        });

        panel.add(new JLabel("First Name:"));
        panel.add(firstNameField);
        panel.add(new JLabel("Last Name:"));
        panel.add(lastNameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(signupButton);

        signupFrame.getContentPane().add(panel);
        signupFrame.setVisible(true);
    }

    private String generateLibraryCardNumber() {
        Random random = new Random();
        int cardNumber = 10000 + random.nextInt(90000);
        return String.valueOf(cardNumber);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WelcomeScreen().setVisible(true);
            }
        });
    }
}