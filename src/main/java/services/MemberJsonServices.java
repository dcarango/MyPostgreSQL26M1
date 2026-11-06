package services;

import daos.MemberDAO;
import daos.MemberRoleDAO;
import dtosJSON.HockeyClub;
import dtosJSON.Member;
import dtosJSON.Role;
import repositories.CategoryRepository;
import repositories.MemberRepository;
import repositories.PlayingPositionRepository;

import java.sql.Date;

public class MemberJsonServices {

    public StringBuilder inserJsonMembers(HockeyClub hockeyClub) {
        StringBuilder message = new StringBuilder();
        MemberDAO newMemberDAO;
        boolean redFlag = true;
        Integer positionId;
        Integer categoryId;
        // Para recuperar los id's playing position y category debo llamar a sus repos
        PlayingPositionRepository pprepo = new PlayingPositionRepository();
        CategoryRepository caterepo = new CategoryRepository();
        // Para insertar el memberDao su repo
        MemberRepository memberRepository = new MemberRepository();

        for (Member newMember: hockeyClub.getMembers()) {
            newMemberDAO = new MemberDAO();

            if (!redFlag){
                newMemberDAO.setFirstName(newMember.getFirstName());
                newMemberDAO.setLastName(newMember.getLastName());
                // Cambiamos de integer a Short
                newMemberDAO.setBirthYear(Short.parseShort(String.valueOf(newMember.getBirthYear())));
                // para pasar de LocalDate a sql.Date
                if (newMember.getJoinDate()==null){
                    newMemberDAO.setJoinDate(null);
                }else {
                    // Member tiene las fechas como LocalDate y tengo que pasarlas a sql.Date
                    newMemberDAO.setJoinDate(Date.valueOf(newMember.getJoinDate()));
                }
                // de localDate a sql.Date
                if (newMember.getLeaveDate()==null){
                    newMemberDAO.setLeaveDate(null);
                }else {
                    newMemberDAO.setLeaveDate(Date.valueOf(newMember.getLeaveDate()));
                }

                positionId = pprepo.idPositionByNombre(newMember.getPlayingPosition());
                categoryId =caterepo.idCategoryByName(newMember.getTeamCategory());

                if (positionId == 0) {
                    newMemberDAO.setPositionId(null);
                    message.append("The position does not exits, set to null");
                } else {
                    newMemberDAO.setPositionId(positionId);
                }

                if (categoryId == 0) {
                    newMemberDAO.setCategoryId(null);
                    message.append("The category does not exits, set to null");
                } else {
                    newMemberDAO.setCategoryId(categoryId);
                }
                // Cargamos la info basica del Member que se almacena en el MemberDao
                if (memberRepository.createMember(newMemberDAO)) {
                    message.append("Member loaded succcesfully");

                    // Variable axuxiliar para guardar los roles e insertalos al repo
                    MemberRoleDAO newRoleDAO;
                    Integer idMember;
                    for (Role newRole: newMember.getRoles()) {
                        newRoleDAO = new MemberRoleDAO();
                        // Recupero el idMember apartir del lastname del newMember
                        idMember = memberRepository.idByLastName(newMember.getLastName());
                        if (idMember == 0) {
                            message.append("Role not loaded, member not found");
                        } else {
                            newRoleDAO.setMemberId(idMember);
                            newRoleDAO.setRoleId(newRole.getRoleId());
                            newRoleDAO.setStratDate(Date.valueOf(newRole.getStartDate()) );
                            newRoleDAO.setEndDate(Date.valueOf(newRole.getEndDate()));
                        }
                    }

                } else {
                    message.append("Something went wrong loading "+ newMember.getFirstName());
                }
            }
        }
        return message;
    }
}
