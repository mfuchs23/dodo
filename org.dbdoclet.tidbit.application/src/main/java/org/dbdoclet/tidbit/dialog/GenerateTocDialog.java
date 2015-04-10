package org.dbdoclet.tidbit.dialog;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.dbdoclet.Identifier;
import org.dbdoclet.jive.Anchor;
import org.dbdoclet.jive.Fill;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.jive.dialog.DataDialog;
import org.dbdoclet.jive.dialog.action.ActionCloseDialog;
import org.dbdoclet.jive.widget.ButtonPanel;
import org.dbdoclet.jive.widget.GridPanel;
import org.dbdoclet.service.ResourceServices;

public class GenerateTocDialog extends DataDialog implements ActionListener,
		ListSelectionListener {

	private static final String NOP = "nop";
	private static final String EQUATION = "equation";
	private static final String EXAMPLE = "example";
	private static final String TABLE = "table";
	private static final String TOC_TITLE = "title";
	private static final String TOC = "toc";
	private static final String FIGURE = "figure";

	private static final long serialVersionUID = 1L;

	private static final String[] DEFAULT_EXPRESSION_LIST = { "appendix",
			"article/appendix", "article", "book", "chapter", "part",
			"preface", "qandadiv", "qandaset", "reference", "sect1", "sect2",
			"sect3", "sect4", "sect5", "section", "set" };

	private TreeMap<String, ArrayList<String>> map = new TreeMap<String, ArrayList<String>>();
	private JList<String> pathList;
	private HashMap<String, JCheckBox> checkBoxMap = new HashMap<String, JCheckBox>();
	private ButtonPanel buttonPanel;
	private JButton selectAll;
	private JButton deselectAll;

	public GenerateTocDialog(Frame parent, String title, String generateToc) {

		super(parent, title);

		initMap();
		parse(generateToc);
		createGui();
	}

	private void createGui() {

		JiveFactory jive = JiveFactory.getInstance();

		GridPanel panel = getGridPanel();

		pathList = new JList<String>(map.keySet().toArray(new String[map.size()]));
		pathList.addListSelectionListener(this);

		panel.addComponent(jive.createScrollPane(pathList), Anchor.NORTHWEST,
				Fill.BOTH);

		GridPanel tocTypePanel = new GridPanel();
		panel.addComponent(tocTypePanel, Anchor.NORTHWEST, Fill.BOTH);
		tocTypePanel.setBorder(BorderFactory.createTitledBorder("ToC/LoT"));

		tocTypePanel.startSubPanel();
		
		JCheckBox checkBox = jive.createCheckBox(new Identifier(
				"generate.toc.toc"), TOC);
		tocTypePanel.addComponent(checkBox);
		checkBox.setActionCommand(TOC);
		checkBox.addActionListener(this);
		checkBoxMap.put(TOC, checkBox);

		checkBox = jive.createCheckBox(new Identifier("generate.toc.title"),
				TOC_TITLE);
		tocTypePanel.addComponent(checkBox);
		checkBox.setActionCommand(TOC_TITLE);
		checkBox.addActionListener(this);
		checkBoxMap.put(TOC_TITLE, checkBox);

		tocTypePanel.incrRow();

		checkBox = jive.createCheckBox(new Identifier("generate.toc.figure"),
				FIGURE);
		tocTypePanel.addComponent(checkBox);
		checkBox.setActionCommand(FIGURE);
		checkBox.addActionListener(this);
		checkBoxMap.put(FIGURE, checkBox);


		checkBox = jive.createCheckBox(new Identifier("generate.toc.table"),
				TABLE);
		tocTypePanel.addComponent(checkBox);
		checkBox.setActionCommand(TABLE);
		checkBox.addActionListener(this);
		checkBoxMap.put(TABLE, checkBox);

		checkBox = jive.createCheckBox(new Identifier("generate.toc.example"),
				EXAMPLE);
		tocTypePanel.addComponent(checkBox);
		checkBox.setActionCommand(EXAMPLE);
		checkBox.addActionListener(this);
		checkBoxMap.put(EXAMPLE, checkBox);

		checkBox = jive.createCheckBox(new Identifier("generate.toc.equation"),
				EQUATION);
		tocTypePanel.addComponent(checkBox);
		checkBox.setActionCommand(EQUATION);
		checkBox.addActionListener(this);
		checkBoxMap.put(EQUATION, checkBox);

		tocTypePanel.leaveSubPanel();
		
		tocTypePanel.addVerticalGlue();

		tocTypePanel.startSubPanel();

		selectAll = jive.createButton(new Identifier("generate.toc.selectAll"), ResourceServices.getString(res, "C_SELECT_ALL"));
		selectAll.setActionCommand("selectAll");
		selectAll.addActionListener(this);
		tocTypePanel.addComponent(selectAll);
		
		deselectAll = jive.createButton(new Identifier("generate.toc.deselectAll"), ResourceServices.getString(res, "C_DESELECT_ALL"));
		deselectAll.setActionCommand("deselectAll");
		deselectAll.addActionListener(this);
		tocTypePanel.addComponent(deselectAll);
		
		panel.incrRow();

		buttonPanel = new ButtonPanel(ButtonPanel.OK | ButtonPanel.CANCEL, this);
		panel.addComponent(buttonPanel);

		JButton okButton = buttonPanel.getOkButton();

		if (okButton != null) {

			okButton.addActionListener(new ActionCloseDialog(this, "ok"));
			getRootPane().setDefaultButton(okButton);
		}

		JButton cancelButton = buttonPanel.getCancelButton();
		if (cancelButton != null) {
			cancelButton
					.addActionListener(new ActionCloseDialog(this, "cancel"));
		}

		pathList.setSelectedIndex(0);

		pack();
		center(getParentWindow());

	}

	private void initMap() {

		for (String expr : DEFAULT_EXPRESSION_LIST) {
			ArrayList<String> values = new ArrayList<String>();
			map.put(expr, values);
		}
	}

	private void parse(String generateToc) {

		if (generateToc == null) {
			return;
		}

		String[] tokens = generateToc.trim().split("\\s+");

		boolean isExpr = true;
		ArrayList<String> values = null;

		for (String token : tokens) {

			if (isExpr == true) {

				values = map.get(token);

				if (values == null) {

					values = new ArrayList<String>();
					map.put(token, values);
				}

				isExpr = false;

			} else {

				String[] tocs = token.split(",");

				for (String toc : tocs) {

					if (toc.equals(NOP) == false && values.contains(toc) == false) {
						values.add(toc);
					}
				}

				isExpr = true;
			}
		}
	}

	public void actionPerformed(ActionEvent event) {

		String cmd = event.getActionCommand();
		
		if (cmd == null) {
			return;
		}

		JCheckBox titleCheckBox = checkBoxMap.get(TOC_TITLE);

		if (cmd.equals("selectAll")) {
			
			for (JCheckBox checkBox : checkBoxMap.values()) {
				checkBox.setSelected(true);
			}

			titleCheckBox.setEnabled(true);
		}
		
		if (cmd.equals("deselectAll")) {
			
			for (JCheckBox checkBox : checkBoxMap.values()) {
				checkBox.setSelected(false);
			}
			
			titleCheckBox.setEnabled(false);
		}
		
		if (event != null && event.getSource() != null
				&& event.getSource() instanceof JCheckBox) {


			String path = (String) pathList.getSelectedValue();

			if (path == null) {
				return;
			}

			ArrayList<String> tocList = map.get(path);

			if (tocList == null) {
				return;
			}

			JCheckBox checkBox = (JCheckBox) event.getSource();

			if (checkBox.isSelected()) {

				if (cmd.equals(TOC)) {
					titleCheckBox.setEnabled(true);
				}
				
				if (tocList.contains(cmd) == false) {
					
					if (cmd.equals(TOC)) {
						tocList.add(0, cmd);
					} else if (cmd.equals(TOC_TITLE) && tocList.size() == 0) {
						tocList.add(0, cmd);
					} else if (cmd.equals(TOC_TITLE) && tocList.size() > 0) {
						tocList.add(1, cmd);
					} else {
						tocList.add(cmd);
					}
				}

			} else {

				if (cmd.equals(TOC)) {

					titleCheckBox.setSelected(false);
					titleCheckBox.setEnabled(false);
					while (tocList.contains(TOC_TITLE)) {
						tocList.remove(TOC_TITLE);
					}
				}
				
				while (tocList.contains(cmd)) {
					tocList.remove(cmd);
				}
			}
		}
	}

	public void valueChanged(ListSelectionEvent event) {

		String key = (String) pathList.getSelectedValue();

		ArrayList<String> tocs = map.get(key);

		for (JCheckBox checkBox : checkBoxMap.values()) {
			checkBox.setSelected(false);
		}

		JCheckBox titleCheckBox = checkBoxMap.get(TOC_TITLE);
		titleCheckBox.setEnabled(false);
		
		if (tocs != null) {

			for (String toc : tocs) {

				JCheckBox checkBox = checkBoxMap.get(toc);

				if (toc.equals(TOC)) {
					titleCheckBox.setEnabled(true);					
				}
				
				if (checkBox != null) {
					checkBox.setSelected(true);
				}
			}
		}
	}

	public String getGenerateTocParam() {

		StringBuilder buffer = new StringBuilder();
		buffer.append("\n");
		
		int maxLength = 0;
		for (String path : map.keySet()) {
			if (path.length() > maxLength) {
				maxLength = path.length();
			}
		}
		
		maxLength += 4;
		
		for (String path : map.keySet()) {
		
			buffer.append(path);
			
			for (int i = 0; i < maxLength - path.length(); i++) {
				buffer.append(' ');
			}
			
			ArrayList<String> tocs = map.get(path);
			
			if (tocs.size() == 0) {
				buffer.append(NOP);
			} else {
				
				int index = 0;
				
				for (String toc : tocs) {
					
					buffer.append(toc);
					index++;
					
					if (index < tocs.size()) {
						buffer.append(",");
					}
				}
			}
			
			buffer.append("\n");
		}
		
		return buffer.toString();
	}
}
