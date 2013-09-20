package org.dbdoclet.tidbit.perspective.docbook.html;

import org.dbdoclet.tidbit.perspective.panel.docbook.SectionPanel;

public class HtmlSectionPanel extends SectionPanel {

    private static final long serialVersionUID = 1L;

    public HtmlSectionPanel() {
        createGui();
    }


    @Override
    protected void createGui() {

        super.createGui();
        
        addHorizontalGlue();
        addVerticalGlue();
    }
}
