package org.dbdoclet.test.sample;


/**
 * The class <code>Hint</code> is a sample documentation class, which
 * tries to use as many features of the javadoc world as possible.
 *
 * @author <a href="mailto:mfuchs@unico-consulting.com">Michael Fuchs</a>
 * @version 1.0
 */
public class Hint implements Thinkable {

    /**
     * The method <code>giveOne</code>.
     *
     * @dreamer Perhaps you have an <code>Idea</code> ({@link Idea}).
     * @return a <code>Hint</code> value
     */
    public Hint giveOne() {

        return new Hint();
    }

    /**
     * Nothing returns !
     *
     * Hint:before {@inheritDoc} Hint:after
     *
     * @see "DocBook Doclet Tutorial"
     * @param what <code>String</code>
     */
    public void think(String what) {

    }
}
