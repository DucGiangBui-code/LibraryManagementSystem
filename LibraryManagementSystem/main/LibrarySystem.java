package main;

import java.util.ArrayList;
import java.util.Scanner;

import repository.BookList;
import repository.MemberList;
import service.BorrowService;
import service.LoanRecord;
import service.ReturnService;
import models.Member;

public class LibrarySystem {
    private Scanner scanner;
    private BookList bookList;
    private MemberList memberList;
    private BorrowService borrowService;
    private ReturnService returnService;

    public LibrarySystem() {
        scanner = new Scanner(System.in);
        bookList = new BookList();
        memberList = new MemberList();
        borrowService = new BorrowService();
        returnService = new ReturnService();
    }

    public static void main(String[] args) {
        LibrarySystem system = new LibrarySystem();
        system.startSystem();
    }

    public void startSystem() {
        int choice;
        do {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Book Management");
            System.out.println("2. Member Management");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Reports");
            System.out.println("0. Exit");
            System.out.print("Choose: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        bookMenu();
                        break;
                    case 2:
                        memberMenu();
                        break;
                    case 3:
                        borrowService.borrowBook(bookList, memberList, scanner);
                        break;
                    case 4:
                        returnService.returnBook(bookList, memberList, borrowService.getLoanRecords(), scanner);
                        break;
                    case 5:
                        reportMenu();
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (Exception e) {
                System.out.println("Invalid input!");
                choice = -1;
            }
        } while (choice != 0);
    }

    private void bookMenu() {
        int choice;
        do {
            System.out.println("\n===== BOOK MANAGEMENT =====");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. Search Book");
            System.out.println("5. Display All Books");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    bookList.inputNewBook(scanner);
                    break;
                case 2:
                    bookList.updateBook(scanner);
                    break;
                case 3:
                    // TODO: Kiểm tra sách có đang được mượn không
                    bookList.deleteBook(scanner, false);
                    break;
                case 4:
                    bookList.searchBookAndDisplay(scanner);
                    break;
                case 5:
                    bookList.displayAll();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private void memberMenu() {
        int choice;
        do {
            System.out.println("\n===== MEMBER MANAGEMENT =====");
            System.out.println("1. Display Members");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    memberList.displayMember();
                    break;
                case 2: // Thêm case 2 nếu sau này bạn muốn bổ sung chức năng
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private void reportMenu() {
        int choice;
        do {
            System.out.println("\n===== REPORT MENU =====");
            System.out.println("1. Borrowed Books");
            System.out.println("2. Overdue Members");
            System.out.println("3. Borrow History");
            System.out.println("0. Back");
            System.out.print("Choose: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    reportBorrowedBooks();
                    break;
                case 2:
                    reportOverdueMembers();
                    break;
                case 3:
                    reportBorrowHistory();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    private void reportBorrowedBooks() {
        System.out.println("\n===== BORROWED BOOKS =====");
        ArrayList<LoanRecord> records = borrowService.getLoanRecords();
        for (LoanRecord record : records) {
            if (record.getReturnDate() == null) {
                System.out.println("Loan ID: " + record.getLoanId()
                        + " | Book ID: " + record.getBookId()
                        + " | Member ID: " + record.getMemberId());
            }
        }
    }

    private void reportOverdueMembers() {
        System.out.println("\n===== OVERDUE MEMBERS =====");
        ArrayList<LoanRecord> records = borrowService.getLoanRecords();
        for (LoanRecord record : records) {
            if (record.getReturnDate() == null && record.isOverdue()) {
                Member member = memberList.findMemberById(record.getMemberId());
                int overdueDays = record.calculateOverdueDays();
                double fine = member.calculateFine(overdueDays);

                System.out.println("Member: " + member.getName()
                        + " | Overdue Days: " + overdueDays
                        + " | Fine: " + fine + " VND");
            }
        }
    }

    private void reportBorrowHistory() {
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine();
        ArrayList<LoanRecord> records = borrowService.getLoanRecords();
        System.out.println("\n===== BORROW HISTORY =====");
        for (LoanRecord record : records) {
            if (record.getMemberId().equalsIgnoreCase(memberId)) {
                System.out.println(record);
            }
        }
    }
}