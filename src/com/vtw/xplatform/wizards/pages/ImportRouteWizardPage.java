package com.vtw.xplatform.wizards.pages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class ImportRouteWizardPage extends WizardPage {

	private Text projectNameText;
	private Text locationText;

	public ImportRouteWizardPage() {
		super("X�÷���");
		setTitle("���Ʈ ���� ��������");
		setDescription("���Ʈ ���������� �ҷ��ͼ� ���Ʈ�� ������ �� �ֵ��� �մϴ�.");
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);

		setControl(container);
		container.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		composite.setLayout(new GridLayout(3, false));

		
		Label projectNameLabel = new Label(composite, SWT.NONE);
		projectNameLabel.setText("������Ʈ��");
		
		projectNameText = new Text(composite, SWT.BORDER);
		projectNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		
		Label locationLabel = new Label(composite, SWT.NONE);
		locationLabel.setText("���ϰ��");

		locationText = new Text(composite, SWT.BORDER);
		locationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button locationButton = new Button(composite, SWT.NONE);
		GridData gd_locationButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_locationButton.widthHint = 80;
		locationButton.setLayoutData(gd_locationButton);
		locationButton.setText("ã��...");
		locationButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				handleLocationDirectoryButtonPressed();
			}

		});
	}

	public String getProjectName() {
		return projectNameText.getText();
	}
	
	public String getLocation() {
		return locationText.getText();
	}

	/**
	 * The browse button has been selected. Select the location.
	 */
	protected void handleLocationDirectoryButtonPressed() {

		FileDialog dialog = new FileDialog(locationText.getShell(), SWT.SHEET);
		// dialog.setMessage("���Ʈ �������� ����");

//		String dirName = locationText.getText().trim();
//		if (dirName.isEmpty()) {
//			dirName = previouslyBrowsedDirectory;
//		}
//
//		if (dirName.isEmpty()) {
//			dialog.setFilterPath(IDEWorkbenchPlugin.getPluginWorkspace()
//					.getRoot().getLocation().toOSString());
//		} else {
//			File path = new File(dirName);
//			if (path.exists()) {
//				dialog.setFilterPath(new Path(dirName).toOSString());
//			}
//		}
//
		String selectedFile = dialog.open();
		if (selectedFile != null) {
			// previouslyBrowsedDirectory = selectedDirectory;
			locationText.setText(selectedFile);
			// updateProjectsList(selectedDirectory);
		}

	}
}