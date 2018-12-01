import static org.junit.Assert.*;

import org.junit.Test;

public class PhDTest {

	@Test
	public void testConstructor1() {
		PhD p1 = new PhD("Bob", 3, 2000, 'b');
		assertEquals("Bob", p1.name());
		assertEquals(3, p1.month());
		assertEquals(2000, p1.year());
		assertEquals(true, p1.isMale());
		assertEquals(null, p1.advisor1());
		assertEquals(null, p1.advisor2());
		assertEquals(0, p1.numAdvisees());
		
		PhD p2 = new PhD("Carol", 2, 2001, 'g');
		assertEquals(false, p2.isMale());
		
		try {new PhD(null, 3, 2000, 'b'); fail("no exception thrown");}
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
		try {new PhD("Bob", 14, 2000, 'b'); fail("no exception thrown");}
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
		try {new PhD("Bob", 3, 2000, 's'); fail("no exception thrown");}
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
	}
	
	@Test
	public void testB() {
		PhD p = new PhD("Alex", 5, 2010, 'b');
		PhD a1 = new PhD("Bob", 3, 2000, 'b');
		PhD a2 = new PhD("Carol", 2, 2001, 'g');
		p.addAdvisor1(a1);
		p.addAdvisor2(a2);
		
		assertEquals(a1, p.advisor1());
		assertEquals(a2, p.advisor2());
		assertEquals(1, a1.numAdvisees());
		assertEquals(1, a2.numAdvisees());
		
		PhD a3 = new PhD("Daisy", 10, 1999, 'g');	
		try {p.addAdvisor1(a3); fail("no exception thrown");} // advisor1 != null
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
		try {a1.addAdvisor1(null); fail("no exception thrown");} // p == null
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
		try {a3.addAdvisor2(p); fail("no exception thrown");} // advisor1 == null
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
		try {p.addAdvisor2(a1); fail("no exception thrown");} // p == advisor1
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
	}
	
	@Test
	public void testC() {
		PhD a1 = new PhD("Bob", 3, 2000, 'b');
		PhD a2 = new PhD("Carol", 2, 2001, 'g');
		PhD p = new PhD("Alex", 5, 2010, 'b', a1, a2);
		
		assertEquals("Alex", p.name());
		assertEquals(5, p.month());
		assertEquals(2010, p.year());
		assertEquals(true, p.isMale());
		assertEquals(a1, p.advisor1());
		assertEquals(a2, p.advisor2());
		assertEquals(0, p.numAdvisees());
		assertEquals(1, a1.numAdvisees());
		assertEquals(1, a2.numAdvisees());
		
		try {new PhD("", 3, 2000, 'b', a1, a2); fail("no exception thrown");}
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
		try {new PhD("Bob", 0, 2000, 'b', a1, a2); fail("no exception thrown");}
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
		try {new PhD("Bob", 3, 2000, 'x', a1, a2); fail("no exception thrown");}
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
		try {new PhD("Bob", 3, 2000, 'b', null, a2); fail("no exception thrown");}
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
		try {new PhD("Bob", 3, 2000, 'x', a1, null); fail("no exception thrown");}
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
		try {new PhD("Bob", 3, 2000, 'x', a1, a1); fail("no exception thrown");}
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
	}
	
	@Test
	public void testD() {
		PhD a = new PhD("Alex", 5, 1990, 'b');
		PhD p1 = new PhD("Bob", 3, 2000, 'b', a);
		PhD p2 = new PhD("Carol", 2, 2000, 'g', a);
		
		assertEquals(false, p1.gotFirst(p2));
		assertEquals(true, a.gotFirst(p1));
		assertEquals(true, p1.arePhDSiblings(p2));
		assertEquals(false, a.arePhDSiblings(p1));
		
		try {a.arePhDSiblings(null); fail("no exception thrown");}
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
	}
}
