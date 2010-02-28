package render.quantifyit.model.operations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static render.quantifyit.model.AssertDecimal.assertDecimal;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import org.junit.Test;

import render.quantifyit.model.Decimal;

public class DecimalOperationByTest {


	//TODO : test for doubles: positive and negative infinity, Double.MAX_VALUE and Double.MIN_VALUE
	
	
	@Test
	public void testByInt() {
		Decimal simple = Decimal.$(100).by(20);
		assertDecimal(Decimal.$(5), simple);
		
		Decimal negativeDivision = Decimal.$(-1000).by(Decimal.$(25));
		assertDecimal(Decimal.$(-40), negativeDivision);
		
		Decimal byIntContext = Decimal.$(127, new MathContext(2)).by(2);
		assertDecimal(Decimal.$(65), byIntContext);
		
		Decimal largeInt = Decimal.$(Integer.MAX_VALUE).by(10);
		assertDecimal(Decimal.$(Integer.MAX_VALUE).by(Decimal.TEN), largeInt);
	}
	
	@Test
	public void testByDouble() {
		Decimal simple = Decimal.$(100d).by(20d);
		assertDecimal(Decimal.$(5d), simple);	
		
		Decimal negativeDivision = Decimal.$(-1000d).by(25d);
		assertDecimal(Decimal.$(-40d), negativeDivision);
		
		Decimal byDoubleContext = Decimal.$(127d, new MathContext(2)).by(2d);
		assertDecimal(Decimal.$(65d), byDoubleContext);
		
		Decimal largeDouble = Decimal.$(Double.MAX_VALUE).by(10d);
		assertDecimal(Decimal.$(Double.MAX_VALUE).by(Decimal.TEN), largeDouble);
	}


	//TODO: Missing by with context

	@Test
	public void testByDecimal() {
		Decimal simple = Decimal.$(100).by(20);
		assertDecimal(Decimal.$(5), simple);	
		
		Decimal negativeDivision = Decimal.$(-1000).by(Decimal.$(25));
		assertDecimal(Decimal.$(-40), negativeDivision);
		
		Decimal byIntContext = Decimal.$(127, new MathContext(2)).by(2);
		assertDecimal(Decimal.$(65), byIntContext);
		
		Decimal largeInt = Decimal.$(Integer.MAX_VALUE).by(10);
		assertDecimal(Decimal.$(Integer.MAX_VALUE).by(Decimal.TEN), largeInt);
	}
	
	@Test
	public void testPeriodicBy(){
		double periodic = 312d/7d;
		assertEquals("long period", 44.57142857142857, periodic, 0);

		BigDecimal bPeriodicRoundedToInt = new BigDecimal(312d).divide(new BigDecimal(7d), RoundingMode.HALF_EVEN);
		assertEquals(new BigDecimal(45), bPeriodicRoundedToInt);
		
		BigDecimal bPeriodicScaled = new BigDecimal(312d).divide(new BigDecimal(7d), 2, RoundingMode.HALF_EVEN);
		assertEquals(new BigDecimal("44.57"), bPeriodicScaled);
		
		Decimal dPeriodicDefaultScaleInt = Decimal.$(312).by(7);
		assertDecimal(Decimal.$(44.5714285714), dPeriodicDefaultScaleInt);	
		Decimal dPeriodicDefaultScaleDouble = Decimal.$(312).by(7);
		assertDecimal(Decimal.$(44.5714285714), dPeriodicDefaultScaleDouble);	
		Decimal dPeriodicDefaultScaleDecimal = Decimal.$(312).by(Decimal.$(7));
		assertDecimal(Decimal.$(44.5714285714), dPeriodicDefaultScaleDecimal);	
	}
	
	@Test
	public void testShouldRoughlyApproximateToPiWithPrimitives(){
		double doubleApproximation = 223d/71d;
		assertEquals(3.1408450704, doubleApproximation, 9);

		BigDecimal bigDecimalApproximation = new BigDecimal(223d).divide(new BigDecimal(71d), RoundingMode.HALF_EVEN);
		assertEquals(new BigDecimal(3), bigDecimalApproximation);
		bigDecimalApproximation = new BigDecimal(223d).divide(new BigDecimal(71d), 5, RoundingMode.HALF_EVEN);
		assertEquals(new BigDecimal("3.14085"), bigDecimalApproximation);
		
		Decimal decimalApproximation = Decimal.$(223).by(71);
		assertDecimal(Decimal.$(3.1408450704), decimalApproximation);	
		decimalApproximation = Decimal.$(223).by(71, 5);
		assertDecimal(Decimal.$(3.14085), decimalApproximation);	
		decimalApproximation = Decimal.$(223d).by(71d);
		assertDecimal(Decimal.$(3.1408450704), decimalApproximation);	
		decimalApproximation = Decimal.$(223d).by(71d, 5);
		assertDecimal(Decimal.$(3.14085), decimalApproximation);	
	}
	
	@Test
	public void testShouldEasilyApproximateToPiWithPrimitives(){
		double doubleApproximation = 355d/113d;
		assertEquals(3.1415929204, doubleApproximation, 9);
		
		BigDecimal bigDecimalApproximation = new BigDecimal(355d).divide(new BigDecimal(113d), RoundingMode.HALF_EVEN);
		assertEquals(new BigDecimal(3), bigDecimalApproximation);
		bigDecimalApproximation = new BigDecimal(355d).divide(new BigDecimal(113d), 7, RoundingMode.HALF_EVEN);
		assertEquals(new BigDecimal("3.1415929"), bigDecimalApproximation);
		
		Decimal decimalApproximation = Decimal.$(355).by(113);
		assertDecimal(Decimal.$(3.1415929204), decimalApproximation);	
		decimalApproximation = Decimal.$(355).by(113, 5);
		assertDecimal(Decimal.$(3.14159), decimalApproximation);	
		decimalApproximation = Decimal.$(355d).by(113d);
		assertDecimal(Decimal.$(3.1415929204), decimalApproximation);	
		decimalApproximation = Decimal.$(355d).by(113d, 5);
		assertDecimal(Decimal.$(3.14159), decimalApproximation);	
	}

	
	@Test(expected=ArithmeticException.class)
	public void testNaN(){
		Decimal.ZERO.by(0d);
	}

	@Test(expected=ArithmeticException.class)
	public void testNaNWithScale(){
		Decimal.ZERO.by(0d, 5);
	}

	@Test(expected=ArithmeticException.class)
	public void testNaNWithPrecision(){
		Decimal.ZERO.by(Decimal.ZERO, new MathContext(0));
	}
	
	@Test
	public void testPosiitiveInfinity(){
		final Decimal positiveinfinity = Decimal.$(Double.MAX_VALUE).times(2);
		assertTrue(positiveinfinity.isInfinite());
		assertEquals(Double.toString(Double.POSITIVE_INFINITY), Double.toString(positiveinfinity.asDouble()));
	}
	
	@Test
	public void testNegativeInfinity(){
		final Decimal negativeInfinity = Decimal.$(-Double.MAX_VALUE).times(2);
		assertTrue(negativeInfinity.isInfinite());
		assertEquals(Double.toString(Double.NEGATIVE_INFINITY), Double.toString(negativeInfinity.asDouble()));
	}
	
}