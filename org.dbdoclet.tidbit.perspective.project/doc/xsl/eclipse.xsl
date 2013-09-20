<?xml version ="1.0" encoding ="UTF-8"?>
<xsl:stylesheet exclude-result-prefixes="#default" version="1.0" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/eclipse/eclipse.xsl"></xsl:import>

  <xsl:include href="http://dbdoclet.org/xsl/functions.xsl"></xsl:include>
  <xsl:include href="http://dbdoclet.org/xsl/html/synopsis.xsl"></xsl:include>
  <xsl:include href="http://dbdoclet.org/xsl/html/themes/color.xsl"></xsl:include>

  <xsl:param name="admon.style"></xsl:param>
<xsl:param name="rootid"></xsl:param>
<xsl:param name="admon.graphics">0</xsl:param>
  <xsl:param name="admon.graphics.extension">.gif</xsl:param>
  <xsl:param name="admon.graphics.path">images/</xsl:param>
  <xsl:param name="admon.textlabel">1</xsl:param>
  
  <xsl:param name="base.dir">./eclipse/</xsl:param>
  <xsl:param name="html.stylesheet"></xsl:param>
  <xsl:param name="img.src.path">../</xsl:param>
  <xsl:param name="manifest.in.base.dir" select="1"></xsl:param>
  
  <xsl:param name="eclipse.plugin.name"></xsl:param>
  <xsl:param name="eclipse.plugin.id"></xsl:param>
  <xsl:param name="eclipse.plugin.version"></xsl:param>
  <xsl:param name="eclipse.plugin.provider"></xsl:param>
  <xsl:param name="chunker.output.quiet">'0'</xsl:param>

  <xsl:template name="plugin.xml">
    <xsl:call-template name="write.chunk">
      <xsl:with-param name="filename">
	<xsl:if test="$manifest.in.base.dir != 0">
	  <xsl:value-of select="$base.dir"></xsl:value-of>
	</xsl:if>
	<xsl:value-of select="'plugin.xml'"></xsl:value-of>
      </xsl:with-param>
      <xsl:with-param name="method" select="'xml'"></xsl:with-param>
      <xsl:with-param name="encoding" select="'utf-8'"></xsl:with-param>
      <xsl:with-param name="indent" select="'yes'"></xsl:with-param>
      <xsl:with-param name="quiet" select="'0'"></xsl:with-param>
      <xsl:with-param name="content">
	<xsl:text>
</xsl:text>
	<xsl:processing-instruction name="eclipse">
	  <xsl:text>version="3.3"</xsl:text>
	</xsl:processing-instruction>
	<plugin>
	  <extension point="org.eclipse.help.toc">
	    <toc file="toc.xml" primary="true"></toc>
	  </extension>
	  <extension point="org.eclipse.help.index">
	    <index file="index.xml"></index>
	  </extension>
	</plugin>
      </xsl:with-param>
    </xsl:call-template>
    <xsl:call-template name="write.text.chunk">
      <xsl:with-param name="filename">
	<xsl:if test="$manifest.in.base.dir != 0">
	  <xsl:value-of select="$base.dir"></xsl:value-of>
	</xsl:if>
	<xsl:value-of select="'META-INF/MANIFEST.MF'"></xsl:value-of>
      </xsl:with-param>
      <xsl:with-param name="encoding" select="'utf-8'"></xsl:with-param>
      <xsl:with-param name="indent" select="'yes'"></xsl:with-param>
      <xsl:with-param name="quiet" select="$chunk.quietly"></xsl:with-param>
      <xsl:with-param name="content">
	<xsl:text>Bundle-Name: </xsl:text><xsl:value-of select="$eclipse.plugin.name"></xsl:value-of>
	<xsl:text>
Bundle-SymbolicName: </xsl:text><xsl:value-of select="$eclipse.plugin.id"></xsl:value-of>
	<xsl:text>
Bundle-Version: </xsl:text><xsl:value-of select="$eclipse.plugin.version"></xsl:value-of>
	<xsl:text>
Bundle-Provider: </xsl:text><xsl:value-of select="$eclipse.plugin.provider"></xsl:value-of>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>

</xsl:stylesheet>
