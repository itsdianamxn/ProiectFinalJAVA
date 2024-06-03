package classes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Matching {
    private Resident resident;
    private Hospital hospital;

    public Matching(Hospital hospital, Resident resident){
        this.resident = resident;
        this.hospital = hospital;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matching matching = (Matching) o;
        return resident.equals(matching.resident) && hospital.equals(matching.hospital);
    }
}
