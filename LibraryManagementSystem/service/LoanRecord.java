package service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class LoanRecord {
    private String loanId;
    private String bookId;
    private String memberId;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public LoanRecord(String loanId, String bookId, String memberId, LocalDate loanDate, LocalDate dueDate) {
        this.loanId = loanId;
        this.bookId = bookId;
        this.memberId = memberId;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = null;
    }

    // Các hàm Getter/Setter thông dụng
    
    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    // Hàm kiểm tra quá hạn
    public boolean isOverdue() {
        LocalDate dateToCompare = (this.returnDate != null) ? this.returnDate : LocalDate.now();
        return dateToCompare.isAfter(this.dueDate);
    }

    // Hàm tính số ngày trễ
    public int calculateOverdueDays() {
        if (!isOverdue()) return 0;
        LocalDate dateToCompare = (this.returnDate != null) ? this.returnDate : LocalDate.now();
        return (int) ChronoUnit.DAYS.between(this.dueDate, dateToCompare);
    }

    @Override
    public String toString() {
        return "LoanRecord [loanId=" + loanId + ", bookId=" + bookId + ", memberId=" + memberId + ", loanDate="
                + loanDate + ", dueDate=" + dueDate + ", returnDate=" + returnDate + "]";
    }
    
}
    

