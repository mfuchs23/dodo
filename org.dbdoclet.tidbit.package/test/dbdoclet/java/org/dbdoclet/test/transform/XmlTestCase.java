/* 
 * $Id$
 *
 * ### Copyright (C) 2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.test.transform;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.dbdoclet.doclet.LinkManager;
import org.dbdoclet.doclet.docbook.DbdOptions;
import org.dbdoclet.log.Logger;
import org.dbdoclet.parser.ParserException;
import org.dbdoclet.service.XmlServices;
import org.dbdoclet.tag.Element;
import org.dbdoclet.tag.docbook.Root;
import org.dbdoclet.tokenizer.TokenizerException;
import org.dbdoclet.transform.Transformer;
import org.dbdoclet.transform.TransformerConfiguration;
import org.dbdoclet.transform.TransformerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class XmlTestCase extends TestCase {

    private static Log logger = LogFactory.getLog(XmlTestCase.class);
    private static boolean TRACE = Boolean.getBoolean("junit.trace");
    private static ResourceBundle res = PropertyResourceBundle.getBundle("org/dbdoclet/herold/Resources", new Locale("de"));

    public void setUp() {

    }

    public void tearDown() {

    }

    protected boolean checkDocBook(String code) {

        return checkDocBook(code, null, -1, true);
    }

    protected boolean checkDocBook(String code, boolean needsBody) {

        return checkDocBook(code, null, -1, needsBody);
    }

    protected boolean checkDocBook(String code, int size) {

        return checkDocBook(code, null, size, true);
    }

    protected boolean checkDocBook(String code, String tag) {

        return checkDocBook(code, tag, -1, true);
    }

    protected boolean checkDocBook(String code, String tag, int size, boolean needsChapter) {

        
        if (code == null) {
            throw new IllegalArgumentException("Parameter code is null!");
        }

        File file = new File("");
        
        try {

            Root root = new Root();

            String[] args = new String[0];

            DbdOptions options = new DbdOptions(args, res);

            TransformerFactory factory = TransformerFactory.newInstance();
            TransformerConfiguration conf = factory.newConfiguration();
            options.fillTransformerConfiguration(conf);

            Transformer html2db = factory.newTransformer(conf);

            String prefix = "";

            if (needsChapter == true) {

                prefix = "    ";
                html2db.setCodeContext(html2db.CONTEXT_SECTION);
                
            } else {

                prefix = "";
                html2db.setCodeContext(html2db.CONTEXT_ARTICLE);
            }

            
            LinkManager.init();

            Element xml = html2db.transform(code, root);

            String buffer = root.toXML();

            int len = buffer.length();

            String id = getName();
            logger.info("-- Check DocBook of " + id + " --");

            String fsep = System.getProperty("file.separator");
            String destPath = System.getProperty("junit.docbook.dir");

            if (destPath == null) {

                destPath = "." + fsep + "xml";
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

            file = new File(destPath + id + ".xml");
            PrintWriter out = new PrintWriter(new FileWriter(file));

            if (needsChapter == true) {

                out.println("<?xml version =\"1.0\" encoding =\"UTF-8\"?>");
                out.println(
                    "<!DOCTYPE book PUBLIC \"-//OASIS//DTD DocBook XML V4.3//EN\" \"http://www.oasis-open.org/docbook/xml/4.3/docbookx.dtd\">");
                out.println("<book>");
                out.println("  <chapter>");
                out.println("    <title>JUnit Test</title>");
            }

            if (len > 0) {
                out.print(buffer);
            } else {
                out.print("    <para></para>");
            }

            if (needsChapter == true) {

                if (!buffer.endsWith("\n")) {
                    out.println();
                }

                out.println("  </chapter>");
                out.println("</book>");
            }

            out.close();

            XmlServices.validate(file);

            if ((size != -1) && (buffer.length() != size)) {
                assertEquals("Wrong buffer size!", size, buffer.length());
            }

        } catch (IOException oops) {

            String msg = oops.getClass().getName() + ": " + file.getPath() + " " + oops.getMessage();
            fail(msg);

        } catch (TokenizerException oops) {

            String msg = oops.getClass().getName() + ": " + file.getPath() + " " + oops.getMessage();
            fail(msg);

        } catch (ParserException oops) {

            String msg = oops.getClass().getName() + ": " + file.getPath() + " " + oops.getMessage();
            fail(msg);

        } catch (ParserConfigurationException oops) {

            String msg = oops.getClass().getName() + ": " + file.getPath() + " " + oops.getMessage();
            fail(msg);

        } catch (SAXException oops) {

            String msg;

            if (oops instanceof SAXParseException) {
                
                msg = oops.getClass().getName() + ": " 
                    + file.getPath() 
                    + "[" 
                    + ((SAXParseException) oops).getLineNumber()
                    + ","
                    + ((SAXParseException) oops).getColumnNumber()
                    + "] "
                    + oops.getMessage();

            } else {

                msg = oops.getClass().getName() + ": " + file.getPath() + " " + oops.getMessage();
            }

            fail(msg);
        }

        return true;
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

                m_hasErrors = true;
                System.out.println(line);
            }

        } catch (Exception oops) {

            oops.printStackTrace();
        }
    }
}
