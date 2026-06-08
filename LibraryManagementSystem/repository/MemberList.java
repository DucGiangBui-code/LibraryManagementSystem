package repository;

import models.Member;
import java.util.ArrayList;

public class MemberList extends ArrayList<Member> {

    //add Member
    public void addMember(Member newMember) {
        this.add(newMember);
    }

    //display Member
    public void displayMember() {
        if (this.isEmpty()) {
            System.out.println("Empty List");
            return;
        }
        int size = this.size();
        for (int i = 0; i < size; i++) {
            Member member = this.get(i);
            System.out.println(member.toString());
        }
    }

    //find Member by id
    public Member findMemberById(String memberId) {
        int size = this.size();
        for (int i = 0; i < size; i++) {
            Member member = this.get(i);
            if (member.getMemberId().equalsIgnoreCase(memberId)) {
                return member;
            }
        }
        return null;
    }

    //Delete Member by Id
    public boolean deleteMember(String memberId) {
        Member member = findMemberById(memberId);
        if (member != null) {
            this.remove(member);
            return true;
        }
        return false;
    }

    //Update Member by Id
    public boolean updateMember(String memberId, String newName, int newPhone) {
        Member member = findMemberById(memberId);
        if (member != null) {
            member.setName(newName);
            member.setPhone(newPhone);
            return true;
        }
        return false;
    }
}
