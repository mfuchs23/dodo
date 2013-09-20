<?xml version ="1.0" encoding ="UTF-8"?>
<xsl:stylesheet exclude-result-prefixes="#default" version="1.0" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:import href="http://docbook.sourceforge.net/release/xsl/current/javahelp/javahelp.xsl"></xsl:import>
	<xsl:include href="http://dbdoclet.org/xsl/html/synopsis.xsl"></xsl:include>
	<xsl:include href="http://dbdoclet.org/xsl/html/themes/color.xsl"></xsl:include>

	<xsl:param name="admon.style"></xsl:param>
<xsl:param name="admon.graphics.path"></xsl:param>
<xsl:param name="admon.graphics.extension"></xsl:param>
<xsl:param name="html.stylesheet"></xsl:param>
<xsl:param name="rootid"></xsl:param>
<xsl:param name="admon.graphics">0</xsl:param>
	<xsl:param name="admon.textlabel">1</xsl:param>
	<xsl:param name="base.dir">./javahelp/</xsl:param>
	<xsl:param name="img.src.path">../</xsl:param>

</xsl:stylesheet>
