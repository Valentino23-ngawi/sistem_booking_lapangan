public class Customer extends User {

    public Customer(int idUser, String nama, String email,
                    String password, String noHp) {
        super(idUser, nama, email, password, noHp, "customer");
    }

    @Override
    public String getRole() {
        return "Customer";
    }
}