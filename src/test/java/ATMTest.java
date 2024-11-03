import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ATMTest {
    private BankInterface bankInterface;
    private Bank bank;
    private User user;
    private ATM atm;
    @BeforeEach
    void setUp() {
        user = new User("1","1337",5000);
        bankInterface = mock(BankInterface.class);
        bank = new Bank(bankInterface);
        atm = new ATM(bank,user);

    }
    @Test
    @DisplayName("Testing input of card")
    public void testInputOfCard() {
        boolean result = atm.insertCard(user.getId());
        assertTrue(result);
    }
    @Test
    @DisplayName("Testing input of wrong card")
    public void testInputOfWrongCard() {
        boolean result = atm.insertCard("FGDG");
        assertFalse(result);
    }
    @Test
    @DisplayName("Testing input null")
    public void testInputNull() {
        assertThrows(UnsupportedOperationException.class, ()-> {
            atm.insertCard(null);
        });
    }
    @Test
    @DisplayName("Testing input empty String")
    public void testInputEmptyString() {
        assertThrows(UnsupportedOperationException.class, ()-> {
            atm.insertCard("");
        });
    }
    @Test
    @DisplayName("Testing input of correct pin code")
    public void testInputOfCorrectPinCode() {
        boolean result = atm.enterPin(user.getPin());
        assertTrue(result);
    }
    @Test
    @DisplayName("Testing input of incorrect pin code")
    public void testInputOfIncorrectPinCode() {
        boolean result = atm.enterPin("1234");
        assertFalse(result);
    }
    @Test
    @DisplayName("Testing input of null pin code")
    public void testInputOfNullPinCode() {
        assertThrows(UnsupportedOperationException.class, ()-> {
            atm.enterPin(null);
        });
    }
    @Test
    @DisplayName("Testing input of null pin code")
    public void testInputOfEmptyPinCode() {
        assertThrows(UnsupportedOperationException.class, ()-> {
            atm.enterPin("");
        });
    }


}