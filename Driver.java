import java.io.IOException;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws IOException {
        User user = Login.login();
        PatientManager manager = new PatientManager(user);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. View Profile");
            System.out.println("2. View Patient");
            System.out.println("3. Edit Patient");
            System.out.println("4. Generate Report"); // New option for reports
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manager.viewProfile();
                    break;
                case 2:
                    if (user instanceof MedicalStaff) {
                        System.out.print("Enter Patient ID to view: ");
                        int id = scanner.nextInt();
                        manager.viewPatient(id);
                    } else {
                        System.out.println("Access denied.");
                    }
                    break;
                case 3:
                    System.out.print("Enter Patient ID to edit: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter field to edit (name, email, treatment_notes): ");
                    String field = scanner.nextLine();
                    System.out.print("Enter new value: ");
                    String newValue = scanner.nextLine();
                    manager.editPatient(id, field, newValue);
                    break;
                case 4:
                    if (user instanceof MedicalStaff) {
                        System.out.print("Enter report filename: ");
                        String fileName = scanner.nextLine();
                        System.out.println("Choose report type:");
                        System.out.println("1. List of Patients sorted by ID");
                        System.out.println("2. List of Patients sorted by Name");
                        System.out.println("3. List of Emails sorted alphabetically");
                        int reportType = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        manager.generateReport(fileName, reportType);
                    } else {
                        System.out.println("Access denied. Only Medical Staff can generate reports.");
                    }
                    break;
                case 5:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
