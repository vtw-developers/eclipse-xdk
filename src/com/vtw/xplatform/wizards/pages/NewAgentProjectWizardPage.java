package com.vtw.xplatform.wizards.pages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Group;

public class NewAgentProjectWizardPage extends WizardPage {

	private Text projectNameText;
	
	private Text agentIdText;
	private Text kafkaUrlText;
	private Text elasticsearchUrlText;
	
	private Text dataSourceUrlText;
	private Text dataSourceDriverClassText;
	private Text dataSourceUsernameText;
	private Text dataSourcePasswordText;

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
		
		Label projectNameLabel = new Label(composite, SWT.NONE);
		projectNameLabel.setText("프로젝트명");
		
		projectNameText = new Text(composite, SWT.BORDER);
		projectNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Group group = new Group(container, SWT.NONE);
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		group.setText("에이전트 설정");
		group.setLayout(new GridLayout(2, false));
		
		Label agentIdLabel = new Label(group, SWT.NONE);
		agentIdLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		agentIdLabel.setText("에이전트 ID");
		
		agentIdText = new Text(group, SWT.BORDER);
		agentIdText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		agentIdText.setText("agent-1");
		
		Label kafkaUrlLabel = new Label(group, SWT.NONE);
		kafkaUrlLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		kafkaUrlLabel.setText("Kafka URL");
		
		kafkaUrlText = new Text(group, SWT.BORDER);
		kafkaUrlText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		kafkaUrlText.setText("34.64.70.138:9093");
		
		Label eleasticsearchUrlLabel = new Label(group, SWT.NONE);
		eleasticsearchUrlLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		eleasticsearchUrlLabel.setText("Elasticsearch URL");
		
		elasticsearchUrlText = new Text(group, SWT.BORDER);
		elasticsearchUrlText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		elasticsearchUrlText.setText("34.64.234.45:9200");
		
		Label dataSourceLabel = new Label(group, SWT.NONE);
		dataSourceLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		dataSourceLabel.setText("DB 연결정보");
		
		Composite dataSourceComposite = new Composite(group, SWT.BORDER);
		dataSourceComposite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		dataSourceComposite.setLayout(new GridLayout(2, false));
		
		Label dataSourceUrlLabel = new Label(dataSourceComposite, SWT.NONE);
		dataSourceUrlLabel.setText("URL");
		
		dataSourceUrlText = new Text(dataSourceComposite, SWT.BORDER);
		dataSourceUrlText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		dataSourceUrlText.setText("jdbc:postgresql://127.0.0.1:5432/portal");
		
		
		Label dataSourceDriverClassLabel = new Label(dataSourceComposite, SWT.NONE);
		dataSourceDriverClassLabel.setText("Driver");
		
		dataSourceDriverClassText = new Text(dataSourceComposite, SWT.BORDER);
		dataSourceDriverClassText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		dataSourceDriverClassText.setText("org.postgresql.Driver");
		
		
		Label dataSourceUsernameLabel = new Label(dataSourceComposite, SWT.NONE);
		dataSourceUsernameLabel.setText("Username");
		
		dataSourceUsernameText = new Text(dataSourceComposite, SWT.BORDER);
		dataSourceUsernameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		dataSourceUsernameText.setText("vtw");
		
		
		Label dataSourcePasswordLabel = new Label(dataSourceComposite, SWT.NONE);
		dataSourcePasswordLabel.setText("Password");
		
		dataSourcePasswordText = new Text(dataSourceComposite, SWT.BORDER);
		dataSourcePasswordText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		dataSourcePasswordText.setText("vtw123#");
	}
	
	public String getProjectName() {
		return this.projectNameText.getText().trim();
	}

}
