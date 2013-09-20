package org.dbdoclet.tidbit.perspective.panel.docbook;

import java.util.ResourceBundle;

import javax.swing.BorderFactory;

import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Context;

public class TitleProperties extends GridPanel {

    private static final long serialVersionUID = 1L;
    private ResourceBundle res;

    public TitleProperties(Context context) {
        
        res = context.getResourceBundle();
        createGui();
    }

    private void createGui() {

        setBorder(BorderFactory.createTitledBorder(ResourceServices.getString(res,"C_TITLE")));
        
    }
}
