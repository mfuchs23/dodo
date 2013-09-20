package org.dbdoclet.tidbit.perspective.docbook.epub;

import javax.swing.JTextField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.common.Context;

public class EpubPanel extends GridPanel {

	private static final long serialVersionUID = 1L;
	private JTextField epubMetainfDirEntry;
	private JTextField epubOebpsDirEntry;
	private JiveFactory jf;

	public EpubPanel(Context context) {

        int width = 24;

        jf = JiveFactory.getInstance();

        epubOebpsDirEntry = jf.createTextField(new Identifier(Constants.PARAM_EPUB_OEBPS_DIR), width);
        epubOebpsDirEntry.setEditable(false);
        addLabeledComponent(Constants.PARAM_EPUB_OEBPS_DIR, epubOebpsDirEntry);
        incrRow();
        
        epubMetainfDirEntry = jf.createTextField(new Identifier(Constants.PARAM_EPUB_METAINF_DIR), width);
        epubMetainfDirEntry.setEditable(false);
        addLabeledComponent(Constants.PARAM_EPUB_METAINF_DIR, epubMetainfDirEntry);
        incrRow();

        addVerticalGlue();
	}

	public String getEpubMetainfDir() {
		return epubMetainfDirEntry.getText();
	}

	public String getEpubOebpsDir() {
		return epubOebpsDirEntry.getText();
	}

	public void setEpubMetainfDir(String epubMetainfDir) {
		epubMetainfDirEntry.setText(epubMetainfDir);
	}

	public void setEpubOebpsDir(String epubOebpsDir) {
		epubOebpsDirEntry.setText(epubOebpsDir);
	}

}
