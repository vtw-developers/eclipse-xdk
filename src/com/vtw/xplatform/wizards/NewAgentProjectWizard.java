package com.vtw.xplatform.wizards;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.osgi.framework.Bundle;

import com.vtw.xplatform.wizards.pages.NewAgentProjectWizardPage;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class NewAgentProjectWizard extends Wizard implements INewWizard {

	private NewAgentProjectWizardPage page;
	
	public NewAgentProjectWizard() {
		setWindowTitle("New Wizard");
	}

	@Override
	public void addPages() {
		page = new NewAgentProjectWizardPage();
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		IProgressMonitor monitor = new NullProgressMonitor();
		
		Bundle bundle = Platform.getBundle("com.vtw.xplatform");
		URL url = FileLocator.find(bundle, new Path("templates/agent"), null);
		URL fileUrl = null;
		try {
			fileUrl = FileLocator.toFileURL(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file = new File(fileUrl.getPath());
		System.out.println(file.isDirectory());
		
		
		String projectName=page.getProjectName();
		//String projectName="demo";
		
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject project = workspace.getRoot().getProject(projectName);
		System.out.println(workspace.getRoot().getFullPath().toString());
		System.out.println(IDEWorkbenchPlugin.getPluginWorkspace().getRoot().getFullPath().toString());
		
		
		IPath wsPath = workspace.getRoot().getLocation();
		//IPath localProjectPath = wsPath.append(projectName);
		
		System.out.println(wsPath.toString());
		
		try {
			copyDirectory(file.toString(), wsPath.toString() + "/" + projectName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		
		
		// Create your Configuration instance, and specify if up to what FreeMarker
		// version (here 2.3.29) do you want to apply the fixes that are not 100%
		// backward-compatible. See the Configuration JavaDoc for details.
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);

		// Specify the source where the template files come from. Here I set a
		// plain directory for it, but non-file-system sources are possible too:
		try {
			cfg.setDirectoryForTemplateLoading(new File(wsPath.toString() + "/" + projectName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// From here we will set the settings recommended for new projects. These
		// aren't the defaults for backward compatibilty.

		// Set the preferred charset template files are stored in. UTF-8 is
		// a good choice in most applications:
		cfg.setDefaultEncoding("UTF-8");

		// Sets how errors will appear.
		// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

		// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
		cfg.setLogTemplateExceptions(false);

		// Wrap unchecked exceptions thrown during template processing into TemplateException-s:
		cfg.setWrapUncheckedExceptions(true);

		// Do not fall back to higher scopes when reading a null loop variable:
		cfg.setFallbackOnNullLoopVariable(false);
		
		
		
		
		try {
			// Create the root hash. We use a Map here, but it could be a JavaBean too.
			Map<String, Object> root = new HashMap<>();
			// Put string "user" into the root
			root.put("projectName", projectName);
			
			Template temp = cfg.getTemplate(".project");
			OutputStream       outputStream       = new FileOutputStream(wsPath.toString() + "/" + projectName + "/.project");
			Writer out = new OutputStreamWriter(outputStream);
			temp.process(root, out);
			
			temp = cfg.getTemplate("settings.gradle");
			outputStream       = new FileOutputStream(wsPath.toString() + "/" + projectName + "/settings.gradle");
			out = new OutputStreamWriter(outputStream);
			temp.process(root, out);
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		
		
		
		
		ProjectRecord record = new ProjectRecord(new File(wsPath.toString() + "/" + projectName));
		
		// error case
		record.description = workspace.newProjectDescription(projectName);
		IPath locationPath = new Path(record.projectSystemFile
				.getAbsolutePath());

		// If it is under the root use the default location
		if (Platform.getLocation().isPrefixOf(locationPath)) {
			record.description.setLocation(null);
		} else {
			record.description.setLocation(locationPath);
		}
		
		try {
			project.create(record.description, monitor);
			project.open(IResource.BACKGROUND_REFRESH, monitor);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return true;
	}

	@Override
	public void init(IWorkbench arg0, IStructuredSelection arg1) {
		// TODO Auto-generated method stub
		
	}

	public void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) 
			throws IOException {
		Files.walk(Paths.get(sourceDirectoryLocation)).forEach(source -> {
			java.nio.file.Path destination = Paths.get(destinationDirectoryLocation,
					source.toString().substring(sourceDirectoryLocation.length()));
			try {
				Files.copy(source, destination);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
