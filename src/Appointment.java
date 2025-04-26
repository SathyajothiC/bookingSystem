
import java.util.Objects;

/**
 * Represents a booked appointment between a patient and physiotherapist Tracks
 * appointment status through its lifecycle (booked → attended/cancelled)
 */
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

    // STATUS CHECKERS ================================================
    public boolean isBooked() {
        return "booked".equalsIgnoreCase(status);
    }

    public boolean isCancelled() {
        return "cancelled".equalsIgnoreCase(status);
    }

    public boolean isAttended() {
        return "attended".equalsIgnoreCase(status);
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
