package com.vtw.xplatform.wizards.pages;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;

public class NewProcessWizardPage extends WizardPage {
	
	private Combo projectNameCombo;
	private Text processIdText;
	private Text processNameText;

	public NewProcessWizardPage() {
		super("X플랫폼");
		setTitle("프로세스 생성");
		setDescription("사용자정의 프로세스를 생성하기 위한 프로세스 라우트 클래스를 생성합니다.");
	}
	
	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		composite.setLayout(new GridLayout(2, false));
		
		
		Label projectNameLabel = new Label(composite, SWT.NONE);
		projectNameLabel.setText("프로젝트명");
		
		projectNameCombo = new Combo(composite, SWT.BORDER);
		projectNameCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IProject[] projects = workspace.getRoot().getProjects();
		for (IProject project : projects) {
			if (project.isOpen()) {
				try {
					IProjectNature nature = project.getNature("com.vtw.xplatform.sampleNature");
					if (nature != null) {
						projectNameCombo.add(project.getName());
					}
				} catch (CoreException e1) {
					e1.printStackTrace();
				}
			}
		}
		projectNameCombo.select(0);
		
		
		
		Label processIdLabel = new Label(composite, SWT.NONE);
		processIdLabel.setText("프로세스ID");
		
		processIdText = new Text(composite, SWT.BORDER);
		processIdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		
		
		Label processNameLabel = new Label(composite, SWT.NONE);
		processNameLabel.setText("프로세스명");
		
		processNameText = new Text(composite, SWT.BORDER);
		processNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}
	
	public String getProjectName() {
		return projectNameCombo.getText();
	}
	
	public String getProcessId() {
		return processIdText.getText();
	}

	public String getProcessName() {
		return processNameText.getText();
	}
}
