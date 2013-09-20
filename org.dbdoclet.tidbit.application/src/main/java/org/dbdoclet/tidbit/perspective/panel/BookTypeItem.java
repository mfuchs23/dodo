/*
 * ### Copyright (C) 2005 Michael Fuchs ###
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
 * Id.........: $Id: DocBookXMLPanel.java,v 1.1.1.1 2004/12/21 14:01:51 mfuchs Exp $
 * Author.....: $Author: mfuchs $
 * Date.......: $Date: 2004/12/21 14:01:51 $
 * Revision...: $Revision: 1.1.1.1 $
 * State......: $State: Exp $
 */
package org.dbdoclet.tidbit.perspective.panel;

import java.util.ResourceBundle;

import org.dbdoclet.jive.model.LabelItem;
import org.dbdoclet.service.ResourceServices;

public final class BookTypeItem extends LabelItem {

    public BookTypeItem(ResourceBundle res, String value) {

        super();

        if (value == null) {
            throw new IllegalArgumentException("Parameter value is null!");
        }

        String label = "???";

        if (value.equalsIgnoreCase("article")) {
            label = ResourceServices.getString(res,"C_ARTICLE");
        } else if (value.equalsIgnoreCase("book")) {
            label = ResourceServices.getString(res,"C_BOOK");
        } else if (value.equalsIgnoreCase("part")) {
            label = ResourceServices.getString(res,"C_PART");
        } else if (value.equalsIgnoreCase("reference")) {
            label = ResourceServices.getString(res,"C_REFERENCE");
        }

        setLabel(label);
        setValue(value);
    }
}
