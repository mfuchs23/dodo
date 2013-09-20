package org.dbdoclet.test.sample;

public class Brainstorm extends Idea implements Comparable {

    public Brainstorm(String idea1) {
        super(idea1);
    }

    public boolean isBoring(Thought meeting) throws DreamingException {

        return true;
    }

    /**
     * Die Methode <code>compareTo</code> aus der Schnittstelle
     * <code>java.lang.Comparable</code>.
     *
     * @param o <code>Object</code>
     * @return <code>int</code>
     */
    @SuppressWarnings(value={"unchecked"}) public int compareTo(Object o) {

        return 0;
    }
}
