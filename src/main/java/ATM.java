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
        if (bank.validateUserId(userId)){
             this.currentUser = bank.getUserById(userId);
             if (currentUser.isLocked()) {
                 return false;
             }
            return true;
        }
        else {
            return false;
        }
    }

    public boolean enterPin(String pin) {
        if (pin == null || pin.trim().isEmpty()) {
            throw new UnsupportedOperationException("Please Enter PIN");
        }
        if (!bank.validatePin(pin)) {
            currentUser.incrementFailedAttempts();
            int amountFailedTries = currentUser.getFailedAttempts();
            int triesLeft = 3 - amountFailedTries;
            System.out.println("Wrong PIN, you have " + triesLeft + " tries left");
            if (amountFailedTries == 3){
                currentUser.lockCard();
                System.out.println("oh no, locked card :(");
            }
            boolean saveResult =  bank.saveUser(currentUser);
            if (saveResult){

                System.out.println("saved amount of failed attempts");
            }
            else {
                System.out.println("failed to save amount of failed attempts");
            }
            return false;
        }
        return true;
    }

    public double checkBalance() {
        double balance = bank.checkBalance(currentUser.getId());
        System.out.println("current balance: " + balance);
        return balance;
    }

    public void deposit(double amount) {
        if (amount<0) {
            throw new UnsupportedOperationException("Amount cannot be negative");
        }
        if (amount>0){
           boolean result =  bank.deposite(amount);
           if (result) {
               currentUser.deposit(amount);
               System.out.println("deposited");
           }
           else {
               System.out.println("something went wrong, try again");
           }
        }
    }

    public boolean withdraw(double amount) {
        if (amount<0) {
            throw new UnsupportedOperationException("Amount cannot be negative");
        }
        if (amount == 0){
            throw new UnsupportedOperationException("Amount cannot be zero");
        }
        if (currentUser.getBalance() >= amount){
           boolean result =  bank.withdraw(amount);
           if (result) {
               currentUser.withdraw(amount);
               System.out.println("withdrawed: " + amount);
               return true;
           }
           else {
               System.out.println("something went wrong, try again");
               return false;
           }
        }
        else {
            System.out.println("Not enough balance");
            return false;
        }
    }
    public boolean identifyBank(){
        String bank = Bank.getBankName().trim();
        if (bank.isEmpty()){
            throw new UnsupportedOperationException("Bank not connected");
        } else if (bank.equals("swedbank")) {
            System.out.println("connected to correct swedbank!");
            return true;
        }
        else {
            System.out.println("connected to incorrect bank");
            return false;
        }
    }

}
