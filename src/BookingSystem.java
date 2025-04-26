
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class BookingSystem {

    private List<Patient> patients; //List of all registered patients
    private List<Physiotherapist> physiotherapists; // List of available physiotherapists
    private List<Treatment> treatments;  // List of offered treatments
    private List<Appointment> appointments; // List of all booked appointments

    public BookingSystem() {
        // Constructor initializes empty lists
        patients = new ArrayList<>();
        physiotherapists = new ArrayList<>();
        treatments = new ArrayList<>();
        appointments = new ArrayList<>();
    }

    // Initialize sample data for treatments and physiotherapists
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

        // Create sample schedule for 4 weeks (Monday, Wednesday, Friday)
        LocalDateTime baseDate = LocalDateTime.now().withHour(9).withMinute(0);
        for (int week = 0; week < 4; week++) {
            for (Physiotherapist physio : physiotherapists) {
                int[] daysOfWeek = {0, 2, 4}; // Monday, Wednesday, Friday
                for (int day : daysOfWeek) {
                    LocalDateTime date = baseDate.plusWeeks(week).plusDays(day);
                    for (int slot = 0; slot < 2; slot++) { // 2 slots per day
                        LocalDateTime slotTime = date.plusHours(slot * 2); // 2-hour slots

                        // Assign treatments based on physiotherapist's name
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
        Patient patient1 = new Patient(UUID.randomUUID().toString(), "Steev", "101 Elm St", "555-1111");
        Patient patient2 = new Patient(UUID.randomUUID().toString(), "Max", "202 Maple Dr", "555-2222");
        Patient patient3 = new Patient(UUID.randomUUID().toString(), "Ray", "303 Cedar Ln", "555-3333");
        Patient patient4 = new Patient(UUID.randomUUID().toString(), "Sam", "404 Birch Ct", "555-4444");
        Patient patient5 = new Patient(UUID.randomUUID().toString(), "dev", "505 Walnut St", "555-5555");
        Patient patient6 = new Patient(UUID.randomUUID().toString(), "rachel", "606 Spruce Ave", "555-6666");
        Patient patient7 = new Patient(UUID.randomUUID().toString(), "fred", "707 Fir Rd", "555-7777");
        Patient patient8 = new Patient(UUID.randomUUID().toString(), "james", "808 Cherry Blvd", "555-8888");
        Patient patient9 = new Patient(UUID.randomUUID().toString(), "jake", "909 Ash St", "555-9999");
        Patient patient10 = new Patient(UUID.randomUUID().toString(), "jane", "1010 Willow Ct", "555-0000");
        Patient patient11 = new Patient(UUID.randomUUID().toString(), "john", "1212 Oak St", "555-2323");

        addPatient(patient1);
        addPatient(patient2);
        addPatient(patient3);
        addPatient(patient4);
        addPatient(patient5);
        addPatient(patient6);
        addPatient(patient7);
        addPatient(patient8);
        addPatient(patient9);
        addPatient(patient10);
        addPatient(patient11);

        // Create time slots for different days
        LocalDateTime[] timeSlots = {
            baseDate.with(DayOfWeek.MONDAY).withHour(9).withMinute(0),
            baseDate.with(DayOfWeek.MONDAY).withHour(11).withMinute(0),
            baseDate.with(DayOfWeek.WEDNESDAY).withHour(9).withMinute(0),
            baseDate.with(DayOfWeek.WEDNESDAY).withHour(11).withMinute(0),
            baseDate.with(DayOfWeek.FRIDAY).withHour(9).withMinute(0),
            baseDate.with(DayOfWeek.FRIDAY).withHour(11).withMinute(0),
            baseDate.plusWeeks(1).with(DayOfWeek.MONDAY).withHour(9).withMinute(0),
            baseDate.plusWeeks(1).with(DayOfWeek.WEDNESDAY).withHour(11).withMinute(0),
            baseDate.plusWeeks(2).with(DayOfWeek.FRIDAY).withHour(14).withMinute(0)
        };

        // Create and book appointments
        createBooking("APT001", patient1, p1, timeSlots[0], treatments.get(0), "booked");
        createBooking("APT002", patient2, p2, timeSlots[1], treatments.get(1), "booked");
        createBooking("APT003", patient3, p3, timeSlots[2], treatments.get(4), "booked");
        createBooking("APT004", patient4, p1, timeSlots[3], treatments.get(0), "attended");
        createBooking("APT005", patient5, p2, timeSlots[4], treatments.get(1), "cancelled");
        createBooking("APT006", patient6, p3, timeSlots[5], treatments.get(4), "booked");
        createBooking("APT007", patient7, p1, timeSlots[6], treatments.get(0), "booked");
        createBooking("APT008", patient8, p2, timeSlots[7], treatments.get(1), "booked");
        createBooking("APT009", patient9, p3, timeSlots[8], treatments.get(4), "booked");

        // Add some overlapping appointments for testing conflict detection
        createBooking("APT010", patient10, p1, timeSlots[0], treatments.get(0), "attended");
        createBooking("APT011", patient11, p2, timeSlots[1], treatments.get(1), "cancelled");
    }

// Helper method to create bookings and manage slots
    private void createBooking(String id, Patient patient, Physiotherapist physio,
            LocalDateTime dateTime, Treatment treatment, String status) {
        AppointmentSlot slot = new AppointmentSlot(dateTime, treatment);

        if (!status.equals("cancelled")) {
            physio.removeAvailableSlot(slot);
        }

        Appointment appointment = new Appointment(id, patient, physio, slot);
        appointment.setStatus(status);
        appointments.add(appointment);

        if (status.equals("cancelled")) {
            physio.addAvailableSlot(slot);
        }
    }

    // Main program loop that displays the menu and handles user input
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            // Display main menu
            System.out.println("\n----- BOOST PHYSIO CLINIC - MAIN MENU -----\n");
            System.out.println("1. Patient Management - Add/Remove Patient");
            System.out.println("2. Book New Treatment");
            System.out.println("3. Change/Cancel Booking");
            System.out.println("4. Mark Appointment Completed");
            System.out.println("5. View Reports");
            System.out.println("6. Exit");
            System.out.print("\nPlease enter your selection (1-6): ");

            String input = scanner.nextLine().trim(); // Read entire line and trim whitespace

            if (input.isEmpty()) {
                System.out.println("No selection made. Please choose an option from the menu.");
                continue; // Skip rest of loop and show menu again
            }

            try {
                int choice = Integer.parseInt(input); // Try converting to integer

                // Handle menu selection
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
                        System.out.println("Thank you for using Boost Physio Clinic Management System!");
                        break;
                    default:
                        System.out.println("Invalid selection. Please enter a number between 1 and 6.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter only the menu number (1-6).");
            }
        }
        scanner.close();
    }

    //Manages patient operations (add/remove)
    private void managePatients(Scanner scanner) {
        System.out.println("\n----- PATIENT MANAGEMENT -----\n");
        System.out.println("1. Register New Patient");
        System.out.println("2. Remove Existing Patient");
        System.out.println("3. Back to Main Menu");
        System.out.print("\nEnter your selection (1-3): ");

        String input = scanner.nextLine().trim();   // Read and clean user input

        // Handle empty input case
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
                    System.out.println("\nReturning to main menu...");
                    return;
                default:
                    System.out.println("Invalid selection. Please choose between options 1-3.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input format. Please enter only the menu number (1-3).");
        }
    }

    //Adds a new patient to the system after collecting and validating required information
    private void addPatient(Scanner scanner) {
        System.out.println("\n----- PATIENT REGISTRATION -----");

        // 1. Get and validate patient name
        String name;
        while (true) {
            System.out.print("\nEnter patient's full name (or 'cancel' to exit): ");
            name = scanner.nextLine().trim();

            // Check for cancellation
            if (name.isEmpty()) {
                System.out.println("Patient name cannot be empty. Registration cancelled. Returning to main menu...");
                return;
            }

            if (name.equalsIgnoreCase("cancel")) {
                System.out.println("Registration cancelled. Returning to main menu...");
                return;
            }

            // Check for existing patient
            if (getPatientByName(name) != null) {
                System.out.println("A patient named '" + name + "' already exists.");
                System.out.println("Please enter a different name or type 'cancel' to exit.");
            } else {
                break;  // Exit loop if name is unique
            }
        }

        // 2. Get patient address
        System.out.print("Enter patient's address: ");
        String address = scanner.nextLine();
        if (address.trim().isEmpty()) {
            System.out.println("Address cannot be empty. Registration cancelled. Returning to main menu...");
            return;
        }
        // 3. Get and validate phone number
        System.out.print("Enter patient's phone number: ");
        String phone = scanner.nextLine();
        if (phone.trim().isEmpty()) {
            System.out.println("Phone number cannot be empty. Registration cancelled. Returning to main menu...");
            return;
        }

        Patient patient = new Patient(UUID.randomUUID().toString(), name, address, phone);
        addPatient(patient);
        System.out.println("\n Success! Patient '" + name + "' has been registered.");
    }

    // Removes a patient from the system after validation checks
    private void removePatient(Scanner scanner) {
        System.out.println("\n----- PATIENT REMOVAL -----");

        // 1. Get patient name to remove
        System.out.print("\nEnter patient's full name to remove (or 'cancel' to exit): ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Required field is empty. Operation cancelled. Returning to main menu...");
            return;
        }

        if (name.equalsIgnoreCase("cancel")) {
            System.out.println("Operation cancelled. Returning to main menu...");
            return;
        }

        // 2. Verify patient exists
        Patient patient = getPatientByName(name);
        if (patient == null) {
            System.out.println("No Patient found with name '" + name + "'");
            System.out.println("Please check the name and try again.");
            return;
        }

        // 3. Check for active appointments
        boolean hasActiveAppointments = appointments.stream()
                .anyMatch(a -> a.getPatient().equals(patient) && !a.getStatus().equalsIgnoreCase("cancelled"));

        if (hasActiveAppointments) {
            System.out.println("Cannot remove: Patient has active appointments.");
            System.out.println("Please cancel all appointments before removing patient.");
            return;
        }

        patients.remove(patient);
        System.out.println("\nSuccess: Patient '" + name + "' has been removed.");
    }

    //Add patient details to the list
    private void addPatient(Patient patient) {
        patients.add(patient);
    }

    //book appointmentsfor the patients
    private void bookAppointment(Scanner scanner) {
        System.out.println("\n----- NEW APPOINTMENT BOOKING -----");

        // 1. Get and validate patient
        System.out.print("\nEnter patient's full name (or 'cancel' to exit): ");
        String patientName = scanner.nextLine().trim();

        if (patientName.isEmpty()) {
            System.out.println("Field cannot be empty. Booking cancelled. Returning to main menu...");
            return;
        }

        if (patientName.equalsIgnoreCase("cancel")) {
            System.out.println("Operation cancelled. Returning to main menu...");
            return;
        }

        Patient patient = getPatientByName(patientName);
        if (patient == null) {
            System.out.println("No patient found with name '" + patientName + "'");
            return;
        }

        // 2. Select search method
        System.out.println("\n----- Search Options -----");
        System.out.println("\n1. By Treatment Specialty (Area of expertise)");
        System.out.println("2. By Therapist Name");
        System.out.print("\nHow would you like to search? (1-2): ");

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
            // 3. Get available slots based on search method
            System.out.println("\n----- Available Specialties -----\n");

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

            System.out.print("\nEnter desired specialty: ");
            String expertise = scanner.nextLine();
            if (expertise.trim().isEmpty()) {
                System.out.println("Specialty required. Booking cancelled.. Returning to main menu...");
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
            // Search by therapist name
            System.out.println("\n----- Available Physiotherapists -----\n");
            physiotherapists.stream()
                    .sorted(Comparator.comparing(Physiotherapist::getName))
                    .forEach(p -> System.out.printf("- Dr. %-15s (Specialties: %s)%n",
                    p.getName(),
                    String.join(", ", p.getExpertise())));

            System.out.print("\nEnter physiotherapist name: ");
            String name = scanner.nextLine();
            if (name.trim().isEmpty()) {
                System.out.println("Therapist name required. Booking cancelled.. Returning to main menu...");
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

        // 4. Display available slots
        if (availableSlots.isEmpty()) {
            System.out.println("No available appointments match your criteria.");
            return;
        }
        System.out.println("\n----- Available Appointments -----\n");
        System.out.println("+-----+----------------------------+----------------------+---------------------------------------+");
        System.out.printf("| %-3s | %-26s | %-20s | %-37s |%n",
                "No.", "Date/Time", "Treatment", "Physiotherapist (Expertise)");
        System.out.println("+-----+----------------------------+----------------------+---------------------------------------+");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy h:mm a");

        for (int i = 0; i < availableSlots.size(); i++) {
            AppointmentSlot slot = availableSlots.get(i);
            Physiotherapist physio = getPhysioBySlot(slot);

            // Format treatment name
            String treatment = slot.getTreatment().getName();
            treatment = treatment.length() > 20 ? treatment.substring(0, 17) + "..." : treatment;

            // Format expertise 
            String expertise = String.join(", ", physio.getExpertise());
            expertise = expertise.length() > 30 ? expertise.substring(0, 27) + "..." : expertise;

            // Combine physio name and expertise
            String physioInfo = String.format("%s (%s)", physio.getName(), expertise);

            System.out.printf("| %-3d | %-26s | %-20s | %-37s |%n",
                    i + 1,
                    slot.getDateTime().format(formatter),
                    treatment,
                    physioInfo);
        }

        System.out.println("+-----+----------------------------+----------------------+---------------------------------------+");

        // 5. Process booking selection
        System.out.print("\nEnter slot number to book (0 to cancel): ");

        int selection;
        try {
            selection = Integer.parseInt(scanner.nextLine());

            if (selection == 0) {
                System.out.println("Booking cancelled by user.");
                return;
            }

            if (selection < 1 || selection > availableSlots.size()) {
                System.out.println("Invalid slot number. Please try again.");
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
                System.out.println("Conflict: You already have an appointment at this time.");
                return;
            }

            // Create and add the appointment
            Appointment appointment = new Appointment(UUID.randomUUID().toString().substring(0, 8),
                    patient, physio, selectedSlot);
            appointments.add(appointment);

            AppointmentSlot slot = availableSlots.get(selection - 1);

            System.out.println("Appointment booked successfully! Booking ID: " + appointment.getBookingId());
            System.out.printf("\nSuccess! Appointment booked with Dr. %s%n",
                    appointment.getPhysiotherapist().getName());
            System.out.printf("\nDate      : %s%n",
                    slot.getDateTime().format(DateTimeFormatter.ofPattern("EEEE, MMMM d 'at' h:mm a")));
            System.out.printf("Booking ID: %s%n", appointment.getBookingId());

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number. Returning to main menu...");
            return;
        }
    }

    ///Change or cancel an existing appointment
    private void changeOrCancelBooking(Scanner scanner) {
        System.out.println("\n----- MANAGE EXISTING APPOINTMENT -----\n");

        // 1. Get booking reference
        System.out.print("Enter your booking reference (ID) number: ");
        String bookingId = scanner.nextLine().trim();

        if (bookingId.isEmpty()) {
            System.out.println("No booking reference provided. Returning to main menu...");
            return;
        }

        // 2. Find the appointment
        Optional<Appointment> appointmentOpt = appointments.stream()
                .filter(a -> a.getBookingId().equals(bookingId))
                .findFirst();

        if (!appointmentOpt.isPresent()) {
            System.out.println("\n No bookings found with reference (ID): " + bookingId);
            System.out.println("Please check your booking ID and try again.");
            return;
        }

        Appointment appointment = appointmentOpt.get();

        // 3. Display current appointment details
        System.out.println("\n----- Current booking Details: -----\n");
        System.out.println("* Patient : " + appointment.getPatient().getName());
        System.out.println("* Physiotherapist : Dr" + appointment.getPhysiotherapist().getName());
        System.out.println("* Treatment : " + appointment.getSlot().getTreatment().getName());
        System.out.println("* Scheduled Date/Time : "
                + appointment.getSlot().getDateTime().format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy h:mm a")));
        System.out.println("* Current  Status : " + appointment.getStatus());
        System.out.println("-------------------------------------------------------------");

        if (appointment.getStatus().equals("attended")) {
            System.out.println("\nThis appointment has been attended. No further actions can be taken.");
            return;
        }

        if (appointment.getStatus().equals("cancelled")) {
            System.out.println("\nThis appointment has been cancelled. No further actions can be taken.");
            return;
        }

        // 4. Present modification options
        System.out.println("\nPlease choose an action:");
        System.out.println("1. Reschedule this booking");
        System.out.println("2. Cancel this booking");
        System.out.print("\nEnter your choice (1 or 2): ");

        String option = scanner.nextLine().trim();

        // Return to main menu if not a number
        if (!option.matches("\\d+")) {
            System.out.println("Invalid selection. Returning to main menu...");
            return;
        }

        int searchOption = Integer.parseInt(option);

        if (searchOption == 1) {
            System.out.println("\n-----  RESCHEDULE APPOINTMENT -----");
            // Change booking - show available slots from the same physiotherapist
            Physiotherapist physio = appointment.getPhysiotherapist();
            List<AppointmentSlot> availableSlots = physio.getAvailableSlots().stream()
                    .filter(slot -> !isSlotBooked(slot) || slot.equals(appointment.getSlot()))
                    .filter(slot -> slot.getDateTime().isAfter(LocalDateTime.now()))
                    .sorted(Comparator.comparing(AppointmentSlot::getDateTime))
                    .collect(Collectors.toList());

            // If the therapist have no available slots, show a message and return to main menu
            if (availableSlots.isEmpty()) {
                System.out.println("\n  No available time slots with Dr. " + physio.getName());
                System.out.println("Please try again later or choose another therapist.");
                return;
            }

            //  Display available slots
            System.out.println("\nAvailable time slots with Dr. " + physio.getName() + ":\n");
            System.out.println("+----+------------------------------+----------------------------+");
            System.out.println("| #  | Date & Time                  | Treatment                  |");
            System.out.println("+----+------------------------------+----------------------------+");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, MMM d, yyyy h:mm a");
            for (int i = 0; i < availableSlots.size(); i++) {
                AppointmentSlot slot = availableSlots.get(i);
                System.out.printf("| %-2d | %-28s | %-26s |%n",
                        i + 1,
                        slot.getDateTime().format(formatter),
                        slot.getTreatment().getName());
            }

            System.out.println("+----+------------------------------+----------------------------+");

            //Process rescheduling selections
            System.out.print("\nSelect new time slot (0 to cancel): ");

            int selection = -1;
            boolean validInput = false;

            while (!validInput) {
                String input = scanner.nextLine().trim();

                try {
                    selection = Integer.parseInt(input);

                    if (selection == 0) {
                        System.out.println("\nRescheduling cancelled.");
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
                System.out.println("\nYour appointment remains unchanged.");
                System.out.println("You selected the same time slot as your existing booking.");
                return;
            }

            // Check for time conflicts with patient's other appointments
            boolean hasConflict = appointments.stream()
                    .anyMatch(a -> a.getPatient().equals(appointment.getPatient())
                    && !a.getStatus().equals("cancelled")
                    && a.getSlot().getDateTime().equals(newSlot.getDateTime())
                    && !a.getBookingId().equals(appointment.getBookingId()));

            if (hasConflict) {
                System.out.println("You already have a confirmed appointment at this date and time.");
                return;
            }

            // Update the appointment with new slot
            appointment.setSlot(newSlot);
            System.out.println("\nSuccess! Your appointment has been rescheduled.\n");
            System.out.println("New appointment details:");
            System.out.println("  - Treatment: " + newSlot.getTreatment().getName());
            System.out.println("  - Date/Time: "
                    + newSlot.getDateTime().format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy h:mm a")));
        } else if (searchOption == 2) {
            System.out.println("\n----- CANCEL APPOINTMENT -----");
            System.out.print("\nAre you sure you want to cancel this appointment? (yes/no): ");

            String confirmation = scanner.nextLine().trim().toLowerCase();
            if (confirmation.equals("yes")) {
                appointment.setStatus("cancelled");
                System.out.println("\nYour appointment has been successfully cancelled.");
                System.out.println("We hope to see you again soon!");
            } else {
                System.out.println("\nCancellation not confirmed. Your appointment remains scheduled.");
            }
        } else {
            System.out.println("Invalid selection. Please choose between options 1-3.");
        }
    }

    // Marks an appointment as attended
    private void attendAppointment(Scanner scanner) {
        System.out.println("\n----- MARK BOOKING AS ATTENDED -----");
        // 1. Get booking ID
        System.out.print("\nPlease enter the booking ID: ");
        String bookingId = scanner.nextLine().trim();

        // Handle empty input
        if (bookingId.isEmpty()) {
            System.out.println("No booking ID provided. Returning to main menu...");
            return;
        }

        // Find the appointment by booking ID
        Optional<Appointment> appointmentOpt = appointments.stream()
                .filter(a -> a.getBookingId().equals(String.valueOf(bookingId)))
                .findFirst();

        // Handle invalid booking ID
        if (!appointmentOpt.isPresent()) {
            System.out.println("\nSorry..! No appointment found with ID '" + bookingId + "'");
            System.out.println("Please check the ID and try again.");
            return;
        }

        Appointment appointment = appointmentOpt.get();

        // Check appointment status
        if (appointment.getStatus().equals("cancelled")) {
            System.out.println("\nCannot mark appointment as attended:");
            System.out.println("This appointment was cancelled");
            return;
        }

        if (appointment.getStatus().equals("attended")) {
            System.out.println("\nThis appointment was already marked as completed ");
            return;
        }

        // Mark appointment as attended
        appointment.setStatus("attended");

        System.out.println("\nSuccessfully recorded attendance for:");
        System.out.println("Patient:    " + appointment.getPatient().getName());
        System.out.println("Treatment:  " + appointment.getSlot().getTreatment().getName());
        System.out.println("Therapist:  Dr. " + appointment.getPhysiotherapist().getName());
        System.out.println("\nThank you for using our services!");
    }

    //Prints the report of all appointments and attendance summary for each physiotherapist
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
              System.out.println("  +-----------------------------------------------------------------------------------------------------+");
System.out.printf ("  | %-12s | %-25s | %-15s | %-25s | %-10s |%n",
        "BOOKING ID", "TREATMENT", "PATIENT", "DATE/TIME", "STATUS");
System.out.println("  +--------------+---------------------------+-----------------+---------------------------+------------+");

// Appointment rows
physioAppointments.forEach(a -> {
    System.out.printf("  | %-12s | %-25s | %-15s | %-25s | %-10s |%n",
            a.getBookingId(),
            truncate(a.getSlot().getTreatment().getName(), 25),
            truncate(a.getPatient().getName(), 15),
            a.getSlot().getDateTime().format(DateTimeFormatter.ofPattern("EEE, MMM d, h:mm a")),
            a.getStatus());
});

System.out.println("  +--------------+---------------------------+-----------------+---------------------------+------------+\n");
                System.out.println();  // Extra space between physiotherapists
            }
        });

        // Attendance summary
        System.out.println("  \n\nATTENDANCE SUMMARY\n");
System.out.println("  +----------------------+-----------------------+");
System.out.printf("  | %-20s | %-21s |%n", "PHYSIOTHERAPIST", "ATTENDED APPOINTMENTS");
System.out.println("  +----------------------+-----------------------+");

physiotherapists.stream()
        .sorted((p1, p2) -> Long.compare(countAttendedAppointments(p2), countAttendedAppointments(p1)))
        .forEach(p -> {
            System.out.printf("  | %-20s | %-21d |%n", p.getName(), countAttendedAppointments(p));
        });

System.out.println("  +----------------------+-----------------------+");
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

    // For junit test
    public List<Physiotherapist> getAllPhysiotherapists() {
        return this.physiotherapists;
    }

    public List<Patient> getAllPatients() {
        return this.patients;
    }

    public List<Appointment> getAllAppointments() {
        return this.appointments;
    }

    public List<Treatment> getAllTreatments() {
        return this.treatments;
    }

    public Patient fetchPatientsByName(String name) {
        return patients.stream()
                .filter(p -> p.getName().equalsIgnoreCase(name.trim()))
                .findFirst()
                .orElse(null);
    }

    public boolean isSlotTaken(AppointmentSlot slot) {
        return appointments.stream()
                .anyMatch(a -> a.getSlot().equals(slot) && !a.getStatus().equalsIgnoreCase("cancelled"));
    }
}
