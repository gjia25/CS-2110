package LinkedList;

import static org.junit.Assert.*;

import org.junit.Test;

public class DLLTest {

	@Test
	public void testConstructor() {
		DLL<Integer> b= new DLL<Integer>();
		assertEquals("[]", b.toString());
		assertEquals("[]", b.toStringRev());
		assertEquals(0, b.size());
		
	}
	
	@Test
	 public void testAppend() {
		DLL<String> ll= new DLL<String>();
		ll.append("Sampson");
		assertEquals("[Sampson]", ll.toString());
		assertEquals("[Sampson]", ll.toStringRev());
		assertEquals(1, ll.size());
		ll.append("Chlorophyll");
		assertEquals("[Sampson, Chlorophyll]", ll.toString());
		assertEquals("[Chlorophyll, Sampson]", ll.toStringRev());
		assertEquals(2, ll.size());
		ll.append("Catalyst");
		assertEquals("[Sampson, Chlorophyll, Catalyst]", ll.toString());
		assertEquals("[Catalyst, Chlorophyll, Sampson]", ll.toStringRev());
		assertEquals(3, ll.size());
	 }
	
	@Test
	 public void testPrepend() {
		DLL<String> ll= new DLL<String>();
		ll.prepend("Sampson");
		assertEquals("[Sampson]", ll.toString());
		assertEquals("[Sampson]", ll.toStringRev());
		assertEquals(1, ll.size());
		ll.prepend("Chlorophyll");
		assertEquals("[Chlorophyll, Sampson]", ll.toString());
		assertEquals("[Sampson, Chlorophyll]", ll.toStringRev());
		assertEquals(2, ll.size());
		ll.prepend("Catalyst");
		assertEquals("[Catalyst, Chlorophyll, Sampson]", ll.toString());
		assertEquals("[Sampson, Chlorophyll, Catalyst]", ll.toStringRev());
		assertEquals(3, ll.size());
	 }
	
	@Test
	 public void testGetNode() {
		DLL<String> ll= new DLL<String>();
		try {ll.getNode(0); fail("no exception thrown");}
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
		ll.append("zero");
		DLL<String>.Node a = ll.getNode(0);
		assertEquals("zero", a.getValue());
		try {ll.getNode(1); fail("no exception thrown");}
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
		 
		ll.append("one");
		ll.append("two");
		ll.append("three");
		DLL<String>.Node b = ll.getNode(2);
		assertEquals("two", b.getValue());
		ll.append("four");
		DLL<String>.Node c = ll.getNode(3);
		assertEquals("three", c.getValue());
	 }
	
	@Test
	public void testInsertAfter() {
		DLL<String> ll= new DLL<String>();
		ll.append("zero"); //case 1: adding "one" after first
		ll.insertAfter("one", ll.getNode(0));
		assertEquals("[zero, one]", ll.toString());
		assertEquals("[one, zero]", ll.toStringRev());
		assertEquals(2, ll.size());
		ll.append("three");
		ll.append("four");
		ll.append("five");
		ll.append("seven");
		ll.insertAfter("two", ll.getNode(1)); //case 3: adding "two" after ll1[1]
		assertEquals("[zero, one, two, three, four, five, seven]", ll.toString());
		assertEquals("[seven, five, four, three, two, one, zero]", ll.toStringRev());
		assertEquals(7, ll.size());
		ll.insertAfter("six", ll.getNode(5)); //case 3: adding "six" after ll1[5]
		assertEquals("[zero, one, two, three, four, five, six, seven]", ll.toString());
		assertEquals("[seven, six, five, four, three, two, one, zero]", ll.toStringRev());
		assertEquals(8, ll.size());
		ll.insertAfter("eight", ll.getNode(7)); //case 2: adding "eight" to end
		assertEquals("[zero, one, two, three, four, five, six, seven, eight]", ll.toString());
		assertEquals("[eight, seven, six, five, four, three, two, one, zero]", ll.toStringRev());
		assertEquals(9, ll.size());
		try {ll.insertAfter("x", null); fail("no exception thrown");}
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
	}
	
	@Test
	public void testInsertBefore() {
		DLL<String> ll= new DLL<String>();
		ll.append("one"); //case 1: adding "zero" before first
		ll.insertBefore("zero", ll.getNode(0));
		assertEquals("[zero, one]", ll.toString());
		assertEquals(2, ll.size());
		ll.append("three");
		ll.append("four");
		ll.append("five");
		ll.append("six");
		ll.insertBefore("two", ll.getNode(2)); //case 3: adding "two" before ll1[2]
		assertEquals("[zero, one, two, three, four, five, six]", ll.toString());
		assertEquals("[six, five, four, three, two, one, zero]", ll.toStringRev());
		assertEquals(7, ll.size());
		ll.insertBefore("neg", ll.getNode(0)); //case 2: adding "neg" to beginning
		assertEquals("[neg, zero, one, two, three, four, five, six]", ll.toString());
		assertEquals("[six, five, four, three, two, one, zero, neg]", ll.toStringRev());
		assertEquals(8, ll.size());
		try {ll.insertAfter("x", null); fail("no exception thrown");}
		catch (AssertionError e) {if (e.getMessage() != null) fail();}
	}
	
	@Test
	public void testRemove(){
		DLL<String> ll= new DLL<String>();
		ll.append("zero");
		ll.append("one");
		ll.append("two");
		ll.append("three");
		ll.remove(ll.getNode(1)); //case 4: (n != first && last != null)
		assertEquals("[zero, two, three]", ll.toString());
		assertEquals("[three, two, zero]", ll.toStringRev());
		assertEquals(3, ll.size());
		ll.remove(ll.getNode(2)); //case 3: (n == last && n.prev != first)
		assertEquals("[zero, two]", ll.toString());
		assertEquals("[two, zero]", ll.toStringRev());
		assertEquals(2, ll.size());
		ll.remove(ll.getNode(1)); //case 3: (n == last && n.prev == first)
		assertEquals("[zero]", ll.toString());
		assertEquals("[zero]", ll.toStringRev());
		assertEquals(1, ll.size());
		ll.append("one");
		ll.remove(ll.getNode(0));
		assertEquals("[one]", ll.toString());
		assertEquals("[one]", ll.toStringRev());
		assertEquals(1, ll.size());
		ll.remove(ll.getNode(0));
		assertEquals("[]", ll.toString());
		System.out.println("passed 3");
		assertEquals("[]", ll.toStringRev());
		System.out.println("passed 4");
	}
}
