//    public void initializeSampleData() {

    //     // Initialize sample treatments
    //     treatments.add(new Treatment("t1"));
    //     treatments.add(new Treatment("t2"));
    //     treatments.add(new Treatment("t3"));
    //     treatments.add(new Treatment("t4"));
    //     treatments.add(new Treatment("t5"));

    //     // Initialize sample physiotherapists
    //     Physiotherapist p1 = new Physiotherapist(1, "DR1", "123 Main St", "555-1234");
    //     p1.addExpertise("t1");
    //     p1.addExpertise("t2");
        
    //     Physiotherapist p2 = new Physiotherapist(2, "dr2", "456 Oak Ave", "555-5678");
    //     p2.addExpertise("t2");
    //     p2.addExpertise("t4");
        
    //     Physiotherapist p3 = new Physiotherapist(3, "Dr3", "789 Pine Rd", "555-9012");
    //     p3.addExpertise("t5");
    //     p3.addExpertise("t3");
        
    //     physiotherapists.add(p1);
    //     physiotherapists.add(p2);
    //     physiotherapists.add(p3);

    //     // Create sample schedule for 4 weeks
    //     LocalDateTime baseDate = LocalDateTime.now().withHour(9).withMinute(0);
    //     for (int week = 0; week < 4; week++) {
    //         for (Physiotherapist physio : physiotherapists) {
    //             for (int day = 0; day < 5; day++) { // Monday to Friday
    //                 LocalDateTime date = baseDate.plusWeeks(week).plusDays(day);
    //                 for (int slot = 0; slot < 4; slot++) { // 4 slots per day
    //                     LocalDateTime slotTime = date.plusHours(slot * 2); // 2-hour slots
    //                     Treatment treatment = treatments.get((physio.getId() + week + day + slot) % treatments.size());
    //                     physio.addAvailableSlot(new AppointmentSlot(slotTime, treatment));
    //                 }
    //             }
    //         }
    //     }

    //     // Initialize sample patients
    //     addPatient(new Patient(1, "pa1", "101 Elm St", "555-1111"));
    //     addPatient(new Patient(2, "pa2", "202 Maple Dr", "555-2222"));
    //     addPatient(new Patient(3, "pa3", "303 Cedar Ln", "555-3333"));
    //     addPatient(new Patient(4, "Pa4", "404 Birch Blvd", "555-4444"));
    //     addPatient(new Patient(5, "pa5", "505 Redwood Way", "555-5555"));
    // }
