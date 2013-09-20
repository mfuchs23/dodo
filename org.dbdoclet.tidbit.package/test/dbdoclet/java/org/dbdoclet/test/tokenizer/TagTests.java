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
 * Id.........: $Id: TagTests.java,v 1.1.1.1 2004/12/21 14:00:06 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:00:06 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.test.tokenizer;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.dbdoclet.log.Logger;
import org.dbdoclet.tokenizer.MLToken;
import org.dbdoclet.tokenizer.MLTokenizer;


public class TagTests extends TestCase {

    private static Log logger = LogFactory.getLog(TagTests.class);

    public TagTests(String name) {
        super(name);
    }

    public static Test suite() {

        return new TestSuite(TagTests.class);
    }

    public void testTag1() throws Exception {

        String code = "<tag>";

        MLTokenizer tokenizer = new MLTokenizer(code);
        MLToken token = null;

        while (tokenizer.hasNext()) {

            token = tokenizer.next();
            logger.debug("token=" + token.getToken());
        } // end of while ()

        assertTrue("Token is not a tag '" + token.toString() + "'.",
            token.isTag());
        assertTrue("Wrong tag buffer", token.getToken().equals("<tag>"));
    }

    public void testTag2() throws Exception {

        String code = "<tag></tag>";

        MLTokenizer tokenizer = new MLTokenizer(code);
        MLToken token = null;

        while (tokenizer.hasNext()) {

            token = tokenizer.next();
            logger.debug("token=" + token.getToken());
            assertTrue("Token is not a tag.", token.isTag());
        } // end of while ()
    }

    public void testTag3() throws Exception {

        String code = "<tag param1=\"value1\" param2=\"http://host:8080/servlet/do?action=�berwachen\"></tag>";

        MLTokenizer tokenizer = new MLTokenizer(code);
        MLToken token = null;

        while (tokenizer.hasNext()) {

            token = tokenizer.next();
            logger.debug("token=" + token.getToken());
            assertTrue("Token is not a tag.", token.isTag());
        } // end of while ()
    }

    public void testTag4() throws Exception {

        String code = "<tag\n" + "param1=\"value1\"\n" +
            "param2=\"value2\">\n" + "</tag>\n";

        String expected = "<tag param1=\"value1\" param2=\"value2\">\n" +
            "</tag>\n";

        compare(code, expected);
    }

    public void testTag5() throws Exception {

        String code = "< tag>Test Tag #5.</tag>";
        String expected = "&lt; tag&gt;Test Tag #5.</tag>";
        compare(code, expected);
    }

    public void testTag6() throws Exception {

        String code = "<tag>Text</tag><";
        String expected = "<tag>Text</tag>&lt;";
        compare(code, expected);
    }

    public void testTag7() throws Exception {

        String code = "<p> 1<2 1< 2 1 < 2 </p>";
        String expected = "<p> 1&lt;2 1&lt; 2 1 &lt; 2 </p>";
        compare(code, expected);
    }

    public void testTag8() throws Exception {

        String code = "<p> 2>1 2> 1 2 > 1 </p>";
        String expected = "<p> 2&gt;1 2&gt; 1 2 &gt; 1 </p>";
        compare(code, expected);
    }

    public void testTag9() throws Exception {

        String code = "<>";
        String expected = "&lt;&gt;";
        compare(code, expected);
    }

    public void testTag10() throws Exception {

        String code = "<<>>";
        String expected = "&lt;&lt;&gt;&gt;";
        compare(code, expected);
    }

    public void testTag11() throws Exception {

        String code = "><";
        String expected = "&gt;&lt;";
        compare(code, expected);
    }

    public void testTag12() throws Exception {

        String code = ">><<";
        String expected = "&gt;&gt;&lt;&lt;";
        compare(code, expected);
    }

    public void testTag13() throws Exception {

        String code = "<rock&roll>";
        String expected = "&lt;rock&amp;roll&gt;";
        compare(code, expected);
    }

    private void compare(String code, String expected)
        throws Exception {

        String result = "";

        MLTokenizer tokenizer = new MLTokenizer(code);
        MLToken token = null;

        while (tokenizer.hasNext()) {

            token = tokenizer.next();
            logger.debug("token=" + token.toString());
            result += token.getToken();
        } // end of while ()

        logger.info("result='" + result + "'");
        assertEquals(result, expected);
    }

    static public void main(String[] args) {

        junit.textui.TestRunner.run(suite());
    }
}

/*
 * $Log: TagTests.java,v $
 * Revision 1.1.1.1  2004/12/21 14:00:06  mfuchs
 * Reimport
 *
 * Revision 1.2  2004/10/05 13:13:18  mfuchs
 * Sicherung
 *
 * Revision 1.1.1.1  2004/02/17 22:49:29  mfuchs
 * dbdoclet
 *
 * Revision 1.1.1.1  2004/01/05 14:57:03  cvs
 * dbdoclet
 *
 * Revision 1.1.1.1  2003/08/01 13:19:22  cvs
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
 * Revision 1.1.1.1  2003/03/17 20:51:36  cvs
 * dbdoclet
 *
 */
