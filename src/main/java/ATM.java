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
        if (bank.isCardLocked(userId)) {
            System.out.println("Card is locked");
            return false;
        }
        return bank.validateUserId(userId);
    }

    public boolean enterPin(String pin) {
        if (pin == null || pin.trim().isEmpty()) {
            throw new UnsupportedOperationException("Please Enter PIN");
        }
        if (!bank.validatePin(pin)) {
            currentUser.incrementFailedAttempts();
            int amountFailedTries = bank.amountOfFailedTries(currentUser.getId());
            int triesLeft = 3 - amountFailedTries;
            System.out.println("Wrong PIN, you have " + triesLeft + " tries left");
            if (amountFailedTries == 3){
                System.out.println("oh no, locked card :(");
            }
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
