<?xml version ="1.0" encoding ="UTF-8"?>

<xsl:stylesheet xmlns:xsl ="http://www.w3.org/1999/XSL/Transform"
		version ="1.0"
		xmlns:fo ="http://www.w3.org/1999/XSL/Format"
		exclude-result-prefixes ="#default"
		>

  <xsl:import href ="http://docbook.sourceforge.net/release/xsl/current/eclipse/eclipse.xsl"/>
  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/html/highlight.xsl"/>
  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/html/autoidx-kosek.xsl"/>
  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/html/autoidx-kimber.xsl"/>

  <xsl:include href="http://dbdoclet.org/xsl/functions.xsl"/>
  <xsl:include href="http://dbdoclet.org/xsl/html/synopsis.xsl"/>
  <xsl:include href="http://dbdoclet.org/xsl/html/themes/color.xsl"/>

  <xsl:param name="admon.graphics">0</xsl:param>
  <xsl:param name="admon.graphics.extension">.gif</xsl:param>
  <xsl:param name="admon.graphics.path">images/</xsl:param>
  <xsl:param name="admon.textlabel">1</xsl:param>
  
  <xsl:param name="base.dir">./eclipse/</xsl:param>
  <xsl:param name="html.stylesheet"></xsl:param>
  <xsl:param name="img.src.path">../</xsl:param>
  <xsl:param name="manifest.in.base.dir" select="1"></xsl:param>
  
  <xsl:param name="eclipse.plugin.name">plugin.name</xsl:param>
  <xsl:param name="eclipse.plugin.id">plugin.id</xsl:param>
  <xsl:param name="eclipse.plugin.version">plugin.version</xsl:param>
  <xsl:param name="eclipse.plugin.provider">plugin.provider</xsl:param>
  <xsl:param name="chunker.output.quiet">'0'</xsl:param>

  <xsl:template name="plugin.xml">
    <xsl:call-template name="write.chunk">
      <xsl:with-param name="filename">
	<xsl:if test="$manifest.in.base.dir != 0">
	  <xsl:value-of select="$base.dir"/>
	</xsl:if>
	<xsl:value-of select="'plugin.xml'"/>
      </xsl:with-param>
      <xsl:with-param name="method" select="'xml'"/>
      <xsl:with-param name="encoding" select="'utf-8'"/>
      <xsl:with-param name="indent" select="'yes'"/>
      <xsl:with-param name="quiet" select="'0'"/>
      <xsl:with-param name="content">
	<xsl:text>&#x0a;</xsl:text>
	<xsl:processing-instruction name="eclipse">
	  <xsl:text>version="3.3"</xsl:text>
	</xsl:processing-instruction>
	<plugin name="{$eclipse.plugin.name}"
        id="{$eclipse.plugin.id}"
        version="{$eclipse.plugin.version}"
        provider-name="{$eclipse.plugin.provider}"> 
	  <extension point="org.eclipse.help.toc">
	    <toc file="toc.xml" primary="true"/>
	  </extension>
	  <extension point="org.eclipse.help.index">
	    <index file="index.xml"/>
	  </extension>
	</plugin>
      </xsl:with-param>
    </xsl:call-template>
    <xsl:call-template name="write.text.chunk">
      <xsl:with-param name="filename">
	<xsl:if test="$manifest.in.base.dir != 0">
	  <xsl:value-of select="$base.dir"/>
	</xsl:if>
	<xsl:value-of select="'META-INF/MANIFEST.MF'"/>
      </xsl:with-param>
      <xsl:with-param name="encoding" select="'utf-8'"/>
      <xsl:with-param name="indent" select="'yes'"/>
      <xsl:with-param name="quiet" select="$chunk.quietly"/>
      <xsl:with-param name="content">
	<xsl:text>Bundle-Name: </xsl:text><xsl:value-of select="$eclipse.plugin.name"/>
	<xsl:text>&#x0a;Bundle-SymbolicName: </xsl:text><xsl:value-of select="$eclipse.plugin.id"/>
	<xsl:text>&#x0a;Bundle-Version: </xsl:text><xsl:value-of select="$eclipse.plugin.version"/>
	<xsl:text>&#x0a;Bundle-Provider: </xsl:text><xsl:value-of select="$eclipse.plugin.provider"/>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>

</xsl:stylesheet>
