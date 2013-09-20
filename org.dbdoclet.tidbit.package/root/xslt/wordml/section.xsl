<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:w="http://schemas.microsoft.com/office/word/2003/wordml"
  xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word"
  xmlns:sl="http://schemas.microsoft.com/schemaLibrary/2003/core"
  xmlns:aml="http://schemas.microsoft.com/aml/2001/core" xmlns:wx="http://schemas.microsoft.com/office/word/2003/auxHint"
  xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882">

  <xsl:template match="book | article | chapter| section">
    <xsl:call-template name="anchor"/>
    <wx:sect>
      <xsl:apply-templates />
    </wx:sect>
  </xsl:template>

  <xsl:template match="book/title | article/title">
    <xsl:call-template name="insert-paragraph-with-style">
      <xsl:with-param name="style" select="'Title'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="chapter/title">
    <xsl:call-template name="insert-paragraph-with-style">
      <xsl:with-param name="style" select="'berschrift1'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template
    match="article/section/title | book/chapter/section/title | sect1/title">
    <xsl:call-template name="insert-paragraph-with-style">
      <xsl:with-param name="style" select="'berschrift2'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template
    match="article/section/section/title | book/chapter/section/section/title |sect2/title">
    <xsl:call-template name="insert-paragraph-with-style">
      <xsl:with-param name="style" select="'berschrift3'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template
    match="article/section/section/section/title | book/chapter/section/section/section/title | sect3/title">
    <xsl:call-template name="insert-paragraph-with-style">
      <xsl:with-param name="style" select="'berschrift4'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template
    match="article/section/section/section/section/title | book/chapter/section/section/section/section/title | sect4/title">
    <xsl:call-template name="insert-paragraph-with-style">
      <xsl:with-param name="style" select="'berschrift5'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template
    match="article/section/section/section/section/section/title | book/chapter/section/section/section/section/section/title | sect5/title">
    <xsl:call-template name="insert-paragraph-with-style">
      <xsl:with-param name="style" select="'berschrift6'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template
    match="article/section/section/section/section/section/section/title | book/chapter/section/section/section/section/section/section/title">
    <xsl:call-template name="insert-paragraph-with-style">
      <xsl:with-param name="style" select="'berschrift7'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template name="anchor">
    <xsl:if test="@id">
      <aml:annotation aml:id="0" w:type="Word.Bookmark.Start" w:name="{@id}"/>
      <aml:annotation aml:id="0" w:type="Word.Bookmark.End"/>
    </xsl:if>
  </xsl:template>

</xsl:stylesheet>
