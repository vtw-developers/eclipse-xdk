package com.vtw.xplatform.wizards.pages;

import java.io.File;

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

public class NewProcessWizardPage extends WizardPage {
	
	private Text projectNameText;
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
		
		projectNameText = new Text(composite, SWT.BORDER);
		projectNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		
		
		
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
		return projectNameText.getText();
	}
	
	public String getProcessId() {
		return processIdText.getText();
	}

	public String getProcessName() {
		return processNameText.getText();
	}
}
