package com.vtw.xplatform.wizards;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;

import com.vtw.xplatform.wizards.pages.ImportRouteWizardPage;

public class ImportRouteWizard extends Wizard implements INewWizard {

	private ImportRouteWizardPage page = new ImportRouteWizardPage();

	public ImportRouteWizard() {
		setWindowTitle("New Wizard");
	}

	@Override
	public void addPages() {
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		String originalPath = page.getLocation();

		String projectName = page.getProjectName();
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject project = workspace.getRoot().getProject(projectName);
		System.out.println(workspace.getRoot().getFullPath().toString());
		System.out.println(IDEWorkbenchPlugin.getPluginWorkspace().getRoot().getFullPath().toString());
		IPath wsPath = workspace.getRoot().getLocation();

		String fileName = originalPath.substring(originalPath.lastIndexOf(File.separator) + 1);
		String directoryName = wsPath.toString() + File.separator + projectName + File.separator + "routes";
		File directory = new File(directoryName);
	    if (! directory.exists()){
	        directory.mkdir();
	    }
		
		
		String copiedPath = wsPath.toString() + File.separator + projectName + File.separator + "routes" + File.separator + fileName;
		
		try {
			copyFile(originalPath, copiedPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public void init(IWorkbench arg0, IStructuredSelection arg1) {
		// TODO Auto-generated method stub

	}

	private String hypensToCamelcase(String hypens) {
		return Arrays.stream(hypens.split("\\-"))
				.map(s -> Character.toUpperCase(s.charAt(0)) + s.substring(1).toLowerCase())
				.collect(Collectors.joining());
	}

	public void copyFile(String originalLocation, String copiedLocation) throws IOException {
		Path originalPath = Paths.get(originalLocation);
		Path copiedPath = Paths.get(copiedLocation);
		Files.copy(originalPath, copiedPath, StandardCopyOption.REPLACE_EXISTING);
	}
}
