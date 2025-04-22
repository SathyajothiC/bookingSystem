
import java.util.Objects;

public class Treatment {

    private String name;

    public Treatment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Treatment treatment = (Treatment) o;
        return Objects.equals(name, treatment.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
