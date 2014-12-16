package driimerfinance.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import driimerfinance.helpers.FinanceHelper;

public class FinanceHelperTest {

	@Test
	public void testFormatAmount() {
		double input = 2003.50;
		String expected = "CHF2,003.50";
		String output = FinanceHelper.formatAmount(input);
		assertTrue(expected.equals(output));
	}
	
	@Test
	public void testUnformatAmount() {
		String input = "CHF2,003.50";
		double expected = 2003.50;
		double output = FinanceHelper.unformatAmount(input);
		assertTrue(expected == output);
	}

}
