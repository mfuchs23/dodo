package org.dbdoclet.tidbit.common;

public enum Output { 
	
	eclipse, epub, fo, html, htmlhelp, javahelp, manpages, pdf, rtf, webhelp, wordml, xhtml;
	
	public String toString() {
		
		if (name().equals("xhtml")) {
			return "xhtml-1_1";
		}
		
		return name().toLowerCase();
	}
}