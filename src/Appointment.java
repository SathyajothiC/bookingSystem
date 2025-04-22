
import java.util.Objects;

public class Appointment {

    private String bookingId;
    private Patient patient;
    private Physiotherapist physiotherapist;
    private AppointmentSlot slot;
    private String status; // "booked", "cancelled", "attended"

    public Appointment(String bookingId, Patient patient, Physiotherapist physiotherapist, AppointmentSlot slot) {
        this.bookingId = bookingId;
        this.patient = patient;
        this.physiotherapist = physiotherapist;
        this.slot = slot;
        this.status = "booked";
    }

    // Getters and setters
    public String getBookingId() {
        return bookingId;
    }

    public Patient getPatient() {
        return patient;
    }

    public Physiotherapist getPhysiotherapist() {
        return physiotherapist;
    }

    public AppointmentSlot getSlot() {
        return slot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSlot(AppointmentSlot slot) {
        this.slot = slot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Appointment that = (Appointment) o;
        return Objects.equals(bookingId, that.bookingId)
                && Objects.equals(patient, that.patient)
                && Objects.equals(physiotherapist, that.physiotherapist)
                && Objects.equals(slot, that.slot)
                && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, patient, physiotherapist, slot, status);
    }
}
