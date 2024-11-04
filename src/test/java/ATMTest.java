import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ATMTest {
    private BankInterface bankInterface;
    private Bank mockedBank;
    private User user;
    private ATM atm;
    @BeforeEach
    void setUp() {
        user = new User("1","1337",5000);
        mockedBank = mock(Bank.class);
        atm = new ATM(mockedBank,user);

    }
    @Test
    @DisplayName("Testing input of card")
    public void testInputOfCard() {
        when(mockedBank.validateUserId("1")).thenReturn(true);
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
    @DisplayName("testing if card is locked")
    public void testIfCardIsLocked() {
        when(mockedBank.isCardLocked("1")).thenReturn(true);
        boolean result = atm.insertCard(user.getId());
        assertFalse(result);
        verify(mockedBank).isCardLocked("1");
    }
    @Test
    @DisplayName("testing if card is not locked")
    public void testIfCardIsNotLocked() {
        when(mockedBank.isCardLocked("1")).thenReturn(false);
        when(mockedBank.validateUserId("1")).thenReturn(true);
        boolean result = atm.insertCard(user.getId());
        assertTrue(result);
        verify(mockedBank).isCardLocked("1");
        verify(mockedBank).validateUserId("1");
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
        when(mockedBank.validatePin("1337")).thenReturn(true);
        boolean result = atm.enterPin(user.getPin());
        assertTrue(result);
        verify(mockedBank).validatePin("1337");
    }
    @Test
    @DisplayName("Testing input of incorrect pin code")
    public void testInputOfIncorrectPinCode() {
        when(mockedBank.validatePin("1234")).thenReturn(false);
        boolean result = atm.enterPin("1234");
        assertFalse(result);
        verify(mockedBank).validatePin("1234");
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