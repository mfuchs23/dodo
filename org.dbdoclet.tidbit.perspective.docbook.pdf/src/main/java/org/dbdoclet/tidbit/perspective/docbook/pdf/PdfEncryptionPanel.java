package org.dbdoclet.tidbit.perspective.docbook.pdf;

import javax.swing.JPasswordField;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.Constants;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.perspective.panel.docbook.AbstractPanel;
import org.dbdoclet.tidbit.project.Project;
import org.dbdoclet.tidbit.project.driver.AbstractDriver;

public class PdfEncryptionPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private JPasswordField userPassword;

	public PdfEncryptionPanel() {
		createGui();
	}

	@Override
	protected void createGui() {
		createGui(0);
	}

	protected void createGui(int columnOffset) {

		super.createGui();

		setColumnOffset(columnOffset);

		GridPanel passwordPanel = createPasswordPanel();
		addComponent(passwordPanel, Anchor.NORTHWEST);

		addHorizontalGlue();
		addVerticalGlue();
	}

	private GridPanel createPasswordPanel() {

		GridPanel panel = new GridPanel(ResourceServices.getString(res,
				"C_PASSWORDS"));

		panel.startSubPanel();

		userPassword = jf.createPasswordField(new Identifier(
				Constants.PDF_USER_PASSWORD), 16);
		panel.addLabeledComponent(ResourceServices.getString(
				StaticContext.getResourceBundle(), "C_PDF_USER_PASSWORD"),
				userPassword);

		return panel;
	}

	@Override
	public void syncView(Project project, AbstractDriver driver) {

		if (project != null) {
		
			String password = (String) project
					.getParameter(Constants.PDF_USER_PASSWORD);

			if (password != null && password.trim().length() > 0) {
				userPassword.setText(password);
			}
		}
	}

	@Override
	public void syncModel(Project project, AbstractDriver driver) {

		if (project != null && userPassword.getPassword() != null) {

			String password = new String(userPassword.getPassword());

			if (password.trim().length() > 0) {
				project.setParameter(Constants.PDF_USER_PASSWORD, password);
			}
		}
	}

}
