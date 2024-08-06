package Library;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryController implements Serializable {
    public String FILE_PATH = "DB/library.ser";
    private List<User> activeUsers;
    private List<User> inactiveUsers;
    private List<Book> books;

    private static LibraryController instance;

    private LibraryController() {
        try {
            loadLibrary();
        }
        catch (IOException | ClassNotFoundException e) {
            System.out.println(FILE_PATH + " not found. Initializing library with default data.");
            initializeLibrary();
            save();
            System.out.println("Library initialized successfully.");
        }
    }

    public static synchronized LibraryController getInstance() {
        if (instance == null) {
            instance = new LibraryController();
        }
        return instance;
    }

    private void initializeLibrary() {
        activeUsers = new ArrayList<>();
        inactiveUsers = new ArrayList<>();
        books = new ArrayList<>();

        // Add default users and books
        activeUsers.add(new User("User 1", "Card1"));
        activeUsers.add(new User("User 2", "Card2"));
        inactiveUsers.add(new User("User 3", "Card3"));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "9780061120084"));
        books.add(new Book("1984", "George Orwell", "9780451524935"));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "9780743273565"));
        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", "9780316769488"));
        books.add(new Book("The Hobbit", "J.R.R. Tolkien", "9780547928227"));
        books.add(new Book("Fahrenheit 451", "Ray Bradbury", "9781451673319"));
        books.add(new Book("Pride and Prejudice", "Jane Austen", "9781503290563"));
        books.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", "9780544003415"));
        books.add(new Book("Jane Eyre", "Charlotte Brontë", "9780141441146"));
        books.add(new Book("Brave New World", "Aldous Huxley", "9780060850524"));
        books.add(new Book("The Chronicles of Narnia", "C.S. Lewis", "9780066238500"));
        books.add(new Book("Moby-Dick", "Herman Melville", "9781503280786"));
        books.add(new Book("War and Peace", "Leo Tolstoy", "9781400079988"));
        books.add(new Book("The Odyssey", "Homer", "9780140268867"));
        books.add(new Book("Crime and Punishment", "Fyodor Dostoevsky", "9780140449136"));

        // Ensure the directory exists
        File file = new File(FILE_PATH);
        File directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs(); // Create directories if they don't exist
        }

        // Create the file if it does not exist
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + FILE_PATH);
            }
        }
        catch (IOException e) {
            System.out.println("Error initializing library data");
            e.printStackTrace();
        }
    }

    public List<User> getActiveUsers() {
        return activeUsers;
    }

    public List<User> getInactiveUsers() {
        return inactiveUsers;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
        save();
    }

    public void removeBook(String title) {
        books.removeIf(book -> book.getTitle().equals(title));
        save();
    }

    public void addUser(User user) {
        activeUsers.add(user);
        save();
    }

    public void removeUser(String name) {
        activeUsers.removeIf(user -> user.getName().equals(name));
        inactiveUsers.removeIf(user -> user.getName().equals(name));
        save();
    }

    public void activateUser(String userName) {
        for (User user : inactiveUsers) {
            if (user.getName().equals(userName)) {
                user.setActive(true);
                activeUsers.add(user);
                inactiveUsers.remove(user);
                save();
                break;
            }
        }
    }

    public void deactivateUser(String userName) {
        for (User user : activeUsers) {
            if (user.getName().equals(userName)) {
                user.setActive(false);
                inactiveUsers.add(user);
                activeUsers.remove(user);
                save();
                break;
            }
        }
    }

    public boolean authenticateLibrarian(String username, String password) {
        return "Admin".equals(username) && "admin123".equals(password);
    }

    public User authenticateUser(String cardNumber) {
        for (User user : activeUsers) {
            if (user.getLibraryCardNumber().equals(cardNumber)) {
                return user;
            }
        }
        return null;
    }

    public List<Book> getCheckedOutBooks(User user) {
        return user.getBorrowedBooks();
    }

    public List<Book> getLibraryBooks() {
        return books;
    }

    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(books);
            oos.writeObject(activeUsers);
            oos.writeObject(inactiveUsers);
        }
        catch (IOException e) {
            System.out.println("Error saving library data");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadLibrary() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            books = (List<Book>) ois.readObject();
            activeUsers = (List<User>) ois.readObject();
            inactiveUsers = (List<User>) ois.readObject();
        }
        catch (FileNotFoundException e) {
            throw new FileNotFoundException("File not found, initialization required.");
        }
        catch (ClassNotFoundException | IOException e) {
            throw new ClassNotFoundException("Error loading library data. " + e.getMessage());
        }
    }
}