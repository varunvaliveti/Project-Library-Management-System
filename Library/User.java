package Library;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class User implements Comparable<User>, Serializable {
    private String name;
    private String libraryCardNumber;
    private boolean isActive;
    private List<Book> borrowedBooks;

    public User(String name, String libraryCardNumber) {
        this.name = name;
        this.libraryCardNumber = libraryCardNumber;
        this.isActive = true;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLibraryCardNumber() {
        return libraryCardNumber;
    }

    public void setLibraryCardNumber(String libraryCardNumber) {
        this.libraryCardNumber = libraryCardNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public boolean borrowBook(Book book) {
        if (!book.isCheckedOut() && isActive) {
            book.setCheckedOut(true);
            borrowedBooks.add(book);
            return true;
        }
        return false;
    }

    public boolean returnBook(Book book) {
        if (borrowedBooks.contains(book)) {
            book.setCheckedOut(false);
            borrowedBooks.remove(book);
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(User other) {
        return this.name.compareTo(other.name);
    }
}
