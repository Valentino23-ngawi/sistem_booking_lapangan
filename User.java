public abstract class User {

    protected int idUser;
    protected String nama;
    protected String email;
    protected String password;
    protected String noHp;
    protected String tipe;

    public User(int idUser, String nama, String email,
                String password, String noHp, String tipe) {
        this.idUser = idUser;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.noHp = noHp;
        this.tipe = tipe;
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void logout() {
        System.out.println("Logout berhasil. Sampai jumpa, " + nama + "!");
    }

    // METHOD ABSTRACT
    public abstract String getRole();

    // Getters & Setters
    public int getIdUser() { return idUser; }
    public String getNama() { return nama; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getNoHp() { return noHp; }
    public String getTipe() { return tipe; }

    public void setNama(String nama) { this.nama = nama; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setNoHp(String noHp) { this.noHp = noHp; }
}