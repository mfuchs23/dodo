package org.dbdoclet.test.sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;


/**
 * The class <code>Beer</code> represents a fresh cold beer.
 *
 * @author <a href ="mailto:mfuchs@unico-consulting.com">Michael Fuchs</a>
 * @author Brewery
 * @author Milk and alcohol
 * @version 1.0
 * @todo Test custom tags
 */
@Todo("Drink less beer")
public class Beer extends Drink 
    implements Serializable, MouthListener, Drinkable {

    /**
     * The field <code>glasses</code> defines how much glasses of beer
     * you should drink.
     *
     *日本語: ビール
     *
     * @serial The number of glasses.
     * @serialData serialData
     * @serialField name type description
     */
    protected int glasses = 2;

    private String brewery;

    /** Generics flavours */
    private ArrayList<String>[] flavours;

    /** Generics flavourMap */
    private HashMap<String,String> flavourMap;

    /**
     * <literal>The</literal> method <code>drink</code>.
     *
     * <h1>Don't use this method too often</h1>
     * <p>Hello</p>
     *
     * A simple example of using the class is:
     *
     * <blockquote><pre>
     * &Auml;
     * TestHttpRequest&nbsp;req = new
     * TestHttpRequest("http://localhost:7001/migrationbundle/bundleinfo?vendorId=MBVendor&webtarget=default&audience=au0");
     * </pre></blockquote>
     *
     * @deprecated
     *
     * @see "DocBook Doclet Tutorial"
     * @see <a href="http://www.dbdoclet.org">dbdoclet Home</a>
     * @see org.dbdoclet.test.sample
     * @see org.dbdoclet.test.sample.leg.Toe
     * @see org.dbdoclet.test.sample A label
     * @see org.dbdoclet.test.sample.leg.Toe A label
     */
    @Todo("Drink less")
    public void drink() {
        
    }
    
    /**
     * The method <code>drink</code> is used to test link tags.
     *
     * <p>Named package link {@link Beer Some label text} and named class linkplain
     * {@linkplain Beer Some label text}. An unknown link {@link org.dbdoclet.time.Free}.</p>
     *
     * <p>An JPG image:</p>
     * <img src="doc-files/unico.jpg">.
     *
     * <p>An GIF image:</p>
     * <img src="doc-files/unico.gif">.
     *
     * <p>An PNG image:</p>
     * <img src="doc-files/unico.png">.
     *
     * @param num an <code>int</code> value
     */
    public void drink(int num) {

    }

    /**
     * The method <code>drink</code>.
     *
     */
    public void drink(ArrayList<String> drinks) {
    }
}


/**
 * Package private class
 */
class BeerDepot {

}
