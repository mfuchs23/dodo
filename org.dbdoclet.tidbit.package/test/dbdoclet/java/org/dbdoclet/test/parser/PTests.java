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
 * Id.........: $Id: PTests.java,v 1.1.1.1 2004/12/21 14:00:09 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:00:09 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.test.parser;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;


public class PTests extends ParserTestCase {

    /**
     * The variable <code>logger</code>{@link org.apache.log4j.Logger (org.apache.log4j.Logger)}
     * can be used to print logging information via the log4j framework.
     */
    private static Log logger = LogFactory.getLog(PTests.class);

    public PTests(String name) {
        super(name);
    }

    public static Test suite() {

        return new TestSuite(PTests.class);
    }

    public void testParseP1() throws Exception {

        String code = "<p>Simple paragraph</p>";
        checkHtml(code, 28);
    }

    public void testParseP2() throws Exception {

        String code = "<p>Simple <b>paragraph</b>" +
            "<p><i>Simple</i> paragraph, too" + "<p>Simple paragraph #3</p>" +
            "<p>Simple paragraph #4";
        checkHtml(code, 137);
    }

    public void testParseP3() throws Exception {

        String code =
            "Some text with a <code>code</code> tag followed by an empty paragraph.<p>" +
            "<h2>H2 Header</h2>" + "Some more text<p>";
        checkHtml(code, 155);
    }

    public void testParseP4() throws Exception {

        String code = "<code>Starts with code</code>" +
            "followed by Text and a " + "<p>new paragraph.";
        checkHtml(code, 108);
    }

    public void testParseP5() throws Exception {

        String code = "<p>" + "<ol><li>Item #1" + "<li>Item #1" + "</ol>" +
            "<br>" + "Some Text";
        checkHtml(code, 165);
    }

    public void testParseP6() throws Exception {

        String code =
            "<p>PSGML contains a simple SGML parser and can work with any DTD.\n" +
            "Functions provided includes menus and commands for inserting tags with\n" +
            "only the contextually valid tags, identification of structural errors,\n" +
            "editing of attribute values in a separate window with information\n" +
            "about types and defaults, and structure based editing.</p>";
        checkHtml(code, 337);
    }

    public void testParseP7() throws Exception {

        String code =
            "<p>PSGML contains a simple SGML parser and can work with any DTD.\n" +
            "Functions provided includes menus and commands for inserting tags with\n" +
            "only the contextually valid tags,<br> identification of structural errors,\n" +
            "editing of attribute values in a separate window with information\n" +
            "about types and defaults, and structure based editing.</p>";
        checkHtml(code, 349);
    }

    static public void main(String[] args) {

        junit.textui.TestRunner.run(suite());
    }
}

/*
 * $Log: PTests.java,v $
 * Revision 1.1.1.1  2004/12/21 14:00:09  mfuchs
 * Reimport
 *
 * Revision 1.2  2004/10/05 13:13:18  mfuchs
 * Sicherung
 *
 * Revision 1.1.1.1  2004/02/17 22:49:33  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2004/01/05 14:57:07  cvs
 * dbdoclet
 *
 * Revision 1.1.1.1  2003/08/01 13:19:25  cvs
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
 * Revision 1.1.1.1  2003/03/17 20:51:38  cvs
 * dbdoclet
 *
 */
