package Library;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserPage extends JFrame {
    private User user;
    private LibraryController controller;
    private JList<String> checkedOutBooksList;
    private JList<String> libraryBooksList;

    public UserPage(User user, LibraryController controller) {
        this.user = user;
        this.controller = controller;

        setTitle("USER PAGE");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        JLabel userLabel = new JLabel(user.getName() + "; " + user.getLibraryCardNumber(), SwingConstants.RIGHT);
        
        headerPanel.add(userLabel, BorderLayout.CENTER);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(1, 2));

        // Checked out books panel
        JPanel checkedOutPanel = new JPanel();
        checkedOutPanel.setLayout(new BorderLayout());
        JLabel checkedOutLabel = new JLabel("Checked out books:", SwingConstants.CENTER);
        checkedOutBooksList = new JList<>(getCheckedOutBooks());
        checkedOutPanel.add(checkedOutLabel, BorderLayout.NORTH);
        checkedOutPanel.add(new JScrollPane(checkedOutBooksList), BorderLayout.CENTER);

        // Library books panel
        JPanel libraryPanel = new JPanel();
        libraryPanel.setLayout(new BorderLayout());
        JLabel libraryLabel = new JLabel("Books in Library:", SwingConstants.CENTER);
        libraryBooksList = new JList<>(getLibraryBooks());
        libraryPanel.add(libraryLabel, BorderLayout.NORTH);
        libraryPanel.add(new JScrollPane(libraryBooksList), BorderLayout.CENTER);

        // Add both panels to content panel
        contentPanel.add(checkedOutPanel);
        contentPanel.add(libraryPanel);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Info and control panel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(2, 1));

        // Book info dialog
        JButton bookInfoButton = new JButton("Book Info");
        bookInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBookInfoDialog();
            }
        });
        controlPanel.add(bookInfoButton);

        // Checkout and checkin panel
        JPanel checkoutPanel = new JPanel();
        JButton checkOutButton = new JButton("Check Out");
        JButton checkInButton = new JButton("Check In");
        JButton logoutButton = new JButton("Logout");

        checkOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkOutBook();
            }
        });

        checkInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkInBook();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the UserPage window
                new WelcomeScreen().setVisible(true); // Show the WelcomeScreen
            }
        });

        checkoutPanel.add(checkOutButton);
        checkoutPanel.add(checkInButton);
        checkoutPanel.add(logoutButton);
        controlPanel.add(checkoutPanel);

        mainPanel.add(controlPanel, BorderLayout.SOUTH);

        // Sorting and searching panel
        JPanel sortSearchPanel = new JPanel();
        sortSearchPanel.setLayout(new GridLayout(1, 4));

        String[] sortOptions = {"Title", "Author", "ISBN"};
        JComboBox<String> sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setSelectedIndex(0);
        JButton sortButton = new JButton("Sort");

        JTextField searchField = new JTextField("Enter Search Keywords ...");
        JButton searchButton = new JButton("Search");
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText().trim();
                if (keyword.isEmpty() || keyword.equals("Enter Search Keywords ...")) {
                    JOptionPane.showMessageDialog(UserPage.this, "Please enter a valid search keyword.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                List<Book> searchResults = controller.searchBooksByKeyword(keyword);
                if (searchResults.isEmpty()) {
                    JOptionPane.showMessageDialog(UserPage.this, "No books found matching the keyword.", "Info", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    String[] bookTitles = searchResults.stream().map(Book::getTitle).toArray(String[]::new);
                    libraryBooksList.setListData(bookTitles);
                }
            }
        });

        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) sortComboBox.getSelectedItem();
                List<Book> sortedBooks;
                switch (selectedOption) {
                    case "Author":
                        sortedBooks = controller.sortBooksByAuthor();
                        break;
                    case "Title":
                        sortedBooks = controller.sortBooksByTitle();
                        break;
                    case "ISBN":
                        sortedBooks = controller.sortBooksByISBN();
                        break;
                    default:
                        sortedBooks = new ArrayList<>();
                }
                String[] bookTitles = sortedBooks.stream().map(Book::getTitle).toArray(String[]::new);
                libraryBooksList.setListData(bookTitles);
            }
        });

        sortSearchPanel.add(sortComboBox);
        sortSearchPanel.add(sortButton);
        sortSearchPanel.add(searchField);
        sortSearchPanel.add(searchButton);

        mainPanel.add(sortSearchPanel, BorderLayout.NORTH);

        // Pack and set visibility
        pack();
        setVisible(true);
    }

    private String[] getCheckedOutBooks() {
        List<Book> books = user.getBorrowedBooks();
        String[] bookTitles = new String[books.size()];
        for (int i = 0; i < books.size(); i++) {
            bookTitles[i] = books.get(i).getTitle();
        }
        return bookTitles;
    }

    private String[] getLibraryBooks() {
        List<Book> books = controller.getBooks().stream()
            .filter(book -> !book.isCheckedOut())
            .collect(Collectors.toList());
        String[] bookTitles = new String[books.size()];
        for (int i = 0; i < books.size(); i++) {
            bookTitles[i] = books.get(i).getTitle();
        }
        return bookTitles;
    }

    private void showBookInfoDialog() {
        String selectedBookTitle = libraryBooksList.getSelectedValue();
        if (selectedBookTitle == null) {
            JOptionPane.showMessageDialog(this, "Please select a book from the library list.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book selectedBook = null;
        for (Book book : controller.getBooks()) {
            if (book.getTitle().equals(selectedBookTitle)) {
                selectedBook = book;
                break;
            }
        }

        if (selectedBook == null) {
            JOptionPane.showMessageDialog(this, "Selected book not found.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Title: " + selectedBook.getTitle() + "\n" +
                        "Author: " + selectedBook.getAuthor() + "\n" +
                        "ISBN: " + selectedBook.getIsbn() + "\n" +
                        "Checked Out: " + selectedBook.isCheckedOut(),
                "Book Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void checkOutBook() {
        if (!user.canCheckOutBooks()) {
            JOptionPane.showMessageDialog(this, "Inactive users cannot check out books.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedBookTitle = libraryBooksList.getSelectedValue();
        if (selectedBookTitle == null) {
            JOptionPane.showMessageDialog(this, "Please select a book from the library list.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book selectedBook = null;
        for (Book book : controller.getBooks()) {
            if (book.getTitle().equals(selectedBookTitle)) {
                selectedBook = book;
                break;
            }
        }

        if (selectedBook == null || selectedBook.isCheckedOut()) {
            JOptionPane.showMessageDialog(this, "Book is not available for checkout.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        user.borrowBook(selectedBook);
        JOptionPane.showMessageDialog(this, "Book checked out successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        updateBookLists();
    }

    private void checkInBook() {
        String selectedBookTitle = checkedOutBooksList.getSelectedValue();
        if (selectedBookTitle == null) {
            JOptionPane.showMessageDialog(this, "Please select a book from the checked out list.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Book selectedBook = null;
        for (Book book : user.getBorrowedBooks()) {
            if (book.getTitle().equals(selectedBookTitle)) {
                selectedBook = book;
                break;
            }
        }

        if (selectedBook == null) {
            JOptionPane.showMessageDialog(this, "Book not found in checked out list.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        user.returnBook(selectedBook);
        JOptionPane.showMessageDialog(this, "Book checked in successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        updateBookLists();
    }

    private void updateBookLists() {
        checkedOutBooksList.setListData(getCheckedOutBooks());
        libraryBooksList.setListData(getLibraryBooks());
    }




}