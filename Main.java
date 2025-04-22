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