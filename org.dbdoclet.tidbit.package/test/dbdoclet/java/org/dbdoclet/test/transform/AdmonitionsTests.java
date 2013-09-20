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
 * Id.........: $Id: AdmonitionsTests.java,v 1.1.1.1 2004/12/21 14:00:04 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:00:04 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.test.transform;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;


public class AdmonitionsTests extends XmlTestCase {

    /**
     * The variable <code>logger</code>{@link org.apache.log4j.Logger (org.apache.log4j.Logger)}
     * can be used to print logging information via the log4j framework.
     */
    private static Log logger = LogFactory.getLog(AdmonitionsTests.class);

    public static Test suite() {

        return new TestSuite(AdmonitionsTests.class);
    }

    public void testTransformExample() throws Exception {

        String code = "<div id=\"example_1\" title=\"Example #1\">\n" +
            "<pre>\n" + "  public class SoUndSo {\n" + "    int i;\n" +
            "  }\n" + "</pre>\n" + "</div>\n";
        checkDocBook(code, true);
    }

    public void testTransformCaution() throws Exception {

        String code = "<div id=\"caution_1\">\n" +
            "<p>This is an example for the tag caution</p>\n" + "</div>\n";
        checkDocBook(code, true);
    }

    public void testTransformCautionWithTitle() throws Exception {

        String code = "<div id=\"caution_1\" title=\"Caution #1\">\n" +
            "<p>This is an example for the tag caution</p>\n" + "</div>\n";
        checkDocBook(code, true);
    }

    public void testTransformImportant() throws Exception {

        String code = "<div id=\"important_1\">\n" +
            "<p>This is an example for the tag important</p>\n" + "</div>\n";
        checkDocBook(code, true);
    }

    public void testTransformImportantWithTitle() throws Exception {

        String code = "<div id=\"important_1\" title=\"Important #1\">\n" +
            "<p>This is an example for the tag important</p>\n" + "</div>\n";
        checkDocBook(code, true);
    }

    public void testTransformNote() throws Exception {

        String code = "<div id=\"note_1\">\n" +
            "<p>This is an example for the tag note</p>\n" + "</div>\n";
        checkDocBook(code, true);
    }

    public void testTransformNoteWithTitle() throws Exception {

        String code = "<div id=\"note_1\" title=\"Note #1\">\n" +
            "<p>This is an example for the tag note</p>\n" + "</div>\n";
        checkDocBook(code, true);
    }

    public void testTransformTip() throws Exception {

        String code = "<div id=\"tip_1\">\n" +
            "<p>This is an example for the tag tip</p>\n" + "</div>\n";
        checkDocBook(code, true);
    }

    public void testTransformTipWithTitle() throws Exception {

        String code = "<div id=\"tip_1\" title=\"Tip #1\">\n" +
            "<p>This is an example for the tag tip</p>\n" + "</div>\n";
        checkDocBook(code, true);
    }

    public void testTransformWarning() throws Exception {

        String code = "<div id=\"warning_1\">\n" +
            "<p>This is an example for the tag warning</p>\n" + "</div>\n";
        checkDocBook(code, true);
    }

    public void testTransformWarningWithTitle() throws Exception {

        String code = "<div id=\"warning_1\" title=\"Warning #1\">\n" +
            "<p>This is an example for the tag warning</p>\n" + "</div>\n";
        checkDocBook(code, true);
    }

    public void testTransformPWarning() throws Exception {

        String code = "Example:\n" +
            "<div id=\"warning_1\" title=\"Warning #1\">\n" +
            "<p>This is an example for the tag warning</p>\n" + "</div>\n";
        checkDocBook(code, true);
    }
}

/*
 * $Log: AdmonitionsTests.java,v $
 * Revision 1.1.1.1  2004/12/21 14:00:04  mfuchs
 * Reimport
 *
 * Revision 1.2  2004/10/05 13:13:18  mfuchs
 * Sicherung
 *
 * Revision 1.1.1.1  2004/02/17 22:49:26  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2004/01/05 14:57:01  cvs
 * dbdoclet
 *
 * Revision 1.2  2003/11/30 20:43:44  cvs
 * Many fixes. Command line options.
 *
 * Revision 1.1.1.1  2003/08/01 13:18:41  cvs
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
 * Revision 1.1.1.1  2003/03/17 20:50:56  cvs
 * dbdoclet
 *
 */
