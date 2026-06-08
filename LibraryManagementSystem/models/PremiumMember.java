package models;

public class PremiumMember extends Member{
    public PremiumMember() {
        super();
    }
    public PremiumMember(String memberId, String name, int phone) {
        super(memberId, name, phone);
    }
    @Override
    public double calculateFine(int overdueDays) {
        // free with 3 first day, day 4 5k/day
        if (overdueDays <= 3) {
        return 0;
        }else 
            return (overdueDays-3)*5000;
    }

    @Override
    public String toString() {
        return super.toString()+ "Type:Premium";
    }
    
}
