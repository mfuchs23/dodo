package test;

import java.io.Serializable;

/**
 * Die Klasse <code>SimpleClass</code> .
 *
 * Einführung in die Klasse SimpleClass.
 *
 * @author Testmarke @author
 * @todo Testmarke @todo
 * @version Testmarke @version
 */
public class SimpleClass 
    implements Serializable {

    /**
     * Das Feld number.
     *
     * @serial Testmarke @serial
     * @serialField number int Testmarke @serialField
     * @todo Testmarke @todo
     */
    public int number = 0;

    /**
     * Die Methode <code>publicMethod</code> .
     *
     * <pre>
12345678901234567890123456789012345678901234567890
         1         2         3         4         5
     * </pre>
     *
     * <table>
     * <tr><td>Reihe 1 Spalte 1</td><td>Reihe 1 Spalte 2</td></tr>
     * <tr><td>Reihe 2 Spalte 1</td><td>Reihe 2 Spalte 2</td></tr>
     * </table>
     *
     * @author Testmarke @author
     * @deprecated Testmarke @deprecated
     * @exception Exception Testmarke @exception
     * @param number Testmarke @param
     * @return Testmarke @return
     * @see "Testmarke @see"
     * @since Testmarke @since
     * @throws Exception Testmarke @throws
     * @todo Testmarke @todo
     */
    public boolean publicMethod(int number)
        throws Exception {
    	
    	return true;
    }
}
