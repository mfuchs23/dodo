package org.dbdoclet.test.sample;

public interface Thinkable {

    /**
     * The method <code>think</code> should be used as often as
     * possible.
     *
     * {@link "Sokrates:"} I know  something!
     *
     * @since 1.0
     * @deprecated Use dumb instead
     * @throws BeerException
     * @param what {@link String String} A hint what should be thought of.
     * @see org.dbdoclet.test.sample.Beer
     * @return void
     */
    public void think(String what) throws BeerException;
}
