package repository;

import models.Book;
import java.util.ArrayList;
import java.util.Scanner;

public class BookList {
    private ArrayList<Book> books = new ArrayList<>();

    public Book findBookById(String id) {
        for (Book book : books) {
            if (book.getId().equalsIgnoreCase(id.trim())) {
                return book; 
            }
        }
        return null; 
    }

    public void inputNewBook(Scanner scanner) {
        System.out.println("\n--- ADD NEW BOOK ---");
        String id;
        
        while (true) {
            System.out.print("Enter Book ID: ");
            id = scanner.nextLine().trim();
            
            if (id.isEmpty()) {
                System.out.println("Error: Book ID cannot be empty!");
                continue;
            }
            
            if (findBookById(id) != null) {
                System.out.println("Error: Book ID '" + id + "' already exists! Please enter a different ID.");
            } else {
                break; 
            }
        }
        
        Book newBook = new Book();
        newBook.inputWithValidatedId(scanner, id);
        books.add(newBook);
        System.out.println("Book added successfully!");
    }

    public void updateBook(Scanner scanner) {
        System.out.println("\n--- UPDATE BOOK INFORMATION ---");
        System.out.print("Enter Book ID to update: ");
        String id = scanner.nextLine();
        
        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Error: Book with ID '" + id + "' not found in the system!");
            return; 
        }

        System.out.println("Book found: " + book.getTitle());
        System.out.print("Enter new title (Press Enter to keep current): ");
        String title = scanner.nextLine();
        if (!title.isEmpty()) book.setTitle(title);

        System.out.print("Enter new author (Press Enter to keep current): ");
        String author = scanner.nextLine();
        if (!author.isEmpty()) book.setAuthor(author);

        System.out.println("Updated successfully!");
    }

    public void deleteBook(Scanner scanner, boolean isBeingBorrowed) {
        System.out.println("\n--- DELETE BOOK FROM SYSTEM ---");
        System.out.print("Enter Book ID to delete: ");
        String id = scanner.nextLine();
        
        Book book = findBookById(id);
        if (book == null) {
            System.out.println("Error: Book with ID '" + id + "' not found!");
            return; 
        }

        if (isBeingBorrowed) {
            System.out.println("Error: Cannot delete! This book is currently being borrowed.");
        } else {
            books.remove(book);
            System.out.println("Deleted book '" + book.getTitle() + "' successfully!");
        }
    }

    public void searchBookAndDisplay(Scanner scanner) {
        System.out.print("\nEnter Book ID to search: ");
        String id = scanner.nextLine();
        Book book = findBookById(id);
        
        if (book == null) {
            System.out.println("Error: Book with ID '" + id + "' not found!");
        } else {
            System.out.println("\nBOOK FOUND:");
            System.out.println("==========================================================================================");
            book.display();
            System.out.println("==========================================================================================");
        }
    }

    public void displayAll() {
        if (books.isEmpty()) {
            System.out.println("The book list is currently empty.");
            return;
        }
        System.out.println("\n==========================================================================================");
        System.out.printf("| %-8s | %-25s | %-20s | %-12s | %-8s | %-8s |\n", 
                "Book ID", "Title", "Author", "Genre", "Year", "Quantity");
        System.out.println("------------------------------------------------------------------------------------------");
        for (Book b : books) b.display();
        System.out.println("==========================================================================================");
    }
}
