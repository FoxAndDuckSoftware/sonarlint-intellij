/*
 * SonarLint for IntelliJ IDEA
 * Copyright (C) 2015-2021 SonarSource
 * sonarlint@sonarsource.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonarlint.intellij.actions;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.options.ShowSettingsUtil;
import com.intellij.openapi.project.Project;
import org.sonarlint.intellij.analysis.AnalysisStatus;
import org.sonarlint.intellij.config.project.SonarLintProjectConfigurable;

public class SonarConfigureProject extends AbstractSonarAction {
  @Override
  protected boolean isEnabled(AnActionEvent e, Project project, AnalysisStatus status) {
    return !status.isRunning();
  }

  @Override
  public void actionPerformed(AnActionEvent e) {
    if (e.getProject() != null) {
      SonarLintProjectConfigurable configurable = new SonarLintProjectConfigurable(e.getProject());
      ShowSettingsUtil.getInstance().editConfigurable(e.getProject(), configurable);
    }
  }
}
