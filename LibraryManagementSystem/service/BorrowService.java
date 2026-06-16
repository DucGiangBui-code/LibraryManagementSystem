package service;

import models.Book;
import models.Member;
import repository.BookList;
import repository.MemberList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class BorrowService {

    private static final int MAX_BORROW_DAYS = 14;
    // Danh sách gốc lưu trữ tất cả phiếu mượn trong hệ thống
    private ArrayList<LoanRecord> loanRecords = new ArrayList<>();
    private int autoIncrementId = 1; 

    public void borrowBook(BookList bookList, MemberList memberList, Scanner scanner) {
        scanner.nextLine(); // Sửa lỗi trôi lệnh
        
        System.out.println("\n--- THỰC HIỆN MƯỢN SÁCH ---");
        System.out.print("Nhập mã thành viên: ");
        String memberId = scanner.nextLine(); // tránh bị nuốt ký tự và nhảy không cho nhập memberID
        System.out.print("Nhập mã sách: ");
        String bookId = scanner.nextLine();

        Member member = memberList.findMemberById(memberId);
        if (member == null) {
            System.out.println("Lỗi: Mã thành viên không tồn tại!");
            return; 
        }

        Book book = bookList.findBookById(bookId);
        if (book == null) {
            System.out.println("Lỗi: Mã sách không tồn tại!");
            return;
        }

        if (book.getQuantity() <= 0) {
            System.out.println("Lỗi: Sách này hiện tại đã hết trong kho!");
            return;
        }

        String loanId = "L" + autoIncrementId++;// để nhận biết đây là phiếu mượn sách
        LocalDate loanDate = LocalDate.now(); 
        LocalDate dueDate = loanDate.plusDays(MAX_BORROW_DAYS); // plusDays là thêm ngày phải trả mà không cần sự dụng if else để so sánh hoặc xét điều kiện là năm nhuận hay không nhuận

        // Tạo phiếu mới và add vào danh sách gốc của file này
        LoanRecord newRecord = new LoanRecord(loanId, bookId, memberId, loanDate, dueDate);
        loanRecords.add(newRecord);

        // Giảm số lượng sách
        book.setQuantity(book.getQuantity() - 1);

        System.out.println("Mượn sách thành công! Mã phiếu: " + loanId);
        System.out.println("Hạn trả sách: " + dueDate);
    }

    // Hàm quan trọng: Để file ReturnService hoặc file Menu (Người 4) có thể lấy danh sách phiếu về dùng
    public ArrayList<LoanRecord> getLoanRecords() {
        return this.loanRecords;
    }
}
