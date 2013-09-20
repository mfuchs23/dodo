<?xml version ="1.0" encoding ="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	exclude-result-prefixes="#default">

  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/epub/docbook.xsl" />
	
  <xsl:include href="http://dbdoclet.org/xsl/html/synopsis.xsl" />
  <xsl:include href="http://dbdoclet.org/xsl/html/themes/color.xsl" />

  <xsl:param name="base.dir">./build/epub/</xsl:param>
  <xsl:param name="epub.oebps.dir">./build/epub/OEBPS/</xsl:param>
  <xsl:param name="epub.metainf.dir">./build/epub/META-INF/</xsl:param>
  <xsl:param name="suppress.navigation">1</xsl:param>
  <xsl:param name="html.stylesheet">dbdoclet.css</xsl:param>
  <xsl:param name="generate.toc"></xsl:param>

  <xsl:template name="container">
    <xsl:call-template name="write.chunk">
      <xsl:with-param name="filename">
        <xsl:value-of select="$epub.metainf.dir" />
        <xsl:value-of select="$epub.container.filename" />
      </xsl:with-param>
      <xsl:with-param name="method" select="'xml'" />
      <xsl:with-param name="encoding" select="'utf-8'" />
      <xsl:with-param name="indent" select="'no'" />
      <xsl:with-param name="quiet" select="$chunk.quietly" />
      <xsl:with-param name="doctype-public" select="''"/> <!-- intentionally blank -->
      <xsl:with-param name="doctype-system" select="''"/> <!-- intentionally blank -->

      <xsl:with-param name="content">
        <xsl:element namespace="urn:oasis:names:tc:opendocument:xmlns:container" name="container">
          <xsl:attribute name="version">1.0</xsl:attribute>
          <xsl:element namespace="urn:oasis:names:tc:opendocument:xmlns:container" name="rootfiles">
            <xsl:element namespace="urn:oasis:names:tc:opendocument:xmlns:container" name="rootfile">
              <xsl:attribute name="full-path">
                <xsl:value-of select="'OEBPS/content.opf'" />
              </xsl:attribute>
              <xsl:attribute name="media-type">
                <xsl:text>application/oebps-package+xml</xsl:text>
              </xsl:attribute>
            </xsl:element>
          </xsl:element>
        </xsl:element>
      </xsl:with-param>
    </xsl:call-template>
  </xsl:template>
</xsl:stylesheet>

