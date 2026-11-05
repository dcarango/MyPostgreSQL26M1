package daos;

public class RoleDAO {
    private Integer roleId;
    private String roleName;
    private Integer deparmentId;


    public RoleDAO() {
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getDeparmentId() {
        return deparmentId;
    }

    public void setDeparmentId(Integer deparmentId) {
        this.deparmentId = deparmentId;
    }

}
