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
 * Id.........: $Id: ParaTests.java,v 1.1.1.1 2004/12/21 14:00:10 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:00:10 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.test.parser;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;


public class ParaTests extends ParserTestCase {

    /**
     * The variable <code>logger</code>{@link org.apache.log4j.Logger (org.apache.log4j.Logger)}
     * can be used to print logging information via the log4j framework.
     */
    private static Log logger = LogFactory.getLog(ParaTests.class);

    public ParaTests(String name) {
        super(name);
    }

    public static Test suite() {

        return new TestSuite(ParaTests.class);
    }

    public void testParsePara1() throws Exception {

        String code = "<p>Simple paragraph</p>";
        checkHtml(code, 15);
    }

    public void testParsePara2() throws Exception {

        String code = "<p>Simple <b>paragraph</b>" +
            "<p><i>Simple</i> paragraph, too" + "<p>Simple paragraph #3</p>" +
            "<p>Simple paragraph #4";
        checkHtml(code, 15);
    }

    public void testParsePara3() throws Exception {

        String code =
            "Some text with a <code>code</code> tag followed by an empty paragraph.<p>" +
            "<h2>H2 Header</h2>" + "Some more text<p>";
        checkHtml(code, 15);
    }

    public void testParsePara4() throws Exception {

        String code = "<code>Starts with code</code>" +
            "followed by Text and a " + "<p>new paragraph.";
        checkHtml(code, 15);
    }

    public void testParsePara5() throws Exception {

        String code = "<p>" + "<ol><li>Item #1" + "<li>Item #1" + "</ol>" +
            "<br>" + "Some Text";
        checkHtml(code, 15);
    }

    static public void main(String[] args) {

        junit.textui.TestRunner.run(suite());
    }
}

/*
 * $Log: ParaTests.java,v $
 * Revision 1.1.1.1  2004/12/21 14:00:10  mfuchs
 * Reimport
 *
 * Revision 1.2  2004/10/05 13:13:18  mfuchs
 * Sicherung
 *
 * Revision 1.1.1.1  2004/02/17 22:49:34  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2004/01/05 14:57:08  cvs
 * dbdoclet
 *
 * Revision 1.1.1.1  2003/08/01 13:19:27  cvs
 * DocBook Doclet
 *
 * Revision 1.1.1.1  2003/07/31 17:05:40  mfuchs
 * DocBook Doclet since 0.46
 *
 * Revision 1.1.1.1  2003/05/30 11:09:40  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2003/03/18 07:41:37  mfuchs
 * DocBook Doclet 0.40
 *
 * Revision 1.1.1.1  2003/03/17 20:51:39  cvs
 * dbdoclet
 *
 */
