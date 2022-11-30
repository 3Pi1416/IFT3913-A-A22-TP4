package ua.karatnyk;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static ua.karatnyk.impl.CurrencyConvertor.convert;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import ua.karatnyk.impl.CurrencyConversion;
import ua.karatnyk.impl.OfflineJsonWorker;

public class TestCurrencyConvertor {

    private CurrencyConversion conversion;

    @Before
    public void before() {
        OfflineJsonWorker manager = new OfflineJsonWorker();
        conversion = manager.parser();

    }

    // USD CAD
    @Test
    public void testCurrencyConvertorSmallerAmountCadUsd() {

        String[] devises = { "CAD", "USD" };

        Double amount = -5000.0;
        double test;
        try {
            test = convert(amount, devises[0], devises[1], conversion);
        } catch (Exception e) {
            assertTrue(true);
            return;
        }
        fail(String.format("Should not have an valide number, got  %d", test));

    }

    @Test
    public void testCurrencyConvertorGoodAmountCadUsd() {

        String[] devises = { "CAD", "USD" };

        Double amount = 5000.0;
        double test;
        try {
            test = convert(amount, devises[0], devises[1], conversion);
        } catch (Exception e) {
            fail(e.toString());
            return;
        }
        Double CAD = 1.377056;
        Double USD = 1.024328;

        assertTrue(amount / CAD * USD == test);

    }

    @Test
    public void testCurrencyConvertorSmallFrontierAmountCadUsd() {

        String[] devises = { "CAD", "USD" };

        Double amount = 0.000001;
        double test;
        try {
            test = convert(amount, devises[0], devises[1], conversion);
        } catch (Exception e) {
            fail(e.toString());
            return;
        }
        Double CAD = 1.377056;
        Double USD = 1.024328;

        // Error of 1 %
        assertEquals(amount / CAD * USD, test, (amount + test) / (2 * 100));

    }

    @Test
    public void testCurrencyConvertorBigFrontierAmountCadUsd() {

        String[] devises = { "CAD", "USD" };

        Double amount = 10000.0;
        double test;
        try {
            test = convert(amount, devises[0], devises[1], conversion);
        } catch (Exception e) {
            fail(e.toString());
            return;
        }
        Double CAD = 1.377056;
        Double USD = 1.024328;

        assertEquals(amount / CAD * USD, test, 0.01);

    }

    @Test
    public void testCurrencyConvertorEqual0AmountCadUsd() {

        String[] devises = { "CAD", "USD" };

        Double amount = 0.0;
        double test;
        try {
            test = convert(amount, devises[0], devises[1], conversion);
        } catch (Exception e) {
            fail(e.toString());
            return;
        }
        Double CAD = 1.377056;
        Double USD = 1.024328;

        assertEquals(amount / CAD * USD, test, 0.01);

    }

    @Test
    public void testCurrencyConvertorBiggerAmountCadUsd() {

        String[] devises = { "CAD", "USD" };

        Double amount = 50000.0;
        double test;
        try {
            test = convert(amount, devises[0], devises[1], conversion);
        } catch (Exception e) {
            assertTrue(true);
            return;
        }
        fail(String.format("Should not have an valide number, got  %d", test));

    }

    //Jpy Cad
    @Test
    public void testCurrencyConvertorGoodAmountJpyCad() {

        String[] devises = { "JPY", "CAD" };

        Double amount = 5000.0;
        double test;
        try {
            test = convert(amount, devises[0], devises[1], conversion);
        } catch (Exception e) {
            assertTrue(true);
            return;
        }
        fail(String.format("Should not have an valide number, got  %d", test));

    }

    // Cad Jpy
    @Test
    public void testCurrencyConvertorGoodAmountCadJpy() {

        String[] devises = { "JPY", "CAD" };

        Double amount = 5000.0;
        double test;
        try {
            test = convert(amount, devises[0], devises[1], conversion);
        } catch (Exception e) {
            assertTrue(true);
            return;
        }
        fail(String.format("Should not have an valide number, got  %d", test));

    }

    // Jpy Bzd
    @Test
    public void testCurrencyConvertorGoodAmountJpyBzd() {

        String[] devises = { "JPY", "BZD" };

        Double amount = 5000.0;
        double test;
        try {
            test = convert(amount, devises[0], devises[1], conversion);
        } catch (Exception e) {
            assertTrue(true);
            return;
        }
        fail(String.format("Should not have an valide number, got  %d", test));

    }

    // Cad Cad
    @Test
    public void testCurrencyConvertorGoodAmountCadCad() {

        String[] devises = { "CAD", "CAD" };

        Double amount = 5000.0;
        double test;
        try {
            test = convert(amount, devises[0], devises[1], conversion);
        } catch (Exception e) {
            fail(e.toString());
            return;
        }
        assertEquals(test, amount, 0.1);

    }
