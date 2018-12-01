import static org.junit.Assert.*;
import static common.JUnitUtil.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.junit.BeforeClass;
import org.junit.Test;

public class SharingTreeTest {

    private static Network n;
    private static Person[] people;

    @BeforeClass
    public static void setup(){
        n= new Network();
        people= new Person[]{
                new Person("A", n, 0), new Person("B", n, 0), new Person("C", n, 0),
                new Person("D", n, 0), new Person("E", n, 0), new Person("F", n, 0),
                new Person("G", n, 0), new Person("H", n, 0), new Person("I", n, 0),
                new Person("J", n, 0), new Person("K", n, 0), new Person("L", n, 0)
        };
    }

    @Test
    public void testInsert() {
        SharingTree st= new SharingTree(people[1]); 

        //Test add to root --check also the returned value
        SharingTree dt2= st.insert(people[1], people[2]);
        assertEquals("B[C]", toStringBrief(st));
        assertEquals(people[2], dt2.getRoot());
        
        //Test add to child --check also the returned value
        SharingTree dt3= st.insert(people[2], people[3]);
        assertEquals("B[C[D]]", toStringBrief(st));
        assertEquals(people[3], dt3.getRoot());
        
      //Test add to child's child
        st.insert(people[3], people[4]);
        assertEquals("B[C[D[E]]]", toStringBrief(st));
        
        st.insert(people[1], people[5]);
        st.insert(people[5], people[6]);
        assertEquals("B[C[D[E]] F[G]]", toStringBrief(st));
        
    }
    
    @Test
    public void testSize() {
        SharingTree st= new SharingTree(people[1]); 
        assertEquals(1, st.size());
        st.insert(people[1], people[2]);
        st.insert(people[1], people[3]);
        assertEquals(3, st.size());
        assertEquals(2, st.getChildrenCount());
        st.insert(people[2], people[4]);
        st.insert(people[2], people[5]);
        assertEquals(2, st.getChildrenCount());
        assertEquals(5, st.size());
    }
    
    @Test
    public void testDepth() {
        SharingTree st= new SharingTree(people[1]); 
        assertEquals(0, st.depth(people[1]));
        st.insert(people[1], people[2]);
        st.insert(people[1], people[3]);
        st.insert(people[2], people[4]);
        st.insert(people[4], people[5]);
        assertEquals(3, st.depth(people[5]));
        assertEquals(-1, st.depth(people[6]));
    }
    
    @Test
    public void testWidthAtDepth() {
        SharingTree st= new SharingTree(people[0]); 
        st.insert(people[0], people[1]);
        st.insert(people[0], people[2]);
        st.insert(people[1], people[3]);
        st.insert(people[2], people[4]);
        st.insert(people[2], people[5]);
        st.insert(people[5], people[6]);
        assertEquals(1, st.widthAtDepth(0));
        assertEquals(2, st.widthAtDepth(1));
        assertEquals(3, st.widthAtDepth(2));
        assertEquals(1, st.widthAtDepth(3));
        assertEquals(0, st.widthAtDepth(4));
    }
    
    @Test
    public void testGetSharingRoute() {
        SharingTree st= new SharingTree(people[0]);
        st.insert(people[0], people[1]);
        st.insert(people[0], people[2]);
        st.insert(people[1], people[3]);
        st.insert(people[2], people[4]);
        st.insert(people[2], people[5]);
        st.insert(people[5], people[6]);
        
        List<Person> lp= new ArrayList<>();
        lp.add(people[0]);
        assertEquals(lp, st.getSharingRoute(people[0]));
        lp.add(people[2]);
        lp.add(people[4]);
        assertEquals(lp, st.getSharingRoute(people[4]));
        assertEquals(null, st.getSharingRoute(people[7]));
        assertEquals(null, st.getTree(people[1]).getSharingRoute(people[2]));
        List<Person> l= new ArrayList<>();
        l.add(people[1]);
        l.add(people[3]);
        assertEquals(l, st.getTree(people[1]).getSharingRoute(people[3]));
    }
    
    @Test
    public void testGetSharedAncestor() {
        SharingTree st= new SharingTree(people[0]);
        assertEquals(people[0], st.getSharedAncestor(people[0], people[0]));
        assertEquals(null, st.getSharedAncestor(people[8], people[0]));
        st.insert(people[0], people[1]);
        st.insert(people[0], people[2]);
        assertEquals(people[0], st.getSharedAncestor(people[0], people[1]));
        st.insert(people[1], people[3]);
        st.insert(people[2], people[4]);
        st.insert(people[2], people[5]);
        st.insert(people[5], people[6]);
        assertEquals(people[2], st.getSharedAncestor(people[4], people[5]));
        assertEquals(null, st.getTree(people[1]).getSharedAncestor(people[3], people[2]));
    }
    
    @Test
    public void testEquals() {
        SharingTree st= new SharingTree(people[0]);
        assertEquals(st, st);
        SharingTree st2= new SharingTree(people[0]);
        assertEquals(st, st2);
       //testing multiple Person tree
        st.insert(people[0], people[1]);
        st.insert(people[0], people[2]);
        st2.insert(people[0], people[1]);
        st2.insert(people[0], people[2]);
        assertEquals(st, st2);
        
        //testing multiple layer tree
        st.insert(people[1], people[3]);
        st2.insert(people[1], people[3]);
        assertEquals(st, st2);
        
        //testing copy
        SharingTree st3= new SharingTree(st);
        assertEquals(st, st3);
    }
    
    
    /** Return a representation of this tree. This representation is:
     * (1) the name of the Person at the root, followed by
     * (2) the representations of the children (in alphabetical
     *     order of the children's names).
     * There are two cases concerning the children.
     *
     * No children? Their representation is the empty string.
     * Children? Their representation is the representation of each child, with
     * a blank between adjacent ones and delimited by "[" and "]".
     * Examples:
     * One-node tree: "A"
     * root A with children B, C, D: "A[B C D]"
     * root A with children B, C, D and B has a child F: "A[B[F] C D]"
     */
    public static String toStringBrief(SharingTree t) {
        String res= t.getRoot().getName();

        Object[] childs= t.getChildren().toArray();
        if (childs.length == 0) return res;
        res= res + "[";
        selectionSort1(childs);

        for (int k= 0; k < childs.length; k= k+1) {
            if (k > 0) res= res + " ";
            res= res + toStringBrief(((SharingTree)childs[k]));
        }
        return res + "]";
    }

    /** Sort b --put its elements in ascending order.
     * Sort on the name of the Person at the root of each SharingTree
     * Throw a cast-class exception if b's elements are not SharingTrees */
    public static void selectionSort1(Object[] b) {
        int j= 0;
        // {inv P: b[0..j-1] is sorted and b[0..j-1] <= b[j..]}
        //          0----------- j---------------b.length
        // inv : b | sorted, <= |       >=      |
        //          ----------------------------
        while (j != b.length) {
            // Put into p the index of smallest element in b[j..]
            int p= j;
            for (int i= j+1; i != b.length; i++) {
                String bi= ((SharingTree)b[i]).getRoot().getName();
                String bp= ((SharingTree)b[p]).getRoot().getName();
                if (bi.compareTo(bp) < 0) {
                    p= i;
                }
            }
            
            // Swap b[j] and b[p]
            Object t= b[j]; b[j]= b[p]; b[p]= t;
            
            j= j+1;
        }
    }

}
