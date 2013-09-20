package org.dbdoclet.tidbit.common.panel;

import java.io.File;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.FileServices;
import org.dbdoclet.service.ResourceServices;

public class ProblemPanel extends GridPanel {

	private static final long serialVersionUID = 1L;
	private final Log logger = LogFactory.getLog(ProblemPanel.class);

	private JTable table;

	public void createGui(ResourceBundle res) {

		new Vector<String>();

		table = new JTable();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.addColumn(ResourceServices.getString(res, "C_DESCRIPTION"));
		model.addColumn(ResourceServices.getString(res, "C_RESOURCE"));
		model.addColumn(ResourceServices.getString(res, "C_PATH"));
		model.addColumn(ResourceServices.getString(res, "C_LOCATION"));

		addComponent(new JScrollPane(table), Anchor.CENTER, Fill.BOTH);
	}

	public void addError(String buffer) {

		logger.debug("buffer=" + buffer);

		if (buffer == null) {
			return;
		}

		buffer = buffer.replace('\n', ' ');
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Vector<String> row = parseBuffer(buffer);

		if (row != null) {
			logger.debug("addError: " + row.get(3));
			model.addRow(row);
		}
	}

	private Vector<String> parseBuffer(String buffer) {

		String line;
		String description;
		File file;
		int locationIndex;
		int descriptionIndex;
		int index;

		String fileName = "";

		if (buffer == null || buffer.length() == 0) {
			return null;
		}

		Vector<String> row = new Vector<String>(4);

		buffer = buffer.trim();

		if (buffer.startsWith("[")) {

			index = buffer.indexOf("]");

			if (index != -1 && buffer.length() > index) {
				buffer = buffer.substring(index + 1);
			}
		}

		index = buffer.indexOf(':');

		while (index != -1 && index < (buffer.length() - 1)) {

			fileName = buffer.substring(0, index).trim();
			file = new File(fileName);

			if (file.exists() == true) {
				break;
			}

			index = buffer.indexOf(':', index + 1);
		}

		if (index == -1 || index == (buffer.length() - 1)) {

			logger.error("Unparsable Error Message: " + buffer.toString());
			return null;
		}

		locationIndex = index + 1;

		if (locationIndex >= buffer.length()) {
			logger.error("Location index > buffer.length() " + locationIndex
					+ " >= " + buffer.length());
			return null;
		}

		index = buffer.indexOf(':', locationIndex);

		if (index == -1) {
			logger.error("Couldn't find colon for location Index \"" + buffer
					+ "\", index=" + locationIndex + ".");
			return null;
		}

		line = buffer.substring(locationIndex, index);

		descriptionIndex = index + 1;

		if (descriptionIndex >= buffer.length()) {
			logger.error("Description index > buffer.length() "
					+ descriptionIndex + " >= " + buffer.length());
			return null;
		}

		description = buffer.substring(descriptionIndex);

		row.add(description);
		row.add(FileServices.getFileName(fileName));
		row.add(FileServices.getDirName(fileName));
		row.add(line);

		return row;
	}

	public void clear() {

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.getDataVector().removeAllElements();
		model.fireTableDataChanged();
	}
}
