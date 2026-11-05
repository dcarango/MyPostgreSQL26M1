package ui;

import database.StructureDB;
import dtosJSON.HockeyClub;
import dtosUI.MemberUI;
import myLib.UserMethods;
import services.MemberServices;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.util.Scanner;


public class MainMenu {
    private Scanner scanner = new Scanner(System.in);
    private boolean exitMenu = false;
    private HockeyClub hockeyClub;

    public void showMenu(){
        UserMethods userMethods = new UserMethods();
        do{
            userMethods.myPrinter("Choose an option: ");
            userMethods.myPrinter("1. Read JSON file using Jackson");
            userMethods.myPrinter("2. Create data base");
            userMethods.myPrinter("3. Insert new member");
            userMethods.myPrinter("0. Exit.");
            processOption(requestOption());
        }while(!exitMenu);
    }
    private String requestOption(){
        return this.scanner.nextLine();
    }

    private void processOption(String option){
        UserMethods userMethods = new UserMethods();
        switch(option){
            case "0" -> exitMenu = true;
            case "1" -> {
                Path p = userMethods.fileToRead("Introduce the path to the file to be read with Jackson: ");
                ObjectMapper objectMapper = new ObjectMapper();
                hockeyClub = objectMapper.readValue(p.toFile(), HockeyClub.class);
                userMethods.myPrinter("Hockey club properly loaded.");
            }
            case "2" -> {
                StructureDB structureDB = new StructureDB();
                //userMethods.myPrinter(structureDB.createDB("chRuben"));
                userMethods.myPrinter(structureDB.createTables());
            } case "3" -> {
                MemberUI newMember = new MemberUI();
                MemberServices memberServices = new MemberServices();
                userMethods.myPrinter("Introduce the data of the new member:\n");
                newMember.setFirstName(userMethods.requestString("First name: \n"));
                newMember.setLastName(userMethods.requestString("Last name: \n"));
                newMember.setBirthYear(userMethods.requestInteger("Birth year: \n"));
                newMember.setJoinDate(userMethods.requestDate("Join date: \n"));
                newMember.setPosition(userMethods.requestString("Playing position: \n"));
                newMember.setCategory(userMethods.requestString("Category: "));
                userMethods.myPrinter(memberServices.insertNewMember(newMember));
            }

            default -> userMethods.myPrinter("Wrong option.");
        }
    }
}
