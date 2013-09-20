package org.dbdoclet.tidbit;

import java.io.IOException;

import org.dbdoclet.tidbit.dialog.GenerateTocDialog;
import org.junit.Test;

public class GenerateTocDialogTest {

    @Test
    public void testGenerateTocDialog() throws IOException {
    	GenerateTocDialog dlg = new GenerateTocDialog(null, "title", "appendix toc,title article nop");
    	dlg.setVisible(true);
    	
    	if (dlg.isCanceled() == false) {
    		System.out.println(dlg.getGenerateTocParam());
    	}
    }

}
