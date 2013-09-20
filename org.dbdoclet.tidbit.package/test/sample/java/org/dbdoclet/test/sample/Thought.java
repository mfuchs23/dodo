package org.dbdoclet.test.sample;


/**
 * The class <code>Thought</code> is the superclass for the most sample
 * documentation classes.
 *
 * @author <a href="mailto:mfuchs@unico-consulting.com">Michael Fuchs</a>
 * @version 1.0
 */
@Service("Sleep")
@Todo("Wake up")
public class Thought {

    public enum Categories { FOOD, SEX, SLEEP, VACATION };

    @Service("Bring' ma an Schweinsbron!")
    public String bringMe = "";

    /**
     * The method <code>isErotic</code> is a private method which is
     * true, if the thought is erotic.
     *
     * This methods tests if private comments are processed.
     *
     * @return a <code>boolean</code> value
     */
    private boolean isErotic() {

        return true;
    }

    /**
     * The method <code>isBoring</code> is true if a thought is
     * totally boring.
     *
     * @param meeting a {@link Thought} value
     * @return a <code>boolean</code> value
     * @exception DreamingException if an error occurs
     */
    public boolean isBoring(Thought meeting) throws DreamingException {

        return true;
    }
}
