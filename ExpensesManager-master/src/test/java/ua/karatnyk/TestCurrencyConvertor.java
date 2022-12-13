package ua.karatnyk;

import static org.junit.jupiter.api.Assertions.*;
import static ua.karatnyk.impl.CurrencyConvertor.convert;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.karatnyk.impl.CurrencyConversion;
import ua.karatnyk.impl.OfflineJsonWorker;

public class TestCurrencyConvertor {

    private CurrencyConversion conversion;

    @BeforeEach
    public void before() {
        OfflineJsonWorker manager = new OfflineJsonWorker();
        conversion = manager.parser();
    }

    @Test
    public void whenAmountIsNegative_thenThrowsException() throws ParseException{
        String currency1 = "CAD";
        String currency2 = "USD";
        double amount = -500.0;

        convert(amount, currency1, currency2, conversion);
    }

    @Test
    public void whenAmountIsSmallButNegative_thenThrowsException() {
        String currency1 = "CAD";
        String currency2 = "USD";
        double amount = -0.01;

        assertThrows(IllegalArgumentException.class, () -> convert(amount, currency1, currency2, conversion));
    }

    @Test
    public void whenAmountIsZero_thenReturnsZero() throws ParseException{
        String currency1 = "CAD";
        String currency2 = "USD";
        double amount = 0.0;

        double convertedAmount = convert(amount, currency1, currency2, conversion);

        assertEquals(amount, convertedAmount);
    }

    @Test
    public void whenAmountIsVerySmall_thenConvertsWithinAcceptedError() throws ParseException{
        String currency1 = "CAD";
        String currency2 = "USD";

        double amount = 0.000001;
        double convertedAmount = convert(amount, currency1, currency2, conversion);

        double CAD = 1.377056;
        double USD = 1.024328;

        // Error of 1 %
        assertEquals(amount / CAD * USD, convertedAmount, (amount + convertedAmount) / (2 * 100));
    }

    @Test
    public void whenAmountIsInEndOfRange_thenReturnsConversion() throws ParseException{
        String currency1 = "CAD";
        String currency2 = "USD";
        double amount = 10000.0;

        convert(amount, currency1, currency2, conversion);
    }

    @Test
    public void whenAmountExceedsRange_thenThrowsException() {
        String currency1 = "CAD";
        String currency2 = "USD";
        double amount = 10001.0;

        assertThrows(Exception.class, () -> convert(amount, currency1, currency2, conversion));
    }

    @Test
    public void whenAmountGreatlyExceeds10000_thenThrowsException() {
        String currency1 = "CAD";
        String currency2 = "USD";
        double amount = 50000.0;

        assertThrows(Exception.class, () -> convert(amount, currency1, currency2, conversion));
    }

    // test boite noire et blanche
    @Test
    public void whenConversionIsValid_thenReturnsCorrectConversion() throws ParseException{
        String currency1 = "CAD";
        String currency2 = "USD";

        double amount = 5000.0;
        double convertedAmount = convert(amount, currency1, currency2, conversion);

        double CAD = 1.377056;
        double USD = 1.024328;

        assertEquals(amount / CAD * USD, convertedAmount);
    }

    // JPY CAD
    @Test
    public void whenGivenOneNonSupportedCurrencyFirst_thenThrowsException() {
        String currency1 ="JPY";
        String currency2 = "CAD";
        double amount = 5000.0;

        assertThrows(Exception.class, () -> convert(amount, currency1, currency2, conversion));
    }

    // CAD JPY
    @Test
    public void whenGivenOneNonSupportedCurrencySecond_thenThrowsException() {
        String currency1 = "CAD";
        String currency2 = "JPY";
        double amount = 5000.0;

        assertThrows(Exception.class, () -> convert(amount, currency1, currency2, conversion));
    }

    // JPY BZD
    @Test
    public void whenGivenTwoNonSupportedCurrencies_thenThrowsException() {
        String currency1 = "JPY";
        String currency2 = "BZD";
        double amount = 5000.0;

        assertThrows(Exception.class, () -> convert(amount, currency1, currency2, conversion));
    }

    // CAD CAD
    @Test
    public void whenCurrenciesAreTheSame_thenReturnsSameAmount() throws ParseException{
        String currency1 = "CAD";
        String currency2 = "CAD";
        double amount = 12.0;

        double convertedAmount = convert(amount, currency1, currency2, conversion);

        assertEquals(amount, convertedAmount);
    }

    @Test
    public void whenGivenNonExistentCurrency_thenThrowsException() {
        String currency1 = "USD";
        String currency2 = "USA";
        double amount = 12.0;

        assertThrows(Exception.class, () -> convert(amount, currency1, currency2, conversion));
    }

    // test boite blanche
    @Test
    public void whenGivenNonExistentCurrency_thenThrowsCorrectException() {
        String currency1 = "USD";
        String currency2 = "USA";
        double amount = 12.0;
        String expectedMessage = "Not correct format currency";

        ParseException exception = assertThrows(ParseException.class,
                () -> convert(amount, currency1, currency2, conversion));

        String message = exception.getMessage();
        assertEquals(expectedMessage, message);
    }
}