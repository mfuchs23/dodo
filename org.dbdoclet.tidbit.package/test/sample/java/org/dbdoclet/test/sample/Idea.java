package org.dbdoclet.test.sample;


/**
 * The class <code>Idea</code> is a sample documentation class, which
 * tries to use as many features of the javadoc world as possible.
 *
 * <h2>Test of @link tag</h2>
 * <p>The super class is of type {@link Thought Thought}.
 * This class implements the interface {@link org.dbdoclet.test.sample.Thinkable}.
 * Therefore it implements the method {@link Thinkable#think think}.</p>
 *
 * @author <a href ="mailto:mfuchs@unico-consulting.com">Michael Fuchs</a>
 * @version 1.0
 */
public class Idea extends Thought implements Thinkable {

    /**
     * Der Wert von NO_IDEA ist {@value}.
     */
    public static final String NO_IDEA = "";

    /**
     * Der Wert von MORE_IDEAS ist {@value}.
     */
    public static final String MORE_IDEAS = ">";

    /**
     * Der Wert von LESS_IDEAS ist {@value}.
     */
    public static final String LESS_IDEAS = "<";
    
    protected String idea1;

    private Idea(String idea1, String idea2, String idea3, String idea4) {

    }

    Idea(String idea1, String idea2, String idea3) {

    }

    protected Idea(String idea1, String idea2) {

    }

    /**
     * Creates a new <code>Idea</code> instance.
     *
     * @param idea1 a <code>String</code> value
     */
    public Idea(String idea1) {

    }

    public Idea() {
    	
    }

    /**
     * Tag code {@code A<B>
Neue Zeile
C}.
     * Tag literal {@literal A<B>
Neue Zeile.
C}.
     *
     * @param what <code>String</code>
     */
    public void think(String what) {
    }

    /**
     * The method <code>think</code> should be used as often as
     * possible.
     *
     * This method implements the Interface {@link Thinkable#think Thinkable}.
     *
     * @param what {@link Hint Hint} A hint what should be thought of.
     * @return {@link Hint Hint} A new hint.
     * @exception BeerException If a fresh {@link Beer beer} is served.
     */
    public Hint think(Hint what) throws BeerException {

        return new Hint();
    }

    protected Hint thinkTwice(Hint what) throws BeerException {
        return new Hint();
    }

    /**
     * The method <code>isBoring</code>.
     *
     * Papa says: {@inheritDoc}.
     * Der Wert von MORE_IDEAS ist "{@value #MORE_IDEAS}".
     * Der Link zu MORE_IDEAS ist "{@link #MORE_IDEAS MORE_IDEAS}".
     *
     * @param meeting a <code>Thought</code> value
     * @return a <code>boolean</code> value
     * @exception DreamingException if an error occurs
     */
    public boolean isBoring(Thought meeting) throws DreamingException {

        return true;
    }
}
