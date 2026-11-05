package services;

import daos.MemberDAO;
import dtosUI.MemberUI;
import repositories.CategoryRepository;
import repositories.MemberRepository;
import repositories.PlayingPositionRepository;

import java.util.Date;

public class MemberServices {
    public String insertNewMember(MemberUI newMember) {
        MemberRepository memberRepository = new MemberRepository();
        PlayingPositionRepository pprepo = new PlayingPositionRepository();
        CategoryRepository categoryRepository = new CategoryRepository();

        String message ="Feedback: \n";
        boolean redFlag = false;

        // inicializamos la categoria y la posicion
        Integer categoryId = categoryRepository.idCategoryByName(newMember.getCategory());
        Integer positionId = pprepo.idPositionByNombre(newMember.getPosition());

        //comprobaciones antes de llamar al MemberRepository
        //join date tiene que ser fecha anterior a hoy
        Date ahora = new Date(); // Crea una fecha con el instante actual del sistema

        if (newMember.getJoinDate().after(ahora)){
            message += "Wrong date: \n";
            redFlag = true;
        }
        //position name tiene que existir y tener un id recuperable o ser null
        if (newMember.getPosition().isBlank()) {
            newMember.setPosition(null);
        } else {
            // si la funcion devuelve = es que no existe esa posicion en la BD
            if (pprepo.idPositionByNombre(String.valueOf(positionId))==0) {
                message+= "Wrong position. \n";
                redFlag = true;

                // Estos null son los que no se gestionan en interfaz
            } else if (pprepo.idPositionByNombre(String.valueOf(positionId))==null) {
                message += "DB error";
                redFlag = true;
            }
        }
        //cateory tiene que existir y ser recuperable o ser null
        if (newMember.getCategory().isBlank()) {
            newMember.setCategory(null);
        } else {
            if (categoryRepository.idCategoryByName(String.valueOf(categoryId))==0) {
                message+= "Wrong category. \n";
                redFlag = true;
            }
        }

        // si no hay ninguna redFlag es que puedo hacer el insert
        //si no hay ninguna redFlag es que puedo hacer el insert
        if (!redFlag){
            MemberDAO newMemberDAO = new MemberDAO();
            newMemberDAO.setFirstName(newMember.getFirstName());
            newMemberDAO.setLastName(newMember.getLastName());
            newMemberDAO.setBirthYear(Short.parseShort(String.valueOf(newMember.getBirthYear())));
            //para pasar de util.date a ms, y usar el constructor de sql.date con parámetro ms
            if (newMember.getJoinDate()==null){
                newMemberDAO.setJoinDate(null);
            }else {
                java.sql.Date joinDateSql = new java.sql.Date(newMember.getJoinDate().getTime());
                newMemberDAO.setJoinDate(joinDateSql);
            }
            //lo ponemos para que este código sea más reutilizable
            if (newMember.getLeaveDate()==null){
                newMemberDAO.setLeaveDate(null);
            }else {
                java.sql.Date leaveDateSql = new java.sql.Date(newMember.getLeaveDate().getTime());
                newMemberDAO.setLeaveDate(leaveDateSql);
            }
            newMemberDAO.setPositionId(positionId);
            newMemberDAO.setCategoryId(categoryId);
            if(memberRepository.createMember(newMemberDAO)){
                message = "Congratulations. Member inserted.";
            }
        }
        return message;
    }
}
