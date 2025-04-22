
import java.util.Objects;

public class Patient {

    private String id;
    private String name;
    private String address;
    private String phone;

    public Patient(String id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Patient patient = (Patient) o;
        return Objects.equals(id, patient.id)
                && Objects.equals(name, patient.name)
                && Objects.equals(address, patient.address)
                && Objects.equals(phone, patient.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phone);
    }
}
