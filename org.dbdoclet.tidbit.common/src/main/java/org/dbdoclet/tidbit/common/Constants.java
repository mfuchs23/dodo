package org.dbdoclet.tidbit.common;

import java.awt.Color;

public final class Constants {

	public static final String COLOR_CHAR = "\u25FC";
    // public static final String[] FONT_FAMILY_LIST = new String[] { "Serif", "SansSerif", "Monospaced" };
    public static final String[] FONT_FAMILY_LIST = null;

    public static final String[] FRAME_STYLE_LIST = new String[] { "solid", "dashed", "dotted", "double", "inset",
            "outset", "groove", "ridge", "none" };
    public static final String[] FRAME_TYPE_LIST = new String[] { "all", "bottom", "none", "sides", "top", "topbot" };
    

    public static final Color MANDATORY_COLOR = new Color(150, 0, 0);

    
    public static final String MANDATORY_SUFFIX = " *";
    public static final String PARAM_ADMON_STYLE = "admon.style";

    public static final String PARAM_ALIGNMENT = "alignment";
    public static final String PARAM_ANNOTATE_TOC = "annotate.toc";
    public static final String PARAM_APPENDIX_AUTOLABEL = "appendix.autolabel";
    public static final String PARAM_AUTOTOC_LABEL_IN_HYPERLINK = "autotoc.label.in.hyperlink";

    public static final String PARAM_AUTOTOC_LABEL_SEPARATOR = "autotoc.label.separator";
    public static final String PARAM_BASE_DIR = "base.dir";
    public static final String PARAM_BIBLIOGRAPHY_COLLECTION = "bibliography.collection";

    public static final String PARAM_BIBLIOGRAPHY_NUMBERED = "bibliography.numbered";
    public static final String PARAM_BODY_END_INDENT = "body.end.indent";
    public static final String PARAM_BODY_FONT_FAMILY = "body.font.family";
    public static final String PARAM_BODY_FONT_MASTER = "body.font.master";
    public static final String PARAM_BODY_MARGIN_BOTTOM = "body.margin.bottom";
    public static final String PARAM_BODY_MARGIN_TOP = "body.margin.top";
    public static final String PARAM_BODY_START_INDENT = "body.start.indent";
    public static final String PARAM_BRIDGEHEAD_IN_TOC = "bridgehead.in.toc";
    public static final String PARAM_CALLOUT_DEFAULTCOLUMN = "callout.defaultcolumn";
    public static final String PARAM_CALLOUT_GRAPHICS = "callout.graphics";
    public static final String PARAM_CALLOUT_GRAPHICS_EXTENSION = "callout.graphics.extension";
    public static final String PARAM_CALLOUT_GRAPHICS_NUMBER_LIMIT = "callout.graphics.number.limit";
    public static final String PARAM_CALLOUT_GRAPHICS_PATH = "callout.graphics.path";
    public static final String PARAM_CALLOUT_LIST_TABLE = "callout.list.table";
    public static final String PARAM_CALLOUT_UNICODE = "callout.unicode";
    public static final String PARAM_CALLOUT_UNICODE_NUMBER_LIMIT = "callout.unicode.number.limit";
    public static final String PARAM_CALLOUT_UNICODE_START_CHARACTER = "callout.unicode.start-character";
    public static final String PARAM_CALLOUTS_EXTENSION = "callouts.extension";
    public static final String PARAM_CHAPTER_AUTOLABEL = "chapter.autolabel";
    public static final String PARAM_CHUNK_FIRST_SECTIONS = "chunk.first.sections";
    public static final String PARAM_CHUNK_SECTION_DEPTH = "chunk.section.depth";
    public static final String PARAM_COLLECT_XREF_TARGETS = "collect.xref.targets";
    public static final String PARAM_COLUMN_COUNT_BACK = "column.count.back";
    public static final String PARAM_COLUMN_COUNT_BODY = "column.count.body";
    public static final String PARAM_COLUMN_COUNT_FRONT = "column.count.front";
    public static final String PARAM_COLUMN_COUNT_INDEX = "column.count.index";
    public static final String PARAM_COMPONENT_LABEL_INCLUDES_PART_LABEL = "component.label.includes.part.label";
    public static final String PARAM_CURRENT_DOCID = "current.docid";
    public static final String PARAM_DEFAULT_IMAGE_WIDTH = "default.image.width";
    public static final String PARAM_DEFAULT_TABLE_FRAME = "default.table.frame";
    public static final String PARAM_DEFAULT_TABLE_RULES = "default.table.rules";
    public static final String PARAM_DEFAULT_TABLE_WIDTH = "default.table.width";
    public static final String PARAM_DOUBLE_SIDED = "double.sided";
    public static final String PARAM_DRAFT_MODE = "draft.mode";
    public static final String PARAM_EBNF_ASSIGNMENT = "ebnf.assignment";
    public static final String PARAM_EBNF_STATEMENT_TERMINATOR = "ebnf.statement.terminator";
    public static final String PARAM_ECLIPSE_PLUGIN_ID = "eclipse.plugin.id";
    public static final String PARAM_ECLIPSE_PLUGIN_NAME = "eclipse.plugin.name";
    public static final String PARAM_ECLIPSE_PLUGIN_VERSION = "eclipse.plugin.version";
    public static final String PARAM_ECLIPSE_PLUGIN_PROVIDER = "eclipse.plugin.provider";
    public static final String PARAM_EPUB_METAINF_DIR = "epub.metainf.dir";
    public static final String PARAM_EPUB_OEBPS_DIR = "epub.oebps.dir";
    public static final String PARAM_FOOTER_COLUMN_WIDTHS = "footer.column.widths";
    public static final String PARAM_FUNCSYNOPSIS_DECORATION = "funcsynopsis.decoration";
    public static final String PARAM_GENERATE_INDEX = "generate.index";
    public static final String PARAM_GENERATE_SECTION_TOC_LEVEL = "generate.section.toc.level";
    public static final String PARAM_GENERATE_TOC = "generate.toc";
    public static final String PARAM_GLOSSARY_COLLECTION = "glossary.collection";
    public static final String PARAM_GLOSSTERM_AUTO_LINK = "glossterm.auto.link";
    public static final String PARAM_GRAPHIC_DEFAULT_EXTENSION = "graphic.default.extension";
    public static final String PARAM_HEADER_COLUMN_WIDTHS = "header.column.widths";
    public static final String PARAM_HIGHLIGHT_DEFAULT_LANGUAGE = "highlight.default.language";
    public static final String PARAM_HIGHLIGHT_SOURCE = "highlight.source";
    public static final String PARAM_HIGHLIGHT_XSLTHL_CONFIG = "highlight.xslthl.config";
    public static final String PARAM_HTML_STYLESHEET = "html.stylesheet";
    public static final String PARAM_HYPHENATE = "hyphenate";
    public static final String PARAM_HYPHENATE_VERBATIM = "hyphenate.verbatim";
    public static final String PARAM_HYPHENATE_VERBATIM_CHARACTERS = "hyphenate.verbatim.characters";
    public static final String PARAM_IGNORE_IMAGE_SCALING = "ignore.image.scaling";
    public static final String PARAM_IMG_SRC_PATH = "img.src.path";
    public static final String PARAM_INDEX_LINKS_TO_SECTION = "index.links.to.section";
    public static final String PARAM_INDEX_METHOD = "index.method";
    public static final String PARAM_INDEX_NUMBER_SEPARATOR = "index.number.separator";
    public static final String PARAM_INDEX_ON_ROLE = "index.on.role";
    public static final String PARAM_INDEX_ON_TYPE = "index.on.type";
    public static final String PARAM_INDEX_PREFER_TITLE_ABBREV = "index.prefer.titleabbrev";
    public static final String PARAM_INDEX_RANGE_SEPARATOR = "index.range.separator";
    public static final String PARAM_INDEX_TERM_SEPARATOR = "index.term.separator";
    public static final String PARAM_INSERT_LINK_PAGE_NUMBER = "insert.link.page.number";
    public static final String PARAM_INSERT_XREF_PAGE_NUMBER = "insert.xref.page.number";
    public static final String PARAM_ITEMIZEDLIST_LABEL_WIDTH = "itemizedlist.label.width";
    public static final String PARAM_KEEP_RELATIVE_IMAGE_URIS = "keep.relative.image.uris";
    public static final String PARAM_L10N_GENTEXT_USE_XREF_LANGUAGE = "l10n.gentext.use.xref.language";
    public static final String PARAM_LABEL_FROM_PART = "label.from.part";
    public static final String PARAM_LINE_HEIGHT = "line-height";
    public static final String PARAM_LINENUMBERING_EVERY_NTH = "linenumbering.everyNth";
    public static final String PARAM_LINENUMBERING_EXTENSION = "linenumbering.extension";
    public static final String PARAM_LINENUMBERING_SEPARATOR = "linenumbering.separator";
    public static final String PARAM_LINENUMBERING_WIDTH = "linenumbering.width";
    public static final String PARAM_MAKE_INDEX_MARKUP = "make.index.markup";
    public static final String PARAM_MAKE_SINGLE_YEAR_RANGES = "make.single.year.ranges";
    public static final String PARAM_MANUAL_TOC = "manual.toc";
    public static final String PARAM_MARKER_SECTION_LEVEL = "marker.section.level";
    public static final String PARAM_NAVIG_GRAPHICS = "navig.graphics";
    public static final String PARAM_NAVIG_GRAPHICS_EXTENSION = "navig.graphics.extension";
    public static final String PARAM_NAVIG_GRAPHICS_PATH = "navig.graphics.path";
    public static final String PARAM_NAVIG_SHOWTITLES = "navig.showtitles";
    public static final String PARAM_NOMINAL_TABLE_WIDTH = "nominal.table.width";
    public static final String PARAM_OLINK_BASE_URI = "olink.base.uri";
    public static final String PARAM_OLINK_DEBUG = "olink.debug";

    public static final String PARAM_ORDEREDLIST_LABEL_WIDTH = "orderedlist.label.width";
    public static final String PARAM_PART_AUTOLABEL = "part.autolabel";
    public static final String PARAM_PREFACE_AUTOLABEL = "preface.autolabel";
    public static final String PARAM_PREFERRED_MEDIAOBJECT_ROLE = "preferred.mediaobject.role";
    public static final String PARAM_PROCESS_EMPTY_SOURCE_TOC = "process.empty.source.toc";
    public static final String PARAM_PROCESS_SOURCE_TOC = "process.source.toc";
    public static final String PARAM_QANDA_IN_TOC = "qanda.in.toc";
    public static final String PARAM_QUANDADIV_AUTOLABEL = "quandadiv.autolabel";
    public static final String PARAM_REFERENCE_AUTOLABEL = "reference.autolabel";
    public static final String PARAM_ROOTID = "rootid";
    public static final String PARAM_SECTION_AUTOLABEL = "section.autolabel";
    public static final String PARAM_SECTION_AUTOLABEL_MAX_DEPTH = "section.autolabel.max.depth";
    public static final String PARAM_SECTION_LABEL_INCLUDES_COMPONENT_LABEL = "section.label.includes.component.label";
    public static final String PARAM_SEGMENTEDLIST_AS_TABLE = "segmentedlist.as.table";
    public static final String PARAM_SHADE_VERBATIM = "shade.verbatim";
    public static final String PARAM_SIMPLESECT_IN_TOC = "simplesect.in.toc";
    public static final String PARAM_SUPPRESS_FOOTER_NAVIGATION = "suppress.footer.navigation";
    public static final String PARAM_SUPPRESS_HEADER_NAVIGATION = "suppress.header.navigation";
    public static final String PARAM_SUPPRESS_NAVIGATION = "suppress.navigation";
    public static final String PARAM_TABLE_CELL_BORDER_COLOR = "table.cell.border.color";
    public static final String PARAM_TABLE_CELL_BORDER_STYLE = "table.cell.border.style";
    public static final String PARAM_TABLE_CELL_BORDER_THICKNESS = "table.cell.border.thickness";
    public static final String PARAM_TABLE_FRAME_BORDER_COLOR = "table.frame.border.color";
    public static final String PARAM_TABLE_FRAME_BORDER_STYLE = "table.frame.border.style";
    public static final String PARAM_TABLE_FRAME_BORDER_THICKNESS = "table.frame.border.thickness";
    public static final String PARAM_TABLECOLUMNS_EXTENSION = "tablecolumns.extension";
    public static final String PARAM_TARGET_DATABASE_DOCUMENT = "target.database.document";
    public static final String PARAM_TARGETS_FILENAME = "targets.filename";
    public static final String PARAM_TOC_INDENT_WIDTH = "toc.indent.width";
    public static final String PARAM_TOC_LIST_TYPE = "toc.list.type";
    public static final String PARAM_TOC_MAX_DEPTH = "toc.max.depth";
    public static final String PARAM_TOC_SECTION_DEPTH = "toc.section.depth";
    public static final String PARAM_ULINK_FOOTNOTES = "ulink.footnotes";
    public static final String PARAM_ULINK_HYPHENATE = "ulink.hyphenate";
    public static final String PARAM_ULINK_HYPHENATE_CHARS = "ulink.hyphenate.chars";
    public static final String PARAM_ULINK_SHOW = "ulink.show";
    public static final String PARAM_ULINK_TARGET = "ulink.target";
    public static final String PARAM_USE_ID_AS_FILENAME = "use.id.as.filename";
    public static final String PARAM_USE_ROLE_AS_XREFSTYLE = "use.role.as.xrefstyle";
    public static final String PARAM_USE_ROLE_FOR_MEDIAOBJECT = "use.role.for.mediaobject";
    public static final String PARAM_VARIABLELIST_AS_BLOCKS = "variablelist.as.blocks";
    public static final String PARAM_VARIABLELIST_MAX_TERMLENGTH = "variablelist.max.termlength";
    public static final String PARAM_VARIABLELIST_TERM_BREAK_AFTER = "variablelist.term.break.after";
    public static final String PARAM_VARIABLELIST_TERM_SEPARATOR = "variablelist.term.separator";
    public static final String PARAM_XREF_LABEL_PAGE_SEPARATOR = "xref.label-page.separator";
    public static final String PARAM_XREF_LABEL_TITLE_SEPARATOR = "xref.label-title.separator";
    public static final String PARAM_XREF_TITLE_PAGE_SEPARATOR = "xref.title-page.separator";
    public static final String PARAM_XREF_WITH_NUMBER_AND_TITLE = "xref.with.number.and.title";
    public static final String RB_ERROR = "C_ERROR";
    public static final String RB_ERROR_INVALID_ECLIPSE_DIRECTORY = "C_ERROR_INVALID_ECLIPSE_DIRECTORY";
    public static final String RB_HOME_DIRECTORY = "C_HOME_DIRECTORY";
    public static final String RB_INSERT_HOME_DIRECTORY = "C_INSERT_HOME_DIRECTORY";
    public static final String[] RULES_TYPE_LIST = new String[] { "all", "rows", "cols", "groups", "none" };
    public static float[] SECTION_FONT_SIZES = { 2.0736F, 1.728F, 1.44F, 1.2F, 1F };
    public static final int TAB_PROJECT_INDEX = 0;
    public static final int TAB_SOURCEPATH_INDEX = 1;
    // public static final String TABBED_PANE = "maintab"; //$NON-NLS-1$
    public static final int TOC_SECTION_DEPTH = 2;
	public static final String WND_NEW_PROJECT_TITLE = "***"; //$NON-NLS-1$
	public static final String WND_PROJECT_TITLE = "dbdoclet.TiDBiT - "; //$NON-NLS-1$
	public static final String PDF_USER_PASSWORD = "pdf.userpassword";
	public static final int FO_ATTRIBUTE_LIST_WIDTH = 230;
	public static final int FO_ATTRIBUTE_LIST_HEIGHT = 290;
}