package org.dbdoclet.runner;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;

import org.apache.felix.framework.Felix;
import org.apache.felix.main.AutoProcessor;
import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.tidbit.common.OsgiRuntime;
import org.dbdoclet.tidbit.common.StaticContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

public class TidbitHost implements OsgiRuntime {

	private static Felix felix = null;

	public void startUp() {

		try {

			System.setProperty("swing.aatext", "true");

			String home = System.getProperty("org.dbdoclet.doclet.home");

			if (home == null) {
				ErrorBox.show(
						"ERROR",
						"System property org.dbdoclet.home is not set. Please use java -Dorg.dbdoclet.doclet.home=<INSTALLATION DIRECTORTY> -jar tidbit.jar to start dbdoclet.");
				System.exit(0);
			}
			// System.out.println("Starting dbdoclet.tidbit(" + home + ")...");

			if (home.endsWith("/") == false) {
				home += "/";
			}

			String bundlePath = home + "bundle/";
			File bundleDir = new File(bundlePath);

			String cachePath = System.getProperty("user.home")
					+ "/.tidbit.d/felix-cache";

			File cacheDir = new File(cachePath);
			deleteDirectory(cacheDir);
			cacheDir.mkdirs();

			HashMap<String, Object> configMap = new HashMap<String, Object>();
			configMap.put(Constants.FRAMEWORK_STORAGE, cachePath);
			configMap.put(AutoProcessor.AUTO_DEPLOY_DIR_PROPERY, bundlePath);

			File[] bundleList = bundleDir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {

					if (name.equals("felix.jar") == false
							&& name.endsWith(".jar"))
						return true;
					return false;
				}
			});

			info("Starting OSGi system...");

			felix = new Felix(configMap);
			felix.init();

			StaticContext.setOsgiRuntime(this);
			BundleContext context = felix.getBundleContext();

			for (File file : bundleList) {

				String bundleUrl = file.toURI().toURL().toString();
				Bundle bundle = context.installBundle(bundleUrl);

				if (bundle.getState() == Bundle.INSTALLED) {
					bundle.start();
				}
			}

			felix.start();

		} catch (Exception oops) {

			oops.printStackTrace();

			try {

				felix.stop();
				felix.waitForStop(0);

			} catch (Exception oops2) {
				oops2.printStackTrace();
			}

			System.exit(-1);
		}
	}

	private boolean deleteDirectory(File file) {

		if (file == null || file.exists() == false) {
			return false;
		}

		if (file.isDirectory()) {

			for (File child : file.listFiles()) {
				if (deleteDirectory(child) == false) {
					return false;
				}
			}
		}

		return file.delete();
	}

	public static void main(final String[] args) {

		TidbitHost app = new TidbitHost();
		app.startUp();
	}

	@Override
	public void shutdown() {

		if (felix != null) {
			try {
				felix.stop();
				felix.waitForStop(0);
			} catch (Exception oops) {
				oops.printStackTrace();
			}
		}

		System.exit(0);
	}

	private static void info(String string) {
		// System.out.println(string);
	}
}
