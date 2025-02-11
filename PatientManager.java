import java.io.*;
import java.util.*;

public class PatientManager {
    private User currentUser;
    private List<Patient> patients;

    public PatientManager(User user) throws IOException {
        this.currentUser = user;
        this.patients = loadPatients();
        sortPatientsById();
    }

    private List<Patient> loadPatients() throws IOException {
        List<Patient> patients = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("patient.csv"));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            patients.add(new Patient(
                Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4], parts[5]
            ));
        }

        reader.close();
        return patients;
    }

    private void sortPatientsById() {
        for (int i = 0; i < patients.size() - 1; i++) {
            for (int j = 0; j < patients.size() - i - 1; j++) {
                if (patients.get(j).getId() > patients.get(j + 1).getId()) {
                    Patient temp = patients.get(j);
                    patients.set(j, patients.get(j + 1));
                    patients.set(j + 1, temp);
                }
            }
        }
    }

    public void viewProfile() {
        System.out.println(currentUser);
    }

    public void viewPatient(int id) {
        if (currentUser instanceof Patient) {
            System.out.println("Access denied. Patients can only view their own information.");
            return;
        }

        Patient found = binarySearch(id);
        if (found != null) {
            System.out.println(found);
        } else {
            System.out.println("Patient not found.");
        }
    }

    private Patient binarySearch(int id) {
        int left = 0, right = patients.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (patients.get(mid).getId() == id) {
                return patients.get(mid);
            } else if (patients.get(mid).getId() < id) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    public void editPatient(int id, String field, String newValue) throws IOException {
        if (currentUser instanceof Patient && ((Patient) currentUser).getId() != id) {
            System.out.println("Access denied. Patients can only edit their own information.");
            return;
        }

        Patient patient = binarySearch(id);
        if (patient == null) {
            System.out.println("Patient not found.");
            return;
        }

        switch (field.toLowerCase()) {
            case "name":
                patient.setName(newValue);
                break;
            case "email":
                patient.setEmail(newValue);
                break;
            case "treatment_notes":
                patient.setTreatmentNotes(newValue);
                break;
            default:
                System.out.println("Invalid field.");
                return;
        }

        savePatients();
        System.out.println("Patient information updated.");
    }

    private void savePatients() throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter("patient.csv"));
        for (Patient patient : patients) {
            writer.println(patient.getId() + "," + patient.getUsername() + "," +
                           patient.getPassword() + "," + patient.getName() + "," +
                           patient.getEmail() + "," + patient.getTreatmentNotes());
        }
        writer.close();
    }

    public void generateReport(String fileName, int reportType) throws IOException {
        List<Patient> sortedPatients = new ArrayList<>(patients);
        PrintWriter writer = new PrintWriter(new FileWriter(fileName));

        switch (reportType) {
            case 1: // List of Patients sorted by ID
                sortPatientsById();
                writer.println("ID,Name,Email");
                for (Patient patient : sortedPatients) {
                    writer.println(patient.getId() + "," + patient.getName() + "," + patient.getEmail());
                }
                break;

            case 2: // List of Patients sorted by Name
                sortedPatients.sort(Comparator.comparing(Patient::getName));
                writer.println("ID,Name,Email");
                for (Patient patient : sortedPatients) {
                    writer.println(patient.getId() + "," + patient.getName() + "," + patient.getEmail());
                }
                break;

            case 3: // List of Emails sorted alphabetically
                List<String> emails = new ArrayList<>();
                for (Patient patient : sortedPatients) {
                    emails.add(patient.getEmail());
                }
                emails.sort(String::compareTo);
                writer.println("Email");
                for (String email : emails) {
                    writer.println(email);
                }
                break;

            default:
                System.out.println("Invalid report type.");
                writer.close();
                return;
        }

        writer.close();
        System.out.println("Report generated: " + fileName);
    }
}
