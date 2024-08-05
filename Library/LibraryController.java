package Library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LibraryController implements Serializable {
    private List<User> activeUsers;
    private List<User> inactiveUsers;
    private List<Book> books;

    public LibraryController() {
        activeUsers = new ArrayList<>();
        inactiveUsers = new ArrayList<>();
        books = new ArrayList<>();
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
    }

    public void removeBook(String title) {
        books.removeIf(book -> book.getTitle().equals(title));
    }

    public void addUser(User user) {
        activeUsers.add(user);
    }

    public void removeUser(String name) {
        activeUsers.removeIf(user -> user.getName().equals(name));
        inactiveUsers.removeIf(user -> user.getName().equals(name));
    }

    public void activateUser(String userName) {
        for (User user : inactiveUsers) {
            if (user.getName().equals(userName)) {
                inactiveUsers.remove(user);
                activeUsers.add(user);
                break;
            }
        }
    }

    public void deactivateUser(String userName) {
        for (User user : activeUsers) {
            if (user.getName().equals(userName)) {
                activeUsers.remove(user);
                inactiveUsers.add(user);
                break;
            }
        }
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
}