
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        BookingSystem system = new BookingSystem();
        system.initializeSampleData();
        system.run();
    }
}

class BookingSystem {

    private List<Patient> patients;
    private List<Physiotherapist> physiotherapists;
    private List<Treatment> treatments;
    private List<Appointment> appointments;

    public BookingSystem() {
        patients = new ArrayList<>(); //lists to store patients
        physiotherapists = new ArrayList<>(); //list to store physiotherapists
        treatments = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    public void initializeSampleData() {
        // Initialize sample treatments
        treatments.add(new Treatment("Neural mobilisation"));
        treatments.add(new Treatment("Acupuncture"));
        treatments.add(new Treatment("Massage"));
        treatments.add(new Treatment("Mobilisation of the spine and joints"));
        treatments.add(new Treatment("Pool rehabilitation"));

        // Initialize sample physiotherapists with their expertise
        Physiotherapist p1 = new Physiotherapist(UUID.randomUUID().toString(), "Smith", "123 Main St", "555-1234");
        p1.addExpertise("Physiotherapy");
        p1.addExpertise("Rehabilitation");

        Physiotherapist p2 = new Physiotherapist(UUID.randomUUID().toString(), "John", "456 Oak Ave", "555-5678");
        p2.addExpertise("Osteopathy");
        p2.addExpertise("Massage");

        Physiotherapist p3 = new Physiotherapist(UUID.randomUUID().toString(), "Will", "789 Pine Rd", "555-9012");
        p3.addExpertise("Rehabilitation");
        p3.addExpertise("Pool rehabilitation");

        physiotherapists.add(p1);
        physiotherapists.add(p2);
        physiotherapists.add(p3);

        // Create sample schedule for 4 weeks
        LocalDateTime baseDate = LocalDateTime.now().withHour(9).withMinute(0);
        // In initializeSampleData()
        for (int week = 0; week < 4; week++) {
            for (Physiotherapist physio : physiotherapists) {
                int[] daysOfWeek = {0, 2, 4}; // Monday, Wednesday, Friday
                for (int day : daysOfWeek) {
                    LocalDateTime date = baseDate.plusWeeks(week).plusDays(day);
                    for (int slot = 0; slot < 2; slot++) { // 2 slots per day
                        LocalDateTime slotTime = date.plusHours(slot * 2); // 2-hour slots

                        // Assign treatments based on expertise
                        if (physio.getName().equals("Smith")) {
                            Treatment treatment = treatments.get(0); // Neural mobilisation
                            physio.addAvailableSlot(new AppointmentSlot(slotTime, treatment));
                        } else if (physio.getName().equals("Will")) {
                            Treatment treatment = treatments.get(4); // Pool rehabilitation
                            physio.addAvailableSlot(new AppointmentSlot(slotTime, treatment));
                        } else if (physio.getName().equals("John")) {
                            Treatment treatment = treatments.get(1); // Acupuncture
                            physio.addAvailableSlot(new AppointmentSlot(slotTime, treatment));
                        }
                    }
                }
            }
        }

        // Initialize sample patients
        addPatient(new Patient(UUID.randomUUID().toString(), "Sathya", "101 Elm St", "555-1111"));
        addPatient(new Patient(UUID.randomUUID().toString(), "Abi", "202 Maple Dr", "555-2222"));
        addPatient(new Patient(UUID.randomUUID().toString(), "Ram", "303 Cedar Ln", "555-3333"));
    }

    // Program starts exection here
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n---------------- Welcome to Boost Physio Clinic Booking System ----------------");
            System.out.println("\nBoost Physio Clinic Booking System\n");
            System.out.println("1. Add/Remove Patient");
            System.out.println("2. Book Treatment Appointment");
            System.out.println("3. Change/Cancel Booking");
            System.out.println("4. Attend Appointment");
            System.out.println("5. Print Report");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

            String input = scanner.nextLine().trim(); // Read entire line and trim whitespace

            if (input.isEmpty()) {
                System.out.println("No input detected. Please try again.");
                continue; // Skip rest of loop and show menu again
            }

            try {
                int choice = Integer.parseInt(input); // Try converting to integer

                switch (choice) {
                    case 1:
                        managePatients(scanner);
                        break;
                    case 2:
                        bookAppointment(scanner);
                        break;
                    case 3:
                        changeOrCancelBooking(scanner);
                        break;
                    case 4:
                        attendAppointment(scanner);
                        break;
                    case 5:
                        printReport();
                        break;
                    case 6:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number (1-6).");
                }
            } catch (NumberFormatException e) {
                System.out.println("Option must be between 1 and 6");
            }
        }
        System.out.println("Thank you for using Boost Physio Clinic!");
        scanner.close();
    }

    //Manage Patient [ Options : Add, Remove, Back]
    private void managePatients(Scanner scanner) {
        System.out.println("\n══════ Patient Management ══════\n");
        System.out.println("1. Add Patient");
        System.out.println("2. Remove Patient");
        System.out.println("3. Back to Main Menu");
        System.out.print("\nSelect an option: ");

        String input = scanner.nextLine().trim();  // Read entire line

        // Handle empty input
        if (input.isEmpty()) {
            System.out.println("No selection made. Returning to main menu...");
            return;
        }

        try {
            int choice = Integer.parseInt(input);

            switch (choice) {
                case 1:
                    addPatient(scanner);
                    break;
                case 2:
                    removePatient(scanner);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid option. Please enter 1, 2, or 3.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Please enter a number (1-3).");
        }
    }

    private void addPatient(Scanner scanner) {
        System.out.println("\nAdd New Patient");

        // Get patient name with validation
        String name;
        while (true) {
            System.out.print("Enter full name: ");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Cancelled. Returning to main menu...");
                return;
            }

            // Check if patient already exists
            if (getPatientByName(name) != null) {
                System.out.println("Patient with this name already exists. Please enter a different name.");
            } else {
                break;  // Exit loop if name is unique
            }
        }

        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        if (address.trim().isEmpty()) {
            System.out.println("Cancelled. Returning to main menu...");
            return;
        }

        System.out.print("Enter telephone number: ");
        String phone = scanner.nextLine();
        if (phone.trim().isEmpty()) {
            System.out.println("Cancelled. Returning to main menu...");
            return;
        }

        Patient patient = new Patient(UUID.randomUUID().toString(), name, address, phone);
        addPatient(patient);
        System.out.println("Patient added successfully.");
    }

    //get patient name from the patients to remove patient
    private void removePatient(Scanner scanner) {
        System.out.println("\nRemove Patient");
        System.out.print("Enter patient name to remove: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
            System.out.println("Operation cancelled. Returning to main menu...");
            return;
        }

        Patient patient = getPatientByName(name);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        // Check if patient has any appointments
        boolean hasAppointments = appointments.stream()
                .anyMatch(a -> a.getPatient().equals(patient) && !a.getStatus().equals("cancelled"));

        if (hasAppointments) {
            System.out.println("Cannot remove patient with active appointments.");
            return;
        }

        patients.remove(patient);
        System.out.println("Patient removed successfully.");
    }

    //Add patient details to the list
    private void addPatient(Patient patient) {
        patients.add(patient);
    }

    //book appointmentsfor the patients
    private void bookAppointment(Scanner scanner) {
        System.out.println("\nBook Treatment Appointment");
        System.out.print("Enter patient Name: ");
        String patientName = scanner.nextLine().trim();
        if (patientName.isEmpty()) {
            System.out.println("Operation cancelled. Returning to main menu...");
            return;
        }
        Patient patient = getPatientByName(patientName);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        System.out.println("Search by:\n");
        System.out.println("1. Area of expertise");
        System.out.println("2. Physiotherapist name");
        System.out.print("Select an option: \n");

        String input = scanner.nextLine().trim();

        // Return to main menu if not a number
        if (!input.matches("\\d+")) {
            System.out.println("Invalid selection. Returning to main menu...");
            return;
        }

        int searchOption = Integer.parseInt(input);

        // Return to main menu if not 1 or 2
        if (searchOption != 1 && searchOption != 2) {
            System.out.println("Invalid option. Returning to main menu...");
            return;
        }

        List<AppointmentSlot> availableSlots = new ArrayList<>();

        if (searchOption == 1) {

            System.out.println("\nAvailable Expertise Areas:");

// Get all unique expertise areas sorted alphabetically
            Set<String> allExpertise = physiotherapists.stream()
                    .flatMap(p -> p.getExpertise().stream())
                    .collect(Collectors.toCollection(TreeSet::new));

// Display each expertise with specialists
            for (String expertise : allExpertise) {
                // Get all specialists for this expertise
                List<String> specialists = physiotherapists.stream()
                        .filter(p -> p.getExpertise().contains(expertise))
                        .map(p -> "Dr " + p.getName())
                        .collect(Collectors.toList());

                // Format the output
                String specialistsList = String.join(", ", specialists);
                System.out.printf("- %s (%s)%n", expertise, specialistsList);
            }

            System.out.print("\nEnter area of expertise: ");
            String expertise = scanner.nextLine();
            if (expertise.trim().isEmpty()) {
                System.out.println("Operation cancelled. Returning to main menu...");
                return;
            }

            availableSlots = physiotherapists.stream()
                    .filter(p -> p.getExpertise().stream()
                    .anyMatch(e -> e.equalsIgnoreCase(expertise.trim())))
                    .flatMap(p -> p.getAvailableSlots().stream())
                    .filter(slot -> !isSlotBooked(slot))
                    .distinct() // Remove duplicates
                    .sorted(Comparator.comparing(AppointmentSlot::getDateTime))
                    .collect(Collectors.toList());
        } else if (searchOption == 2) {

            System.out.println("\nAvailable Physiotherapists:");
            physiotherapists.stream()
                    .sorted(Comparator.comparing(Physiotherapist::getName))
                    .forEach(p -> System.out.printf("- %s (Expertise: %s)%n",
                    p.getName(),
                    String.join(", ", p.getExpertise())));

            System.out.print("\nEnter physiotherapist name: ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) {
                System.out.println("Operation cancelled. Returning to main menu...");
                return;
            }

            availableSlots = physiotherapists.stream()
                    .filter(p -> p.getName().equalsIgnoreCase(name))
                    .flatMap(p -> p.getAvailableSlots().stream())
                    .filter(slot -> !isSlotBooked(slot))
                    .collect(Collectors.toList());
        } else {
            System.out.println("Invalid option.");
            return;
        }

        if (availableSlots.isEmpty()) {
            System.out.println("No available appointments found.");
            return;
        }

        System.out.println("\nAvailable Appointments:");
        System.out.println("+-----+----------------------------+----------------------+--------------------------------+");
        System.out.printf("| %-3s | %-26s | %-20s | %-30s |%n", "No.", "Date/Time", "Treatment", "Physiotherapist (Expertise)");
        System.out.println("+-----+----------------------------+----------------------+--------------------------------+");

        for (int i = 0; i < availableSlots.size(); i++) {
            AppointmentSlot slot = availableSlots.get(i);
            Physiotherapist physio = getPhysioBySlot(slot);

            String treatment = slot.getTreatment().getName();
            if (treatment.length() > 20) {
                treatment = treatment.substring(0, 17) + "...";
            }

            String expertise = String.join(", ", physio.getExpertise());
            if (expertise.length() > 30) {
                expertise = expertise.substring(0, 27) + "...";
            }

            System.out.printf("| %-3d | %-26s | %-20s | %-30s |%n",
                    i + 1,
                    slot.getDateTime().format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy h:mm a")),
                    treatment,
                    physio.getName() + " (" + expertise + ")");
        }

        System.out.println("+-----+----------------------------+----------------------+--------------------------------+");
        System.out.print("Select an appointment to book (0 to cancel): ");

        int selection;
        try {
            selection = Integer.parseInt(scanner.nextLine());

            if (selection <= 0 || selection > availableSlots.size()) {
                System.out.println("Booking cancelled.");
                return;
            }

            AppointmentSlot selectedSlot = availableSlots.get(selection - 1);
            Physiotherapist physio = getPhysioBySlot(selectedSlot);

            // Check for time conflicts
            boolean hasConflict = appointments.stream()
                    .anyMatch(a -> a.getPatient().equals(patient)
                    && !a.getStatus().equals("cancelled")
                    && a.getSlot().getDateTime().equals(selectedSlot.getDateTime()));

            if (hasConflict) {
                System.out.println("You already have an appointment at this time.");
                return;
            }

            // Create and add the appointment
            Appointment appointment = new Appointment(UUID.randomUUID().toString().substring(0, 8),
                    patient, physio, selectedSlot);
            appointments.add(appointment);
            System.out.println("Appointment booked successfully! Booking ID: " + appointment.getBookingId());

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }
    }

    private void changeOrCancelBooking(Scanner scanner) {
        System.out.println("\nChange/Cancel Booking");
        System.out.print("Enter booking ID: ");
        String bookingId = scanner.nextLine();
        if (bookingId.trim().isEmpty()) {
            System.out.println("Operation cancelled. Returning to main menu...");
            return;
        }

        Optional<Appointment> appointmentOpt = appointments.stream()
                .filter(a -> a.getBookingId().equals(bookingId))
                .findFirst();

        if (!appointmentOpt.isPresent()) {
            System.out.println("Booking not found.");
            return;
        }

        Appointment appointment = appointmentOpt.get();

        System.out.println("\nCurrent Appointment Details:\n");
        System.out.println("* Patient         : " + appointment.getPatient().getName());
        System.out.println("* Physiotherapist : " + appointment.getPhysiotherapist().getName());
        System.out.println("* Treatment       : " + appointment.getSlot().getTreatment().getName());
        System.out.println("* Date/Time       : "
                + appointment.getSlot().getDateTime().format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy h:mm a")));
        System.out.println("* Status          : " + appointment.getStatus());

        System.out.println("\n1. Change booking");
        System.out.println("2. Cancel booking");
        System.out.print("Select an option: ");

        int option = scanner.nextInt();
        scanner.nextLine();

        if (option == 1) {
            // Change booking - show available slots from the same physiotherapist
            Physiotherapist physio = appointment.getPhysiotherapist();
            List<AppointmentSlot> availableSlots = physio.getAvailableSlots().stream()
                    .filter(slot -> !isSlotBooked(slot) || slot.equals(appointment.getSlot()))
                    .filter(slot -> slot.getDateTime().isAfter(LocalDateTime.now()))
                    .sorted(Comparator.comparing(AppointmentSlot::getDateTime))
                    .collect(Collectors.toList());

            if (availableSlots.isEmpty()) {
                System.out.println("No available slots found for this physiotherapist.");
                return;
            }

            System.out.println("\nAvailable Slots with " + physio.getName() + ":");
            for (int i = 0; i < availableSlots.size(); i++) {
                AppointmentSlot slot = availableSlots.get(i);
                System.out.printf("%d. %s - %s\n",
                        i + 1,
                        slot.getDateTime().format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy h:mm a")),
                        slot.getTreatment().getName());
            }

            System.out.print("\nSelect a new slot (0 to cancel): ");

            int selection = -1;
            boolean validInput = false;

            while (!validInput) {
                String input = scanner.nextLine().trim();

                try {
                    selection = Integer.parseInt(input);

                    if (selection == 0) {
                        System.out.println("\nChange cancelled.");
                        return;
                    }

                    if (selection < 0 || selection > availableSlots.size()) {
                        System.out.println("Invalid selection. Please enter a number between 1 and "
                                + availableSlots.size() + " (or 0 to cancel): ");
                        continue;
                    }

                    validInput = true;

                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and "
                            + availableSlots.size() + " (or 0 to cancel): ");
                }
            }

            AppointmentSlot newSlot = availableSlots.get(selection - 1);

            // Check if the new slot is the same as current one
            if (newSlot.equals(appointment.getSlot())) {
                System.out.println("\nThis is the same as current appointment. No changes made.");
                return;
            }

            // Check for time conflicts with patient's other appointments
            boolean hasConflict = appointments.stream()
                    .anyMatch(a -> a.getPatient().equals(appointment.getPatient())
                    && !a.getStatus().equals("cancelled")
                    && a.getSlot().getDateTime().equals(newSlot.getDateTime())
                    && !a.getBookingId().equals(appointment.getBookingId()));

            if (hasConflict) {
                System.out.println("\nYou already have another appointment at this time.");
                return;
            }

            // Update the appointment with new slot
            appointment.setSlot(newSlot);
            System.out.println("\nAppointment changed successfully!\n");
            System.out.println("New appointment details:");
            System.out.println("  - Treatment: " + newSlot.getTreatment().getName());
            System.out.println("  - Date/Time: "
                    + newSlot.getDateTime().format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy h:mm a")));
        } else if (option == 2) {
            // Cancel booking
            appointment.setStatus("cancelled");
            System.out.println("Booking cancelled successfully.");
        } else {
            System.out.println("Invalid option.");
        }
    }

    private void attendAppointment(Scanner scanner) {
        System.out.println("\nAttend Appointment");
        System.out.print("Enter booking ID: ");
        String bookingId = scanner.nextLine();
        if (bookingId.trim().isEmpty()) {
            System.out.println("Operation cancelled. Returning to main menu...");
            return;
        }

        Optional<Appointment> appointmentOpt = appointments.stream()
                .filter(a -> a.getBookingId().equals(String.valueOf(bookingId)))
                .findFirst();

        if (!appointmentOpt.isPresent()) {
            System.out.println("Booking not found.");
            return;
        }

        Appointment appointment = appointmentOpt.get();

        if (appointment.getStatus().equals("cancelled")) {
            System.out.println("This appointment has been cancelled.");
            return;
        }

        if (appointment.getStatus().equals("attended")) {
            System.out.println("This appointment has already been attended.");
            return;
        }

        appointment.setStatus("attended");
        System.out.println("Appointment marked as attended. Thank you!");
    }

    private void printReport() {
        // Header
        System.out.println("\n\n  BOOST PHYSIO CLINIC - TREATMENT REPORT (4 WEEKS)");
        System.out.println("  ----------------------------------------------\n");

        // Appointments by physiotherapist
        physiotherapists.forEach(physio -> {
            System.out.println("  PHYSIOTHERAPIST: " + physio.getName());
            System.out.println("  EXPERTISE: " + String.join(", ", physio.getExpertise()));
            System.out.println("  ----------------------------------------------\n");

            List<Appointment> physioAppointments = appointments.stream()
                    .filter(a -> a.getPhysiotherapist().equals(physio))
                    .sorted(Comparator.comparing(a -> a.getSlot().getDateTime()))
                    .collect(Collectors.toList());

            if (physioAppointments.isEmpty()) {
                System.out.println("  No appointments scheduled\n");
            } else {
                // Header row
                System.out.printf("  %-12s %-25s %-15s %-25s %-10s%n",
                        "BOOKING ID", "TREATMENT", "PATIENT", "DATE/TIME", "STATUS");
                System.out.println("  ---------------------------------------------------------------------------");

                // Appointment rows
                physioAppointments.forEach(a -> {
                    System.out.printf("  %-12s %-25s %-15s %-25s %-10s%n",
                            a.getBookingId(),
                            truncate(a.getSlot().getTreatment().getName(), 25),
                            truncate(a.getPatient().getName(), 15),
                            a.getSlot().getDateTime().format(DateTimeFormatter.ofPattern("EEE, MMM d, h:mm a")),
                            a.getStatus());
                });
                System.out.println();  // Extra space between physiotherapists
            }
        });

        // Attendance summary
        System.out.println("  ATTENDANCE SUMMARY");
        System.out.println("  ----------------------------------------------");
        System.out.printf("  %-20s %s%n", "PHYSIOTHERAPIST", "ATTENDED APPOINTMENTS");
        System.out.println("  ----------------------------------------------");

        physiotherapists.stream()
                .sorted((p1, p2) -> Long.compare(countAttendedAppointments(p2), countAttendedAppointments(p1)))
                .forEach(p -> {
                    System.out.printf("  %-20s %d%n", p.getName(), countAttendedAppointments(p));
                });

        System.out.println("\n  END OF REPORT\n");
    }

    private String truncate(String text, int maxLength) {
        return text.length() <= maxLength ? text : text.substring(0, maxLength - 3) + "...";
    }

    private long countAttendedAppointments(Physiotherapist physio) {
        return appointments.stream()
                .filter(a -> a.getPhysiotherapist().equals(physio))
                .filter(a -> a.getStatus().equals("attended"))
                .count();
    }

    private boolean isSlotBooked(AppointmentSlot slot) {
        return appointments.stream()
                .anyMatch(a -> a.getSlot().equals(slot) && !a.getStatus().equals("cancelled"));
    }

    private Physiotherapist getPhysioBySlot(AppointmentSlot slot) {
        return physiotherapists.stream()
                .filter(p -> p.getAvailableSlots().contains(slot))
                .findFirst()
                .orElse(null);
    }

    //get patient name from the patient list
    private Patient getPatientByName(String name) {
        return patients.stream()
                .filter(p -> name.trim().toLowerCase().equals(p.getName().trim().toLowerCase()))
                .findFirst()
                .orElse(null);
    }
}

class Patient {

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
}

class Physiotherapist {

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
}

class Treatment {

    private String name;

    public Treatment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class AppointmentSlot {

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

class Appointment {

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

    // Add this setter method
    public void setSlot(AppointmentSlot slot) {
        this.slot = slot;
    }
}
