package Library;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public enum Library {
    INSTANCE;

    private List<Book> books;
    private List<User> users;

    Library() {
        books = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void removeBook(Book book) {
        books.remove(book);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<User> getUsers() {
        return users;
    }

    public void saveLibraryData(String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(books);
            oos.writeObject(users);
        }
    }

    public void loadLibraryData(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            books = (List<Book>) ois.readObject();
            users = (List<User>) ois.readObject();
        }
    }
}