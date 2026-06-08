package models;

public class RegularMember extends Member{
    public RegularMember() {
        super();
    }
    public RegularMember(String memberId, String name, int phone) {
        super(memberId, name, phone);
    }
    @Override
    public double calculateFine(int overdueDays) {
        // 5k/day
        return overdueDays * 5000;
    }

    @Override
    public String toString() {
        return super.toString()+ "Type:Regular";
    }
    
}
