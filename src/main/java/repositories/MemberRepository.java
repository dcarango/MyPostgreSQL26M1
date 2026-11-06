package repositories;

import daos.MemberDAO;
import database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public Integer idByLastName (String lastName) {
        Integer idMember = 0;
        String sql = "SELECT member_id FROM member WHERE last_name = ?";

        DBConnection con = new DBConnection();

        try (
                Connection miCon = con.connect();
                PreparedStatement pstm = miCon.prepareStatement(sql)
        ) {
            pstm.setString(1,lastName);
            ResultSet rs = pstm.executeQuery();

            if (rs.next()) {
                idMember = rs.getInt("member_id");
            }
        } catch (SQLException e) {
            idMember = null;
        }

        return idMember;
    }
}
