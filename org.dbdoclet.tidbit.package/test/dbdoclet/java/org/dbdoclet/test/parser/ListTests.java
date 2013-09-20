/*
 * ### Copyright (C) 2001-2003 Michael Fuchs ###
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
 * Id.........: $Id: ListTests.java,v 1.1.1.1 2004/12/21 14:00:11 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:00:11 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.test.parser;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;


public class ListTests extends ParserTestCase {

    /**
     * The variable <code>logger</code>{@link org.apache.log4j.Logger (org.apache.log4j.Logger)}
     * can be used to print logging information via the log4j framework.
     */
    private static Log logger = LogFactory.getLog(ListTests.class);

    public ListTests(String name) {
        super(name);
    }

    public static Test suite() {

        return new TestSuite(ListTests.class);
    }

    public void testParseList1() throws Exception {

        String code = "<ul>" + "<li>Listitem #1" + "<li>Listitem #2" +
            "<li>Listitem #3" + "<li>Listitem #4" + "</ul>\n";
        checkHtml(code, 219);
    }

    public void testParseList2() throws Exception {

        String code = "<ul>" + "<li>Listitem #1</li>" + "<li>Listitem #2" +
            "<li>Listitem #3" + "<li>Listitem #4" + "</ul>\n";
        checkHtml(code, 219);
    }

    public void testParseList3() throws Exception {

        String code = "<ul>" + "<li>Listitem #1" + "<li>Listitem #2" +
            "<li>Listitem #3</li>" + "<li>Listitem #4" + "</ul>\n";
        checkHtml(code, 219);
    }

    public void testParseList4() throws Exception {

        String code = "<ul>" + "<li>Listitem #1" + "<li>Listitem #2" +
            "<li>Listitem #3" + "<li>Listitem #4</li>" + "</ul>\n";
        checkHtml(code, 219);
    }

    public void testParseList5() throws Exception {

        String code = "<ol>" + "<li>Listitem #1 <p>" + "<li>Listitem #2 <p>" +
            "<li>Listitem #3 <p>" + "<li>Listitem #4 <p>" + "</ol>\n";
        checkHtml(code, 287);
    }

    public void testParseList6() throws Exception {

        String code = "Some Text followed by a p-tag<p><ol>" +
            "<li>Listitem #1 <p>" + "<li>Listitem #2 <p>" +
            "<li>Listitem #3 <p>" + "<li>Listitem #4 <p>" +
            "</ol><p>End of list.\n";
        checkHtml(code, 365);
    }

    public void testParseList7() throws Exception {

        String code = "<ol>" + "<li>Listitem #1" + "<li>Nested List" +
            "  <ol><li>2 Listitem #1</ol>" + "<li>Listitem #2" +
            "<li>Listitem #3" + "<li>Listitem #4" + "</ol>\n";
        checkHtml(code, 361);
    }

    public void testParseList8() throws Exception {

        String code = "<ol>\n" + "  <li>Listitem #1\n" + "  <li>Nested List\n" +
            "    <ol>\n" + "      <li>2 Listitem #1\n" + "    </ol>\n" +
            "  <li>Listitem #2\n" + "  <li>Nested List 2.1\n" + "    <ol>\n" +
            "      <li>Nested List 3.1\n" + "        <ol>\n" +
            "          <li>Deeply nested\n" + "        </ol>\n" +
            "    </ol>\n" + "  <li>Listitem #3" + "  <li>Listitem #4" +
            "</ol>\n";
        checkHtml(code, 626);
    }

    public void testParseList9() throws Exception {

        String code = "<ul>" + "<li>Listitem #1" + "<li>Nested List" +
            "  <ul><li>2 Listitem #1</ul>" + "<li>Listitem #2" +
            "<li>Listitem #3" + "<li>Listitem #4" + "</ul>\n";
        checkHtml(code, 361);
    }

    public void testParseList10() throws Exception {

        String code = "<ul>\n" + "  <li>Listitem #1\n" + "  <li>Nested List\n" +
            "    <ul>\n" + "      <li>2 Listitem #1\n" + "    </ul>\n" +
            "  <li>Listitem #2\n" + "  <li>Nested List 2.1\n" + "    <ul>\n" +
            "      <li>Nested List 3.1\n" + "        <ul>\n" +
            "          <li>Deeply nested\n" + "        </ul>\n" +
            "    </ul>\n" + "  <li>Listitem #3" + "  <li>Listitem #4" +
            "</ul>\n";
        checkHtml(code, 626);
    }

    public void testParseList11() throws Exception {

        String code = "This is an unordered list:\n" + "<ul>\n" +
            "  <li>Listeneintrag A\n" + "  <li>Listeneintrag B\n" + "  <ol>\n" +
            "    <li>Eingebettete Liste Eintrag A\n" +
            "    <li>Eingebettete Liste Eintrag B\n" +
            "    <li>Eingebettete Liste Eintrag C\n" + "  </ol>\n" +
            "  <li><p>Listeneintrag P tag mit <code>Code</code> tag\n" +
            "</ul>\n";
        checkHtml(code, 520);
    }

    public void testParseList12() throws Exception {

        String code = "This is an ordered list:\n" + "<ol>\n" +
            "  <li>Listeneintrag A\n" + "  <li>Listeneintrag B\n" + "  <ul>\n" +
            "    <li>Eingebettete Liste Eintrag A\n" +
            "    <li>Eingebettete Liste Eintrag B\n" +
            "    <li>Eingebettete Liste Eintrag C\n" + "  </ul>\n" +
            "  <li><p>Listeneintrag P tag mit <code>Code</code> tag\n" +
            "</ol>\n";
        checkHtml(code, 518);
    }

    public void testParseList13() throws Exception {

        String code = "Registers a new virtual-machine shutdown hook.\n" +
            "\n" +
            "<p> The Java virtual machine <i>shuts down</i> in response to two kinds" +
            "of events:" + "\n" + "<ul>" + "<p><li> Listitem #1\n" +
            "<p><li> Listitem #2\n" + "</ul>\n" +
            "<p>The Java virtual machine <i>shuts down</i> in response to two kinds";
        checkHtml(code, 384);
    }

    public void testParseList14() throws Exception {

        String code = "Some Text followed by a list<p>\n" + "<ul>" +
            "<li>Listitem #1<p>" + "<li>Listitem #2<p>" + "<li>Listitem #3<p>" +
            "</ul>\n" + "which is followed by text.";
        checkHtml(code, 308);
    }

    public void testParseList15() throws Exception {

        String code = "Some Text followed by a list\n" + "<ul>" +
            "<li>Listitem #1" + "<li>Listitem #2" + "<li>Listitem #3</li>" +
            "<li>Listitem #4" + "</ul>\n" +
            "<p>which is followed by a paragraph.";
        checkHtml(code, 305);
    }

    public void testParseList16() throws Exception {

        String code =
            "<p>This paragraph is enclosing a list. The closing p tag after the list is invalid, because the opening p tag is closed automatically before the list starts!\n" +
            "<ul>" + "<li>Listitem #1" + "<li>Listitem #2" +
            "<li>Listitem #3</li>" + "<li>Listitem #4" + "</ul>\n" + "</p>\n" +
            "<p>You should see this paragraph!.</p>";
        checkHtml(code, 429);
    }

    public void testParseList17() throws Exception {

        String code = "<ul>\n" + "<li>Listitem #1\n" + "<li>Listitem #2\n" +
            "<ul>\n" + "<li>Listitem #1\n" + "<li>Listitem #2\n";
        checkHtml(code, 274);
    }

    public void testParseList18() throws Exception {

        String code = "<dl>\n" + "<dt><i>term #1</i>" + "<dd>Description #1" +
            "<dt><i>term #2</i>" + "<dd>Description #2" + "</dl>";
        checkHtml(code, 139);
    }

    public void testParseList19() throws Exception {

        String code = "<dl>" + "<dt>Listitem #1<dd>Description" +
            "<dt>Nested List<dd>Description" +
            "  <dl><dt>2 Listitem #1<dd>Description</dl>" +
            "<dt>Listitem #2<dd>Description" +
            "<dt>Listitem #3<dd>Description" +
            "<dt>Listitem #4<dd>Description" + "</dl>\n";
        checkHtml(code, 388);
    }

    public void testParseList20() throws Exception {

        String code = "<dl>\n" + "  <dt>Listitem #1<dd>Description\n" +
            "  <dt>Nested List<dd>Description\n" + "    <dl>\n" +
            "      <dt>2 Listitem #1<dd>Description\n" + "    </dl>\n" +
            "  <dt>Listitem #2<dd>Description\n" +
            "  <dt>Nested List 2.1<dd>Description\n" + "    <dl>\n" +
            "      <dt>Nested List 3.1<dd>Description\n" + "        <dl>\n" +
            "          <dt>Deeply nested<dd>Description\n" + "        </dl>\n" +
            "    </dl>\n" + "  <dt>Listitem #3<dd>Description" +
            "  <dt>Listitem #4<dd>Description" + "</dl>\n";
        checkHtml(code, 671);
    }

    public void testParseList21() throws Exception {

        String code = "\n" + "<dl>\n" + "  <dt>Term</dt>\n" +
            "  <dd>Description 1</dd>\n" + "  <dd>Description 2</dd>\n" +
            "</dl>\n";
        checkHtml(code, 97);
    }

    static public void main(String[] args) {

        junit.textui.TestRunner.run(suite());
    }
}

/*
 * $Log: ListTests.java,v $
 * Revision 1.1.1.1  2004/12/21 14:00:11  mfuchs
 * Reimport
 *
 * Revision 1.2  2004/10/05 13:13:18  mfuchs
 * Sicherung
 *
 * Revision 1.1.1.1  2004/02/17 22:49:36  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2004/01/05 14:57:09  cvs
 * dbdoclet
 *
 * Revision 1.1.1.1  2003/08/01 13:19:28  cvs
 * DocBook Doclet
 *
 * Revision 1.1.1.1  2003/07/31 17:05:40  mfuchs
 * DocBook Doclet since 0.46
 *
 * Revision 1.2  2003/06/27 08:39:12  mfuchs
 * Fixes and improvemts for 0.45
 *
 * Revision 1.1.1.1  2003/05/30 11:09:40  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2003/03/18 07:41:37  mfuchs
 * DocBook Doclet 0.40
 *
 * Revision 1.1.1.1  2003/03/17 20:51:40  cvs
 * dbdoclet
 *
 */
