import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

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
        when(mockedBank.getUserById("1")).thenReturn(user);

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
        User lockedUser = new User("1","1337",5000);
        lockedUser.lockCard();
        when(mockedBank.getUserById("1")).thenReturn(lockedUser);
        when(mockedBank.validateUserId("1")).thenReturn(true);
        boolean result = atm.insertCard(user.getId());
        assertFalse(result);
        verify(mockedBank).getUserById(lockedUser.getId());

    }
    @Test
    @DisplayName("testing if card is not locked")
    public void testIfCardIsNotLocked() {
        when(mockedBank.validateUserId("1")).thenReturn(true);
        boolean result = atm.insertCard(user.getId());
        assertTrue(result);
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
        user.incrementFailedAttempts();
        when(mockedBank.validatePin("1234")).thenReturn(false);
        when(mockedBank.saveUser(user)).thenReturn(true);
        boolean result = atm.enterPin("1234");
        assertFalse(result);
        verify(mockedBank).validatePin("1234");
        verify(mockedBank).saveUser(user);
    }
    @Test
    @DisplayName("Testing input of incorrect pin code 3 times")
    public void testInputOfIncorrectPinCodeThreeTimes() {
        user.incrementFailedAttempts();
        user.incrementFailedAttempts();
        when(mockedBank.validatePin("1234")).thenReturn(false);
        when(mockedBank.saveUser(user)).thenReturn(true);
        boolean result = atm.enterPin("1234");
        assertFalse(result);
        verify(mockedBank).validatePin("1234");
        verify(mockedBank).saveUser(user);
    }
    @Test
    @DisplayName("Testing input of null pin code")
    public void testInputOfNullPinCode() {
        assertThrows(UnsupportedOperationException.class, ()-> {
            atm.enterPin(null);
        });
    }
    @Test
    @DisplayName("Testing input of empty pin code")
    public void testInputOfEmptyPinCode() {
        assertThrows(UnsupportedOperationException.class, ()-> {
            atm.enterPin("");
        });
    }
    @Test
    @DisplayName("Testing showing, adding and removing money from saldo")
    public void testShowingAndRemovingMoneyFromSaldo() {
        when(mockedBank.checkBalance(user.getId())).thenReturn(5000.0);
        when(mockedBank.deposite(500.0)).thenReturn(true);
        when(mockedBank.withdraw(500.0)).thenReturn(true);

        assertAll(
                ()-> assertEquals(5000.0,atm.checkBalance()),
                ()-> assertTrue(atm.withdraw(500.0))
        );
        atm.deposit(500.0);

        verify(mockedBank).checkBalance(user.getId());
        verify(mockedBank).deposite(500.0);
        verify(mockedBank).withdraw(500.0);
    }
    @Test
    @DisplayName("Testing internal errors")
    public void testShowingInternalErrors() {

        when(mockedBank.deposite(500.0)).thenReturn(false);
        when(mockedBank.withdraw(500.0)).thenReturn(false);

        assertFalse(atm.withdraw(500.0));
        atm.deposit(500.0);

        verify(mockedBank).deposite(500.0);
        verify(mockedBank).withdraw(500.0);
    }
    @Test
    @DisplayName("Testing Throwing if amount is less than zero")
    public void testThrowingIfAmountIsLessThanZero() {
        assertAll(
                ()-> assertThrows(UnsupportedOperationException.class, ()-> {
                   atm.deposit(-500.0);
                }),
                ()-> assertThrows(UnsupportedOperationException.class, ()-> {
                    atm.withdraw(-500.0);
                })
        );

    }
    @Test
    @DisplayName("Testing Throwing if withdrawing zero")
    public void testThrowingIfwithdrawingZero() {
        assertThrows(UnsupportedOperationException.class, ()-> {
            atm.withdraw(0);
        });
    }
    @Test
    @DisplayName("Testing withdrawing more then what is in saldo")
    public void testWithdrawingTooMuch() {
            assertFalse(atm.withdraw(100000000));
    }
    @Test
    @DisplayName("Testing identifying bank")
    public void testIdentifyingBank() {
        try (MockedStatic<Bank> staticBank = Mockito.mockStatic(Bank.class)) {
            staticBank.when(Bank::getBankName).thenReturn("swedbank");
            assertTrue(atm.identifyBank());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    @DisplayName("Testing identifying Norwegian bank")
    public void testIdentifyingNorwegianBank() {
        try (MockedStatic<Bank> staticBank = Mockito.mockStatic(Bank.class)) {
            staticBank.when(Bank::getBankName).thenReturn("bank norwegian");
            assertFalse(atm.identifyBank());

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    @DisplayName("Testing identifying empty String")
    public void testIdentifyingEmptyString() {
        try (MockedStatic<Bank> staticBank = Mockito.mockStatic(Bank.class)) {
            staticBank.when(Bank::getBankName).thenReturn("");
            assertThrows(UnsupportedOperationException.class, ()-> {
                atm.identifyBank();
            });

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }



}