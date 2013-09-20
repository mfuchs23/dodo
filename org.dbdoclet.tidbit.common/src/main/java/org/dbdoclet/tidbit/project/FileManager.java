package org.dbdoclet.tidbit.project;

import java.io.File;
import java.util.ArrayList;

import org.dbdoclet.service.FileServices;

public class FileManager {

	private File buildDirectory;
	private String docBookFileName;

	public File getBuildDirectory() {
		return buildDirectory;
	}

	public File getDocBook5File() {

		if (getDocBookFileBase() != null) {
			return new File(getDocBookFileBase() + ".xml.db5");
		}

		return null;
	}

	public File getDocBookFile() {

		String path = getDocBookFileName();

		if (path != null && path.trim().length() > 0) {
			return new File(getDocBookFileName());
		}

		return null;
	}

	public String getDocBookFileBase() {

		if (docBookFileName != null && docBookFileName.trim().length() > 0) {
			return FileServices.getFileBase(docBookFileName);
		}

		return null;
	}

	public String getDocBookFileDirName() {

		if (docBookFileName != null && docBookFileName.trim().length() > 0) {
			return new File(docBookFileName).getAbsoluteFile().getParent();
		}

		return null;
	}

	public String getDocBookFileName() {
		return docBookFileName;
	}

	public File getFoFile() {
		if (getDocBookFileBase() != null) {
			return new File(getDocBookFileBase() + ".fo");
		}

		return null;

	}

	public ArrayList<File> getJavadocArtifacts() {

		ArrayList<File> artefacts = new ArrayList<File>();

		artefacts.add(getJavadocZip());
		artefacts.add(getJavadocDirectory());
		
		return artefacts;
	}
	
	public File getJavadocDirectory() {
		String path = FileServices.appendPath(buildDirectory, "standard");
		return new File(path);
	}
	
	public File getJavadocZip() {
		String path = FileServices.appendFileName(buildDirectory, "javadoc.zip");
		return new File(path);
	}

	public ArrayList<File> getPdfArtifacts() {

		ArrayList<File> artefacts = new ArrayList<File>();

		if (getDocBook5File() != null) {
			artefacts.add(getDocBook5File());
		}

		if (getFoFile() != null) {
			artefacts.add(getFoFile());
		}

		if (getPdfFile() != null) {
			artefacts.add(getPdfFile());
		}

		return artefacts;
	}

	public File getPdfFile() {

		if (getDocBookFileBase() != null) {
			return new File(getDocBookFileBase() + ".pdf");
		}

		return null;

	}

	public ArrayList<File> getRtfArtifacts() {

		ArrayList<File> artefacts = new ArrayList<File>();

		if (getDocBook5File() != null) {
			artefacts.add(getDocBook5File());
		}

		if (getFoFile() != null) {
			artefacts.add(getFoFile());
		}

		if (getPdfFile() != null) {
			artefacts.add(getRtfFile());
		}

		return artefacts;
	}

	public File getRtfFile() {

		if (getDocBookFileBase() != null) {
			return new File(getDocBookFileBase() + ".rtf");
		}

		return null;

	}

	public void setBuildDirectory(File buildDirectory) {
		this.buildDirectory = buildDirectory;
	}

	public void setDocBookFileName(String docBookFileName) {
		this.docBookFileName = docBookFileName;
	}
}
