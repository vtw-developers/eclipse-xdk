package com.vtw.xplatform.wizards.pages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class NewAgentProjectWizardPage extends WizardPage {

	private Text agentIdText;

	public NewAgentProjectWizardPage() {
		super("X플랫폼");
		setTitle("에이전트 프로젝트 생성");
		setDescription("라우트를 구현하기 위한 에이전트 프로젝트를 생성합니다.");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));
		
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		composite.setLayout(new GridLayout(2, false));
		
		Label agentIdLabel = new Label(composite, SWT.NONE);
		agentIdLabel.setText("에이전트ID");
		
		agentIdText = new Text(composite, SWT.BORDER);
		agentIdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	}
	
	public String getProjectName() {
		return this.agentIdText.getText().trim();
	}

}
