
import java.util.Objects;

// Represents a patient in the clinic system
public class Patient {

    private String id;
    private String name;
    private String address;
    private String phone;

    // Constructor to create new Patient
    public Patient(String id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    // Getters
    // Returns patient's ID
    public String getId() {
        return id;
    }

    // Returns patient's name
    public String getName() {
        return name;
    }

    // Returns patient's address
    public String getAddress() {
        return address;
    }

    // Returns patient's phone number
    public String getPhone() {
        return phone;
    }

    // Sets new ID for patient
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
