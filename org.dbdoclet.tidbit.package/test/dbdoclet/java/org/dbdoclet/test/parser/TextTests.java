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
 * Id.........: $Id: TextTests.java,v 1.1.1.1 2004/12/21 14:00:07 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:00:07 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.test.parser;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;


public class TextTests extends ParserTestCase {

    /**
     * The variable <code>logger</code>{@link org.apache.log4j.Logger (org.apache.log4j.Logger)}
     * can be used to print logging information via the log4j framework.
     */
    private static Log logger = LogFactory.getLog(TextTests.class);

    public TextTests(String name) {
        super(name);
    }

    public static Test suite() {

        return new TestSuite(TextTests.class);
    }

    public void testParseText1() throws Exception {

        String code = "Text ohne Paragraph.";
        checkHtml(code, 32);
    }

    public void testParseText2() throws Exception {

        String code = "<h1>Header 1</h1>\n" + "Text.";

        checkHtml(code, 40);
    }

    public void testParseText3() throws Exception {

        String code = "<h1>Header 1</h1>\n" + "<p>Paragraph</p>" + "Text.";

        checkHtml(code, 60);
    }

    public void testParseText4() throws Exception {

        String code = "<h1>Header 1</h1>\n" + "<p>Paragraph</p>" +
            "<pre>Code</pre>" + "Variable <var>number</var>.";

        checkHtml(code, 114);
    }

    public void testParseText5() throws Exception {

        String code = "<a name=\"header\"><h1>Header 1</h1></a>\n" +
            "<p>Paragraph</p>" + "<pre>Code</pre>" +
            "Variable <var>number<sub>1</sub></var>.";

        checkHtml(code, 142);
    }

    static public void main(String[] args) {

        junit.textui.TestRunner.run(suite());
    }
}

/*
 * $Log: TextTests.java,v $
 * Revision 1.1.1.1  2004/12/21 14:00:07  mfuchs
 * Reimport
 *
 * Revision 1.2  2004/10/05 13:13:18  mfuchs
 * Sicherung
 *
 * Revision 1.1.1.1  2004/02/17 22:49:31  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2004/01/05 14:57:05  cvs
 * dbdoclet
 *
 * Revision 1.1.1.1  2003/08/01 13:19:24  cvs
 * DocBook Doclet
 *
 * Revision 1.1.1.1  2003/07/31 17:05:40  mfuchs
 * DocBook Doclet since 0.46
 *
 */
