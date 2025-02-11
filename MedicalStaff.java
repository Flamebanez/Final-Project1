public class MedicalStaff extends User {
    private String department;

    public MedicalStaff(int id, String username, String password, String name, String email, String department) {
        super(id, username, password, name, email);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "MedicalStaff{id=" + getId() + ", username=" + getUsername() +
               ", name=" + getName() + ", email=" + getEmail() +
               ", department=" + department + "}";
    }
}
