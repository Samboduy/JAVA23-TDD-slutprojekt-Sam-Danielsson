import java.util.HashMap;
import java.util.Map;

public class Bank  {
    private BankInterface bankInterface;
    public Bank(BankInterface bankInterface) {
        this.bankInterface = bankInterface;
    }

    public User getUserById(String id) {
        return bankInterface.getUserById(id);
    }

    public boolean isCardLocked(String userId) {
        User user = bankInterface.getUserById(userId);
        return user != null && user.isLocked();
    }

    public static String getBankName() {
        return "MockBank";
    }
}
