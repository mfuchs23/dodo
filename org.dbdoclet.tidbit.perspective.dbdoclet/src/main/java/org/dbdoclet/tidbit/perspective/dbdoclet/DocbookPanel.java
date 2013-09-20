/* 
 * $Id$
 *
 * ### Copyright (C) 2006 Michael Fuchs ###
 * ### All Rights Reserved.             ###
 *
 * Author: Michael Fuchs
 * E-Mail: michael.fuchs@unico-group.com
 * URL:    http://www.michael-a-fuchs.de
 */
package org.dbdoclet.tidbit.perspective.dbdoclet;

import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.dbdoclet.Identifier;
import org.dbdoclet.doclet.docbook.DbdScript;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Colspan;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.tidbit.project.Project;

public class DocbookPanel extends GridPanel {

	private static final long serialVersionUID = 1L;

	private static int FIELD_SIZE = 18;

	protected JiveFactory widgetMap;
	protected ResourceBundle res;
	private final JTextField bookTitleEntry;
	private final JTextField copyrightYearEntry;
	private final JTextField copyrightHolderEntry;
	private final JTextField corporationEntry;
	private final JTextField authorFirstnameEntry;
	private final JTextField authorSurnameEntry;
	private final JTextField authorEmailEntry;
	private final JTextField releaseInfoEntry;

	public DocbookPanel(Perspective perspective) {

		super();

		if (perspective == null) {
			throw new IllegalArgumentException(
					"The argument perspective must not be null!");
		}

		JLabel label;

		res = StaticContext.getResourceBundle();
		widgetMap = JiveFactory.getInstance();

		JTextComponent helpArea = widgetMap.createHelpArea(this,
				ResourceServices.getString(res, "C_HELP_DOCBOOK_PANEL"));
		addComponent(helpArea, Colspan.CS_4, Anchor.NORTHWEST, Fill.HORIZONTAL);
		incrRow();

		label = widgetMap.createLabel(ResourceServices.getString(res,
				"C_BOOK_TITLE"));
		bookTitleEntry = widgetMap.createTextField(
				new Identifier("book.title"), FIELD_SIZE);
		addLabeledComponent(label, bookTitleEntry);

		label = widgetMap.createLabel(ResourceServices.getString(res,
				"C_COPYRIGHT_YEAR"));
		copyrightYearEntry = widgetMap.createTextField(new Identifier(
				"copyright.year"), FIELD_SIZE);
		addLabeledComponent(label, copyrightYearEntry);
		incrRow();

		label = widgetMap.createLabel(ResourceServices.getString(res,
				"C_COPYRIGHT_HOLDER"));
		copyrightHolderEntry = widgetMap.createTextField(new Identifier(
				"copyright.holder"), FIELD_SIZE);
		addLabeledComponent(label, copyrightHolderEntry);

		label = widgetMap.createLabel(ResourceServices.getString(res,
				"C_CORPORATION"));
		corporationEntry = widgetMap.createTextField(new Identifier(
				"corporation"), FIELD_SIZE);
		addLabeledComponent(label, corporationEntry);
		incrRow();

		label = widgetMap.createLabel(ResourceServices.getString(res,
				"C_AUTHOR_FIRSTNAME"));
		authorFirstnameEntry = widgetMap.createTextField(new Identifier(
				"author.firstname"), FIELD_SIZE);
		addLabeledComponent(label, authorFirstnameEntry);

		label = widgetMap.createLabel(ResourceServices.getString(res,
				"C_AUTHOR_SURNAME"));
		authorSurnameEntry = widgetMap.createTextField(new Identifier(
				"author.surname"), FIELD_SIZE);
		addLabeledComponent(label, authorSurnameEntry);
		incrRow();

		label = widgetMap.createLabel(ResourceServices.getString(res,
				"C_AUTHOR_EMAIL"));
		authorEmailEntry = widgetMap.createTextField(new Identifier(
				"author.email"), FIELD_SIZE);
		addLabeledComponent(label, authorEmailEntry);

		label = widgetMap.createLabel(ResourceServices.getString(res,
				"C_RELEASE_INFO"));
		releaseInfoEntry = widgetMap.createTextField(new Identifier(
				"docbook.xml.releaseinfo"), FIELD_SIZE);
		addLabeledComponent(label, releaseInfoEntry);
		incrRow();

		addVerticalGlue();
	}

	public void syncModel(Project project, DbdScript script) {

		script.setTitle(bookTitleEntry.getText());
		script.setCopyrightYear(copyrightYearEntry.getText());
		script.setCopyrightHolder(copyrightHolderEntry.getText());
		script.setCorporation(corporationEntry.getText());
		script.setAuthorFirstname(authorFirstnameEntry.getText());
		script.setAuthorSurname(authorSurnameEntry.getText());
		script.setAuthorEmail(authorEmailEntry.getText());
		script.setReleaseInfo(releaseInfoEntry.getText());
	}

	public void syncView(Project project, DbdScript script) {

		bookTitleEntry.setText(script.getTitle());
		copyrightYearEntry.setText(script.getCopyrightYear());
		copyrightHolderEntry.setText(script.getCopyrightHolder());
		corporationEntry.setText(script.getCorporation());
		authorFirstnameEntry.setText(script.getAuthorFirstname());
		authorSurnameEntry.setText(script.getAuthorSurname());
		authorEmailEntry.setText(script.getAuthorEmail());
		releaseInfoEntry.setText(script.getReleaseInfo());

	}
}
