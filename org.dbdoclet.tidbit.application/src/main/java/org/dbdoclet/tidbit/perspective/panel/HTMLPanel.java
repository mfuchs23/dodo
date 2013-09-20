/*
 * ### Copyright (C) 2001-2003 Michael Fuchs ###
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * Author: Michael Fuchs
 * E-Mail: mfuchs@unico-consulting.com
 *
 * RCS Information:
 * ---------------
 * Id.........: $Id: HTMLPanel.java,v 1.1.1.1 2004/12/21 14:01:48 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:01:48 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.tidbit.perspective.panel;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.service.ResourceServices;
import org.dbdoclet.tidbit.project.Project;

public class HTMLPanel extends JPanel {

    private static final long serialVersionUID = 1L;

    public HTMLPanel(Project project, JiveFactory widgetMap, Font font, ResourceBundle res) {
	
        super();

	JLabel label;
	JTextField entry;

	if (project == null) {
	    throw new IllegalArgumentException("Parameter ctrl is null!");
	}

	if (widgetMap == null) {
	    throw new IllegalArgumentException("Parameter widgetMap is null!");
	}

	if (font == null) {
	    throw new IllegalArgumentException("Parameter font is null!");
	}

	if (res == null) {
	    throw new IllegalArgumentException("Parameter res is null!");
	}

	setLayout(new GridBagLayout());

	GridBagConstraints gbc = new GridBagConstraints();
	gbc.fill = GridBagConstraints.BOTH;
	gbc.insets = new Insets(4, 4, 4, 4);

	label = new JLabel(ResourceServices.getString(res,"C_WINDOW_TITLE"));
	label.setFont(font);
	add(label, gbc);

	entry = new JTextField(32);
	entry.setFont(font);
	add(entry, gbc);
    }
}
