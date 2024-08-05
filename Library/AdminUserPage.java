package Library;

import javax.swing.*;
import java.awt.*;

public class AdminUserPage extends JFrame {
    
    public AdminUserPage() {
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
        userInfoPanel.add(new JLabel("Tsao, Chung-Wen: xxx"));
        userInfoPanel.add(new JButton("Logout"));
        
        panel.add(userInfoPanel, BorderLayout.EAST);
        return panel;
    }
    
    private JPanel createInactiveUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Library Inactive Users:"));
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Bhat, Toshi");
        JList<String> userList = new JList<>(listModel);
        panel.add(new JScrollPane(userList), BorderLayout.CENTER);
        
        panel.add(new JButton("Re-Activate"), BorderLayout.SOUTH);
        return panel;
    }
    
    private JPanel createActiveUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Active Users:"));
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        // Add active users here
        JList<String> userList = new JList<>(listModel);
        panel.add(new JScrollPane(userList), BorderLayout.CENTER);
        
        panel.add(new JButton("Inactivate"), BorderLayout.SOUTH);
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

