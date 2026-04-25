import java.sql.*;
import java.util.Scanner;

public class Library {
    Scanner sc = new Scanner(System.in);

    // Add Book
    public void addBook() {
        System.out.print("Enter book name: ");
        String book = sc.nextLine();
        try (Connection con = DBConnection.getConnection()) {
            String query = "INSERT INTO books (title, issued) VALUES (?, false)";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, book);
            pst.executeUpdate();
            System.out.println(book + " has been added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Show Available Books
    public void showAvailableBooks() {
        try (Connection con = DBConnection.getConnection()) {
            String query = "SELECT title FROM books WHERE issued = false";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            System.out.println("Available books:");
            boolean found = false;
            while (rs.next()) {
                System.out.println("* " + rs.getString("title"));
                found = true;
            }
            if (!found) {
                System.out.println("No books available!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Issue Book
    public void issueBook() {
        System.out.print("Enter book name to issue: ");
        String book = sc.nextLine();
        try (Connection con = DBConnection.getConnection()) {
            String query = "UPDATE books SET issued = true WHERE title = ? AND issued = false";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, book);
            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("The book has been issued!");
            } else {
                System.out.println("This book does not exist or is already issued.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Return Book
    public void returnBook() {
        System.out.print("Enter book name to return: ");
        String book = sc.nextLine();
        try (Connection con = DBConnection.getConnection()) {
            String query = "UPDATE books SET issued = false WHERE title = ? AND issued = true";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, book);
            int rows = pst.executeUpdate();
            if (rows > 0) {
                System.out.println("The book has been returned!");
            } else {
                System.out.println("This book was not issued or does not exist.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
