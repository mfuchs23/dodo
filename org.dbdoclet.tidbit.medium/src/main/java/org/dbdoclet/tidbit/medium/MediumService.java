package org.dbdoclet.tidbit.medium;

import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import org.dbdoclet.antol.AntFileWriter;
import org.dbdoclet.jive.model.Settings;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.project.Project;

public interface MediumService extends Comparable<MediumService> {

	public String getCategory();

	public Generator getGenerator(Settings settings)
			throws IOException;

	public Icon getIcon();

	public String getId();

	public String getName();

	public void write(AntFileWriter writer, Project project) throws IOException;

	public AbstractAction getAction(Context context);
}
