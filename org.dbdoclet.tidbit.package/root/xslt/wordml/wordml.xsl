<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:w="http://schemas.microsoft.com/office/word/2003/wordml"
  xmlns:v="urn:schemas-microsoft-com:vml" xmlns:w10="urn:schemas-microsoft-com:office:word"
  xmlns:sl="http://schemas.microsoft.com/schemaLibrary/2003/core"
  xmlns:aml="http://schemas.microsoft.com/aml/2001/core" xmlns:wx="http://schemas.microsoft.com/office/word/2003/auxHint"
  xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:dt="uuid:C2F41010-65B3-11d1-A29F-00AA00C14882">
  
  <xsl:import href="template.xsl" />
  <xsl:include href="index.xsl" />
  <xsl:include href="link.xsl" />
  <xsl:include href="list.xsl" />
  <xsl:include href="refentry.xsl" />
  <xsl:include href="section.xsl" />
  <xsl:include href="table.xsl" />
  <xsl:include href="verbatim.xsl" />

  <xsl:output method="xml" version="1.0" encoding="UTF-8"
    indent="no" />

  <xsl:key name="id" match="*" use="@id|@xml:id"/>

  <xsl:template match="classname">
    <xsl:call-template name="insert-text-with-style">
      <xsl:with-param name="style" select="'ProgrammListing'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template match="classsynopsis">
    <w:p>
      <xsl:apply-templates select="ooclass" />
      <xsl:apply-templates select="oointerface" />
      <xsl:apply-templates select="ooexception" />
      <xsl:call-template name="insert-opening-brace" />
      <xsl:apply-templates
        select="classsynopsisinfo | fieldsynopsis | constructorsynopsis | methodsynopsis" />
      <xsl:call-template name="insert-closing-brace" />
    </w:p>
  </xsl:template>

  <xsl:template match="classsynopsisinfo">
    <xsl:call-template name="insert-newline" />
    <xsl:call-template name="insert-text-with-style">
      <xsl:with-param name="style" select="'Comment'" />
      <xsl:with-param name="newline" select="'after'" />
      <xsl:with-param name="indent" select="'1'" />
    </xsl:call-template>
  </xsl:template>

  <!--
       constructorsynopsis
       -->
  <xsl:template match="constructorsynopsis">
    <w:p>
      <xsl:apply-templates select="modifier" />
      <xsl:apply-templates select="methodname" />
      <xsl:call-template name="insert-opening-parenthesis" />
      <xsl:apply-templates select="methodparam" />
      <xsl:call-template name="insert-closing-parenthesis" />
      <xsl:call-template name="insert-eosl" />
    </w:p>
  </xsl:template>

  <!--
       methodsynopsis
       -->
  <xsl:template match="methodsynopsis">
    <w:p>
      <xsl:apply-templates select="modifier" />
      <xsl:apply-templates select="methodname" />
      <xsl:call-template name="insert-opening-parenthesis" />
      <xsl:apply-templates select="methodparam" />
      <xsl:call-template name="insert-closing-parenthesis" />
      <xsl:call-template name="insert-eosl" />
    </w:p>
  </xsl:template>

  <!--
       Konstruktoren und Methoden innerhalb eines classsynopsis-Elements
       -->
  <xsl:template
    match="classsynopsis/constructorsynopsis
           | classsynopsis/methodsynopsis">
    <xsl:apply-templates select="modifier" />
    <xsl:apply-templates select="methodname" />
    <xsl:call-template name="insert-opening-parenthesis" />
    <xsl:apply-templates select="methodparam" />
    <xsl:call-template name="insert-closing-parenthesis" />
    <xsl:call-template name="insert-eosl" />
  </xsl:template>

  <xsl:template
    match="classsynopsis//methodparam 
           | constructorsynopsis//methodparam
           | methodsynopsis//methodparam">
    <xsl:apply-templates select="type" />
    <xsl:apply-templates select="parameter" />
    <xsl:if test="position() != last()">
      <xsl:call-template name="insert-comma" />
    </xsl:if>
  </xsl:template>

  <xsl:template match="fieldsynopsis">
    <xsl:apply-templates />
    <xsl:call-template name="insert-eosl" />
  </xsl:template>

  <!--
       modifier
       -->
  <xsl:template
    match="classsynopsis//modifier 
           | constructorsynopsis//modifier
           | methodsynopsis//modifier">
    <xsl:choose>
      <xsl:when test="position() = 1">
        <xsl:call-template name="insert-text-with-style">
          <xsl:with-param name="style" select="'Keyword'" />
          <xsl:with-param name="indent" select="'1'" />
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="insert-text-with-style">
          <xsl:with-param name="style" select="'Keyword'" />
          <xsl:with-param name="indent" select="'0'" />
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>
    <xsl:call-template name="insert-space" />
  </xsl:template>

  <xsl:template
    match="classsynopsis//methodname
           | classsynopsis//methodparam/parameter
           | constructorsynopsis//methodname
           | methodsynopsis//methodname
           | methodsynopsis//methodparam/parameter
           ">
    <xsl:call-template name="insert-text-with-style">
      <xsl:with-param name="style" select="'ProgrammListing'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template
    match="classsynopsis//type
           | classsynopsis//varname
           | methodsynopsis//type
           | member/varname">
    <xsl:call-template name="insert-text-with-style">
      <xsl:with-param name="style" select="'ProgrammListing'" />
    </xsl:call-template>
    <xsl:call-template name="insert-space" />
  </xsl:template>

  <xsl:template match="colspec">
    <w:gridCol w:w="3070" />
  </xsl:template>

  <!-- E -->

  <xsl:template match="emphasis">
    <w:r>
      <w:rPr>
        <xsl:choose>
          <xsl:when test="@role='bold'">
            <w:b />
          </xsl:when>
          <xsl:otherwise>
            <w:i />
          </xsl:otherwise>
        </xsl:choose>
      </w:rPr>

      <w:t>
        <xsl:value-of select="." />
      </w:t>
    </w:r>
  </xsl:template>

  <!-- F -->
  <xsl:template match="fieldsynopsis/initializer">
    <xsl:call-template name="insert-text-with-style">
      <xsl:with-param name="before" select="'= '" />
      <xsl:with-param name="style" select="'ProgrammListing'" />
    </xsl:call-template>
    <xsl:call-template name="insert-space" />
  </xsl:template>

  <!-- I -->
  <xsl:template match="imagedata" mode="base64">
    <xsl:variable name="picname">
      <xsl:text>wordml://</xsl:text>
      <xsl:number level="any" format="00000001" />
      <xsl:text>.png</xsl:text>
    </xsl:variable>
    <w:pict>
      <v:shapetype id="_x0000_t75" coordsize="21600,21600"
        o:spt="75" o:preferrelative="t" path="m@4@5l@4@11@9@11@9@5xe"
        filled="f" stroked="f">
        <v:stroke joinstyle="miter" />
        <v:formulas>
          <v:f eqn="if lineDrawn pixelLineWidth 0" />
          <v:f eqn="sum @0 1 0" />
          <v:f eqn="sum 0 0 @1" />
          <v:f eqn="prod @2 1 2" />
          <v:f eqn="prod @3 21600 pixelWidth" />
          <v:f eqn="prod @3 21600 pixelHeight" />
          <v:f eqn="sum @0 0 1" />
          <v:f eqn="prod @6 1 2" />
          <v:f eqn="prod @7 21600 pixelWidth" />
          <v:f eqn="sum @8 21600 0" />
          <v:f eqn="prod @7 21600 pixelHeight" />
          <v:f eqn="sum @10 21600 0" />
        </v:formulas>
        <v:path o:extrusionok="f" gradientshapeok="t" o:connecttype="rect" />
        <o:lock v:ext="edit" aspectratio="t" />
      </v:shapetype>
      <w:binData>
        <xsl:attribute name="w:name">
          <xsl:value-of select="$picname" />
        </xsl:attribute>
        <xsl:value-of select="document(@fileref)" />
        <!-- <xsl:value-of select="concat('file:/', @fileref)"/> -->
      </w:binData>
      <v:shape id="_x0000_i1028" type="#_x0000_t75"
        style="width:{@width};height:{@depth}">
        <v:imagedata o:title="image">
          <xsl:attribute name="src">
            <xsl:value-of select="$picname" />
          </xsl:attribute>
        </v:imagedata>
      </v:shape>
    </w:pict>
  </xsl:template>

  <xsl:template match="imagedata[1]" mode="ref">
    <w:pict>
      <v:shapetype id="_x0000_t75" coordsize="21600,21600"
        o:spt="75" o:preferrelative="t" path="m@4@5l@4@11@9@11@9@5xe"
        filled="f" stroked="f">
        <v:stroke joinstyle="miter" />
        <v:formulas>
          <v:f eqn="if lineDrawn pixelLineWidth 0" />
          <v:f eqn="sum @0 1 0" />
          <v:f eqn="sum 0 0 @1" />
          <v:f eqn="prod @2 1 2" />
          <v:f eqn="prod @3 21600 pixelWidth" />
          <v:f eqn="prod @3 21600 pixelHeight" />
          <v:f eqn="sum @0 0 1" />
          <v:f eqn="prod @6 1 2" />
          <v:f eqn="prod @7 21600 pixelWidth" />
          <v:f eqn="sum @8 21600 0" />
          <v:f eqn="prod @7 21600 pixelHeight" />
          <v:f eqn="sum @10 21600 0" />
        </v:formulas>
        <v:path o:extrusionok="f" gradientshapeok="t" o:connecttype="rect" />
        <o:lock v:ext="edit" aspectratio="t" />
      </v:shapetype>
      <v:shape id="_x0000_i1028" type="#_x0000_t75"
        style="width:{@contentwidth};height:{@contentdepth}">
        <v:imagedata src="{@fileref}" />
      </v:shape>
    </w:pict>
  </xsl:template>

  <xsl:template match="imageobject">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="informalfigure">
    <xsl:apply-templates />
  </xsl:template>

  <xsl:template match="inlinemediaobject">
    <xsl:apply-templates select="imageobject[1]/imagedata"
      mode="ref" />
  </xsl:template>

  <!-- M -->
  <xsl:template match="mediaobject">
    <xsl:choose>
      <xsl:when test="parent::para">
        <xsl:apply-templates select="imageobject[1]/imagedata"
          mode="ref" />
      </xsl:when>
      <xsl:otherwise>
        <w:p>
          <xsl:apply-templates select="imageobject[1]/imagedata"
            mode="ref" />
        </w:p>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="member">
    <xsl:apply-templates />
    <xsl:if test="position() != last()">
      <xsl:call-template name="insert-comma" />
    </xsl:if>
  </xsl:template>

  <!-- O -->
  <xsl:template match="ooclass">
    <xsl:apply-templates select="modifier" />
    <xsl:apply-templates select="classname" />
  </xsl:template>

  <xsl:template match="oointerface">
    <xsl:choose>
      <xsl:when test="position() = 1">
        <xsl:call-template name="insert-text-with-style">
          <xsl:with-param name="style" select="'Keyword'" />
          <xsl:with-param name="newline" select="'before'" />
          <xsl:with-param name="indent" select="'1'" />
          <xsl:with-param name="text" select="'implements '" />
        </xsl:call-template>
        <xsl:call-template name="insert-text-with-style">
          <xsl:with-param name="style" select="'ProgrammListing'" />
          <xsl:with-param name="text" select="interfacename" />
        </xsl:call-template>
      </xsl:when>
      <xsl:otherwise>
        <xsl:call-template name="insert-text-with-style">
          <xsl:with-param name="style" select="'ProgrammListing'" />
          <xsl:with-param name="before" select="', '" />
          <xsl:with-param name="text" select="interfacename" />
        </xsl:call-template>
      </xsl:otherwise>
    </xsl:choose>

  </xsl:template>

  <!-- P -->
  <xsl:template match="para">
    <xsl:variable name="style">
      <xsl:choose>
        <xsl:when test="parent::entry">
          <xsl:text>Standard</xsl:text>
        </xsl:when>
        <xsl:otherwise>
          <xsl:text>Para</xsl:text>
        </xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <w:p>
      <w:pPr>
        <w:pStyle w:val="{$style}" />
      </w:pPr>
      <xsl:apply-templates/>
    </w:p>
  </xsl:template>

  <xsl:template match="para[not(parent::listitem)]/text()">
    <w:r>
      <w:t>
        <xsl:value-of select="normalize-space(.)" />
      </w:t>
    </w:r>
  </xsl:template>

  <xsl:template match="simplelist">
    <xsl:apply-templates select="member" />
  </xsl:template>

  <!-- T -->
  <xsl:template match="term">
    <xsl:call-template name="insert-paragraph-with-style">
      <xsl:with-param name="style" select="'Standard'" />
      <xsl:with-param name="bold" select="'true'" />
    </xsl:call-template>
  </xsl:template>

  <!-- V -->

  <!-- Benannte Vorlagen -->

  <!--
       insert-eosl End Of Statement Line Fügt ein Semikolon und einen
       Zeilenumbruch ein.
       -->
  <xsl:template name="insert-eosl">
    <w:r>
      <w:t>
        <xsl:text>;</xsl:text>
      </w:t>
      <w:br />
    </w:r>
  </xsl:template>

  <!-- insert-newline
       Fügt einen Zeilenumbruch ein
       -->
  <xsl:template name="insert-newline">
    <w:r>
      <w:br />
    </w:r>
  </xsl:template>

  <!-- insert-space
       Fügt ein Leerzeichen ein
       -->
  <xsl:template name="insert-space">
    <w:r>
      <w:t>
        <xsl:text> </xsl:text>
      </w:t>
    </w:r>
  </xsl:template>

  <!-- insert-comma
       Fügt ein Komma ein
       -->
  <xsl:template name="insert-comma">
    <w:r>
      <w:t>
        <xsl:text>, </xsl:text>
      </w:t>
    </w:r>
  </xsl:template>

  <!--
       insert-indent Fügt eine Einrückung ein. Die Tiefe der Einrückung wird
       durch den Parameter level bestimmt.
       -->
  <xsl:template name="insert-indent">
    <xsl:param name="level" select="'0'" />
    <xsl:if test="$level &gt; 0">
      <xsl:text>   </xsl:text>
      <xsl:call-template name="insert-indent">
        <xsl:with-param name="level" select="$level - 1" />
      </xsl:call-template>
    </xsl:if>
  </xsl:template>

  <xsl:template name="insert-opening-brace">
    <xsl:call-template name="insert-text-with-style">
      <xsl:with-param name="style" select="'ProgrammListing'" />
      <xsl:with-param name="text" select="' {'" />
      <xsl:with-param name="newline" select="'after'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template name="insert-closing-brace">
    <xsl:call-template name="insert-text-with-style">
      <xsl:with-param name="style" select="'ProgrammListing'" />
      <xsl:with-param name="text" select="'}'" />
      <xsl:with-param name="newline" select="'after'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template name="insert-opening-parenthesis">
    <xsl:call-template name="insert-text-with-style">
      <xsl:with-param name="style" select="'ProgrammListing'" />
      <xsl:with-param name="text" select="'('" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template name="insert-closing-parenthesis">
    <xsl:call-template name="insert-text-with-style">
      <xsl:with-param name="style" select="'ProgrammListing'" />
      <xsl:with-param name="text" select="')'" />
    </xsl:call-template>
  </xsl:template>

  <xsl:template name="insert-paragraph-with-style">
    <xsl:param name="style" select="'Para'" />
    <xsl:param name="bold" select="'false'" />
    <w:p>
      <w:pPr>
        <w:pStyle>
          <xsl:attribute name="w:val">
            <xsl:value-of select="$style" />
          </xsl:attribute>
        </w:pStyle>
      </w:pPr>
      <w:r>
        <w:rPr>
          <xsl:if test="$bold='true'">
            <w:b />
          </xsl:if>
          <w:rStyle>
            <xsl:attribute name="w:val">
              <xsl:value-of select="$style" />
            </xsl:attribute>
          </w:rStyle>
        </w:rPr>
        <w:t>
          <xsl:apply-templates />
        </w:t>
      </w:r>
    </w:p>
  </xsl:template>

  <xsl:template name="insert-text-with-style">
    <xsl:param name="style" select="''" />
    <xsl:param name="text" select="." />
    <xsl:param name="newline" select="'no'" />
    <xsl:param name="indent" select="'0'" />
    <xsl:param name="before" select="''" />
    <w:r>
      <w:rPr>
        <w:rStyle>
          <xsl:attribute name="w:val">
            <xsl:value-of select="$style" />
          </xsl:attribute>
        </w:rStyle>
        <wx:font wx:val="Courier New" />
      </w:rPr>
      <xsl:choose>
        <xsl:when test="$newline = 'before'">
          <w:br />
          <w:t>
            <xsl:call-template name="insert-indent">
              <xsl:with-param name="level" select="$indent" />
            </xsl:call-template>
            <xsl:value-of select="$text" />
          </w:t>
        </xsl:when>
        <xsl:when test="$newline = 'after'">
          <w:t>
            <xsl:call-template name="insert-indent">
              <xsl:with-param name="level" select="$indent" />
            </xsl:call-template>
            <xsl:value-of select="$text" />
          </w:t>
          <w:br />
        </xsl:when>
        <xsl:otherwise>
          <w:t>
            <xsl:call-template name="insert-indent">
              <xsl:with-param name="level" select="$indent" />
            </xsl:call-template>
            <xsl:value-of select="$before" />
            <xsl:value-of select="$text" />
          </w:t>
        </xsl:otherwise>
      </xsl:choose>
    </w:r>
  </xsl:template>

</xsl:stylesheet>

