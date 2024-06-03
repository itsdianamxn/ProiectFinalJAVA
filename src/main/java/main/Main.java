package main;

import Interface.MainFrame;
import classes.Hospital;
import classes.Resident;
import classes.Specialization;
import dao.HospitalDAO;
import dao.ResidentDAO;
import db.Database;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        Database db =Database.getInstance();
        db.deleteAllData();
        List<Hospital> hospitals = Arrays.asList(
                new Hospital("Green Valley Hospital", 3, 8, Arrays.asList(new Specialization("Cardiology"), new Specialization("Neurology"))),
                new Hospital("City Health Center", 5, 7, Arrays.asList(new Specialization("Dermatology"))),
                new Hospital("Lakeside Medical", 2, 9, Arrays.asList(new Specialization("Orthopedics"), new Specialization("Stomatology"), new Specialization("Cardiology"))),
                new Hospital("Mountain View Clinic", 4, 6, Arrays.asList(new Specialization("Neurology"), new Specialization("Cardiology"))),
                new Hospital("Riverside Hospital", 3, 8, Arrays.asList(new Specialization("Orthopedics"))),
                new Hospital("Downtown Medical Center", 5, 10, Arrays.asList(new Specialization("Stomatology"), new Specialization("Dermatology"))),
                new Hospital("Seaside Health Clinic", 1, 7, Arrays.asList(new Specialization("Cardiology"))),
                new Hospital("Central City Hospital", 4, 6, Arrays.asList(new Specialization("Neurology"), new Specialization("Dermatology"))),
                new Hospital("Uptown Hospital", 2, 6, Arrays.asList(new Specialization("Stomatology"))),
                new Hospital("Harmony Health Center", 5, 10, Arrays.asList(new Specialization("Cardiology"), new Specialization("Neurology"), new Specialization("Orthopedics")))

        );
        List<Resident> residents = Arrays.asList(
                new Resident("John Smith", 8, Arrays.asList(new Specialization("Cardiology"), new Specialization("Neurology"))),
                new Resident("Jane Doe", 7, Arrays.asList(new Specialization("Dermatology"))),
                new Resident("Michael Johnson", 9, Arrays.asList(new Specialization("Orthopedics"), new Specialization("Stomatology"))),
                new Resident("Emily Davis", 6, Arrays.asList(new Specialization("Neurology"), new Specialization("Cardiology"))),
                new Resident("Daniel Brown", 8, Arrays.asList(new Specialization("Orthopedics"))),
                new Resident("Jessica Wilson", 10, Arrays.asList(new Specialization("Stomatology"), new Specialization("Dermatology"))),
                new Resident("Christopher Moore", 7, Arrays.asList(new Specialization("Cardiology"))),
                new Resident("Amanda Taylor", 6, Arrays.asList(new Specialization("Neurology"), new Specialization("Dermatology"))),
                new Resident("Joshua Anderson", 8, Arrays.asList(new Specialization("Orthopedics"), new Specialization("Cardiology"))),
                new Resident("Sarah Thomas", 9, Arrays.asList(new Specialization("Dermatology"), new Specialization("Stomatology"))),
                new Resident("Andrew Jackson", 5, Arrays.asList(new Specialization("Neurology"))),
                new Resident("Laura White", 7, Arrays.asList(new Specialization("Orthopedics"), new Specialization("Cardiology"))),
                new Resident("Matthew Harris", 8, Arrays.asList(new Specialization("Dermatology"), new Specialization("Neurology"))),
                new Resident("Samantha Martin", 6, Arrays.asList(new Specialization("Stomatology"))),
                new Resident("David Thompson", 10, Arrays.asList(new Specialization("Cardiology"), new Specialization("Neurology"))),
                new Resident("Olivia Garcia", 7, Arrays.asList(new Specialization("Orthopedics"))),
                new Resident("James Martinez", 9, Arrays.asList(new Specialization("Stomatology"), new Specialization("Dermatology"))),
                new Resident("Sophia Robinson", 8, Arrays.asList(new Specialization("Cardiology"))),
                new Resident("Benjamin Clark", 6, Arrays.asList(new Specialization("Neurology"), new Specialization("Dermatology"))),
                new Resident("Emma Rodriguez", 10, Arrays.asList(new Specialization("Orthopedics"), new Specialization("Cardiology")))

        );

        for(Resident resident : residents){
            ResidentDAO.getInstance().insert(resident);
            resident.setResident_id(ResidentDAO.getInstance().findByName(resident.getName()).getResident_id());
        }

        for(Hospital hospital: hospitals){
            HospitalDAO.getInstance().insert(hospital);
            hospital.setHospital_id(HospitalDAO.getInstance().findByName(hospital.getName()).getHospital_id());
        }

        HRInstance hrInstance = new HRInstance(hospitals, residents);
        MainFrame mainFrame = new MainFrame(hrInstance);
        MainFrame.center(mainFrame);
        mainFrame.setVisible(true);
////        for(Resident resident : residents)
////        {
////            System.out.println(resident.getName());
////            for(Hospital hospital : resident.getHospitalList())
////                System.out.println(hospital.getName() + " " + hospital.getGrade());
////            System.out.println();
////
////        }
//
//        hrInstance.printPairings();


    }
}