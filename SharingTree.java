import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/** An instance of SharingTree represents the spreading of a Post through
 * a (social) Network of people.
 * The root of the SharingTree is the original poster. From there, each
 * person in the SharingTree is the child of the person from whom they saw
 * the post. For example, for the tree:
 * <p>
 *      A
 *     / \
 *    B   C
 *       / \
 *      D   E
 * <p>
 * Person A originally created the post, B and C saw A's post,
 * C reshared the post, and D and E saw the post from C.
 * <p>
 * Important note: The name of each person in the sharing tree is unique.
 *
 * @author Mshnik and ebirrell
 //TODOs by gjia25
 */
public class SharingTree {

    /** The String to be used as a separator in toString() */
    public static final String SEPARATOR = " - ";

    /** The String that marks the start of children in toString() */
    public static final String START_CHILDREN_DELIMITER = "[";

    /**  The String that divides children in toString() */
    public static final String DELIMITER = ", ";

    /** The String that marks the end of children in toString() */
    public static final String END_CHILDREN_DELIMITER = "]";

    /** The String that is the space increment in toStringVerbose() */
    public static final String VERBOSE_SPACE_INCREMENT = "\t";

    /** The person at the root of this SharingTree.
     * This is the original poster, who first create the content.
     * root is non-null.
     * All Person's in a SharingTree have different names. There are no duplicates
     */
    private Person root;

    /** The hashCode for this SharingTree object. It is the hashCode for
     *  the root's name. Calculated only once in the constructor. */
    private final int hashCode;

    /** The immediate children of this SharingTree node.
     * Each element of children saw the post from the person at this node.
     * root is non-null but will be an empty set if this is a leaf. */
    private Set<SharingTree> children;

    /** Constructor: a new SharingTree with root p and no children.
     * Throw an IllegalArgumentException if p is null. */
    public SharingTree(Person p) throws IllegalArgumentException {
        if (p == null)
            throw new IllegalArgumentException("Can't construct SharingTree with null root");
        root= p;
        hashCode= root.getName().hashCode();
        children= new HashSet<>();
    }

    /** Constructor: a new SharingTree that is a copy of tree p.
     * Tree p and its copy have no node in common (but nodes can share a Person).
     * Throw an IllegalArgumentException if p is null. */
    public SharingTree(SharingTree p) throws IllegalArgumentException {
        if (p == null)
            throw new IllegalArgumentException("Can't construct SharingTree as copy of null");
        root= p.root;
        hashCode= root.getName().hashCode();
        children= new HashSet<>();

        for (SharingTree st : p.children) {
            children.add(new SharingTree(st));
        }
    }

    /** Return the person that is at the root of this SharingTree */
    public Person getRoot() {
        return root;
    }

    /** Return the number of direct children of this SharingTree */
    public int getChildrenCount() {
        return children.size();
    }

    /** Return a COPY of the set of children of this SharingTree. */
    public Set<SharingTree> getChildren() {
        return new HashSet<>(children);
    }

    /** Insert c in this SharingTree as a child of p and
     * return the SharingTree whose root is the new child.
     * Throw an IllegalArgumentException if:<br>
     * -- p or c is null,<br>
     * -- c is already in this SharingTree, or<br>
     * -- p is not in this SharingTree */
    public SharingTree insert(Person p, Person c) throws IllegalArgumentException {
        //TODO 1
    	if (p == null || c == null || this.contains(c) || !this.contains(p))
    		throw new IllegalArgumentException("Can't construct SharingTree");

    	SharingTree cTree = new SharingTree(c);
    	getTree(p).children.add(cTree);
    	return cTree;
    }

    /** Return the number of people in this SharingTree.
     * Note: If this is a leaf, the size is 1 (just the root) */
    public int size() {
        //TODO 2
    	int size = 1;
    	if (children == null) return 1;
    	else {
    		for (SharingTree st : children) {
        		size += st.size();
        	}
    	}
    	return size;
    }

    /**Return the depth at which p occurs in this SharingTree,
     * or -1 if p is not in the SharingTree.
     * Note: depth(root) is 0.
     * If p is a child of this SharingTree, then depth(p) is 1. etc. */
    public int depth(Person p) {
        //TODO 3
    	if (root == p) {
    		return 0;
    	}
    	if (this.contains(p)) {
    		for (SharingTree st : children) {
    			return 1 + st.depth(p);
    		}
    	}
    	return -1;
    }

    /** If p is in this tree, return the SharingTree object in this tree
     * that contains p. If p is not in this tree, return null.
     * <p>
     * Example: Calling getTree(root) should return this.
     */
    public SharingTree getTree(Person p) {
        if (root == p) return this; //Base case - look here

        // Recursive case - ask children to look
        for (SharingTree st : children) {
            SharingTree search= st.getTree(p);
            if (search != null) return search;
        }

        return null; // Not found
    }

    /** Return true iff this SharingTree contains p. */
    public boolean contains(Person p) {
        /* Note: This SharingTree contains p iff the root of this
         * SharingTree is p or if one of p's children contains p. */
        if (root == p) return true;
        for (SharingTree st : children) {
            if (st.contains(p)) return true;
        }
        return false;
    }


    /** Return the maximum depth of this SharingTree, i.e. the longest path from
     * the root to a leaf. Example. If this SharingTree is a leaf, return 0.
     */
    public int maxDepth() {
        int maxDepth= 0;
        for (SharingTree dt : children) {
            maxDepth= Math.max(maxDepth, dt.maxDepth() + 1);
        }
        return maxDepth;
    }

    /** Return the width of this tree at depth d (i.e. the number of sharing
     * trees that occur at depth d. Note: the depth of the root is 0.
     * Throw an IllegalArgumentException if depth < 0.
     * Thus, for the following tree :
     * Depth level:
     * 0       A
     *        / \
     * 1     B   C
     *      /   / \
     * 2   D   E   F
     *              \
     * 3             G
     * <p>
     * A.widthAtDepth(0) = 1,  A.widthAtDepth(1) = 2,
     * A.widthAtDepth(2) = 3,  A.widthAtDepth(3) = 1,
     * A.widthAtDepth(4) = 0.
     * C.widthAtDepth(0) = 1,  C.widthAtDepth(1) = 2
     */
    public int widthAtDepth(int d) throws IllegalArgumentException {
        //TODO 4
    	int width = 0;
    	if (d == 0) return 1;

    	for (SharingTree st : children) {
            width += st.widthAtDepth(d-1);
        }

    	return width;
    }

    /** Return the maximum width of all the widths in this tree, i.e. the
     * maximum value that could be returned from widthAtDepth for this tree.
     */
    public int maxWidth() {
        return maxWidthImplementationOne(this);
    }

    // Simple implementation of maxWith. Relies on widthAtDepth.
    // Takes time proportional the the square of the size of the t.
    static int maxWidthImplementationOne(SharingTree t) {
        int width = 0;
        int depth = t.maxDepth();
        for (int i = 0; i <= depth; i++) {
            width = Math.max(width, t.widthAtDepth(i));
        }
        return width;
    }

    /* Better implementation of maxWidth. Caches results in an array.
     * Takes time proportional to the size of t. */
    static int maxWidthImplementationTwo(SharingTree t) {
        // For each integer d, 0 <= d <= maximum depth of t, store in
        // widths[d] the number of nodes at depth d in t.
        // The calculation is done by calling recursive procedure addToWidths.
        int[] widths = new int[t.maxDepth() + 1];   // initially, contains 0's
        t.addToWidths(0, widths);

        int max = 0;
        for (int width : widths) {
            max = Math.max(max, width);
        }
        return max;
    }

    /* For each node of this SharingTree that is at some depth d in this
     * SharingTree add 1 to widths[depth + d]. */
    private void addToWidths(int depth, int[] widths) {
        widths[depth]++;        //the root of this SharintTree is at depth d = 0
        for (SharingTree dt : children) {
            dt.addToWidths(depth + 1, widths);
        }
    }

    /* Better implementation of maxWidth. Caches results in a HashMap.
     * Takes time proportional to the size of t. */
    static int maxWidthImplementationThree(SharingTree t) {
        // For each possible depth d >= 0 in tree t, widthMap will contain the
        // entry (d, number of nodes at depth d in t). The calculation is
        // done using recursive procedure addToWidthMap.

        // For each integer d, 0 <= d <= maximum depth of t, add to
        // widthMap an entry <d, 0>.
        HashMap<Integer, Integer> widthMap = new HashMap<>();
        for (int d= 0; d <= t.maxDepth() + 1; d= d+1) {
            widthMap.put(d, 0);
        }

        t.addToWidthMap(0, widthMap);

        int max= 0;
        for (Integer w : widthMap.values()) {
            max= Math.max(max, w);
        }
        return max;
    }

    /* For each node of this SharingTree that is at some depth d in this SharingTree,
     * add 1 to the value part of entry <depth + d, ...> of widthMap. */
    private void addToWidthMap(int depth, HashMap<Integer, Integer> widthMap) {
        widthMap.put(depth, widthMap.get(depth) + 1);  //the root is at depth d = 0
        for (SharingTree dt : children) {
            dt.addToWidthMap(depth + 1, widthMap);
        }
    }

    /** Return the route the Post took to get from "here" (the root of
     * this SharingTree) to child c.
     * Return null if no such route.
     * For example, for this tree:
     * <p>
     * Depth level:
     * 0      A
     *       / \
     * 1    B   C
     *     /   / \
     * 2  D   E   F
     *             \
     * 3            G
     * <p>
     * A.getDiseaseRoute(E) should return a list of [A, C, E].
     * A.getDiseaseRoute(A) should return [A].
     * A.getDiseaseRoute(X) should return null.
     * <p>
     * B.getDiseaseRoute(C) should return null.
     * B.getDiseaseRoute(D) should return [B, D]
     */
    public List<Person> getSharingRoute(Person c) {
        //TODO 5

    	LinkedList<Person> sharingRoute = new LinkedList<Person>();
    	sharingRoute.add(root);

    	if (this.root == c) { return sharingRoute; }
    	else if (!this.contains(c)) { return null; }
    	else {
    		for (SharingTree st : children) {
        		if(st.contains(c)) {
        			sharingRoute.addAll(st.getSharingRoute(c));
        		}
        	}
    	}
    	return sharingRoute;
    }

    /** Return the immediate parent of c (null if c is not in this
     * SharingTree).
     * <p>
     * Thus, for the following tree:
     * Depth level:
     * 0      A
     *       / \
     * 1    B   C
     *     /   / \
     * 2  D   E   F
     *     \
     * 3    G
     * <p>
     * A.getParent(E) returns C.
     * C.getParent(E) returns C.
     * A.getParent(B) returns A.
     * E.getParent(F) returns null.
     */
    public Person getParent(Person c) {
        // Base case
        for (SharingTree dt : children) {
            if (dt.root == c) return root;
        }

        // Recursive case - ask children to look
        for (SharingTree dt : children) {
            Person parent = dt.getParent(c);
            if (parent != null) return parent;
        }

        return null; //Not found
    }

    /** If either child1 or child2 is null or is not in this SharingTree, return null.
     * Otherwise, return the person at the root of the smallest subtree of this
     * SharingTree that contains child1 and child2.
     * <p>
     * Examples. For the following tree (which does not contain H):
     * <p>
     * Depth level:
     * 0      A
     *       / \
     * 1    B   C
     *     /   / \
     * 2  D   E   F
     *     \
     * 3    G
     * <p>
     * A.getSharedAncestor(B, A) is A
     * A.getSharedAncestor(B, B) is B
     * A.getSharedAncestor(B, C) is A
     * A.getSharedAncestor(A, C) is A
     * A.getSharedAncestor(E, F) is C
     * A.getSharedAncestor(G, F) is A
     * B.getSharedAncestor(B, D) is B
     * B.getSharedAncestor(B, A) is null
     * B.getSharedAncestor(D, F) is null
     * B.getSharedAncestor(D, H) is null
     * A.getSharedAncestor(null, C) is null
     */
    public Person getSharedAncestor(Person child1, Person child2) {
        //TODO 6
    	if (child1 == null || child2 == null ||
    		!this.contains(child1) || !this.contains(child2)) {
    		return null;
    	}

    	List<Person> route1 = getSharingRoute(child1);
    	List<Person> route2 = getSharingRoute(child2);
    	for (int i= Math.min(route1.size(), route2.size())-1; i>= 0; i--) {
    		if(route1.get(i) == route2.get(i)) { return route1.get(i); }
    	}
        return null;
    }

    /** Return a (single line) String representation of this SharingTree.
     * If this SharingTree has no children (it is a leaf), return the root's substring.
     * Otherwise, return
     * root's substring + SEPARATOR + START_CHILDREN_DELIMITER + each child's
     * toString, separated by DELIMITER, followed by END_CHILD_DELIMITER.
     * Make sure there is not an extra DELIMITER following the last child.
     * <p>
     * Finally, make sure to use the static final fields declared at the top of
     * SharingTree.java.
     * <p>
     * Thus, for the following tree:
     * Depth level:
     * 0      A
     *       / \
     * 1    B   C
     *     /   / \
     * 2  D   E   F
     *         \
     * 3        G
     */
    public String toString() {
        if (children.isEmpty()) return root.toString();
        String s = root.toString() + SEPARATOR + START_CHILDREN_DELIMITER;
        for (SharingTree dt : children) {
            s = s + dt.toString() + DELIMITER;
        }
        return s.substring(0, s.length() - 2) + END_CHILDREN_DELIMITER;
    }


    /** Return a verbose (multi-line) string representing this SharingTree. */
    public String toStringVerbose() {
        return toStringVerbose(0);
    }

    /** Return a verbose (multi-line) string representing this SharingTree.
     * Each person in the tree is on its own line, with indentation representing
     * what each person is a child of.
     * indent is the the amount of indentation to put before this line.
     * Should increase on recursive calls to children to create the above pattern.
     * Thus, for the following tree:
     * Depth level:
     * 0      A
     *       / \
     * 1    B   C
     *     /   / \
     * 2  D   E   F
     *         \
     * 3        G
     * <p>
     * A.toStringVerbose(0) could return (the state BORED could be different):
     * (A) - BORED
     *    (C) - BORED
     *       (F) - BORED
     *       (E) - BORED
     *         (G) - BORED
     *    (B) - BORED
     *        (D) - BORED
     * <p>
     * Make sure to use VERBOSE_SPACE_INCREMENT for indentation.
     */
    private String toStringVerbose(int indent) {
        String s = "";
        for (int i = 0; i < indent; i++) {
            s = s + VERBOSE_SPACE_INCREMENT;
        }
        s = s + root.toString();

        if (children.isEmpty()) return s;

        for (SharingTree dt : children) {
            s = s + "\n" + dt.toStringVerbose(indent + 1);
        }
        return s;
    }

    /** Return true iff this is equal to ob. Here's details:
     * Two SharingTrees are equal if they are the same object (==) OR:
     * <br> - 1. they are of the same class and
     * <br> - 2. they have the same root Person object (==) and
     * <br> - 3. their children sets are the same size and
     * <br> - 4. their children sets are equal, which, since their sizes
     * <br>      are equal, requires:
     * <br>      for every SharingTree dt in one set there is a SharingTree dt2
     * <br>      in the other set for which dt.equals(dt2) is true.
     * <p>
     * Otherwise the two SharingTrees are not equal.
     * Do not use any of the toString functions to write equals(). */
    public boolean equals(Object ob) {
        //TODO 7
    	if (this == ob) { return true; }

    	if (ob instanceof SharingTree &&
    		root == ((SharingTree) ob).getRoot() &&
    		getChildrenCount() == ((SharingTree) ob).getChildrenCount()) {
    		if(getChildrenCount() == 0) { return true; }

    		for (SharingTree st : children) {
    			for (SharingTree st2 : ((SharingTree) ob).getChildren()) {
    				if (st.equals(st2)) {
    					return true;
    				}
    			}
    		}
    	}

        return false;

    }

    /** return a hashCode for this object. For the equals function
     * implemented above, it must be the case that if t1 and t2 are
     * equal, then t1.hashCode == t2.hashCode must be true.
     * The hashcode is the hashCode of the name of the root Person,
     * calculated once when this tree is created. Remember: each
     * Person can appear in at most one node of a tree.
     */
    public @Override int hashCode() {
        return hashCode;
    }
}
