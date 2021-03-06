package org.dbdoclet.test.transform;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StringWriter;

import junit.framework.Test;
import junit.framework.TestSuite;

public class FileTests extends XmlTestCase 
{
    private String m_fsep = System.getProperty("file.separator");
    private String m_filePath = System.getProperty("junit.data.dir");

    public static Test suite()
    {
      return new TestSuite(FileTests.class);
    }

    
    public void testFileENovative()
        throws Exception
    {
        String code = getBuffer("ENovative.html");
        checkDocBook(code,false);
    }
  
    public void testFileJakarta()
        throws Exception
    {
        String code = getBuffer("Jakarta.html");
        checkDocBook(code,false);
    }
  
    public void testFileLdap()
        throws Exception
    {
        String code = getBuffer("Ldap.html");
        checkDocBook(code,false);
    }
  
    public void testFileMinimum()
        throws Exception
    {
        String code = getBuffer("Minimum.html");
        checkDocBook(code,false);
    }
  
    public void testFileTest()
        throws Exception
    {
        String code = getBuffer("Test.html");
        checkDocBook(code,false);
    }
  
    public void testFileTest1()
        throws Exception
    {
        String code = getBuffer("Test1.html");
        checkDocBook(code,false);
    }
  
    public void testFileTest10()
        throws Exception
    {
        String code = getBuffer("Test10.html");
        checkDocBook(code,false);
    }
  
    public void testFileTest11()
        throws Exception
    {
        String code = getBuffer("Test11.html");
        checkDocBook(code,false);
    }
  
    public void testFileTest12()
        throws Exception
    {
        String code = getBuffer("Test12.html");
        checkDocBook(code,false);
    }
  
    public void testFileTest2()
        throws Exception
    {
        String code = getBuffer("Test2.html");
        checkDocBook(code,false);
    }
  
    public void testFileTest3()
        throws Exception
    {
        String code = getBuffer("Test3.html");
        checkDocBook(code,false);
    }
  
    public void testFileTest4()
        throws Exception
    {
        String code = getBuffer("Test4.html");
        checkDocBook(code,false);
    }
  
    public void testFileTest5()
        throws Exception
    {
        String code = getBuffer("Test5.html");
        checkDocBook(code,false);
    }
  
    public void testFileTest6()
        throws Exception
    {
        String code = getBuffer("Test6.html");
        checkDocBook(code,false);
    }
  
    public void testFileTest7()
        throws Exception
    {
        String code = getBuffer("Test7.html");
        checkDocBook(code,false);
    }
  
    public void testFileTest8()
        throws Exception
    {
        String code = getBuffer("Test8.html");
        checkDocBook(code,false);
    }
  
    public void testFileTest9()
        throws Exception
    {
        String code = getBuffer("Test9.html");
        checkDocBook(code,false);
    }
  
    public void testFileTest_jp()
        throws Exception
    {
        String code = getBuffer("Test_jp.html");
        checkDocBook(code,false);
    }
  

    private String getBuffer(String fname) 
        throws Exception
    {
	fname = m_filePath + m_fsep + fname;
	
        FileReader in = new FileReader(fname);      
	StringWriter code = new StringWriter();

	char[] buffer = new char[4096];

	int length = 0;
        while ((length = in.read(buffer,0,4096)) != -1 )
	    code.write(buffer,0,length);

        return code.toString();
    }
}
  