package org.dbdoclet.tidbit.project;

import java.util.ResourceBundle;

import org.dbdoclet.tidbit.common.StaticContext;

public abstract class AbstractProjectStream {

	protected final Project project;
	protected final FileManager fileManager;
	protected ResourceBundle res;

	public AbstractProjectStream(Project project) {

		if (project == null) {
			throw new IllegalArgumentException(
					"The argument project must not be null!");
		}

		this.project = project;

		res = StaticContext.getResourceBundle();
		fileManager = project.getFileManager();
	}
}
