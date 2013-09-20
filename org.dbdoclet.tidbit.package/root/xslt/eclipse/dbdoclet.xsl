<?xml version ="1.0" encoding ="UTF-8"?>

<xsl:stylesheet xmlns:xsl ="http://www.w3.org/1999/XSL/Transform"
		version ="1.0"
		xmlns:fo ="http://www.w3.org/1999/XSL/Format"
		exclude-result-prefixes ="#default"
		>

  <xsl:import href ="http://docbook.sourceforge.net/release/xsl/current/eclipse/eclipse3.xsl"/>
  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/html/highlight.xsl"/>
  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/html/autoidx-kosek.xsl"/>
  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/html/autoidx-kimber.xsl"/>

  <xsl:include href="http://dbdoclet.org/xsl/functions.xsl"/>
  <xsl:include href="http://dbdoclet.org/xsl/html/synopsis.xsl"/>
  <xsl:include href="http://dbdoclet.org/xsl/html/themes/color.xsl"/>

  <xsl:param name="dbdoclet.version">1</xsl:param>
  <xsl:param name="base.dir">./build/eclipse/</xsl:param>
  <xsl:param name="manifest.in.base.dir">1</xsl:param>
  <xsl:param name="eclipse.plugin.name"></xsl:param>
  <xsl:param name="eclipse.plugin.id"></xsl:param>
  <xsl:param name="eclipse.plugin.version"></xsl:param>
  <xsl:param name="eclipse.plugin.provider"></xsl:param>

	<xsl:template name="manifest.content">
    <xsl:text>Manifest-Version: 1.0</xsl:text>
    <xsl:text>&#x0A;</xsl:text>
    <xsl:text>Bundle-Version: </xsl:text><xsl:value-of select="$eclipse.plugin.version"/>
    <xsl:text>&#x0A;</xsl:text>
    <xsl:text>Bundle-Name: </xsl:text><xsl:value-of select="$eclipse.plugin.name"/>
    <xsl:text>&#x0A;</xsl:text>
    <xsl:text>Bundle-SymbolicName: </xsl:text><xsl:value-of select="$eclipse.plugin.id"/>
    <xsl:text>&#x0A;</xsl:text>
    <xsl:text>Bundle-Vendor: </xsl:text><xsl:value-of select="$eclipse.plugin.provider"/>
    <xsl:text>&#x0A;</xsl:text>
    <xsl:text>Bundle-Localization: plugin</xsl:text>
    <xsl:text>&#x0A;</xsl:text>
    <xsl:text>Require-Bundle: org.eclipse.help</xsl:text>
    <xsl:text>&#x0A;</xsl:text>
  </xsl:template>
</xsl:stylesheet>
