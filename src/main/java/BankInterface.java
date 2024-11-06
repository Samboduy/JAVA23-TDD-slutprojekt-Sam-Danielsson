public interface BankInterface {
    public User getUserById(String id);
    public boolean isCardLocked(String userId);
    public boolean validateUserId(String userId);
    public boolean validatePin(String pin);
    public int amountOfFailedTries(String userId);
    public boolean withdraw(double amount);
    public boolean deposite(double amount);
    public double checkBalance(String id);
    public boolean saveUser(User user);
}
