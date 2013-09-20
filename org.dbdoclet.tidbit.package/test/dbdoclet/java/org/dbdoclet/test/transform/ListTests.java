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
 * Id.........: $Id: ListTests.java,v 1.1.1.1 2004/12/21 14:00:05 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:00:05 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.test.transform;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;


public class ListTests extends XmlTestCase {

    /**
     * The variable <code>logger</code>{@link org.apache.log4j.Logger (org.apache.log4j.Logger)}
     * can be used to print logging information via the log4j framework.
     */
    private static Log logger = LogFactory.getLog(ListTests.class);

    public static Test suite() {

        return new TestSuite(ListTests.class);
    }

    public void testTransformDlDtDdDd() throws Exception {

        String code = "\n" + "<dl>\n" + "  <dt>Term 1</dt>\n" +
            "  <dd>Description 1.1</dd>\n" + "  <dd>Description 1.2</dd>\n" +
            "</dl>\n";
        checkDocBook(code, true);
    }

    public void testTransformDlDtDdDd2() throws Exception {

        String code = "\n" + "<dl>\n" + "  <dt>Term 1</dt>\n" +
            "  <dd>Description 1.1</dd>\n" + "  <dd>Description 1.2</dd>\n" +
            "  <dt>Term 2</dt>\n" + "  <dd>Description 2.1</dd>\n" +
            "  <dd>Description 2.2</dd>\n" + "</dl>\n";
        checkDocBook(code, true);
    }

    public void testTransformDlDtDtDd() throws Exception {

        String code = "\n" + "<dl>\n" + "  <dt>Term 1</dt>\n" +
            "  <dt>Term 2</dt>\n" + "  <dd>Description</dd>\n" + "</dl>\n";
        checkDocBook(code, true);
    }

    public void testTransformUl() throws Exception {

        String code = "This is a unordered list:\n" + "<ul>\n" +
            "  <li>Listeneintrag A\n" + "  <li>Listeneintrag B\n" + "  <ol>\n" +
            "    <li>Eingebettete Liste Eintrag A\n" +
            "    <li>Eingebettete Liste Eintrag B\n" +
            "    <li>Eingebettete Liste Eintrag C\n" + "  </ol>\n" +
            "  <li><p>Listeneintrag P tag mit <code>Code</code> tag\n" +
            "</ul>\n";
        checkDocBook(code, true);
    }

    public void testTransformTextUlPara() throws Exception {

        String code = "Some Text followed by a list\n" + "<ul>" +
            "<li>Listitem #1" + "<li>Listitem #2" + "<li>Listitem #3</li>" +
            "<li>Listitem #4" + "</ul>\n" +
            "<p>which is followed by a paragraph.";
        checkDocBook(code, true);
    }

    public void testTransformOlLiPara() throws Exception {

        String code = "<ol>" + "<li>Listitem #1 <p>" + "<li>Listitem #2 <p>" +
            "<li>Listitem #3 <p>" + "<li>Listitem #4 <p>" + "</ol>\n";
        checkDocBook(code, true);
    }

    public void testTransformOlParaLi() throws Exception {

        String code = "Registers a new virtual-machine shutdown hook.\n" +
            "\n" +
            "<p> The Java virtual machine <i>shuts down</i> in response to two kinds" +
            "of events:" + "\n" + "   <ul>" + "\n" +
            "   <p> <li> Listitem #1\n" + "   <p><li>Listitem #2\n" +
            "</ul>\n" +
            "<p> The Java virtual machine <i>shuts down</i> in response to two kinds";
        checkDocBook(code, true);
    }
}

/*
 * $Log: ListTests.java,v $
 * Revision 1.1.1.1  2004/12/21 14:00:05  mfuchs
 * Reimport
 *
 * Revision 1.2  2004/10/05 13:13:18  mfuchs
 * Sicherung
 *
 * Revision 1.1.1.1  2004/02/17 22:49:28  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2004/01/05 14:57:02  cvs
 * dbdoclet
 *
 * Revision 1.2  2003/11/30 20:43:44  cvs
 * Many fixes. Command line options.
 *
 * Revision 1.1.1.1  2003/08/01 13:19:19  cvs
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
 * Revision 1.1.1.1  2003/03/17 20:51:34  cvs
 * dbdoclet
 *
 */
