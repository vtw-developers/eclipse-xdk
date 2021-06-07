package com.vtw.xplatform.wizards;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.osgi.framework.Bundle;

import com.vtw.xplatform.wizards.pages.NewProcessWizardPage;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class NewProcessWizard extends Wizard implements INewWizard {

	private NewProcessWizardPage page = new NewProcessWizardPage();

	public NewProcessWizard() {
		setWindowTitle("New Wizard");
	}

	@Override
	public void addPages() {
		addPage(page);
	}

	@Override
	public boolean performFinish() {
		IProgressMonitor monitor = new NullProgressMonitor();

		String projectName = page.getProjectName();
		String str = projectName + "/src/main/java";
		IPath path = new Path(str);
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IResource res = workspace.getRoot().findMember(path);
		IProject proj = res.getProject();
		IJavaProject jproject = JavaCore.create(proj);
		IPackageFragmentRoot root = jproject.getPackageFragmentRoot(res);
		try {
			IPackageFragment pack = root.createPackageFragment("com.vtw.pleiades.agent.custom.processes", true,
					new SubProgressMonitor(monitor, 1));

			String processId = page.getProcessId();
			String processName = page.getProcessName();
			String className = processId + "Process";
			String cuName = className + ".java";

			Bundle bundle = Platform.getBundle("com.vtw.xplatform");
			URL url = FileLocator.find(bundle, new Path("templates/processes"), null);
			URL fileUrl = null;
			try {
				fileUrl = FileLocator.toFileURL(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			File file = new File(fileUrl.getPath());
			
			
			IPath wsPath = workspace.getRoot().getLocation();
			// Create your Configuration instance, and specify if up to what FreeMarker
			// version (here 2.3.29) do you want to apply the fixes that are not 100%
			// backward-compatible. See the Configuration JavaDoc for details.
			Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);

			// Specify the source where the template files come from. Here I set a
			// plain directory for it, but non-file-system sources are possible too:
			try {
				cfg.setDirectoryForTemplateLoading(file);
			} catch (IOException e) {
				e.printStackTrace();
			}

			// From here we will set the settings recommended for new projects. These
			// aren't the defaults for backward compatibilty.

			// Set the preferred charset template files are stored in. UTF-8 is
			// a good choice in most applications:
			cfg.setDefaultEncoding("UTF-8");

			// Sets how errors will appear.
			// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is
			// better.
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

			// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
			cfg.setLogTemplateExceptions(false);

			// Wrap unchecked exceptions thrown during template processing into
			// TemplateException-s:
			cfg.setWrapUncheckedExceptions(true);

			// Do not fall back to higher scopes when reading a null loop variable:
			cfg.setFallbackOnNullLoopVariable(false);

			try {
				// Create the root hash. We use a Map here, but it could be a JavaBean too.
				Map<String, Object> params = new HashMap<>();
				// Put string "user" into the root
				params.put("processId", processId);
				params.put("processName", processName);
				params.put("className", className);

				Template temp = cfg.getTemplate("Process.java");
				ByteArrayOutputStream bios = new ByteArrayOutputStream();
				Writer out = new OutputStreamWriter(bios);
				temp.process(params, out);

				ICompilationUnit parentCU = pack.createCompilationUnit(cuName, bios.toString(), false,
						new SubProgressMonitor(monitor, 2)); // $NON-NLS-1$
			} catch (IOException | TemplateException e) {
				e.printStackTrace();
			}

		} catch (JavaModelException e) {
			// TODO Auto-generated catch block
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

}
