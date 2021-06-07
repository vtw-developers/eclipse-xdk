package com.vtw.xplatform.wizards;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;

import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.datatransfer.DataTransferMessages;
import org.eclipse.ui.internal.wizards.datatransfer.TarEntry;

public class ProjectRecord {
		File projectSystemFile;

		Object projectArchiveFile;

		String projectName;

		Object parent;

		int level;

		boolean hasConflicts;

		boolean isInvalid = false;

		IProjectDescription description;

		/**
		 * Create a record for a project based on the info in the file.
		 *
		 * @param file
		 */
		ProjectRecord(File file) {
			projectSystemFile = file;
			setProjectName();
		}

		/**
		 * @param file
		 * 		The Object representing the .project file
		 * @param parent
		 * 		The parent folder of the .project file
		 * @param level
		 * 		The number of levels deep in the provider the file is
		 */
		ProjectRecord(Object file, Object parent, int level) {
			this.projectArchiveFile = file;
			this.parent = parent;
			this.level = level;
			setProjectName();
		}

		/**
		 * Set the name of the project based on the projectFile.
		 */
		private void setProjectName() {
			try {

				// If we don't have the project name try again
				if (projectName == null) {
					IPath path = new Path(projectSystemFile.getPath());
					// if the file is in the default location, use the directory
					// name as the project name
					if (isDefaultLocation(path)) {
						projectName = path.segment(path.segmentCount() - 2);
						description = IDEWorkbenchPlugin.getPluginWorkspace()
								.newProjectDescription(projectName);
					} else {
						description = IDEWorkbenchPlugin.getPluginWorkspace()
								.loadProjectDescription(path);
						projectName = description.getName();
					}

				}
			} catch (CoreException e) {
				this.projectName = DataTransferMessages.WizardProjectsImportPage_invalidProjectName;
				this.isInvalid = true;
			}
		}

		/**
		 * Returns whether the given project description file path is in the
		 * default location for a project
		 *
		 * @param path
		 * 		The path to examine
		 * @return Whether the given path is the default location for a project
		 */
		private boolean isDefaultLocation(IPath path) {
			// The project description file must at least be within the project,
			// which is within the workspace location
			if (path.segmentCount() < 2) {
				return false;
			}
			return path.removeLastSegments(2).toFile().equals(
					Platform.getLocation().toFile());
		}

		/**
		 * Get the name of the project
		 *
		 * @return String
		 */
		public String getProjectName() {
			return projectName;
		}

		/**
		 * Returns whether the given project description file was invalid
		 *
		 * @return boolean
		 */
		public boolean isInvalidProject() {
			return isInvalid;
		}

		/**
		 * Gets the label to be used when rendering this project record in the
		 * UI.
		 *
		 * @return String the label
		 * @since 3.4
		 */
		public String getProjectLabel() {
			String path = projectSystemFile.getParent();
			return NLS.bind(
					DataTransferMessages.WizardProjectsImportPage_projectLabel,
					projectName, path);
		}

		/**
		 * @return Returns the hasConflicts.
		 */
		public boolean hasConflicts() {
			return hasConflicts;
		}
	}
