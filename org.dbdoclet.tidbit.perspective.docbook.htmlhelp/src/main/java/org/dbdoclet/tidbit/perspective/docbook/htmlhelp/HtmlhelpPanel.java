package org.dbdoclet.tidbit.perspective.docbook.htmlhelp;

import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.common.Context;

public class HtmlhelpPanel extends GridPanel {

	private static final long serialVersionUID = 1L;
	private JTextField htmlhelpMetainfDirEntry;
	private JTextField htmlhelpOebpsDirEntry;
	private JiveFactory jf;

	public HtmlhelpPanel(Context context) {

        int width = 24;

        jf = JiveFactory.getInstance();

        htmlhelpOebpsDirEntry = jf.createTextField(new Identifier(Constants.PARAM_EPUB_OEBPS_DIR), width);
        htmlhelpOebpsDirEntry.setEditable(false);
        addLabeledComponent(Constants.PARAM_EPUB_OEBPS_DIR, htmlhelpOebpsDirEntry);
        incrRow();
        
        htmlhelpMetainfDirEntry = jf.createTextField(new Identifier(Constants.PARAM_EPUB_METAINF_DIR), width);
        htmlhelpMetainfDirEntry.setEditable(false);
        addLabeledComponent(Constants.PARAM_EPUB_METAINF_DIR, htmlhelpMetainfDirEntry);
        incrRow();

        addVerticalGlue();
	}

	public String getEpubMetainfDir() {
		return htmlhelpMetainfDirEntry.getText();
	}

	public String getEpubOebpsDir() {
		return htmlhelpOebpsDirEntry.getText();
	}

	public void setEpubMetainfDir(String htmlhelpMetainfDir) {
		htmlhelpMetainfDirEntry.setText(htmlhelpMetainfDir);
	}

	public void setEpubOebpsDir(String htmlhelpOebpsDir) {
		htmlhelpOebpsDirEntry.setText(htmlhelpOebpsDir);
	}

}
