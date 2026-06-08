package models;

public class Member {
    private String memberId;
    private String name;
    private int phone;
    //calculateFine
    public double calculateFine(int overdueDays) {
       return 0;
    }

    public Member() {
    }

    public Member(String memberId, String name, int phone) {
        this.memberId = memberId;
        this.name = name;
        this.phone = phone;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Member{" + "memberId=" + memberId + ", name=" + name + ", phone=" + phone + '}';
    }
}

