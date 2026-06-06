package main;

import java.util.ArrayList;
import java.util.Scanner;

import models.Member;
import models.PremiumMember;
import models.RegularMember;

import repository.BookList;
import repository.MemberList;

import service.BorrowService;
import service.LoanRecord;
import service.ReturnService;

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
            System.out.println("5. Reporting");
            System.out.println("0. Exit");

            System.out.print("Choose: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    bookMenu();
                    break;

                case 2:
                    memberMenu();
                    break;

                case 3:
                    borrowService.borrowBook(
                            bookList,
                            memberList,
                            scanner);
                    break;

                case 4:
                    returnService.returnBook(
                            bookList,
                            memberList,
                            borrowService.getLoanRecords(),
                            scanner);
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
                    bookList.deleteBook(scanner, false);
                    break;

                case 4:
                    bookList.searchBookAndDisplay(scanner);
                    break;

                case 5:
                    bookList.displayAll();
                    break;
            }

        } while (choice != 0);
    }

    private void memberMenu() {

        int choice;

        do {

            System.out.println("\n===== MEMBER MANAGEMENT =====");
            System.out.println("1. Add Regular Member");
            System.out.println("2. Add Premium Member");
            System.out.println("3. Display All Members");
            System.out.println("4. Search Member");
            System.out.println("5. Update Member");
            System.out.println("6. Delete Member");
            System.out.println("0. Back");

            System.out.print("Choose: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    addRegularMember();
                    break;

                case 2:
                    addPremiumMember();
                    break;

                case 3:
                    memberList.displayMember();
                    break;

                case 4:
                    searchMember();
                    break;

                case 5:
                    updateMember();
                    break;

                case 6:
                    deleteMember();
                    break;
            }

        } while (choice != 0);
    }

    private void addRegularMember() {

        System.out.println("\n--- ADD REGULAR MEMBER ---");

        System.out.print("Member ID: ");
        String id = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Phone: ");
        int phone = Integer.parseInt(scanner.nextLine());

        memberList.addMember(
                new RegularMember(id, name, phone));

        System.out.println("Regular Member added successfully!");
    }

    private void addPremiumMember() {

        System.out.println("\n--- ADD PREMIUM MEMBER ---");

        System.out.print("Member ID: ");
        String id = scanner.nextLine();

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Phone: ");
        int phone = Integer.parseInt(scanner.nextLine());

        memberList.addMember(
                new PremiumMember(id, name, phone));

        System.out.println("Premium Member added successfully!");
    }

    private void searchMember() {

        System.out.print("Enter Member ID: ");

        String id = scanner.nextLine();

        Member member = memberList.findMemberById(id);

        if (member == null) {

            System.out.println("Member not found.");
            return;
        }

        System.out.println(member);
    }

    private void updateMember() {

        System.out.print("Enter Member ID: ");

        String id = scanner.nextLine();

        Member member = memberList.findMemberById(id);

        if (member == null) {

            System.out.println("Member not found.");
            return;
        }

        System.out.print("New Name: ");
        String newName = scanner.nextLine();

        System.out.print("New Phone: ");
        int newPhone = Integer.parseInt(scanner.nextLine());

        boolean result =
                memberList.updateMember(
                        id,
                        newName,
                        newPhone);

        if (result) {

            System.out.println("Updated successfully.");
        }
    }

    private void deleteMember() {

        System.out.print("Enter Member ID: ");

        String id = scanner.nextLine();

        boolean result =
                memberList.deleteMember(id);

        if (result) {

            System.out.println("Deleted successfully.");
        } else {

            System.out.println("Member not found.");
        }
    }

    private void reportMenu() {

        int choice;

        do {

            System.out.println("\n===== REPORTING =====");
            System.out.println("1. Borrowed Books");
            System.out.println("2. Overdue Members");
            System.out.println("3. Member Borrow History");
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
                    reportMemberHistory();
                    break;
            }

        } while (choice != 0);
    }

    private void reportBorrowedBooks() {

        ArrayList<LoanRecord> records =
                borrowService.getLoanRecords();

        System.out.println("\n===== CURRENTLY BORROWED BOOKS =====");

        boolean found = false;

        for (LoanRecord record : records) {

            if (record.getReturnDate() == null) {

                System.out.println(record);

                found = true;
            }
        }

        if (!found) {

            System.out.println("No borrowed books.");
        }
    }

    private void reportOverdueMembers() {

        ArrayList<LoanRecord> records =
                borrowService.getLoanRecords();

        System.out.println("\n===== OVERDUE MEMBERS =====");

        boolean found = false;

        for (LoanRecord record : records) {

            if (record.getReturnDate() == null
                    && record.isOverdue()) {

                Member member =
                        memberList.findMemberById(
                                record.getMemberId());

                if (member != null) {

                    int overdueDays =
                            record.calculateOverdueDays();

                    double fine =
                            member.calculateFine(
                                    overdueDays);

                    System.out.println("--------------------");
                    System.out.println("Member ID: "
                            + member.getMemberId());

                    System.out.println("Name: "
                            + member.getName());

                    System.out.println("Overdue Days: "
                            + overdueDays);

                    System.out.println("Estimated Fine: "
                            + fine + " VND");

                    found = true;
                }
            }
        }

        if (!found) {

            System.out.println("No overdue members.");
        }
    }

    private void reportMemberHistory() {

        System.out.print("\nEnter Member ID: ");

        String memberId =
                scanner.nextLine();

        ArrayList<LoanRecord> records =
                borrowService.getLoanRecords();

        boolean found = false;

        System.out.println("\n===== BORROW HISTORY =====");

        for (LoanRecord record : records) {

            if (record.getMemberId()
                    .equalsIgnoreCase(memberId)) {

                System.out.println(record);

                found = true;
            }
        }

        if (!found) {

            System.out.println("No records found.");
        }
    }
}