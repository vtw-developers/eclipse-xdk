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
		super("X플랫폼");
		setTitle("라우트 설정 가져오기");
		setDescription("라우트 설정파일을 불러와서 라우트를 실행할 수 있도록 합니다.");
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
		projectNameLabel.setText("프로젝트명");
		
		projectNameText = new Text(composite, SWT.BORDER);
		projectNameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		
		Label locationLabel = new Label(composite, SWT.NONE);
		locationLabel.setText("파일경로");

		locationText = new Text(composite, SWT.BORDER);
		locationText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Button locationButton = new Button(composite, SWT.NONE);
		GridData gd_locationButton = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_locationButton.widthHint = 80;
		locationButton.setLayoutData(gd_locationButton);
		locationButton.setText("찾기...");
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
		// dialog.setMessage("라우트 설정파일 선택");

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
