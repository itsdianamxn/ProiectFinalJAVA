package classes;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;


@Getter
@Setter
public class Hospital implements Comparable<Hospital> {
    private int hospital_id;
    private String name;
    private int capacity;
    private int grade;
    private List<Specialization> specialization = new ArrayList<>();
    private PriorityQueue<Resident> residentList;


    public Hospital(Hospital hospital)
    {
        this.hospital_id = hospital.hospital_id;
        this.name = hospital.name;
        this.capacity = hospital.capacity;
        this.grade = hospital.grade;
        this.specialization = hospital.specialization;
        residentList = new PriorityQueue<>(Comparator.comparingInt(Resident::getGrade).reversed());
        residentList.addAll(hospital.residentList);
    }

    public Hospital(int id, String name, int capacity, int grade){
        this.hospital_id = id;
        this.name = name;
        this.capacity = capacity;
        this.grade = grade;
        residentList = new PriorityQueue<>(Comparator.comparingInt(Resident::getGrade).reversed());
    }
    public Hospital(String name, int capacity, int grade, List<Specialization> specialization){
        this.name = name;
        this.capacity = capacity;
        this.grade = grade;
        this.specialization = specialization;
        residentList = new PriorityQueue<>(Comparator.comparingInt(Resident::getGrade).reversed());

    }
    public int getSpecializationIndex(Resident resident){
        for(int i = 0; i < specialization.size(); i++){
            if(resident.getSpecialization().contains(specialization.get(i))){
                return i;
            }
        }
        return -1;
    }

    public void removeResident(Resident resident){
        residentList.remove(resident);
    }

    public void decrementCapacity(){
        capacity--;
    }

    public void incrementCapacity(){capacity++;}

    @Override
    public int compareTo(Hospital o) {
        return this.grade - o.grade;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Hospital hospital = (Hospital) o;
        return hospital_id == hospital.hospital_id && name.equals(hospital.name);
    }

    @Override
    public String toString() {
        return "Hospital{" +
                "name='" + name + '\'' +
                ", capacity=" + capacity +
                ", grade=" + grade +
                ", specialization=" + specialization +
                '}';
    }

}


