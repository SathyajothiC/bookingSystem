
import java.time.LocalDateTime;
import java.util.Objects;

public class AppointmentSlot {

    private LocalDateTime dateTime;
    private Treatment treatment;

    public AppointmentSlot(LocalDateTime dateTime, Treatment treatment) {
        this.dateTime = dateTime;
        this.treatment = treatment;
    }

    // Getters
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppointmentSlot that = (AppointmentSlot) o;
        return Objects.equals(dateTime, that.dateTime)
                && Objects.equals(treatment, that.treatment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateTime, treatment);
    }
}
