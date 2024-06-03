package main;

import classes.Hospital;
import classes.Matching;
import classes.Resident;
import classes.Specialization;
import dao.MatchingDAO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
public class HRInstance {
    private List<Hospital> hospitals;
    private List<Resident> residents;
    private List<Matching> pairings;


    public HRInstance(List<Hospital> hospitals, List<Resident> residents){

        this.hospitals = new ArrayList<>(hospitals);
        this.residents = new ArrayList<>(residents);
        pairings = new ArrayList<>();
        makePreferences();
        makePairings();
    }

    public void makePreferencesOfResident(Resident resident)
    {
        for(Hospital hospital : hospitals){
            for(Specialization specialization : resident.getSpecialization()){
                if(hospital.getSpecialization().contains(specialization) && !resident.getHospitalList().contains(hospital)){
                    resident.addHospital(hospital);
                }
            }
        }
        for(Hospital hospital: hospitals)
        {
            for(Specialization specialization : resident.getSpecialization())
            {
                if(hospital.getSpecialization().contains(specialization) && !hospital.getResidentList().contains(resident))
                {
                    hospital.getResidentList().add(resident);
                }
            }
        }
    }

    public void makePreferencesOfHospital(Hospital hospital) {
        for (Resident resident : residents) {
            for (Specialization specialization : hospital.getSpecialization()) {
                if (resident.getSpecialization().contains(specialization) && !hospital.getResidentList().contains(resident)) {
                    hospital.getResidentList().add(resident);
                }
            }
        }
        for (Resident resident : residents) {
            for (Specialization specialization : hospital.getSpecialization()) {
                if (resident.getSpecialization().contains(specialization) && !resident.getHospitalList().contains(hospital)) {
                    resident.getHospitalList().add(hospital);
                }
            }
        }

    }

    public void addResident(Resident resident)
    {
        residents.add(resident);
        makePreferencesOfResident(resident);
    }

    public void addHospital(Hospital hospital)
    {
        hospitals.add(hospital);
        makePreferencesOfHospital(hospital);
    }

    public void makePreferences(){
        for(Resident resident : residents){
            resident.setHospitalList(new PriorityQueue<>(Comparator.comparingInt(Hospital::getGrade).reversed()));
            for(Hospital hospital : hospitals){
                for(Specialization specialization : resident.getSpecialization()){
                    if(hospital.getSpecialization().contains(specialization) && !resident.getHospitalList().contains(hospital)){
                        resident.getHospitalList().add(hospital);
                    }
                }
            }
        }
        for(Hospital hospital: hospitals){
            hospital.setResidentList(new PriorityQueue<>(Comparator.comparingInt(Resident::getGrade).reversed()));
            for(Resident resident : residents){
                for(Specialization specialization : hospital.getSpecialization()){
                    if(resident.getSpecialization().contains(specialization) && !hospital.getResidentList().contains(resident)){
                        hospital.getResidentList().add(resident);
                    }
                }
            }
        }
    }

    private boolean areHospitalsFilled(){
        for(Hospital hospital : hospitals){
            if(hospital.getCapacity() > 0){
                return false;
            }
        }
        return true;
    }

    private Resident getUnassignedResident(){
        for(Resident resident : residents){
            if(!resident.isAssigned() && !resident.getHospitalList().isEmpty()){
                return resident;
            }
        }

        return null;
    }

    public List<Resident> getAssignedResidents(Hospital hospital){
        return pairings.stream()
                .filter(pair -> pair.getHospital().equals(hospital))
                .map(Matching::getResident)
                .toList();
    }


    public Resident getWorstResident(Hospital hospital){

        List<Resident> hospitalsResidents = getAssignedResidents(hospital);
        if(hospitalsResidents.isEmpty()){
            return null;
        }
        Resident resident = hospitalsResidents.getFirst();
        for(Resident resident1 : hospitalsResidents)
        {
            if(resident.compareTo(resident1) < 0){
                resident = resident1;
            }
        }
        return resident;
    }


    private void printResidents(){
        int nr = 0;
        for(Resident resident: residents)
            if(!resident.isAssigned()) {
                System.out.println(resident.getName());
                nr++;
            }
        System.out.println(nr + "\n");
    }

    public void makePairings(){
        for(Resident resident : residents)
            resident.setAssigned(false);
        List<Resident> residentsCopy = new ArrayList<>();
        for(Resident resident: residents)
        {
            Resident copy = new Resident(resident);
            residentsCopy.add(copy);
        }
        List<Hospital> hospitalsCopy = new ArrayList<>();
        for (Hospital hospital : hospitals) {
            Hospital copy = new Hospital(hospital);
            hospitalsCopy.add(copy);
        }
        Resident ri = getUnassignedResident();
        while (ri != null && !areHospitalsFilled()) /// while there are unassigned residents
        {
            PriorityQueue<Hospital> hospitalsCopy1 = new PriorityQueue<>(ri.getHospitalList()); /// create a copy of the resident's preference list
            Hospital hj = hospitalsCopy1.poll(); /// get the first hospital from the resident's preference list
            while(hj!=null && hj.getCapacity()==0)
            {
                System.out.println(ri.getName() + " cannot be assigned to " + hj + " hospital.");
                hj = hospitalsCopy1.poll();
                //ri = getUnassignedResident(); /// get the next unassigned resident
            }

            if(hj == null) //all the hospitals in ri's list are full
            {
                System.out.println(ri + " cannot be assigned to any hospital.");
                ri.setAssigned(true);
                ri = getUnassignedResident();
                continue;
            }
            //ri.removeHospital(hj); /// remove the hospital from the resident's preference list
            pairings.add(new Matching(hj,ri)); /// add the pair to the list of pairings
            ri.setAssigned(true); /// set the resident as assigned
            hj.decrementCapacity(); /// decrement the capacity of the hospital

            ri = getUnassignedResident();

        }

        printPairings();

        System.out.println("+++");
        for(Matching matching: pairings){
            System.out.println(matching.getHospital().getName() + " " + matching.getResident().getName());
            MatchingDAO.getInstance().insert(matching);
        }
        residents=residentsCopy;
        hospitals=hospitalsCopy;

        System.out.println("---");
    }

    public void printPairings(){
        for(Hospital hospital : hospitals)
        {
            System.out.println(hospital.getName() + " " + hospital.getGrade());
            for(Resident resident : getAssignedResidents(hospital))
            {
                System.out.println(resident.getName() + " " + resident.getGrade());
            }
            System.out.println();
        }
    }
}