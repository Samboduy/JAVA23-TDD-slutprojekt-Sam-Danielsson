public class ATM {
    private Bank bank;
    private User currentUser;

    ATM(Bank bank, User currentUser) {
        this.bank = bank;
        this.currentUser = currentUser;
    }

    public boolean insertCard(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new UnsupportedOperationException("Please Insert card");
        }
        return currentUser.getId().equals(userId);
    }

    public boolean enterPin(String pin) {
        if (pin == null || pin.trim().isEmpty()) {
            throw new UnsupportedOperationException("Please Enter PIN");
        }
        if (!currentUser.getPin().equals(pin)) {
            currentUser.incrementFailedAttempts();
            int amountFailedTries = currentUser.getFailedAttempts();
            System.out.println("Wrong PIN");
            return false;
        }
        return true;
    }

    public double checkBalance() {
        return 0;
    }

    public void deposit(double amount) {
    }

    public boolean withdraw(double amount) {
        return false;
    }

}
