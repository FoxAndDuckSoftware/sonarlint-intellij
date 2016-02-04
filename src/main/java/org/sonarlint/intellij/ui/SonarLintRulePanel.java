/**
 * SonarLint for IntelliJ IDEA
 * Copyright (C) 2015 SonarSource
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
package org.sonarlint.intellij.ui;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.ui.IdeBorderFactory;
import com.intellij.ui.SideBorder;
import org.jetbrains.annotations.Nullable;
import org.sonarlint.intellij.analysis.SonarLintFacade;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.BorderLayout;
import java.awt.Desktop;
import com.intellij.openapi.project.Project;

public class SonarLintRulePanel {
  private final Project project;
  private final SonarLintFacade sonarlint;
  private JPanel panel;
  private JEditorPane editor;

  public SonarLintRulePanel(Project project, SonarLintFacade sonarlint) {
    this.project = project;
    this.sonarlint = sonarlint;
    panel = new JPanel(new BorderLayout());
    panel.setBorder(IdeBorderFactory.createBorder(SideBorder.LEFT));
    setRuleKey(null);
    show();
  }

  public void setRuleKey(@Nullable String key) {
    if(key == null) {
      nothingToDisplay(false);
    } else {
      String description = sonarlint.getDescription(key);
      if(description == null) {
        nothingToDisplay(true);
      }
      updateEditor(description);
    }
  }

  private void nothingToDisplay(boolean error) {
    editor = null;
    panel.removeAll();

    String txt;
    if(error) {
      txt = "Couldn't find an extended description for the rule";
    } else {
      txt = "Select an issue to see extended rule description";
    }

    JComponent titleComp = new JLabel(txt, SwingConstants.CENTER);
    panel.add(titleComp, BorderLayout.CENTER);
    panel.revalidate();
  }

  private void updateEditor(String text) {
    ApplicationManager.getApplication().assertIsDispatchThread();
    if(editor == null) {
      panel.removeAll();
      editor = new JEditorPane();
      editor.setBorder(new EmptyBorder(10, 10, 10, 10));
      editor.setEditable(false);
      editor.setContentType("text/html");
      editor.addHyperlinkListener(new HyperlinkListener() {
        @Override public void hyperlinkUpdate(HyperlinkEvent e) {
          if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
            System.out.println(e.getURL());
            Desktop desktop = Desktop.getDesktop();
            try {
              desktop.browse(e.getURL().toURI());
            } catch (Exception ex) {
              SonarLintConsole.get(project).error("Error opening browser: " + e.getURL(), ex);
            }
          }
        }
      });
      panel.add(editor, BorderLayout.CENTER);
    }

    editor.setText(text);
    editor.setCaretPosition(0);
    panel.revalidate();
  }

  public JComponent getPanel() {
    return panel;
  }

  public void hide() {
    panel.setVisible(false);
  }

  public void show() {
    panel.setVisible(true);
  }
}