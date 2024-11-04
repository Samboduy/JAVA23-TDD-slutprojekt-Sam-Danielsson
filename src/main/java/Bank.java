import java.util.HashMap;
import java.util.Map;

public class Bank  {
    private BankInterface bankInterface;

    private Map<String, User> users = new HashMap<>();

    public User getUserById(String id) {
        return users.get(id);
    }

    public boolean validateUserId(String userId) {
        return false;
    }

    public boolean validatePin(String pin) {
        return false;
    }
    public int amountOfFailedTries(String userId) {
        return 0;
    }

    public void saveUser(User user) {
        users.put(user.getId(), user);
    }

    public boolean isCardLocked(String userId) {
        User user = users.get(userId);
        return user != null && user.isLocked();
    }

    public static String getBankName() {
        return "MockBank";
    }
}
