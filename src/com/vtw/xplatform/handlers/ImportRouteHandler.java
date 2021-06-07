package com.vtw.xplatform.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

import com.vtw.xplatform.wizards.ImportRouteWizard;

public class ImportRouteHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		Shell activeShell = HandlerUtil.getActiveShell(event);
		IWizard wizard = new ImportRouteWizard();
		WizardDialog dialog = new WizardDialog(activeShell, wizard);
		dialog.open();
		return null;
	}
}
