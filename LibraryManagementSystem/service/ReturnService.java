package service;

import models.Book;
import models.Member;
import repository.BookList;
import repository.MemberList;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class ReturnService {

    // Điểm mấu chốt: Hàm nhận vào danh sách loanRecords được truyền từ bên ngoài vào
    public void returnBook(BookList bookList, MemberList memberList, ArrayList<LoanRecord> loanRecords, Scanner scanner) {
        scanner.nextLine(); // Xóa bộ nhớ đệm
        
        System.out.println("\n--- THỰC HIỆN TRẢ SÁCH ---");
        System.out.print("Nhập mã phiếu mượn muốn trả (Loan ID): ");
        String loanId = scanner.nextLine();

        // Tìm kiếm phiếu mượn trong danh sách được truyền sang
        LoanRecord record = null;
        for (int i = 0; i < loanRecords.size(); i++) {
            LoanRecord currentRecord = loanRecords.get(i);
            if (currentRecord.getLoanId().equalsIgnoreCase(loanId) && currentRecord.getReturnDate() == null) {
                record = currentRecord;
                break; 
            }
        }

        if (record == null) {
            System.out.println("Lỗi: Không tìm thấy phiếu mượn hợp lệ!");
            return;
        }

        // Cập nhật ngày trả sách
        record.setReturnDate(LocalDate.now());

        // Cộng lại số lượng sách vào kho
        Book book = bookList.findBookById(record.getBookId());
        if (book != null) {
            book.setQuantity(book.getQuantity() + 1);
        }

        System.out.println("Đã nhận lại sách thành công!");

        // Tính tiền phạt bằng Đa hình (Polymorphism Core)
        if (record.isOverdue() == true) {
            int overdueDays = record.calculateOverdueDays(); 
            Member member = memberList.findMemberById(record.getMemberId());
            
            if (member != null) {
                double fine = member.calculateFine(overdueDays);
                System.out.println("CẢNH BÁO: Phiếu mượn quá hạn " + overdueDays + " ngày!");
                System.out.println("Số tiền phạt cần thu: " + fine + " VND");
            }
        } else {
            System.out.println("Tuyệt vời: Sách trả đúng hạn, không bị phạt.");
        }
    }
}