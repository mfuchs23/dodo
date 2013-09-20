/*
 * ### Copyright (C) 2001-2005 Michael Fuchs ###
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * Author: Michael Fuchs
 * E-Mail: mfuchs@unico-consulting.com
 *
 * RCS Information:
 * ---------------
 * Id.........: $Id: TableTests.java,v 1.1.1.1 2004/12/21 14:00:03 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:00:03 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.test.transform;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;


public class TableTests extends XmlTestCase {

    /**
     * The variable <code>logger</code>{@link org.apache.log4j.Logger (org.apache.log4j.Logger)}
     * can be used to print logging information via the log4j framework.
     */
    private static Log logger = LogFactory.getLog(TableTests.class);

    public static Test suite() {

        return new TestSuite(TableTests.class);
    }

    public void testTransformTable1() throws Exception {

        String code = "\n" + "<table border=1>\n" +
            "  <tr><td width=\"20pt\">F1</td><td width=\"60pt\">find-tag</td></tr>\n" +
            "</table>\n";
        checkDocBook(code, true);
    }

    public void testTransformTable2() throws Exception {

        String code = "\n" + "<table border=1>\n" +
            "  <tr><th width=\"30%\" align=\"center\">TH1</th><th width=\"70%\" align=\"right\">TH2</th></tr>\n" +
            "  <tr><td>Column 1-1</td><td>Column 1-2</td></tr>\n" +
            "</table>\n";
        checkDocBook(code, true);
    }

    public void testTransformTable3() throws Exception {

        String code = "\n" + "<table border=1>\n" +
            "  <caption>Test Table</caption>\n" +
            "  <tr><th>Taste</th><th>Wert</th></tr>\n" +
            "  <tr><td>F1</td><td>find-tag</td></tr>\n" +
            "  <tr><td>F2</td><td>jde-complete-at-point-menu</td></tr>\n" +
            "  <tr><td>F3</td><td>chdir-project-dir-tag</td></tr>\n" +
            "  <tr><td>F4</td><td>goto-line</td></tr>\n" + "</table>\n";
        checkDocBook(code, true);
    }

    public void testTransformTable4() throws Exception {

        String code = "\n" + "<table border=1>\n" +
            "  <tr><td align=\"char\" char=\",\" charOff=\"10\">0,123435</td</tr>\n" +
            "  <tr><td align=\"char\" char=\",\" charOff=\"10\">12345,0</td></tr>\n" +
            "</table>\n";
        checkDocBook(code, true);
    }

    public void testTransformTable5() throws Exception {

        String code = "\n" + "<table border=1>\n" +
            "  <tr><td valign=\"top\">top</td</tr>\n" +
            "  <tr><td valign=\"middle\">middle</td></tr>\n" +
            "  <tr><td valign=\"bottom\">bottom</td></tr>\n" + "</table>\n";
        checkDocBook(code, true);
    }

    public void testTransformTable6() throws Exception {

        String code = "\n" + "<table border=1>\n" +
            "  <tr valign=\"top\"><td>top</td</tr>\n" +
            "  <tr valign=\"middle\"><td>middle</td></tr>\n" +
            "  <tr valign=\"bottom\"><td>bottom</td></tr>\n" + "</table>\n";
        checkDocBook(code, true);
    }

    public void testTransformTable7() throws Exception {

        String code = "\n" + "<table border=1>\n" +
            "  <caption>JUnit Test TransformTableSimple</caption>\n" +
            "  <tr><th>Taste</th><th>Wert</th></tr>\n" +
            "  <tr><td>F1</td><td>find-tag</td></tr>\n" +
            "  <tr><td>F2</td><td>jde-complete-at-point-menu</td></tr>\n" +
            "  <tr><td>F3</td><td>chdir-project-dir-tag</td></tr>\n" +
            "  <tr><td>F4</td><td>goto-line</td></tr>\n" + "</table>\n";
        checkDocBook(code, true);
    }

    public void testTransformTable8() throws Exception {

        String code = "\n" + "<table border=1>\n" +
            "  <caption>JUnit Test TransformTableSimple</caption>\n" +
            "  <tr><th>Taste</th><th>Wert</th></tr>\n" +
            "  <tr><td>Label</td><td>Source <pre>int i;</pre></td></tr>\n" +
            "</table>\n";
        checkDocBook(code, true);
    }

    public void testTransformTable9() throws Exception {

        String code = "\n" + "<table border=1>\n" +
            "  <tr><th>left</th><th colspan=\"2\">colspan</th><th>right</th></tr>\n" +
            "  <tr><td>cell 1</td><td>cell 2</td><td>cell 3</td><td>cell 4</td></tr>\n" +
            "  <tr><td>left</td><td colspan=\"2\" align=\"center\">colspan</td><td>right</td></tr>\n" +
            "  <tr><td>cell 1</td><td>cell 2</td><td>cell 3</td><td>cell 4</td></tr>\n" +
            "</table>\n";
        checkDocBook(code, true);
    }

    public void testTransformTable10() throws Exception {

        String code = "\n" + "<table border=1>\n" +
            "  <tr><th>cell 1</th><th>cell 2</th><th>cell 3</th><th>cell 4</th></tr>\n" +
            "  <tr><td rowspan=\"3\">cell 1</td><td>cell 2</td><td>cell 3</td><td>cell 4</td></tr>\n" +
            "  <tr><td>cell 2</td><td>cell 3</td><td>cell 4</td></tr>\n" +
            "  <tr><td>cell 2</td><td>cell 3</td><td>cell 4</td></tr>\n" +
            "</table>\n";
        checkDocBook(code, true);
    }

    public void testTransformTable11() throws Exception {

        String code = "<table>\n" + "  <tr>\n" + "    <td>Column 1</td>\n" +
            "    <td>\n" + "      <table>\n" + "        <tr>\n" +
            "          <td>Table 2 Column 1</td>\n" + "        </tr>\n" +
            "     </table>\n" + "    </td>\n" + "    <td>Column 2</td>\n" +
            "  </tr>\n" + "</table>\n";
        checkDocBook(code, true);
    }

    public void testTransformTable12() throws Exception {

        String code = "\n" + "<table border=1>\n" +
            "  <tr><th><p>table header</th></tr>\n" + "</table>\n";
        checkDocBook(code, true);
    }

    public void testTransformTable13() throws Exception {

        String code = "<table>\n" + "  <tr>\n" + "    <td>Column 1</td>\n" +
            "    <td>\n" + "      <table>\n" + "        <tr>\n" +
            "          <td>\n" + "            <table>\n" +
            "              <tr>\n" + "                <td>\n" +
            "                  <p>Deeply Nested</p>\n" +
            "                </td>\n" + "              </tr>\n" +
            "            </table>\n" + "            Free text.\n" +
            "            <a href=\"href\">Anchor</a>\n" + "          </td>\n" +
            "        </tr>\n" + "      </table>\n" + "    </td>\n" +
            "    <td>Column 2</td>\n" + "  </tr>\n" + "</table>\n";

        checkDocBook(code, true);
    }

    public void testTransformTable14() throws Exception {

        String code = "<table border=\"0\">\n" +
            "<tbody><tr align=\"center\">\n" +
            "<!-- www.heise.de/navi/Homepage@Left1 -->\n" + "</tr>\n" +
            "</tbody></table>\n";

        checkDocBook(code, true);
    }

    public void testTransformTable15() throws Exception {

        String code = "<table border=1>\n" +
            "<caption><a name=\"fillerapi\">Creating Space Fillers</a>\n" +
            "<br>\n" +
            "<em>These methods are defined in the <code>Box</code> class.</em></caption>\n" +
            "<tr>\n" + "<th>Constructor or Method</th>\n" +
            "<th>Purpose</th>\n" + "</tr>\n" + "</table>";

        checkDocBook(code, true);
    }

    public void testTransformTable16() throws Exception {

        String code = "<table id=\"table14\">\n" +
            "<tr>" + "<td>Constructor or Method</td>" +
            "<td>Purpose</td>\n" + "</tr>\n" + "</table>";

        checkDocBook(code, true);
    }
}

/*
 * $Log: TableTests.java,v $
 * Revision 1.1.1.1  2004/12/21 14:00:03  mfuchs
 * Reimport
 *
 * Revision 1.2  2004/10/05 13:13:18  mfuchs
 * Sicherung
 *
 * Revision 1.1.1.1  2004/02/17 22:49:26  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2004/01/05 14:57:00  cvs
 * dbdoclet
 *
 * Revision 1.2  2003/11/30 20:43:44  cvs
 * Many fixes. Command line options.
 *
 * Revision 1.1.1.1  2003/08/01 13:18:33  cvs
 * DocBook Doclet
 *
 * Revision 1.1.1.1  2003/07/31 17:05:40  mfuchs
 * DocBook Doclet since 0.46
 *
 * Revision 1.1.1.1  2003/05/30 11:09:40  mfuchs
 * dbdoclet
 *
 * Revision 1.2  2003/03/25 21:34:37  mfuchs
 * New test number 17.
 *
 * Revision 1.1.1.1  2003/03/18 07:41:37  mfuchs
 * DocBook Doclet 0.40
 *
 * Revision 1.1.1.1  2003/03/17 20:50:47  cvs
 * dbdoclet
 *
 */
