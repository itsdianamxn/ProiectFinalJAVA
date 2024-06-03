package classes;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Specialization {

    private int specialization_id = -1;
    private String name;

    public Specialization(int specialization_id, String name) {
        this.specialization_id = specialization_id;
        this.name = name;
    }
    public Specialization(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Specialization that = (Specialization) o;
        return specialization_id == that.specialization_id && name.equals(that.name);
    }

    @Override
    public String toString() {
        return  name;
    }

}

