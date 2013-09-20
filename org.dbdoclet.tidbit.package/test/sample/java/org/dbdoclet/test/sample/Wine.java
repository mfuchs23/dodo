package org.dbdoclet.test.sample;

/**
 * The class <code>Wine</code> represents a fresh cold beer.
 *
 * @author <a href="mailto:mfuchs@unico-consulting.com">Michael Fuchs</a>
 * @deprecated As of Party 1.1, replaced by {@link Beer}.
 * @version 1.0
 */
public class Wine extends Drink {

    /**
     * The field <code>glasses</code> defines how much glasses of beer
     * you should drink..
     *
     * @since 1.2
     * @serialField field1 java.lang.String Test serialField.
     * @serialData Test serialData.
     * @serial How many glasses of wine a person drunk.
     * @deprecated As of API 1.1, ...
    */
    protected int glasses = 2;

    /**
     * Creates a new <code>Wine</code> instance.
     *
     * @throws BeerException If an input exception occured.
     * @param brand a <code>String</code> value
     * @deprecated As of API 1.1, ...
     */
    public Wine(String brand) throws BeerException {

    }

    public Wine() {
		
	}
    
    /**
     * The method <code>drink</code>.
     *
     * @see #isDrunk()
     * @since 1.0
     * @param liters a <code>long</code> value
     * @return a <code>boolean</code> value
     * @deprecated As of API 1.1, ...
     */
    public boolean drink(long liters) {

        return true;
    }

    /**
     * The method <code>isDrunk</code>.
     *
     * @return a <code>boolean</code> value
     */
    public boolean isDrunk() {

        return true;
    }

    /**
     * The method <code>sing</code>.
     *
     * <p>
     * @see Beer Beer
     * </p>
     */
    public void sing() {

    }
}
