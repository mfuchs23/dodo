/* 
 * $Id$
 *
 * ### Copyright (C) 2005 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 *
 * RCS Information
 * Author..........: $Author$
 * Date............: $Date$
 * Revision........: $Revision$
 * State...........: $State$
 */
package org.dbdoclet.test.parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import junit.framework.TestCase;

import org.dbdoclet.log.Logger;
import org.dbdoclet.parser.HTMLParser;
import org.dbdoclet.parser.ParserException;
import org.dbdoclet.tag.html.HTMLElement;
import org.dbdoclet.tokenizer.TokenizerException;


public class ParserTestCase extends TestCase {

    private static Log logger = LogFactory.getLog(ParserTestCase.class.getName());


    protected static boolean TRACE = Boolean.getBoolean("junit.trace");
    protected HTMLParser m_parser;

    public ParserTestCase() {
        super();
    }

    public ParserTestCase(String name) {
        super(name);
    }

    public void setUp() {

        m_parser = new HTMLParser();
    }

    public void tearDown() {

    }

    static protected void printHtml(String html) {

        printHtml(html, false);
    }

    static protected void printHtml(String html, boolean doPrint) {

        if ((TRACE == true) || (doPrint == true)) {

            System.out.println(">>>>> Parsed HTML <<<<<");
            System.out.println(html);
            System.out.println(">>>>>>>>>>>><<<<<<<<<<<");
        }
    }

    protected void treeView(String code) {

        if (code == null) {

            throw new IllegalArgumentException("Parameter code is null!");
        }

        try {

            HTMLElement root = m_parser.parse(code);
            assertNotNull(root);

            String buffer = root.treeView();
            logger.info(buffer);

        } catch (Exception oops) {

            oops.printStackTrace();
        }
    }

    protected boolean checkHtml(String code) {

        return checkHtml(code, null, -1, true);
    }

    protected boolean checkHtml(String code, boolean needsBody) {

        return checkHtml(code, null, -1, needsBody);
    }

    protected boolean checkHtml(String code, int size) {

        return checkHtml(code, null, size, true);
    }

    protected boolean checkHtml(String code, String tag) {

        return checkHtml(code, tag, -1, true);
    }

    protected boolean checkHtml(String code, String tag, int size,
        boolean needsBody) {

        if (code == null) {

            throw new IllegalArgumentException("Parameter code is null!");
        }

        try {

            String buffer;
            String prefix = "";

            if (needsBody == true) {

                m_parser.setCodeContext(HTMLParser.CONTEXT_BODY);
                prefix = "    ";
            } else {

                m_parser.setCodeContext(HTMLParser.CONTEXT_HTML);
                prefix = "";
            }

            HTMLElement root = m_parser.parse(code);
            assertNotNull(root);

            treeView(code);

            // System.out.println(buffer);
            buffer = root.toHTML(HTMLElement.SGML, prefix, true, false);

            String id = getName();

            logger.info("-- Check HTML of " + id + " --");

            String fsep = System.getProperty("file.separator");
            String destPath = System.getProperty("junit.html.dir");

            if (destPath == null) {

                destPath = "." + fsep + "html";
            }

            if (!destPath.endsWith(fsep)) {
                destPath += fsep;
            }

            if ((tag != null) && (tag.length() > 0)) {
                destPath += (tag + fsep);
            }

            File destDir = new File(destPath);

            if (!destDir.exists() && !destDir.mkdirs()) {

                throw new IOException("Can't create destination directory '" +
                    destDir.getAbsolutePath() + "'!");
            }

            File file = new File(destPath + id + ".html");
            PrintWriter out = new PrintWriter(new FileWriter(file));

            if (needsBody == true) {

                out.println(
                    "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
                out.println("<html>");
                out.println("  <head>");
                out.println("    <title>TEST " + id + "</title>");
                out.println("  </head>");
                out.println("  <body>");
            }

            out.print(buffer);

            if (needsBody == true) {

                if (!buffer.endsWith("\n")) {
                    out.println();
                }

                out.println("  </body>");
                out.println("</html>");
            }

            out.close();

            Runtime run = Runtime.getRuntime();
            Process process = run.exec("onsgmls -sv -c /usr/share/sgml/html/dtd/catalog " +
                    file.getAbsolutePath());

            String line;
            boolean valid = true;

            Console stderr = new Console(process.getErrorStream());
            stderr.start();

            Console stdout = new Console(process.getInputStream());
            stdout.start();

            try {

                process.waitFor();
                stderr.join();
                stdout.join();

            } catch (InterruptedException oops) {

                oops.printStackTrace();
            } // end of try-catch

            if (stdout.hasErrors() || stderr.hasErrors()) {
                valid = false;
            }

            int status = process.exitValue();

            if ((status != 0) || (valid == false)) {

                fail("Invalid HTML! [" + file.getAbsolutePath() +
                    "]. Exit status: " + status + "!");
            }

            if ((size != -1) && (buffer.length() != size)) {
                assertEquals("Wrong buffer size!", size, buffer.length());
            }
        } catch (IOException oops) {

            fail("checkHTML IOException - " + oops.getMessage());
        } // end of catch
        catch (TokenizerException oops) {

            fail("checkHTML TokenierException - " + oops.getMessage());
        } // end of catch
        catch (ParserException oops) {

            fail("checkHTML ParserException - " + oops.getMessage());
        } // end of catch

        return true;
    }

    static protected boolean checkSize(String buffer, int size) {

        if (buffer.length() != size) {
            TestCase.assertEquals("Wrong buffer size!", size, buffer.length());
        }

        return true;
    }

    static protected void error(String msg) {

        System.err.println(msg);
    }

    static protected void trace(String msg) {

        if (TRACE == true) {
            System.err.println(msg);
        }
    }
}


class Console extends Thread {

    BufferedReader m_in;
    boolean m_hasErrors = false;

    public Console(InputStream in) {

        if (in == null) {

            throw new IllegalArgumentException("Parameter in is null!");
        }

        m_in = new BufferedReader(new InputStreamReader(in));
    }

    public boolean hasErrors() {

        return m_hasErrors;
    }

    public void run() {

        try {

            String line;

            while ((line = m_in.readLine()) != null) {

                // if ( line.indexOf(":I:") == -1 )
                System.out.println(line);

                if (line.indexOf(":E:") != -1) {
                    m_hasErrors = true;
                }
            } // end of while ()
        } catch (Exception oops) {

            oops.printStackTrace();
        } // end of catch
    }
}
/*
 * $Log$
 */
