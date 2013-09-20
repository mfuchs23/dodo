package org.dbdoclet.tidbit.application.action;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.AbstractAction;
import javax.swing.Icon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dbdoclet.jive.JiveFactory;
import org.dbdoclet.tidbit.application.Application;
import org.dbdoclet.tidbit.common.StaticContext;
import org.dbdoclet.tidbit.common.TidbitExceptionHandler;
import org.dbdoclet.tidbit.perspective.Perspective;

public abstract class AbstractTidbitAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(AbstractTidbitAction.class);

	protected JiveFactory wm;
	protected ResourceBundle res;
	protected Perspective perspective;
	protected Application application;

	public AbstractTidbitAction(String name, Icon icon) {

		super(name, icon);

		res = StaticContext.getResourceBundle();
		wm = JiveFactory.getInstance();
	}

	public AbstractTidbitAction(Application application, String name, Icon icon) {

		super(name, icon);

		this.application = application;
		res = StaticContext.getResourceBundle();
		wm = JiveFactory.getInstance();
	}

	public AbstractTidbitAction(Perspective perspective, String name, Icon icon) {

		super(name, icon);

		if (perspective == null) {
			throw new IllegalArgumentException(
					"The argument application must not be null!");
		}

		this.perspective = perspective;
		this.application = perspective.getApplication();

		res = StaticContext.getResourceBundle();
		wm = JiveFactory.getInstance();
	}

	protected abstract void action(ActionEvent event) throws Exception;

	@Override
	public void actionPerformed(ActionEvent event) {

		try {

			logger.debug("action class = " + getClass().getName());
			logger.debug("action cmd = " + event.getActionCommand());

			action(event);

		} catch (Throwable oops) {

			logger.fatal("Action Failed", oops);
			TidbitExceptionHandler.handleException(application.getContext(),
					oops);
		}
	}

	protected void finished() {

		logger.debug("action finished");
	}

}
