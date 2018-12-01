/** An instance maintains info about the PhD of a person. */

public class PhD {
	private String name; // name of person with PhD, length > 0
	private int month; // month PhD was awarded, in range 1..12
	private int year; // year PhD was awarded
	private char gender; // gender of person. 'g' for girl, 'b' for boy
	private PhD advisor1; // first PhD advisor of person (null if unknown)
	private PhD advisor2; // second PhD advisor of person
	private int advisees; // no. of advisees of person

	/** Constructor: an instance for a person with name n, PhD month m,
	 * PhD year y, and gender g. Its advisors are unknown, and it has no
	 * advisees.
	 * Precondition: n has at least 1 char. m is in 1..12.
	 * g is 'g' for girl or 'b' for boy.*/
	public PhD(String n, int m, int y, char g) {
		assert n != null && n.length() >= 1;
		assert 1 <= m && m <= 12;
		assert g == 'g' || g == 'b';

		name = n;
		month = m;
		year = y;
		gender = g;
		advisor1 = null;
		advisor2 = null;
		advisees = 0;
	}

	/** Constructor: a PhD with name n, PhD month m, PhD year y, gender g, first
	 * advisor adv, and no second advisor.
	 * Precondition: n has at least 1 char, m is in 1..12, g is 'g' for girl or
	 * 'b' for boy, and adv is not null.*/
	public PhD(String n, int m, int y, char g, PhD adv) {
		assert n != null && n.length() >= 1;
		assert 1 <= m && m <= 12;
		assert g == 'g' || g == 'b';
		assert adv != null;

		name = n;
		month = m;
		year = y;
		gender = g;
		advisor1 = adv;
		advisor2 = null;
		advisees = 0;

		adv.advisees += 1;
	}

	/** Constructor: a PhD with name n, PhD month m, PhD year y, gender g,
	 * first advisor adv1, and second advisor adv2.
	 * Precondition: n has at least 1 char, m is in 1..12, g is 'g' for girl or
	 * 'b' for boy, adv1 and adv2 are not null, and adv1 and adv2 are different.*/
	public PhD(String n, int m, int y, char g, PhD adv1, PhD adv2) {
		assert n != null && n.length() >= 1;
		assert 1 <= m && m <= 12;
		assert g == 'g' || g == 'b';
		assert adv1 != null && adv2 != null && adv1 != adv2;

		name = n;
		month = m;
		year = y;
		gender = g;
		advisor1 = adv1;
		advisor2 = adv2;
		advisees = 0;

		adv1.advisees += 1;
		adv2.advisees += 1;
	}

	/** Return the name of this person. */
	public String name() {
		return name;
	}

	/** Return the month this person got their PhD. */
	public int month() {
		return month;
	}

	/** Return the year this person got their PhD. */
	public int year() {
		return year;
	}

	/** Return the value of the sentence "This person is male." */
	public boolean isMale() {
		return (gender == 'b');
	}

	/** Return the first advisor of this PhD (null if unknown). */
	public PhD advisor1() {
		return advisor1;
	}

	/** Return the  second advisor of this PhD (null if unknown or
	 * non-existent). */
	public PhD advisor2() {
		return advisor2;
	}

	/** Return the number of PhD advisees of this person. */
	public int numAdvisees() {
		return advisees;
	}

	/** Add p as the first advisor of this person.
	 * Precondition: the first advisor is unknown and p is not null.*/
	public void addAdvisor1(PhD p) {
		assert advisor1 == null && p != null;

		advisor1 = p;
		p.advisees += 1;
	}

	/** Add p as the second advisor of this person.
	 * Precondition: the first advisor (of this person) is known, the second
	 * advisor is unknown, p is not null, and p is different from the first advisor.*/
	public void addAdvisor2(PhD p) {
		assert advisor1 != null && advisor2 == null;
		assert p != null && p != advisor1;

		advisor2 = p;
		p.advisees += 1;
	}

	/** Return value of “p is not null and p got the PhD before this person.” */
	public boolean gotFirst(PhD p) {
		return (year == p.year && month < p.month) || (year < p.year);
	}

	/** Return value of “this person and p are intellectual siblings.”
	 * Precondition: p is not null.*/
	public boolean arePhDSiblings(PhD p) {
		assert p != null;
		return this != p //p and this person are not the same
				&& ( ( (advisor1 == p.advisor1 || advisor1 == p.advisor2)
						&& advisor1 != null)
				|| ( (advisor2 == p.advisor2 || advisor2 == p.advisor1)
						&& advisor2 != null) ); // have a common non-null advisor
	}
}
