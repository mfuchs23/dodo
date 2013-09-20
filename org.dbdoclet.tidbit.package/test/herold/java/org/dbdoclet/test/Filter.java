package org.dbdoclet.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.dbdoclet.transform.Transformer;
import org.dbdoclet.transform.TransformerConfiguration;
import org.dbdoclet.transform.TransformerFactory;
import org.dbdoclet.transform.TransformerResult;


public class Filter {

    public static void main(String[] args) {

        try {

            String instr = args[0];
            String inenc = args[1];
            String outstr = args[2];
            String outenc = args[3];

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(instr), inenc));

            String line = reader.readLine();
            String htmlCode = "";

            while (line != null) {

                htmlCode += line;
                line = reader.readLine();
            }

            reader.close();

            TransformerFactory factory = TransformerFactory.newInstance();

            TransformerConfiguration conf = factory.newConfiguration();
            conf.setEncoding(outenc);
            conf.setSystemId(
                "file:///usr/share/dbdoclet/docbook/dtd/docbookx.dtd");

            Transformer transformer = factory.newTransformer(conf);

            // System.out.println(htmlCode);
            String xmlCode = transformer.transform(htmlCode,
                    new TransformerResult(new File(outstr)));

            System.out.println(xmlCode);
        } catch (Exception oops) {

            oops.printStackTrace();
        }
    }
}
