import java.util.*;
import java.util.stream.Collectors;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Main {
    public static void main(String[] args) {
        BookingSystem system = new BookingSystem();
        system.initializeSampleData();
        system.run();
    }
}

class BookingSystem{
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
    for (int week = 0; week < 4; week++) {
        for (Physiotherapist physio : physiotherapists) {
            for (int day = 0; day < 5; day++) { // Monday to Friday
                LocalDateTime date = baseDate.plusWeeks(week).plusDays(day);
                for (int slot = 0; slot < 4; slot++) { // 4 slots per day
                    LocalDateTime slotTime = date.plusHours(slot * 2); // 2-hour slots
                    
                    // Get treatments that match this physio's expertise
                    List<Treatment> matchingTreatments = getTreatmentsForPhysio(physio);
                    if (!matchingTreatments.isEmpty()) {
                        // Use a combination of week, day and slot to cycle through treatments
                        int treatmentIndex = (week + day + slot) % matchingTreatments.size();
                        Treatment treatment = matchingTreatments.get(treatmentIndex);
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

private List<Treatment> getTreatmentsForPhysio(Physiotherapist physio) {
    List<Treatment> matchingTreatments = new ArrayList<>();
    for (Treatment treatment : treatments) {
        // Check if treatment name matches any of the physio's expertise
        if (physio.getExpertise().stream()
            .anyMatch(expertise -> treatment.getName().toLowerCase().contains(expertise.toLowerCase()))) {
            matchingTreatments.add(treatment);
        }
    }
    return matchingTreatments;
}

   // Program starts exection here
    public void run() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        
        while (running) {

// TODO: PATIENTS LIST
//   System.out.println("\n--- Patient List ---");
//     if (patients.isEmpty()) {
//         System.out.println("No patients in the system.");
//     } else {
//         for (Patient patient : patients) {
//             System.out.println("ID: " + patient.getId());
//             System.out.println("Name: " + patient.getName());
//             System.out.println("Address: " + patient.getAddress());
//             System.out.println("Phone: " + patient.getPhone());
//             System.out.println("----------------------------");
//         }}

            System.out.println("\u001B[1;36m\n ═════════════ Welcome to Boost Physio Clinic Booking System ═════════════ \u001B[0m");    
            System.out.println("\u001B[32m\nBoost Physio Clinic Booking System\n\u001B[0m");
            System.out.println("1. Add/Remove Patient"); 
            System.out.println("2. Book Treatment Appointment");
            System.out.println("3. Change/Cancel Booking");
            System.out.println("4. Attend Appointment");
            System.out.println("5. Print Report");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");

        String input = scanner.nextLine().trim(); // Read entire line and trim whitespace
 
         if (input.isEmpty()) {
            System.out.println("\u001B[31mNo input detected. Please try again.\u001B[0m");
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
               System.out.println("\u001B[33mInvalid input. Please enter a number (1-6).\u001B[0m");
        }
    } catch (NumberFormatException e) {
        System.out.println("Error: Please enter a number (1-6).");
    }
      }
        System.out.println("Thank you for using the Boost Physio Clinic Booking System.");
        scanner.close();
    }

    //Manage Patient [ Options : Add, Remove, Back]
    private void managePatients(Scanner scanner) {
        System.out.println("\u001B[32m\n══════ Patient Management ══════\n\u001B[0m");
        System.out.println("1. Add Patient");
        System.out.println("2. Remove Patient");
        System.out.println("3. Back to Main Menu");
        System.out.print("\nSelect an option: ");
             
     String input = scanner.nextLine().trim();  // Read entire line

    // Handle empty input
    if (input.isEmpty()) {
     System.out.println("\u001B[33mNo selection made. Returning to main menu...\u001B[0m");
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
            System.out.println("\u001B[33mInvalid option. Please enter 1, 2, or 3.\u001B[0m");
        }
    } catch (NumberFormatException e) {
        System.out.println("\u001B[31mError: Please enter a number (1-3).\u001B[0m");
}   
    }

    //get inputs from patients to add the patients
    private void addPatient(Scanner scanner) {
        System.out.println("\nAdd New Patient");
        String id = UUID.randomUUID().toString();        
        
        System.out.print("Enter full name: ");
        String name = scanner.nextLine();
        if (name.trim().isEmpty()) {
         System.out.println("\u001B[33mCancelled. Returning to main menu...\u001B[0m");
         return;
        }

        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        if (address.trim().isEmpty()) {
         System.out.println("\u001B[33mCancelled. Returning to main menu...\u001B[0m");
         return;
        } 
               
        System.out.print("Enter telephone number: ");
        String phone = scanner.nextLine();
        if (phone.trim().isEmpty()) {
         System.out.println("\u001B[33mCancelled. Returning to main menu...\u001B[0m");
         return;
       }
                
        Patient patient = new Patient(id, name, address, phone);
        addPatient(patient);
        System.out.println("\u001B[32mPatient added successfully.\u001B[0m");    
        }

    //get patient name from the patients to remove patient
    private void removePatient(Scanner scanner) {
        System.out.println("\nRemove Patient");
        System.out.print("Enter patient name to remove: ");
        String name = scanner.nextLine();
        if(name.trim().isEmpty()){
            System.out.println("\u001B[33mOperation cancelled. Returning to main menu...\u001B[0m");
            return;
        }
                 
        Patient patient = getPatientByName(name);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        
        // TODO: Cant remove patient with active appointments
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
            System.out.println("\u001B[33mOperation cancelled. Returning to main menu...\u001B[0m");
            return;
        }        
        Patient patient = getPatientByName(patientName);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }
        
        System.out.println("Search by:");
        System.out.println("1. Area of expertise");
        System.out.println("2. Physiotherapist name");
        System.out.print("Select an option: ");
        
        int searchOption = scanner.nextInt();
        scanner.nextLine();
        
        List<AppointmentSlot> availableSlots = new ArrayList<>();
        
        if (searchOption == 1) {
            System.out.print("Enter area of expertise: ");
            String expertise = scanner.nextLine();
            if(expertise.trim().isEmpty()) {
                System.out.println("\u001B[33mOperation cancelled. Returning to main menu...\u001B[0m");
                return;
            }
    
        // Store the lowercase trimmed expertise for reuse
        final String searchTerm = expertise.toLowerCase().trim();
            availableSlots = physiotherapists.stream()
                    .filter(p -> p.getExpertise().contains(expertise))
                    .flatMap(p -> p.getAvailableSlots().stream())
                    .filter(slot -> !isSlotBooked(slot))
                    .collect(Collectors.toList());

        } else if (searchOption == 2) {
            System.out.print("Enter physiotherapist name: ");
            String name = scanner.nextLine();
            if(name.trim().isEmpty()){
              System.out.println("\u001B[33mOperation cancelled. Returning to main menu...\u001B[0m");
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
        for (int i = 0; i < availableSlots.size(); i++) {
            AppointmentSlot slot = availableSlots.get(i);
            System.out.printf("%d. %s - %s with %s (%s)\n",
                    i + 1,
                    slot.getDateTime().format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy h:mm a")),
                    slot.getTreatment().getName(),
                    getPhysioBySlot(slot).getName(),
                    String.join(", ", getPhysioBySlot(slot).getExpertise()));
        }
        
        System.out.print("Select an appointment to book (0 to cancel): ");
        int selection = scanner.nextInt();
        scanner.nextLine();
        
        if (selection <= 0 || selection > availableSlots.size()) {
            System.out.println("Booking cancelled.");
            return;
        }
        
        AppointmentSlot selectedSlot = availableSlots.get(selection - 1);
        Physiotherapist physio = getPhysioBySlot(selectedSlot);
        
        // Check for time conflicts
        boolean hasConflict = appointments.stream()
                .anyMatch(a -> a.getPatient().equals(patient) &&
                        !a.getStatus().equals("cancelled") &&
                        a.getSlot().getDateTime().equals(selectedSlot.getDateTime()));
        
        if (hasConflict) {
            System.out.println("You already have an appointment at this time.");
            return;
        }
        
        // Create and add the appointment
        Appointment appointment = new Appointment(UUID.randomUUID().toString().substring(0, 8), patient, physio, selectedSlot);
        appointments.add(appointment);
        System.out.println("\u001B[32mAppointment booked successfully! Booking ID: \u001B[0m"+ appointment.getBookingId());
    }

    private void changeOrCancelBooking(Scanner scanner) {
        System.out.println("\nChange/Cancel Booking");
        System.out.print("Enter booking ID: ");
        String bookingId = scanner.nextLine();
        if(bookingId.trim().isEmpty()){
            System.out.println("\u001B[33mOperation cancelled. Returning to main menu...\u001B[0m");
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
        
        System.out.println("1. Change booking");
        System.out.println("2. Cancel booking");
        System.out.print("Select an option: ");
        
        int option = scanner.nextInt();
        scanner.nextLine();
        
        if (option == 1) {
            // Change booking - first cancel the existing one
            appointment.setStatus("cancelled");
            
            // Then book a new one
            System.out.println("Please select a new appointment:");
            bookAppointment(scanner);
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
        if(bookingId.trim().isEmpty()){
            System.out.println("\u001B[33mOperation cancelled. Returning to main menu...\u001B[0m");
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
System.out.println("\u001B[1;36m\n ════════════ Boost Physio Clinic - Treatment Report (4 Weeks) ════════════ \u001B[0m");    
    // Print all appointments grouped by physiotherapist
    physiotherapists.forEach(physio -> {
        System.out.println("\nPhysiotherapist: " + physio.getName());
        System.out.println("Expertise: " + String.join(", ", physio.getExpertise()));
        
        List<Appointment> physioAppointments = appointments.stream()
                .filter(a -> a.getPhysiotherapist().equals(physio))
                .sorted(Comparator.comparing(a -> a.getSlot().getDateTime()))
                .collect(Collectors.toList());
        
        if (physioAppointments.isEmpty()) {
            System.out.println("No appointments scheduled.");
        } else {
            // Printing Table Header
            System.out.printf("%-15s %-35s %-25s %-30s %-25s\n", "Booking ID", "Treatment", "Patient", "Date/Time", "Status");
            System.out.println("------------------------------------------------------------------------------------");
            
            // Printing table rows
            physioAppointments.forEach(a -> {
                System.out.printf("%-15s %-35s %-25s %-30s %-25s\n",
                        a.getBookingId(),
                        a.getSlot().getTreatment().getName(),
                        a.getPatient().getName(),
                        a.getSlot().getDateTime().format(DateTimeFormatter.ofPattern("EEE, MMM d, yyyy h:mm a")),
                        a.getStatus());
            });
        }
    });
    
    // Print physiotherapists by number of attended appointments
    System.out.println("\nPhysiotherapist Attendance Summary:");
    System.out.printf("%-20s %-20s\n", "Name", "Attended Appointments");
    System.out.println("-----------------------------------------------");
    
    physiotherapists.stream()
            .sorted((p1, p2) -> {
                long p1Count = countAttendedAppointments(p1);
                long p2Count = countAttendedAppointments(p2);
                return Long.compare(p2Count, p1Count); // Descending order
            })
            .forEach(p -> {
                System.out.printf("%-20s %-20d\n",
                        p.getName(),
                        countAttendedAppointments(p));
            });
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


class Patient{
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
    public String getId() { return id; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
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
     public String getId() { return id; } 
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public Set<String> getExpertise() { return expertise; }
    public List<AppointmentSlot> getAvailableSlots() { return availableSlots; }
}

class Treatment {
    private String name;

    public Treatment(String name) {
        this.name = name;
    }

    public String getName() { return name; }
} 

class AppointmentSlot {
    private LocalDateTime dateTime;
    private Treatment treatment;

    public AppointmentSlot(LocalDateTime dateTime, Treatment treatment) {
        this.dateTime = dateTime;
        this.treatment = treatment;
    }

    // Getters
    public LocalDateTime getDateTime() { return dateTime; }
    public Treatment getTreatment() { return treatment; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentSlot that = (AppointmentSlot) o;
        return Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(treatment, that.treatment);
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
    public String getBookingId() { return bookingId; }
    public Patient getPatient() { return patient; }
    public Physiotherapist getPhysiotherapist() { return physiotherapist; }
    public AppointmentSlot getSlot() { return slot; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}