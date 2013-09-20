<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:w="http://schemas.microsoft.com/office/word/2003/wordml"
  xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word"
  xmlns:sl="http://schemas.microsoft.com/schemaLibrary/2003/core"
  xmlns:aml="http://schemas.microsoft.com/aml/2001/core" xmlns:wx="http://schemas.microsoft.com/office/word/2003/auxHint"
  xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882">

  <xsl:template match="refentry">
    <xsl:call-template name="anchor"/>
    <wx:sect>
      <xsl:apply-templates />
    </wx:sect>
  </xsl:template>

  <xsl:template
    match="refsect1/title">
    <xsl:call-template name="insert-paragraph-with-style">
      <xsl:with-param name="style" select="'berschrift2'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="refentry/refmeta/refentrytitle">
    <xsl:call-template name="insert-paragraph-with-style">
      <xsl:with-param name="style" select="'berschrift1'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="manvolnum | date | author | productname">
  </xsl:template>

  <xsl:template match="refname | refpurpose">
    <xsl:call-template name="insert-paragraph-with-style">
      <xsl:with-param name="style" select="'Standard'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="cmdsynopsis">
    <w:p>
      <w:pPr>
        <w:pStyle w:val="ProgrammListing" />
      </w:pPr>
      <xsl:apply-templates/>
    </w:p>
  </xsl:template>

  <xsl:template match="command | replaceable">
    <w:r>
      <w:rPr>
        <w:rStyle w:val="ProgrammListing" />
      </w:rPr>
      <w:t><xsl:value-of select="normalize-space(.)"/></w:t>
    </w:r>
  </xsl:template>

  <xsl:template match="arg">
    <w:r>
      <w:t><xsl:text> </xsl:text><xsl:value-of select="normalize-space(.)"/></w:t>
    </w:r>
  </xsl:template>

</xsl:stylesheet>
