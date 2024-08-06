package Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserPage extends JFrame {
    private LibraryController controller;
    private DefaultListModel<String> activeUsersListModel;
    private DefaultListModel<String> inactiveUsersListModel;

    public AdminUserPage(LibraryController controller) {
        this.controller = controller;
        setTitle("Admin USER PAGE");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // Content panel
        JPanel contentPanel = new JPanel(new GridLayout(1, 2));
        
        // Inactive Users Panel
        JPanel inactiveUsersPanel = createInactiveUsersPanel();
        contentPanel.add(inactiveUsersPanel);
        
        // Active Users Panel
        JPanel activeUsersPanel = createActiveUsersPanel();
        contentPanel.add(activeUsersPanel);
        
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        
        // Footer
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Admin USER PAGE"), BorderLayout.WEST);
        
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userInfoPanel.add(new JLabel("Admin: Admin"));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the AdminUserPage window
                new WelcomeScreen().setVisible(true); // Show the WelcomeScreen
            }
        });
        userInfoPanel.add(logoutButton);
        
        panel.add(userInfoPanel, BorderLayout.EAST);
        return panel;
    }
    
    private JPanel createInactiveUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Library Inactive Users:"));
        
        inactiveUsersListModel = new DefaultListModel<>();
        // Populate with inactive users from the controller
        for (User user : controller.getInactiveUsers()) {
            inactiveUsersListModel.addElement(user.getName());
        }
        JList<String> userList = new JList<>(inactiveUsersListModel);
        panel.add(new JScrollPane(userList), BorderLayout.CENTER);
        
        JButton activateButton = new JButton("Re-Activate");
        activateButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {
                controller.activateUser(selectedUser);
                inactiveUsersListModel.removeElement(selectedUser);
                activeUsersListModel.addElement(selectedUser);
            }
        });
        panel.add(activateButton, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createActiveUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Active Users:"));
        
        activeUsersListModel = new DefaultListModel<>();
        // Populate with active users from the controller
        for (User user : controller.getActiveUsers()) {
            activeUsersListModel.addElement(user.getName());
        }
        JList<String> userList = new JList<>(activeUsersListModel);
        panel.add(new JScrollPane(userList), BorderLayout.CENTER);
        
        JButton deactivateButton = new JButton("Inactivate");
        deactivateButton.addActionListener(e -> {
            String selectedUser = userList.getSelectedValue();
            if (selectedUser != null) {
                controller.deactivateUser(selectedUser);
                activeUsersListModel.removeElement(selectedUser);
                inactiveUsersListModel.addElement(selectedUser);
            }
        });
        panel.add(deactivateButton, BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JComboBox<>(new String[]{"Ascending Order"}));
        panel.add(new JLabel("Search/Sort By"));
        panel.add(new JComboBox<>(new String[]{"Last+First+ID"}));
        panel.add(new JButton("Sort"));
        panel.add(new JTextField("Enter Search Keywords...", 20));
        panel.add(new JButton("Search"));
        return panel;
    }
}