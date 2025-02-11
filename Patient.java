public class Patient extends User {
    private String treatmentNotes;

    public Patient(int id, String username, String password, String name, String email, String treatmentNotes) {
        super(id, username, password, name, email);
        this.treatmentNotes = treatmentNotes;
    }

    public String getTreatmentNotes() {
        return treatmentNotes;
    }

    public void setTreatmentNotes(String treatmentNotes) {
        this.treatmentNotes = treatmentNotes;
    }

    @Override
    public String toString() {
        return "Patient{id=" + getId() + ", username=" + getUsername() +
               ", name=" + getName() + ", email=" + getEmail() +
               ", treatmentNotes=" + treatmentNotes + "}";
    }
}
