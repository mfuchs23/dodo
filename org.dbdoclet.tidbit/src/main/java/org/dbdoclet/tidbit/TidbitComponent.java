package org.dbdoclet.tidbit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.dbdoclet.jive.dialog.ErrorBox;
import org.dbdoclet.jive.dialog.ExceptionBox;
import org.dbdoclet.tidbit.common.Context;
import org.dbdoclet.tidbit.importer.ImportService;
import org.dbdoclet.tidbit.medium.MediumService;
import org.dbdoclet.tidbit.perspective.Perspective;
import org.dbdoclet.trafo.TrafoService;
import org.osgi.service.component.ComponentContext;

public class TidbitComponent {

	// private Logger logger = getLogger(TidbitComponent.class, INFO);

	private HashMap<String, ArrayList<TrafoService>> trafoServiceMap;
	private HashMap<String, ArrayList<MediumService>> mediumServiceMap;
	private List<Perspective> perspectiveList;
	private List<ImportService> importServiceList;

	private Tidbit tidbit;

	private Context tidbitContext;

	public TidbitComponent() {

		trafoServiceMap = new HashMap<String, ArrayList<TrafoService>>();
		mediumServiceMap = new HashMap<String, ArrayList<MediumService>>();
		importServiceList = Collections
				.synchronizedList(new ArrayList<ImportService>());
		perspectiveList = Collections
				.synchronizedList(new ArrayList<Perspective>());

	}

	public void addImportService(ImportService importService) {

		info("Neuer ImportService " + importService.getName() + ". Insgesamt: "
				+ importServiceList.size());

		importServiceList.add(importService);

		if (tidbit != null) {
			tidbit.addImportService(importService);
		}
	}

	public void addMediumService(MediumService mediumService) {

		String category = mediumService.getCategory();

		if (category == null) {
			category = "";
		}

		ArrayList<MediumService> mediumServiceList = mediumServiceMap
				.get(category);

		if (mediumServiceList == null) {

			mediumServiceList = new ArrayList<MediumService>();
			mediumServiceMap.put(category, mediumServiceList);
		}

		mediumServiceList.add(mediumService);
		info("Neuer MediumService " + mediumService.getName() + ". Insgesamt: "
				+ mediumServiceList.size());
	}

	public void addTrafoService(TrafoService trafoService) {

		String id = trafoService.getId();

		if (id == null) {
			id = "";
		}

		ArrayList<TrafoService> trafoServiceList = trafoServiceMap.get(id);

		if (trafoServiceList == null) {

			trafoServiceList = new ArrayList<TrafoService>();
			trafoServiceMap.put(id, trafoServiceList);
		}

		trafoServiceList.add(trafoService);
		info("Neuer TrafoService " + trafoService.getId() + ". Insgesamt: "
				+ trafoServiceList.size());
	}

	public void addPerspective(Perspective perspective) {

		perspectiveList.add(perspective);
		info("Neue Perspektive " + perspective.getName() + ". Insgesamt: "
				+ perspectiveList.size());

		Collections.sort(perspectiveList);

		if (tidbit != null) {
			tidbit.addPerspective(perspective);
		}
	}

	public List<ImportService> getImportServiceList() {
		return importServiceList;
	}

	public ArrayList<MediumService> getMediumServiceList() {

		ArrayList<MediumService> mediumServiceList = new ArrayList<MediumService>();

		for (ArrayList<MediumService> list : mediumServiceMap.values()) {
			mediumServiceList.addAll(list);
		}

		return mediumServiceList;
	}

	public List<MediumService> getMediumServiceList(String category) {

		if (category == null) {
			category = "";
		}

		return mediumServiceMap.get(category);
	}

	public List<TrafoService> getTrafoServiceList(String category) {

		if (category == null) {
			category = "";
		}

		return trafoServiceMap.get(category);
	}

	public Perspective getPerspective(String name) {

		if (name == null || perspectiveList == null) {
			return null;
		}

		for (Perspective perspective : perspectiveList) {

			if (name.equals(perspective.getName())) {
				return perspective;
			}
		}

		return null;
	}

	public List<Perspective> getPerspectiveList() {
		return perspectiveList;
	}

	public void removeImportService(ImportService importService) {

		importServiceList.remove(importService);

		if (tidbit != null) {
			tidbit.removeImportService(importService);
		}
	}

	public void removeMediumService(MediumService mediumService) {

		String category = mediumService.getCategory();

		if (category == null) {
			category = "";
		}

		ArrayList<MediumService> mediumServiceList = mediumServiceMap
				.get(category);

		if (mediumServiceList != null) {

			mediumServiceList.remove(mediumService);

			info("Entferne MediumService " + mediumService.getName()
					+ ". Insgesamt: " + mediumServiceList.size());

			if (tidbit != null) {
				tidbit.removeMediumService(mediumService);
			}
		}
	}

	public void removeTrafoService(TrafoService trafoService) {

		String category = trafoService.getId();

		if (category == null) {
			category = "";
		}

		ArrayList<TrafoService> trafoServiceList = trafoServiceMap
				.get(category);

		if (trafoServiceList != null) {

			trafoServiceList.remove(trafoService);

			info("Entferne TrafoService " + trafoService.getId()
					+ ". Insgesamt: " + trafoServiceList.size());
		}
	}

	public void removePerspective(Perspective perspective) {

		perspectiveList.remove(perspective);

		info("Entferne Perspective " + perspective.getName() + ". Insgesamt: "
				+ perspectiveList.size());

		if (tidbit != null) {
			tidbit.removePerspective(perspective);
		}
	}

	private Context getContext() {

		if (tidbitContext == null) {
			tidbitContext = new Context();
		}

		return tidbitContext;
	}

	private void info(String text) {
		System.out.println(text);
	}

	protected void activate(ComponentContext context) {

		info("Activating tidbit");

		if (tidbit != null) {
			return;
		}

		try {

			System.setProperty("swing.aatext", "true");
			String home = System.getProperty("org.dbdoclet.doclet.home");

			if (home == null) {

				ErrorBox.show(
						"ERROR",
						"System property org.dbdoclet.doclet.home is not set. Please use java -Dorg.dbdoclet.doclet.home=<INSTALLATION DIRECTORTY> -jar tidbit.jar to start dbdoclet.");
			}

			tidbit = (Tidbit) Tidbit.getApplication(this, getContext());
			tidbit.initialize();

			tidbit.start(new String[] {}, this);

		} catch (Throwable oops) {

			oops.printStackTrace();
			ExceptionBox ebox = new ExceptionBox("", oops);
			ebox.setVisible(true);
			ebox.toFront();
		}
	}

	protected void deactivate(ComponentContext context) {

		info("Tidbit Deaktivierung");
	}
}
