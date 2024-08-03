package Library;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryController implements Serializable{
    private List<String> activeUsers;
    private List<String> inactiveUsers;
    private List<Book> books;

    public LibraryController() {
        if (new File("DB/library.ser").exists()) {
            loadStatus();
        }

        else {
            System.out.println("New libary, init values");
            activeUsers = new ArrayList<>();
            inactiveUsers = new ArrayList<>();
            books = new ArrayList<>();
            activeUsers.add("User 1");
            activeUsers.add("User 2");
            inactiveUsers.add("User 3");
            books.add(new Book("Book 1", "Author 1", "ISBN1"));
            books.add(new Book("Book 2", "Author 2", "ISBN2"));
            books.add(new Book("Book 3", "Author 3", "ISBN3"));
            saveStatus();
        }
    }

    public List<String> getActiveUsers() {
        return activeUsers;
    }

    public List<String> getInactiveUsers() {
        return inactiveUsers;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(String title) {
        books.removeIf(book -> book.getTitle().equals(title));
    }

    public void addUser(User user) {
        activeUsers.add(user.getName());
    }

    public void removeUser(String name) {
        activeUsers.remove(name);
        inactiveUsers.remove(name);
    }

    public void activateUser(String user) {
        if (inactiveUsers.contains(user)) {
            inactiveUsers.remove(user);
            activeUsers.add(user);
        }
    }

    public void deactivateUser(String user) {
        if (activeUsers.contains(user)) {
            activeUsers.remove(user);
            inactiveUsers.add(user);
        }
    }


    // serialize here, easier to load compared too Users and Books
    public void saveStatus() {
        String path = "DB/library.ser";
        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
            System.out.println("Serialized data is saved in DB/library.ser");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // pull data from ser 
    public void loadStatus() {
        String path = "DB/library.ser";
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            LibraryController libraryController = (LibraryController) in.readObject();
            this.activeUsers = libraryController.activeUsers;
            this.inactiveUsers = libraryController.inactiveUsers;
            this.books = libraryController.books;
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    } 
}