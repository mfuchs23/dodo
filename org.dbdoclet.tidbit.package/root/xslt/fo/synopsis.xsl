<?xml version='1.0'?>
<!DOCTYPE xsl:stylesheet [
<!ENTITY RE "&#10;">
<!ENTITY nbsp "&#160;">
]>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:fo="http://www.w3.org/1999/XSL/Format" version="1.0">

	<xsl:output method="xml" />

	<xsl:param name="line-length" select="80" />

	<xsl:template match="processing-instruction('line-break')">
		<fo:block />
	</xsl:template>

	<xsl:template match="classsynopsisinfo" mode="java">
		<xsl:if test="@role = 'comment'">
			<xsl:text>&RE;&nbsp;&nbsp;</xsl:text>
		</xsl:if>
		<fo:inline xsl:use-attribute-sets="synopsis.comment">
			<xsl:apply-templates mode="java" />
		</fo:inline>
		<!-- <xsl:if test="@role = 'comment'"> <xsl:text>&RE;</xsl:text> </xsl:if> -->
	</xsl:template>

	<xsl:template match="classsynopsis" mode="java">
		<fo:block wrap-option="wrap" white-space-collapse="false"
			linefeed-treatment="preserve" xsl:use-attribute-sets="monospace.verbatim.properties">

			<xsl:apply-templates select="ooclass[1]" mode="java" />

			<xsl:if test="ooclass[position() &gt; 1]">
				<fo:inline xsl:use-attribute-sets="synopsis.keyword">
					<xsl:text> extends </xsl:text>
				</fo:inline>
				<xsl:apply-templates select="ooclass[position() &gt; 1]"
					mode="java" />
				<xsl:if test="oointerface|ooexception">
					<xsl:text>&RE;&nbsp;&nbsp;</xsl:text>
				</xsl:if>
			</xsl:if>
			<xsl:if test="oointerface">
				<fo:inline xsl:use-attribute-sets="synopsis.keyword">
					<xsl:text> implements </xsl:text>
				</fo:inline>
				<xsl:apply-templates select="oointerface" mode="java" />
				<xsl:if test="ooexception">
					<xsl:text>&RE;&nbsp;&nbsp;&nbsp;&nbsp;</xsl:text>
				</xsl:if>
			</xsl:if>
			<xsl:if test="ooexception">
				<fo:inline xsl:use-attribute-sets="synopsis.keyword">
					<xsl:text>throws</xsl:text>
				</fo:inline>
				<xsl:apply-templates select="ooexception" mode="java" />
			</xsl:if>
			<xsl:text>&nbsp;{&RE;</xsl:text>
			<xsl:apply-templates
				select="constructorsynopsis
                                   |destructorsynopsis
                                   |fieldsynopsis
                                   |methodsynopsis
                                   |classsynopsisinfo"
				mode="java" />
			<xsl:text>}</xsl:text>
		</fo:block>
	</xsl:template>

	<xsl:template match="fieldsynopsis" mode="java">
		<fo:block wrap-option='wrap' white-space-collapse='false'
			linefeed-treatment="preserve" xsl:use-attribute-sets="monospace.verbatim.properties">
			<xsl:attribute name="space-before.minimum">0em</xsl:attribute>
			<xsl:attribute name="space-before.optimum">0em</xsl:attribute>
			<xsl:attribute name="space-before.maximum">0em</xsl:attribute>
			<xsl:attribute name="space-after.minimum">0em</xsl:attribute>
			<xsl:attribute name="space-after.optimum">0em</xsl:attribute>
			<xsl:attribute name="space-after.maximum">0em</xsl:attribute>
			<xsl:text>&nbsp;&nbsp;</xsl:text>
			<xsl:apply-templates mode="java" />
			<xsl:text>;</xsl:text>
			<xsl:call-template name="synop-break" />
		</fo:block>
	</xsl:template>

	<xsl:template mode="java"
		match="constructorsynopsis|destructorsynopsis|methodsynopsis">
		<xsl:variable name="start-modifiers"
			select="modifier[following-sibling::*[name(.) != 'modifier']]" />
		<xsl:variable name="notmod" select="*[name(.) != 'modifier']" />
		<xsl:variable name="end-modifiers"
			select="modifier[preceding-sibling::*[name(.) != 'modifier']]" />
		<xsl:variable name="decl">
			<xsl:text>&nbsp;&nbsp;</xsl:text>
			<xsl:apply-templates select="$start-modifiers"
				mode="java" />
			<!-- type -->
			<xsl:if test="name($notmod[1]) != 'methodname'">
				<xsl:apply-templates select="$notmod[1]" mode="java" />
			</xsl:if>
			<xsl:apply-templates select="methodname" mode="java" />
		</xsl:variable>
		<fo:block wrap-option='wrap' white-space-collapse='false'
			linefeed-treatment="preserve" xsl:use-attribute-sets="monospace.verbatim.properties">
			<xsl:attribute name="space-before.minimum">0em</xsl:attribute>
			<xsl:attribute name="space-before.optimum">0em</xsl:attribute>
			<xsl:attribute name="space-before.maximum">0em</xsl:attribute>
			<xsl:attribute name="space-after.minimum">0em</xsl:attribute>
			<xsl:attribute name="space-after.optimum">0em</xsl:attribute>
			<xsl:attribute name="space-after.maximum">0em</xsl:attribute>
			<xsl:copy-of select="$decl" />
			<xsl:text>(</xsl:text>
			<!-- Länge der Deklaration mit allen Parametern -->
			<xsl:variable name="decl-length">
				<xsl:call-template name="get-decl-length">
					<xsl:with-param name="indent" select="string-length($decl)" />
				</xsl:call-template>
			</xsl:variable>
			<!-- Länge der Deklaration mit nur dem ersten Parameter -->
			<xsl:variable name="decl-first-param-length">
				<xsl:call-template name="get-decl-first-param-length">
					<xsl:with-param name="indent" select="string-length($decl)" />
				</xsl:call-template>
			</xsl:variable>
			<!-- Zur Berechnung der Einrückungstiefe ist nur die letzte Zeile der 
				Deklaration interessant. Darüber stehende Annotationen dürfen nicht mit in 
				die Berechnung einfliessen. -->
			<xsl:variable name="decl-last-line">
				<xsl:choose>
					<xsl:when test="contains($decl, '&#xA;')">
						<xsl:value-of select="substring-after($decl, '&#xA;')" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="$decl" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:variable name="max-param-length">
				<xsl:for-each select="methodparam">
					<xsl:sort select="string-length(concat(type, ' ', parameter))"
						data-type="number" />
					<xsl:if test="position() = last()">
						<xsl:value-of select="string-length(concat(type, ' ', parameter))" />
					</xsl:if>
				</xsl:for-each>
			</xsl:variable>
			<!-- <xsl:message> <xsl:text>[</xsl:text> <xsl:value-of select="../methodname"/> 
				<xsl:text>] decl=</xsl:text> <xsl:value-of select="$decl"/> <xsl:text>, decl-last-line=</xsl:text> 
				<xsl:value-of select="$decl-last-line"/> <xsl:text>, decl-length=</xsl:text> 
				<xsl:value-of select="string-length($decl)"/> <xsl:text>, decl-first-param-length=</xsl:text> 
				<xsl:value-of select="$decl-first-param-length"/> <xsl:text>, max-param-length=</xsl:text> 
				<xsl:value-of select="$max-param-length"/> </xsl:message> -->
			<!-- Bestimmung der Einrückungstiefe -->
			<xsl:variable name="indent">
				<xsl:choose>
					<xsl:when
						test="((string-length($decl-last-line) + $max-param-length) &gt; $line-length)
			  and (string-length($decl-last-line) &gt; ($line-length - $max-param-length))">
						<xsl:value-of select="$line-length - $max-param-length + 1" />
					</xsl:when>
					<xsl:otherwise>
						<xsl:value-of select="string-length($decl-last-line) + 1" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:variable>
			<xsl:apply-templates select="methodparam" mode="java">
				<xsl:with-param name="indent" select="$indent" />
				<xsl:with-param name="decl-length" select="$decl-length" />
				<xsl:with-param name="decl-first-param-length"
					select="$decl-first-param-length" />
			</xsl:apply-templates>
			<xsl:text>)</xsl:text>
			<xsl:if test="exceptionname">
				<fo:inline xsl:use-attribute-sets="synopsis.keyword">
					<xsl:text>&RE;&nbsp;&nbsp;&nbsp;&nbsp;throws&nbsp;</xsl:text>
				</fo:inline>
				<xsl:apply-templates select="exceptionname"
					mode="java" />
			</xsl:if>
			<xsl:if test="modifier[preceding-sibling::*[name(.) != 'modifier']]">
				<xsl:text> </xsl:text>
				<xsl:apply-templates select="$end-modifiers"
					mode="java" />
			</xsl:if>
			<xsl:text>;</xsl:text>
		</fo:block>
		<xsl:call-template name="synop-break" />
	</xsl:template>

	<xsl:template match="ooclass" mode="java">
		<xsl:if test="position() &gt; 1">
			<xsl:text>, </xsl:text>
		</xsl:if>
		<xsl:apply-templates mode="java" />
	</xsl:template>

	<xsl:template match="oointerface" mode="java">
		<xsl:choose>
			<xsl:when test="name(preceding-sibling::*[1]) = 'oointerface'">
				<xsl:text>,&RE;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</xsl:text>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text></xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:apply-templates mode="java" />
	</xsl:template>

	<xsl:template match="classname" mode="java">
		<xsl:if test="name(preceding-sibling::*[1]) = 'classname'">
			<xsl:text>, </xsl:text>
		</xsl:if>
		<fo:inline xsl:use-attribute-sets="synopsis.name">
			<xsl:apply-templates mode="java" />
		</fo:inline>
	</xsl:template>

	<xsl:template match="interfacename" mode="java">
		<xsl:if test="name(preceding-sibling::*[1]) = 'interfacename'">
			<xsl:text>, </xsl:text>
		</xsl:if>
		<fo:inline xsl:use-attribute-sets="synopsis.name">
			<xsl:apply-templates mode="java" />
		</fo:inline>
	</xsl:template>

	<xsl:template match="exceptionname" mode="java">
		<xsl:if test="name(preceding-sibling::*[1]) = 'exceptionname'">
			<xsl:text>,&RE;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</xsl:text>
		</xsl:if>
		<fo:inline xsl:use-attribute-sets="synopsis.name">
			<xsl:apply-templates mode="java" />
		</fo:inline>
	</xsl:template>

	<xsl:template match="methodname" mode="java">
		<fo:inline xsl:use-attribute-sets="synopsis.name">
			<xsl:apply-templates mode="java" />
		</fo:inline>
	</xsl:template>

	<xsl:template match="methodparam" mode="java">
		<xsl:param name="indent" select="0"/>
		<xsl:param name="decl-length" select="0" />
		<xsl:param name="decl-first-param-length" select="0" />
		<xsl:if
			test="not(preceding-sibling::methodparam) and $decl-first-param-length &gt; $line-length">
			<xsl:text>&RE;</xsl:text>
			<xsl:if test="$indent &gt; 0">
				<xsl:call-template name="copy-string">
					<xsl:with-param name="string">&nbsp;
					</xsl:with-param>
					<xsl:with-param name="count" select="$indent" />
				</xsl:call-template>
			</xsl:if>
		</xsl:if>
		<xsl:if test="preceding-sibling::methodparam">
			<xsl:choose>
				<xsl:when test="$decl-length &gt; $line-length">
					<xsl:text>,&RE;</xsl:text>
					<xsl:if test="$indent &gt; 0">
						<xsl:call-template name="copy-string">
							<xsl:with-param name="string" select="' '" />
							<xsl:with-param name="count" select="$indent" />
						</xsl:call-template>
					</xsl:if>
				</xsl:when>
				<xsl:otherwise>
					<xsl:text>, </xsl:text>
				</xsl:otherwise>
			</xsl:choose>
		</xsl:if>
		<xsl:apply-templates mode="java" />
	</xsl:template>

	<xsl:template name="get-decl-length">
		<xsl:param name="indent">
			0
		</xsl:param>
		<xsl:variable name="buffer">
			<xsl:for-each select="methodparam">
				<xsl:if test="position()>1">
					<xsl:text>, </xsl:text>
				</xsl:if>
				<xsl:value-of select="type" />
				<xsl:text> </xsl:text>
				<xsl:value-of select="parameter" />
				<xsl:text> </xsl:text>
			</xsl:for-each>
		</xsl:variable>
		<xsl:value-of select="$indent + string-length($buffer)" />
	</xsl:template>

	<xsl:template name="get-decl-first-param-length">
		<xsl:param name="indent">
			0
		</xsl:param>
		<xsl:variable name="buffer">
			<xsl:value-of select="methodparam[1]/type" />
			<xsl:text> </xsl:text>
			<xsl:value-of select="methodparam[1]/parameter" />
			<xsl:text> </xsl:text>
		</xsl:variable>
		<xsl:value-of select="$indent + string-length($buffer)" />
	</xsl:template>

	<xsl:template match="type" mode="java">
		<fo:inline keep-together="always" wrap-option="no-wrap"
			xsl:use-attribute-sets="synopsis.type">
			<xsl:apply-templates mode="java" />
		</fo:inline>
		<xsl:text>&nbsp;</xsl:text>
	</xsl:template>

	<xsl:template match="varname" mode="java">
		<fo:inline xsl:use-attribute-sets="synopsis.name">
			<xsl:apply-templates mode="java" />
		</fo:inline>
		<xsl:choose>
			<xsl:when test="name(.. ) = 'fieldsynopsis'">
				<xsl:if test="count(../initializer) &gt; 0">
					<xsl:text>&nbsp;</xsl:text>
				</xsl:if>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>&nbsp;</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="parameter" mode="java">
		<fo:inline xsl:use-attribute-sets="synopsis.name">
			<xsl:apply-templates mode="java" />
		</fo:inline>
	</xsl:template>

	<xsl:template match="modifier" mode="java">
		<xsl:choose>
			<xsl:when test="@role = 'annotation'">
				<fo:inline xsl:use-attribute-sets="synopsis.annotation">
					<xsl:apply-templates mode="java" />
				</fo:inline>
			</xsl:when>
			<xsl:otherwise>
				<fo:inline xsl:use-attribute-sets="synopsis.keyword">
					<xsl:apply-templates mode="java" />
				</fo:inline>
			</xsl:otherwise>
		</xsl:choose>
		<!-- <xsl:message>[dbdoclet.xsl] modifier role = <xsl:value-of select="@role"/></xsl:message> -->
		<xsl:choose>
			<xsl:when test="@role = 'annotation'">
				<xsl:choose>
					<xsl:when test="local-name(..)='ooclass'">
						<xsl:text>&RE;</xsl:text>
					</xsl:when>
					<xsl:otherwise>
						<xsl:text>&RE;&nbsp;&nbsp;</xsl:text>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:when>
			<xsl:otherwise>
				<xsl:text>&nbsp;</xsl:text>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>
