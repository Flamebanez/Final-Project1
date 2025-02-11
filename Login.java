import java.io.*;
import java.util.*;

public class Login {
    private static final String PATIENT_FILE = "patient.csv";
    private static final String STAFF_FILE = "medicalstaff.csv";

    public static User login() throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            User user = findUser(username, password);
            if (user != null) {
                System.out.println("Login successful!");
                return user;
            }

            System.out.println("Invalid username or password. Try again.");
        }
    }

    private static User findUser(String username, String password) throws IOException {
        User user = findInFile(PATIENT_FILE, username, password, "Patient");
        if (user == null) {
            user = findInFile(STAFF_FILE, username, password, "MedicalStaff");
        }
        return user;
    }

    private static User findInFile(String fileName, String username, String password, String userType) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0]);
            if (parts[1].equals(username) && parts[2].equals(password)) {
                if (userType.equals("Patient")) {
                    return new Patient(id, parts[1], parts[2], parts[3], parts[4], parts[5]);
                } else {
                    return new MedicalStaff(id, parts[1], parts[2], parts[3], parts[4], parts[5]);
                }
            }
        }

        reader.close();
        return null;
    }
}
