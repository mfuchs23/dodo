<?xml version ="1.0" encoding ="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="#default">

  	<xsl:import href="http://docbook.sourceforge.net/release/xsl/current/html/chunkfast.xsl" />
  	<xsl:import href="http://docbook.sourceforge.net/release/xsl/current/html/highlight.xsl"/>
  	<xsl:import href="http://docbook.sourceforge.net/release/xsl/current/html/autoidx-kosek.xsl"/>
  	<xsl:import href="http://docbook.sourceforge.net/release/xsl/current/html/autoidx-kimber.xsl"/>
	
  	<xsl:include href="http://dbdoclet.org/xsl/html/synopsis.xsl" />
  	<xsl:include href="http://dbdoclet.org/xsl/html/themes/color.xsl" />
  
  	<xsl:param name="dbdoclet.version">1</xsl:param>
	<xsl:param name="admon.graphics.path">images/</xsl:param>
	<xsl:param name="callout.graphics.path">images/callouts/</xsl:param>
	<xsl:param name="draft.watermark.image">images/draft.png</xsl:param>
	<xsl:param name="base.dir">./build/html/</xsl:param>
	<xsl:param name="html.stylesheet">dbdoclet.css</xsl:param>

</xsl:stylesheet>

