package models;


import java.util.Scanner;

public class Book {
    private String id;
    private String title;
    private String author;
    private String genre;
    private int publicationYear;
    private int quantity;

    public Book() {}

    public void inputWithValidatedId(Scanner scanner, String id) {
        this.id = id; 
        System.out.print("Enter title: ");
        this.title = scanner.nextLine();
        System.out.print("Enter author: ");
        this.author = scanner.nextLine();
        System.out.print("Enter genre: ");
        this.genre = scanner.nextLine();
        
        while (true) {
            try {
                System.out.print("Enter publication year: ");
                this.publicationYear = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Error: Publication year must be an integer!");
            }
        }

        while (true) {
            try {
                System.out.print("Enter quantity: ");
                int q = Integer.parseInt(scanner.nextLine());
                if (q >= 0) {
                    this.quantity = q;
                    break;
                } else {
                    System.out.println("Error: Quantity cannot be less than 0! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Quantity must be an integer!");
            }
        }
    }

    public void display() {
        System.out.printf("| %-8s | %-25s | %-20s | %-12s | %-8d | %-8d |\n", 
                id, title, author, genre, publicationYear, quantity);
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { if (quantity >= 0) this.quantity = quantity; }
}
