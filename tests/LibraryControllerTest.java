package tests;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Library.*;

import java.io.File;
import java.util.List;
import static org.junit.Assert.*;

public class LibraryControllerTest {
    private LibraryController libraryController;

    @Before
    public void setUp() {
        libraryController = LibraryController.getInstance();
        libraryController.FILE_PATH = "DB/test.ser";
    }

    @After
    public void tearDown() {
        libraryController = null; // Clean up resources if needed

        File toDel = new File(libraryController.FILE_PATH);
        toDel.delete();
    }

    @Test
    public void testGetActiveUsers() {
        List<User> activeUsers = libraryController.getActiveUsers();
        assertEquals(2, activeUsers.size());
        assertTrue(activeUsers.contains("User 1"));
        assertTrue(activeUsers.contains("User 2"));
    }

    @Test
    public void testGetInactiveUsers() {
        List<User> inactiveUsers = libraryController.getInactiveUsers();
        assertEquals(1, inactiveUsers.size());
        assertTrue(inactiveUsers.contains("User 3"));
    }

    @Test
    public void testGetBooks() {
        List<Book> books = libraryController.getBooks();
        assertEquals(3, books.size());
        assertEquals("Book 1", books.get(0).getTitle());
        assertEquals("Book 2", books.get(1).getTitle());
        assertEquals("Book 3", books.get(2).getTitle());
    }

    @Test
    public void testAddBook() {
        Book newBook = new Book("Book 4", "Author 4", "ISBN4");
        libraryController.addBook(newBook);
        List<Book> books = libraryController.getBooks();
        assertEquals(4, books.size());
        assertTrue(books.contains(newBook));
    }

    @Test
    public void testRemoveBook() {
        libraryController.removeBook("Book 2");
        List<Book> books = libraryController.getBooks();
        assertEquals(2, books.size());
        for (Book book : books) {
            assertNotEquals("Book 2", book.getTitle());
        }
    }

    @Test
    public void testAddUser() {
        User newUser = new User("User 4", "1234");
        libraryController.addUser(newUser);
        List<User> activeUsers = libraryController.getActiveUsers();
        assertEquals(3, activeUsers.size());
        assertTrue(activeUsers.contains("User 4"));
    }

    @Test
    public void testRemoveUser() {
        libraryController.removeUser("User 2");
        List<User> activeUsers = libraryController.getActiveUsers();
        List<User> inactiveUsers = libraryController.getInactiveUsers();
        assertEquals(1, activeUsers.size());
        assertFalse(activeUsers.contains("User 2"));
        assertFalse(inactiveUsers.contains("User 2"));
    }

    @Test
    public void testActivateUser() {
        libraryController.deactivateUser("User 1");
        libraryController.activateUser("User 1");
        List<User> activeUsers = libraryController.getActiveUsers();
        List<User> inactiveUsers = libraryController.getInactiveUsers();
        assertTrue(activeUsers.contains("User 1"));
        assertFalse(inactiveUsers.contains("User 1"));
    }

    @Test
    public void testDeactivateUser() {
        libraryController.deactivateUser("User 1");
        List<User> activeUsers = libraryController.getActiveUsers();
        List<User> inactiveUsers = libraryController.getInactiveUsers();
        assertFalse(activeUsers.contains("User 1"));
        assertTrue(inactiveUsers.contains("User 1"));
    }
}
