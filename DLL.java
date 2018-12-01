package LinkedList;

/** An instance is a doubly linked list. */
public class DLL<E> {
    private int size;   // Number of values in the linked list.
    private Node first; // first node of linked list (null if none)
    private Node last;  // last node of linked list (null if none)

    /** Constructor: an empty linked list. */
    public DLL() {
    }

    /** Return the number of values in this list.
     *  This function takes constant time. */
    public int size() {
        return size;
    }

    /** Return the first node of the list (null if the list is empty). */
    public Node getFirst() {
        return first;
    }

    /** Return the last node of the list (null if the list is empty). */
    public Node getLast() {
        return last;
    }

    /** Return the value of node n of this list.
     * Precondition: n is a node of this list; it may not be null. */
    public E valueOf(Node n) {
        assert n != null;
        return n.val;
    }

    /** Return a representation of this list: its values, with adjacent
     * ones separated by ", ", "[" at the beginning, and "]" at the end. <br>
     * Takes time proportional to the length of this list.<br>
     * E.g. for the list containing 6 3 8 in that order, the result it "[6, 3, 8]". */
    public String toString() {
        String res= "[";
        Node n= first;
        // inv: res contains values of nodes before node n (all of them if n = null),
        //      with ", " after each (except for the last value)
        while (n != null) {
            res= res + n.val;
            n= n.next;
            if (n != null) {
                res= res + ", ";
            }
        }

        return res + "]";
    }

    /** Return a representation of this list: its values in reverse, with adjacent
     * ones separated by ", ", "[" at the beginning, and "]" at the end. <br>
     * Takes time proportional to the length of this list.
     * E.g. for the list containing 6 3 8 in that order, the result is "[8, 3, 6]".
     * E.g. for the list containing ""  "" in that order, the result is "[ , ]". */
    public String toStringRev() {
        //TODO 1. Implement me

    	String res= "[";
        Node n= last;

        if (n == null && first != null) {
        	return res + first.val + "]";
        }
        while (n != null) {
            res= res + n.val;
            n= n.prev;
            if (n != null) {
                res= res + ", ";
            }
        }

        return res + "]";
    }

    /** add value v in a new node at the end of the list.
     *  This operation takes constant time. */
    public void append(E v) {
        //TODO 2. Implement me
    	if (first == null) {
        	Node n = new Node(null, null, v);
        	first = n;
    	} else if (last == null){
    		Node n = new Node(first, null, v);
    		first.next = n;
    		last = n;
    	} else {
    		Node n = new Node(last, null, v);
    		last.next = n;
    		last = n;
    	}
    	size += 1;
    }

    /** Add value v in a new node at the beginning of the list.
     * This operation takes constant time. */
    public void prepend(E v) {
    	Node n = new Node(null, first, v);
    	if (first == null) {
    		first = n;
    	} else if (last == null){
        	first.prev = n;
        	last = first;
        	first = n;
    	} else {
    		first.prev = n;
    		first = n;
    	}
    	size += 1;
    }

    /** Return node number k.
     *  Precondition: 0 <= k < size of the list.
     *  Example. Suppose list is [8, 6, 7].
     *  If k is 0, return first node; if k = 1, return second node, ... */
    public Node getNode(int k) {
        //TODO 4. Method should take time proportional to min(k, size-k).
        // For example, if k <= size/2, search from the beginning of the
        // list, otherwise search from the end of the list.
        assert (0 <= k) && (k < size);
        Node n = first;

        if (k <= size/2) {
        	for (int i=0; i < k; i++) {
        		n = n.next;
        	}
        } else {
        	n = last;
        	for (int i=0; i < size-1-k; i++) {
        		n = n.prev;
        	}
        }
        return n;
    }

    /** Insert value v in a new node after node n.
     * This operation takes constant time.
     * Precondition: n must be a node of this list; it may not be null. */
    public void insertAfter(E v, Node n) {
        //TODO 5. Method should take constant time.
    	//case 1: (last == null) m becomes last
    	//case 2: (last != null && n == last) m becomes last
    	//case 3: (last != null && n != last) m in the middle
        assert n != null;

        if (last == null) {
        	Node m = new Node(first, null, v);
        	first.next = m;
        	last = m;
        } else if (last != null && n == last) {
        	Node m = new Node(n, null, v);
        	n.next = m;
        	last = m;
        } else {
        	Node m = new Node(n, n.next, v);
        	n.next.prev = m;
        	n.next = m;
        }
        size += 1;
    }

    /** Insert value v in a new node before node n.
     * This operation takes constant time.
     * Precondition: n must be a node of this list; it may not be null. */
    public void insertBefore(E v, Node n) {
        //TODO 6. Method should take constant time.
    	//case 1: (last == null) m becomes first, first becomes last
    	//case 2: (last != null && n == first) m becomes first
    	//case 3: (last != null && n != first) m in the middle
    	assert n != null;

        if (last == null) {
        	Node m = new Node(null, n, v);
        	n.prev = m;
        	last = n;
        	first = m;
        } else if (last != null && n == first) {
        	Node m = new Node(null, first, v);
        	n.prev = m;
        	first = m;
        } else {
        	Node m = new Node(n.prev, n, v);
        	n.prev.next = m;
        	n.prev = m;
        }
        size += 1;
    }

    /** Remove node n from this list.
     * This operation must take constant time.
     * Precondition: n must be a node of this list; it may not be null. */
    public void remove(Node n) {
        //TODO 7. Method should take constant time.
    	//case 1: (n == first && last == null)
    	//case 2: (n == first && last != null)
    	//case 3: (n == last)
    	//case 4: n in the middle
    	assert n != null;
    	assert size >= 1;

    	if (last == null) {
    		first = null;
    	} else if (n == first) {
    		if (n.next == last) {
    			last.prev = null;
    			first = last;
    			last = null;
    		} else {
    			n.next.prev = null;
        		first = n.next;
    		}
    	} else if (n == last) {
    		if (n.prev == first) {
    			first.next = null;
    			last = null;
    		} else {
    			last = n.prev;
    			last.next = null;
    		}
    	} else {
    		n.next.prev = n.prev;
    		n.prev.next = n.next;
    	}
    	size -= 1;
    }

    /*********************/

    /** An instance is a node of this list. */
    public class Node {
        private Node prev; // Previous node on list (null if this is first node)
        private E val;   // The value of this element
        private Node next; // Next node on list. (null if this is last node)

        /** Constructor: an instance with previous node p (can be null),
         * next node n (can be null), and value v. */
        Node(Node p, Node n, E v) {
            prev= p;
            next= n;
            val= v;
        }

        /** Return the value of this node. */
        public E getValue() {
            return val;
        }

        /** Return the node previous to this one (null if this is the
         * first node of the list). */
        public Node prev() {
            return prev;
        }

        /** Return the next node in this list (null if this is the
         * last node of this list). */
        public Node next() {
            return next;
        }
    }

}
