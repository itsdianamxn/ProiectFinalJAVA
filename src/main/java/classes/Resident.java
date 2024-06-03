package classes;
import dao.ResidentDAO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Getter
@Setter
public class Resident implements Comparable<Resident> {
    private int resident_id;
    private String name;
    private boolean assigned = false;
    private int grade;
    private List<Specialization> specialization = new ArrayList<>();
    private PriorityQueue<Hospital> hospitalList;

    public Resident(int id, String name, boolean assigned, int grade){
        this.resident_id = id;
        this.name = name;
        this.grade = grade;
        this.assigned = assigned;
        hospitalList = new PriorityQueue<>(Comparator.comparingInt(Hospital::getGrade).reversed());
    }

    public Resident(String name, int grade, List<Specialization> specialization){
        this.resident_id = ResidentDAO.getInstance().getMaxId() + 1;
        this.name = name;
        this.grade = grade;
        this.specialization = specialization;
        hospitalList = new PriorityQueue<>(Comparator.comparingInt(Hospital::getGrade).reversed());
    }

    public Resident(Resident resident) {
        this.resident_id = resident.resident_id;
        this.name = resident.name;
        this.grade = resident.grade;
        this.assigned = resident.assigned;
        this.specialization = resident.specialization;
        hospitalList = new PriorityQueue<>(Comparator.comparingInt(Hospital::getGrade).reversed());
        hospitalList.addAll(resident.hospitalList);
    }

    public void addHospital(Hospital hospital){
        hospitalList.add(hospital);
    }

    public int getSpecializationIndex(Hospital hospital){
        for(int i = 0; i < specialization.size(); i++){
            if(hospital.getSpecialization().contains(specialization.get(i))){
                return i;
            }
        }
        return -1;
    }



    public void removeHospital(Hospital hospital){
        hospitalList.remove(hospital);
    }

    public boolean isAssigned(){
        return assigned;
    }


    @Override
    public int compareTo(Resident o) {
        int gradeComparison = Integer.compare(this.grade, o.grade);
        if (gradeComparison == 0) {
            return this.name.compareTo(o.name);
        } else {
            return gradeComparison;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resident resident = (Resident) o;
        return resident_id == resident.resident_id && name.equals(resident.name);
    }

    @Override
    public String toString() {
        return "Resident{" +
                "resident_id=" + resident_id +
                ", name='" + name + '\'' +
                ", grade=" + grade +
                '}';
    }
}