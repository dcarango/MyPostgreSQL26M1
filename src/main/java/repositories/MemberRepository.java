package repositories;

import daos.MemberDAO;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Repositorio que nos permite realiza un CRUD
 * del DAO MemberDAO
 */

public class MemberRepository {

    //este repositorio me permite hacer las operaciones CRUD del DAO MemberDAO en la tabla member
    public boolean createMember(MemberDAO newmeMember) {
        boolean insertOk = false;
        String sql = "INSERT INTO `member` ("
                + "  `first_name`, `last_name`, `birth_year`, "
                + "  `join_date`, `leave_date`, `position_id`, `category_id`"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?)";

        DBConnection dbConnection = new DBConnection();
        try (Connection miCon = dbConnection.connect()) {

            PreparedStatement statement = miCon.prepareStatement(sql);
            statement.setString(1,newmeMember.getFirstName());
            statement.setString(2,newmeMember.getLastName());
            statement.setInt(3,newmeMember.getBirthYear());
            statement.setDate(4,newmeMember.getJoinDate());
            statement.setDate(5,newmeMember.getLeaveDate());
            statement.setInt(6,newmeMember.getPositionId());
            statement.setInt(7,newmeMember.getCategoryId());

            statement.executeUpdate();

            insertOk = true;
        } catch (SQLException e) {
            insertOk = false;
        }

        return insertOk;
    }
}
