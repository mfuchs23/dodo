package org.dbdoclet.tidbit.perspective.docbook.webhelp;

import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.common.Context;

public class WebhelpPanel extends GridPanel {

	private static final long serialVersionUID = 1L;
	private JTextField webhelpMetainfDirEntry;
	private JTextField webhelpOebpsDirEntry;
	private JiveFactory jf;

	public WebhelpPanel(Context context) {

        int width = 24;

        jf = JiveFactory.getInstance();

        webhelpOebpsDirEntry = jf.createTextField(new Identifier(Constants.PARAM_EPUB_OEBPS_DIR), width);
        webhelpOebpsDirEntry.setEditable(false);
        addLabeledComponent(Constants.PARAM_EPUB_OEBPS_DIR, webhelpOebpsDirEntry);
        incrRow();
        
        webhelpMetainfDirEntry = jf.createTextField(new Identifier(Constants.PARAM_EPUB_METAINF_DIR), width);
        webhelpMetainfDirEntry.setEditable(false);
        addLabeledComponent(Constants.PARAM_EPUB_METAINF_DIR, webhelpMetainfDirEntry);
        incrRow();

        addVerticalGlue();
	}

	public String getEpubMetainfDir() {
		return webhelpMetainfDirEntry.getText();
	}

	public String getEpubOebpsDir() {
		return webhelpOebpsDirEntry.getText();
	}

	public void setEpubMetainfDir(String webhelpMetainfDir) {
		webhelpMetainfDirEntry.setText(webhelpMetainfDir);
	}

	public void setEpubOebpsDir(String webhelpOebpsDir) {
		webhelpOebpsDirEntry.setText(webhelpOebpsDir);
	}

}
