package Library;
import java.util.List;

public class Librarian extends User implements LibraryUser {
    public Librarian(String name, String libraryCardNumber) {
        super(name, libraryCardNumber);
    }

    @Override
    public void viewBooks() {
        List<Book> books = LibraryController.getInstance().getBooks();
        for (Book book : books) {
            System.out.println(book.getTitle() + " by " + book.getAuthor());
        }
    }

    @Override
    public void viewUsers() {
        List<User> users = LibraryController.getInstance().getActiveUsers();
        for (User user : users) {
            System.out.println(user.getName() + " (Card Number: " + user.getLibraryCardNumber() + ")");
        }
    }

    public void disableUser(User user) {
        user.setActive(false);
        LibraryController.getInstance().save();
    }

    public void enableUser(User user) {
        user.setActive(true);
        LibraryController.getInstance().save();
    }

    public void checkOutBook(RegularUser user, Book book) {
        if (user.borrowBook(book)) {
            System.out.println("Book checked out successfully.");
            LibraryController.getInstance().save();
        } else {
            System.out.println("Book is not available or already checked out.");
        }
    }

    public void checkInBook(RegularUser user, Book book) {
        if (user.returnBook(book)) {
            System.out.println("Book returned successfully.");
            LibraryController.getInstance().save();
        } else {
            System.out.println("This user does not have this book or the book was not checked out.");
        }
    }
}