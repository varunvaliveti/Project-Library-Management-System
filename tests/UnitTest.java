package tests;
import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import Library.*;

public class UnitTest {
    private Book b;
    private RegularUser u;
    private Librarian librarian;
    private LibraryController libraryController;

    @Before
    public void setUp() {
        b = new Book("The Book", "Anoosh", "ISBN1");
        u = new RegularUser("Varoon", "1234");
        librarian = new Librarian("Libby", "5678");
        libraryController = LibraryController.getInstance();
        libraryController.addBook(b);
        libraryController.addUser(u);
        libraryController.addUser(librarian);
        libraryController.FILE_PATH = "DB/test.ser";
    }

    @After
    public void cleanUp() {
        // Reset the library state for other tests
        libraryController.getBooks().clear();
        libraryController.getActiveUsers().clear();
        libraryController.getInactiveUsers().clear();

        // delete file
        File toDel = new File(libraryController.FILE_PATH);
        toDel.delete();

    }

    // Book functionality
    @Test
    public void bookFn() {
        assertEquals("The Book", b.getTitle());
        assertEquals("Anoosh", b.getAuthor());
        assertEquals("ISBN1", b.getIsbn());

        assertFalse(b.isCheckedOut());
        b.setCheckedOut(true);
        assertTrue(b.isCheckedOut());

        Book b2 = new Book("The Book", "Aneesh", "ISBN1");
        assertNotEquals(0, b.compareTo(b2));
    }

    @Test
    public void UserFn() {
        assertEquals("Varoon", u.getName());
        assertEquals("1234", u.getLibraryCardNumber());
        assertTrue(u.isActive());
        u.setActive(false);
        assertFalse(u.isActive());

        User u2 = new RegularUser("Varon", "0000");
        assertNotEquals(0, u.compareTo(u2));

        u2.setName("Varoon");
        assertEquals(0, u.compareTo(u2));
    }

    @Test
    public void CheckOutInactive() {
        u.setActive(false);
        u.borrowBook(b);
        assertEquals(0, u.getBorrowedBooks().size());
        u.setActive(true);
        u.borrowBook(b);
        assertEquals(1, u.getBorrowedBooks().size());
    }

    @Test 
    public void CheckOutEdge() {
        assertTrue(u.borrowBook(b));
        assertFalse(u.borrowBook(b)); // Book is already checked out
        assertFalse(u.borrowBook(b)); // Still not available
    }

    // Librarian functionality tests
    @Test
    public void testViewBooks() {
        librarian.viewBooks();
        assertEquals(1, libraryController.getBooks().size());
    }

    @Test
    public void testViewUsers() {
        librarian.viewUsers();
        assertEquals(2, libraryController.getActiveUsers().size() + libraryController.getInactiveUsers().size()); // Includes the librarian
    }

    @Test
    public void testDisableAndEnableUser() {
        librarian.disableUser(u);
        assertFalse(u.isActive());
        librarian.enableUser(u);
        assertTrue(u.isActive());
    }

    @Test
    public void testCheckOutAndCheckInBook() {
        librarian.checkOutBook(u, b);
        assertTrue(b.isCheckedOut());
        librarian.checkInBook(u, b);
        assertFalse(b.isCheckedOut());
    }

    @Test
    public void testCheckOutBookWhenUserIsInactive() {
        librarian.disableUser(u);
        librarian.checkOutBook(u, b);
        assertEquals(0, u.getBorrowedBooks().size()); // Inactive user cannot check out books
    }
}