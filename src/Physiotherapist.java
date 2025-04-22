
import java.util.*;

public class Physiotherapist {

    private String id;
    private String name;
    private String address;
    private String phone;
    private Set<String> expertise;
    private List<AppointmentSlot> availableSlots;

    public Physiotherapist(String id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.expertise = new HashSet<>();
        this.availableSlots = new ArrayList<>();
    }

    public void addExpertise(String area) {
        expertise.add(area);
    }

    public void addAvailableSlot(AppointmentSlot slot) {
        availableSlots.add(slot);
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

    public Set<String> getExpertise() {
        return expertise;
    }

    public List<AppointmentSlot> getAvailableSlots() {
        return availableSlots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Physiotherapist that = (Physiotherapist) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(address, that.address)
                && Objects.equals(phone, that.phone)
                && Objects.equals(expertise, that.expertise)
                && Objects.equals(availableSlots, that.availableSlots);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, phone, expertise, availableSlots);
    }
}
