import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

class BookingSystemTest {
    private BookingSystem bookingSystem;
    private Physiotherapist physio1, physio2;
    private Patient patient1, patient2;
    private Treatment treatment1, treatment2;
    private AppointmentSlot slot1, slot2, conflictingSlot;

    @BeforeEach
    void setUp() {
        bookingSystem = new BookingSystem();
        bookingSystem.initializeSampleData();

        // Get sample data
        List<Physiotherapist> physios = bookingSystem.getAllPhysiotherapists();
        physio1 = physios.get(0);
        physio2 = physios.get(1);

        List<Patient> patients = bookingSystem.getAllPatients();
        patient1 = patients.get(0);
        patient2 = patients.get(1);

        List<Treatment> treatments = bookingSystem.getAllTreatments();
        treatment1 = treatments.get(0);
        treatment2 = treatments.get(1);

        LocalDateTime now = LocalDateTime.now();
        slot1 = new AppointmentSlot(now.plusDays(1).withHour(10).withMinute(0), treatment1);
        slot2 = new AppointmentSlot(now.plusDays(2).withHour(14).withMinute(0), treatment2);
        conflictingSlot = new AppointmentSlot(slot1.getDateTime(), treatment2);
    }

    // =============== BookingSystem Tests ===============
    @Test
    void testIsSlotBooked_WhenNotBooked_ReturnsFalse() {
        assertFalse(bookingSystem.isSlotTaken(slot1));
    }

    @Test
    void testGetPatientByName_WhenExists_ReturnsPatient() {
        Patient found = bookingSystem.fetchPatientsByName("Steev");
        assertNotNull(found);
        assertEquals("Steev", found.getName());
        assertEquals("101 Elm St", found.getAddress());
    }

    @Test
    void testGetPatientByName_WhenNotExists_ReturnsNull() {
        assertNull(bookingSystem.fetchPatientsByName("Nonexistent Patient"));
    }

    @Test
    void testGetPatientByName_IsCaseInsensitive() {
        assertNotNull(bookingSystem.fetchPatientsByName("sTeeV"));
    }

    // =============== Appointment Tests ===============
    @Test
    void testAppointmentStatus_InitialState_IsBooked() {
        Appointment appointment = new Appointment("A1", patient1, physio1, slot1);
        assertEquals("booked", appointment.getStatus());
        assertTrue(appointment.isBooked());
    }

    @Test
    void testAppointmentStatus_CanChangeToAttended() {
        Appointment appointment = new Appointment("A1", patient1, physio1, slot1);
        appointment.setStatus("attended");
        assertTrue(appointment.isAttended());
        assertFalse(appointment.isBooked());
    }

    // =============== Physiotherapist Tests ===============
    @Test
    void testPhysioAddExpertise_NewExpertise_AddedSuccessfully() {
        String newExpertise = "Sports Therapy";
        physio1.addExpertise(newExpertise);
        assertTrue(physio1.getExpertise().contains(newExpertise));
    }

    @Test
    void testPhysioAddExpertise_Duplicate_NoChange() {
        int initialSize = physio1.getExpertise().size();
        physio1.addExpertise("Physiotherapy"); // Already exists
        assertEquals(initialSize, physio1.getExpertise().size());
    }

    @Test
    void testPhysioAddAvailableSlot_IncreasesSlotCount() {
        int initialSize = physio1.getAvailableSlots().size();
        physio1.addAvailableSlot(slot1);
        assertEquals(initialSize + 1, physio1.getAvailableSlots().size());
    }

    @Test
    void testPhysioRemoveAvailableSlot_ExistingSlot_ReturnsTrue() {
        physio1.addAvailableSlot(slot1);
        assertTrue(physio1.removeAvailableSlot(slot1));
        assertFalse(physio1.getAvailableSlots().contains(slot1));
    }

    // =============== Patient Tests ===============

    @Test
    void testPatientEquality_DifferentId_ReturnsFalse() {
        Patient p1 = new Patient("1", "John", "Addr", "123");
        Patient p2 = new Patient("2", "John", "Addr", "123");
        assertNotEquals(p1, p2);
    }

    // =============== Treatment Tests ===============
    @Test
    void testTreatmentEquality_SameName_ReturnsTrue() {
        Treatment t1 = new Treatment("Massage");
        Treatment t2 = new Treatment("Massage");
        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void testTreatmentEquality_DifferentName_ReturnsFalse() {
        Treatment t1 = new Treatment("Massage");
        Treatment t2 = new Treatment("Acupuncture");
        assertNotEquals(t1, t2);
    }

    // =============== AppointmentSlot Tests ===============
    @Test
    void testAppointmentSlotEquality_SameDateTimeAndTreatment_ReturnsTrue() {
        LocalDateTime now = LocalDateTime.now();
        AppointmentSlot slot1 = new AppointmentSlot(now, treatment1);
        AppointmentSlot slot2 = new AppointmentSlot(now, treatment1);
        assertEquals(slot1, slot2);
        assertEquals(slot1.hashCode(), slot2.hashCode());
    }

    @Test
    void testAppointmentSlotEquality_DifferentTreatment_ReturnsFalse() {
        LocalDateTime now = LocalDateTime.now();
        AppointmentSlot slot1 = new AppointmentSlot(now, treatment1);
        AppointmentSlot slot2 = new AppointmentSlot(now, treatment2);
        assertNotEquals(slot1, slot2);
    }
}