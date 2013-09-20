<xsl:stylesheet exclude-result-prefixes="#default" version="1.0" xmlns:fo="http://www.w3.org/1999/XSL/Format" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:import href="http://docbook.sourceforge.net/release/xsl/current/fo/docbook.xsl"/>
  <xsl:include href="http://docbook.sourceforge.net/release/xsl/current/fo/dbdoclet-titlepage.xsl"/>
   <xsl:param name="admon.graphics">1</xsl:param>
  <xsl:param name="admon.graphics.extension">.gif</xsl:param>
  <xsl:param name="admon.graphics.path">file:/usr/share/dbdoclet/docbook/xsl/images/</xsl:param>
  <xsl:param name="alignment">left</xsl:param>
  <xsl:param name="autotoc.label.separator"> </xsl:param>
  <xsl:param name="body.font.family">sans-serif</xsl:param>
  <xsl:param name="body.font.master">10</xsl:param>
  <xsl:param name="body.start.indent">2pt</xsl:param>
  <xsl:param name="callout.graphics">1</xsl:param>
  <xsl:param name="callout.graphics.extension">.gif</xsl:param>
  <xsl:param name="callout.graphics.path">file:/usr/share/dbdoclet/docbook/xsl/images/callouts/</xsl:param>
  <xsl:param name="chapter.autolabel">1</xsl:param>
  <xsl:param name="column.count.back">1</xsl:param>
  <xsl:param name="column.count.body">1</xsl:param>
  <xsl:param name="column.count.front">1</xsl:param>
  <xsl:param name="column.count.index">1</xsl:param>
  <xsl:param name="double.sided">0</xsl:param>
  <xsl:param name="draft.mode">0</xsl:param>
  <xsl:param name="draft.watermark.image">/usr/share/dbdoclet/docbook/xsl/images/draft.png</xsl:param>
  <xsl:param name="fop.extensions">0</xsl:param>
  <xsl:param name="fop1.extensions">1</xsl:param>
  <xsl:param name="generate.index">1</xsl:param>
  <xsl:param name="insert.xref.page.number">1</xsl:param>
  <xsl:param name="page.orientation">portrait</xsl:param>
  <xsl:param name="paper.type">A4</xsl:param>
  <xsl:param name="section.autolabel">1</xsl:param>
  <xsl:param name="section.label.includes.component.label">0</xsl:param>
  <xsl:param name="shade.verbatim">0</xsl:param>
  <xsl:param name="tablecolumns.extension">1</xsl:param>
  <xsl:param name="title.margin.left">0in</xsl:param>
  <xsl:param name="toc.section.depth">1</xsl:param>
  <xsl:param name="use.extensions">1</xsl:param>
  <xsl:attribute-set name="component.title.properties">
    <xsl:attribute name="color">#333333</xsl:attribute>
    <xsl:attribute name="font-size">
       <xsl:value-of select="$body.font.master * 1.6"/>
       <xsl:text>pt</xsl:text>
     </xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="section.title.properties">
    <xsl:attribute name="color">#333333</xsl:attribute>
    <xsl:attribute name="font-size">
       <xsl:value-of select="$body.font.master * 1.2"/>
       <xsl:text>pt</xsl:text>
     </xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="section.title.level1.properties">
    <xsl:attribute name="color">#333333</xsl:attribute>
    <xsl:attribute name="border-after-style">solid</xsl:attribute>
    <xsl:attribute name="border-after-width">.1mm</xsl:attribute>
    <xsl:attribute name="font-size">
       <xsl:value-of select="$body.font.master * 1.2"/>
       <xsl:text>pt</xsl:text>
     </xsl:attribute>
    <xsl:attribute name="space-before.minimum">4.0em</xsl:attribute>
    <xsl:attribute name="space-before.optimum">4.0em</xsl:attribute>
    <xsl:attribute name="space-before.maximum">4.0em</xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="section.title.level2.properties">
    <xsl:attribute name="color">#333333</xsl:attribute>
    <xsl:attribute name="start-indent">0in</xsl:attribute>
    <xsl:attribute name="end-indent">0in</xsl:attribute>
    <xsl:attribute name="font-style">normal</xsl:attribute>
    <xsl:attribute name="font-weight">bold</xsl:attribute>
    <xsl:attribute name="font-size">
       <xsl:value-of select="$body.font.master * 1.1"/>
       <xsl:text>pt</xsl:text>
     </xsl:attribute>
    <xsl:attribute name="space-before.minimum">3.0em</xsl:attribute>
    <xsl:attribute name="space-before.optimum">3.0em</xsl:attribute>
    <xsl:attribute name="space-before.maximum">3.0em</xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="section.title.level3.properties">
    <xsl:attribute name="color">#444444</xsl:attribute>
    <xsl:attribute name="font-style">normal</xsl:attribute>
    <xsl:attribute name="font-size">
       <xsl:value-of select="$body.font.master * 1.0"/>
       <xsl:text>pt</xsl:text>
     </xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="formal.title.properties">
    <xsl:attribute name="font-size">
       <xsl:value-of select="$body.font.master * 1.0"/>
       <xsl:text>pt</xsl:text>
     </xsl:attribute>
    <xsl:attribute name="space-after.minimum">0.0em</xsl:attribute>
    <xsl:attribute name="space-after.optimum">0.2em</xsl:attribute>
    <xsl:attribute name="space-after.maximum">0.2em</xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="formal.object.properties">
    <xsl:attribute name="space-before.minimum">0.2em</xsl:attribute>
    <xsl:attribute name="space-before.optimum">0.5em</xsl:attribute>
    <xsl:attribute name="space-before.maximum">1em</xsl:attribute>
    <xsl:attribute name="space-after.minimum">0.2em</xsl:attribute>
    <xsl:attribute name="space-after.optimum">0.5em</xsl:attribute>
    <xsl:attribute name="space-after.maximum">1em</xsl:attribute>
    <xsl:attribute name="keep-together.within-column">always</xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="list.block.spacing">
    <xsl:attribute name="space-before.minimum">0em</xsl:attribute>
    <xsl:attribute name="space-before.optimum">0em</xsl:attribute>
    <xsl:attribute name="space-before.maximum">0em</xsl:attribute>
    <xsl:attribute name="space-after.minimum">0.0em</xsl:attribute>
    <xsl:attribute name="space-after.optimum">0.7em</xsl:attribute>
    <xsl:attribute name="space-after.maximum">0.7em</xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="list.item.spacing">
    <xsl:attribute name="space-before.minimum">0.0em</xsl:attribute>
    <xsl:attribute name="space-before.optimum">0.3em</xsl:attribute>
    <xsl:attribute name="space-before.maximum">0.3em</xsl:attribute>
    <xsl:attribute name="space-after.minimum">0em</xsl:attribute>
    <xsl:attribute name="space-after.optimum">0.2em</xsl:attribute>
    <xsl:attribute name="space-after.maximum">0.2em</xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="classsynopsis.properties">
    <xsl:attribute name="font-family">
       <xsl:value-of select="$monospace.font.family"/>
     </xsl:attribute>
    <xsl:attribute name="font-size">
       <xsl:value-of select="$body.font.master * 0.9"/>
       <xsl:text>pt</xsl:text>
     </xsl:attribute>
    <xsl:attribute name="space-before.minimum">1.0em</xsl:attribute>
    <xsl:attribute name="space-before.optimum">1.0em</xsl:attribute>
    <xsl:attribute name="space-before.maximum">1.0em</xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="synopsis.properties">
    <xsl:attribute name="font-family">
       <xsl:value-of select="$monospace.font.family"/>
     </xsl:attribute>
    <xsl:attribute name="font-size">
       <xsl:value-of select="$body.font.master * 0.9"/>
       <xsl:text>pt</xsl:text>
     </xsl:attribute>
    <xsl:attribute name="space-before.minimum">0.33em</xsl:attribute>
    <xsl:attribute name="space-before.optimum">0.33em</xsl:attribute>
    <xsl:attribute name="space-before.maximum">0.33em</xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="synopsis.name">
    <xsl:attribute name="font-family">
       <xsl:value-of select="$monospace.font.family"/>
     </xsl:attribute>
    <xsl:attribute name="font-weight">normal</xsl:attribute>
    <xsl:attribute name="font-style">normal</xsl:attribute>
    <xsl:attribute name="color">#111111</xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="synopsis.keyword">
    <xsl:attribute name="font-family">
       <xsl:value-of select="$monospace.font.family"/>
     </xsl:attribute>
    <xsl:attribute name="font-weight">bold</xsl:attribute>
    <xsl:attribute name="font-style">normal</xsl:attribute>
    <xsl:attribute name="color">purple</xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="synopsis.annotation">
    <xsl:attribute name="font-family">
       <xsl:value-of select="$monospace.font.family"/>
     </xsl:attribute>
    <xsl:attribute name="font-weight">normal</xsl:attribute>
    <xsl:attribute name="font-style">normal</xsl:attribute>
    <xsl:attribute name="color">#e75480</xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="synopsis.type">
    <xsl:attribute name="font-family">
       <xsl:value-of select="$monospace.font.family"/>
     </xsl:attribute>
    <xsl:attribute name="font-weight">normal</xsl:attribute>
    <xsl:attribute name="font-style">normal</xsl:attribute>
    <xsl:attribute name="color">darkblue</xsl:attribute>
  </xsl:attribute-set>
  <xsl:attribute-set name="synopsis.comment">
    <xsl:attribute name="font-family">
       <xsl:value-of select="$monospace.font.family"/>
     </xsl:attribute>
    <xsl:attribute name="font-weight">normal</xsl:attribute>
    <xsl:attribute name="font-style">italic</xsl:attribute>
    <xsl:attribute name="color">darkcyan</xsl:attribute>
  </xsl:attribute-set>
  <xsl:template match="processing-instruction(&apos;line-break&apos;)">
    <fo:block/>
  </xsl:template>
  <xsl:template match="classsynopsisinfo" mode="java">
    <xsl:if test="@role = &apos;comment&apos;">
       <xsl:text>&#x0A;  </xsl:text>
     </xsl:if>
    <fo:inline xsl:use-attribute-sets="synopsis.comment">
       <xsl:apply-templates mode="java"/>
     </fo:inline>
  </xsl:template>
  <xsl:template match="classsynopsis" mode="java">
    <fo:block linefeed-treatment="preserve" white-space-collapse="false" wrap-option="wrap" xsl:use-attribute-sets="classsynopsis.properties">
       <xsl:apply-templates mode="java" select="ooclass[1]"/>
       <xsl:if test="ooclass[position() &gt; 1]">
         <fo:inline xsl:use-attribute-sets="synopsis.keyword">
           <xsl:text> extends </xsl:text>
         </fo:inline>
         <xsl:apply-templates mode="java" select="ooclass[position() &gt; 1]"/>
         <xsl:if test="oointerface|ooexception">
           <xsl:text>&#x0A;  </xsl:text>
         </xsl:if>
       </xsl:if>
       <xsl:if test="oointerface">
         <fo:inline xsl:use-attribute-sets="synopsis.keyword">
           <xsl:text> implements </xsl:text>
         </fo:inline>
         <xsl:apply-templates mode="java" select="oointerface"/>
         <xsl:if test="ooexception">
           <xsl:text>&#x0A;    </xsl:text>
         </xsl:if>
       </xsl:if>
       <xsl:if test="ooexception">
         <fo:inline xsl:use-attribute-sets="synopsis.keyword">
           <xsl:text>throws</xsl:text>
         </fo:inline>
         <xsl:apply-templates mode="java" select="ooexception"/>
       </xsl:if>
       <xsl:text> {</xsl:text>
       <xsl:apply-templates mode="java" select="constructorsynopsis                                    |destructorsynopsis                                    |fieldsynopsis                                    |methodsynopsis                                    |classsynopsisinfo"/>
       <xsl:text>}</xsl:text>
     </fo:block>
  </xsl:template>
  <xsl:template match="fieldsynopsis" mode="java">
    <fo:block linefeed-treatment="preserve" white-space-collapse="false" wrap-option="wrap" xsl:use-attribute-sets="synopsis.properties">
       <xsl:text>  </xsl:text>
       <xsl:apply-templates mode="java"/>
       <xsl:text>;</xsl:text>
       <xsl:call-template name="synop-break"/>
     </fo:block>
  </xsl:template>
  <xsl:template match="constructorsynopsis|destructorsynopsis|methodsynopsis" mode="java">
    <xsl:variable name="start-modifiers" select="modifier[following-sibling::*[name(.) != &apos;modifier&apos;]]"/>
    <xsl:variable name="notmod" select="*[name(.) != &apos;modifier&apos;]"/>
    <xsl:variable name="end-modifiers" select="modifier[preceding-sibling::*[name(.) != &apos;modifier&apos;]]"/>
    <xsl:variable name="decl">
       <xsl:text>  </xsl:text>
       <xsl:apply-templates mode="java" select="$start-modifiers"/>
       <xsl:if test="name($notmod[1]) != &apos;methodname&apos;">
         <xsl:apply-templates mode="java" select="$notmod[1]"/>
       </xsl:if>
       <xsl:apply-templates mode="java" select="methodname"/>
     </xsl:variable>
    <fo:block linefeed-treatment="preserve" white-space-collapse="false" wrap-option="wrap" xsl:use-attribute-sets="synopsis.properties">
       <xsl:copy-of select="$decl"/>
       <xsl:text>(</xsl:text>
       <xsl:variable name="decl-length">
         <xsl:call-template name="get-decl-length">
           <xsl:with-param name="indent" select="string-length($decl)"/>
         </xsl:call-template>
       </xsl:variable>
       <xsl:apply-templates mode="java" select="methodparam">
         <xsl:with-param name="indent" select="string-length($decl)"/>
         <xsl:with-param name="decl-length" select="$decl-length"/>
       </xsl:apply-templates>
       <xsl:text>)</xsl:text>
       <xsl:if test="exceptionname">
         <fo:inline xsl:use-attribute-sets="synopsis.keyword">
           <xsl:text>&#x0A;    throws </xsl:text>
         </fo:inline>
         <xsl:apply-templates mode="java" select="exceptionname"/>
       </xsl:if>
       <xsl:if test="modifier[preceding-sibling::*[name(.) != &apos;modifier&apos;]]">
         <xsl:text>&#x0A; </xsl:text>
         <xsl:apply-templates mode="java" select="$end-modifiers"/>
       </xsl:if>
       <xsl:text>;</xsl:text>
     </fo:block>
    <xsl:call-template name="synop-break"/>
  </xsl:template>
  <xsl:template match="ooclass" mode="java">
    <xsl:if test="position() &gt; 1">
       <xsl:text>&#x0A; </xsl:text>
     </xsl:if>
    <fo:inline xsl:use-attribute-sets="synopsis.type">
       <xsl:apply-templates mode="java"/>
     </fo:inline>
  </xsl:template>
  <xsl:template match="oointerface" mode="java">
    <xsl:choose>
       <xsl:when test="name(preceding-sibling::*[1]) = &apos;oointerface&apos;">
         <xsl:text>,&#x0A;             </xsl:text>
       </xsl:when>
       <xsl:otherwise>
         <xsl:text/>
       </xsl:otherwise>
     </xsl:choose>
    <fo:inline xsl:use-attribute-sets="synopsis.type">
       <xsl:apply-templates mode="java"/>
     </fo:inline>
  </xsl:template>
  <xsl:template match="classname" mode="java">
    <xsl:if test="name(preceding-sibling::*[1]) = &apos;classname&apos;">
       <xsl:text>, </xsl:text>
     </xsl:if>
    <fo:inline xsl:use-attribute-sets="synopsis.name">
       <xsl:apply-templates mode="java"/>
     </fo:inline>
  </xsl:template>
  <xsl:template match="interfacename" mode="java">
    <xsl:if test="name(preceding-sibling::*[1]) = &apos;interfacename&apos;">
       <xsl:text>, </xsl:text>
     </xsl:if>
    <fo:inline xsl:use-attribute-sets="synopsis.name">
       <xsl:apply-templates mode="java"/>
     </fo:inline>
  </xsl:template>
  <xsl:template match="exceptionname" mode="java">
    <xsl:if test="name(preceding-sibling::*[1]) = &apos;exceptionname&apos;">
       <xsl:text>,&#x0A;           </xsl:text>
     </xsl:if>
    <fo:inline xsl:use-attribute-sets="synopsis.name">
       <xsl:apply-templates mode="java"/>
     </fo:inline>
  </xsl:template>
  <xsl:template match="methodname" mode="java">
    <fo:inline xsl:use-attribute-sets="synopsis.name">
       <xsl:apply-templates mode="java"/>
     </fo:inline>
  </xsl:template>
  <xsl:template match="methodparam" mode="java">
    <xsl:param name="indent">0</xsl:param>
    <xsl:param name="decl-length">0</xsl:param>
    <xsl:if test="preceding-sibling::methodparam">
       <xsl:choose>
         <xsl:when test="$decl-length &gt; 80">
           <xsl:text>,</xsl:text>
           <xsl:if test="$indent &gt; 0">
             <xsl:call-template name="copy-string">
               <xsl:with-param name="string"> </xsl:with-param>
               <xsl:with-param name="count" select="$indent + 1"/>
             </xsl:call-template>
           </xsl:if>
         </xsl:when>
         <xsl:otherwise>
           <xsl:text>, </xsl:text>
         </xsl:otherwise>
       </xsl:choose>
     </xsl:if>
    <xsl:apply-templates mode="java"/>
  </xsl:template>
  <xsl:template name="get-decl-length">
    <xsl:param name="indent">0</xsl:param>
    <xsl:variable name="buffer">
       <xsl:for-each select="methodparam">
         <xsl:if test="position()&gt;1">
           <xsl:text>, </xsl:text>
         </xsl:if>
         <xsl:value-of select="type"/>
         <xsl:text>&#x0A; </xsl:text>
         <xsl:value-of select="parameter"/>
         <xsl:text>&#x0A; </xsl:text>
       </xsl:for-each>
     </xsl:variable>
    <xsl:value-of select="$indent + string-length($buffer)"/>
  </xsl:template>
  <xsl:template match="type" mode="java">
    <fo:inline xsl:use-attribute-sets="synopsis.type">
       <xsl:apply-templates mode="java"/>
     </fo:inline>
    <xsl:text> </xsl:text>
  </xsl:template>
  <xsl:template match="varname" mode="java">
    <fo:inline xsl:use-attribute-sets="synopsis.name">
       <xsl:apply-templates mode="java"/>
     </fo:inline>
    <xsl:choose>
       <xsl:when test="name(.. ) = &apos;fieldsynopsis&apos;">
         <xsl:if test="count(../initializer) &gt; 0">
           <xsl:text> </xsl:text>
         </xsl:if>
       </xsl:when>
       <xsl:otherwise>
         <xsl:text> </xsl:text>
       </xsl:otherwise>
     </xsl:choose>
  </xsl:template>
  <xsl:template match="parameter" mode="java">
    <fo:inline xsl:use-attribute-sets="synopsis.name">
       <xsl:apply-templates mode="java"/>
     </fo:inline>
  </xsl:template>
  <xsl:template match="modifier" mode="java">
    <xsl:choose>
       <xsl:when test="@role = &apos;annotation&apos;">
         <fo:inline xsl:use-attribute-sets="synopsis.annotation">
           <xsl:apply-templates mode="java"/>
         </fo:inline>
       </xsl:when>
       <xsl:otherwise>
         <fo:inline xsl:use-attribute-sets="synopsis.keyword">
           <xsl:apply-templates mode="java"/>
         </fo:inline>
       </xsl:otherwise>
     </xsl:choose>
    <xsl:choose>
       <xsl:when test="@role = &apos;annotation&apos;">
         <xsl:choose>
           <xsl:when test="local-name(..)=&apos;ooclass&apos;">
             <xsl:text>&#x0A;</xsl:text>
           </xsl:when>
           <xsl:otherwise>
             <xsl:text>&#x0A;  </xsl:text>
           </xsl:otherwise>
         </xsl:choose>
       </xsl:when>
       <xsl:otherwise>
         <xsl:text> </xsl:text>
       </xsl:otherwise>
     </xsl:choose>
  </xsl:template>
</xsl:stylesheet>
