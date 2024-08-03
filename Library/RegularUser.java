package Library;
import java.util.List;

public class RegularUser extends User implements LibraryUser {
    public RegularUser(String name, String libraryCardNumber) {
        super(name, libraryCardNumber);
    }

    @Override
    public void viewBooks() {
        List<Book> books = Library.INSTANCE.getBooks();
        for (Book book : books) {
            System.out.println(book.getTitle() + " by " + book.getAuthor());
        }
    }

    @Override
    public void viewUsers() {
        System.out.println("Regular users cannot view other users.");
    }
}
