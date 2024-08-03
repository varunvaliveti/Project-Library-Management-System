package Library;
public class Main {
    // testing out stuff, delete later
    
    
    public static void main (String args[]) {
        System.out.println("Starting libary instance");

        LibraryController libraryController = new LibraryController(); 
       
        System.out.println("Active users: " + libraryController.getActiveUsers());
        System.out.println("Inactive users: " + libraryController.getInactiveUsers());
        System.out.println("Books: " + libraryController.getBooks());
    }

}
