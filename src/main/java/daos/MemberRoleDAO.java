package daos;

import java.util.Date;

public class MemberRoleDAO {
    private Integer memberRoleId;
    private Integer memberId;
    private Integer roleId;
    private Date stratDate;
    private Date endDate;

    public MemberRoleDAO() {
    }

    public Integer getMemberRoleId() {
        return memberRoleId;
    }

    public void setMemberRoleId(Integer memberRoleId) {
        this.memberRoleId = memberRoleId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Date getStratDate() {
        return stratDate;
    }

    public void setStratDate(Date stratDate) {
        this.stratDate = stratDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
