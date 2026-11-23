public class Admin extends User {

    private String role;

    public Admin(int idUser, String nama, String email,
                 String password, String noHp, String role) {
        super(idUser, nama, email, password, noHp, "admin");
        this.role = role;
    }

    @Override
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}