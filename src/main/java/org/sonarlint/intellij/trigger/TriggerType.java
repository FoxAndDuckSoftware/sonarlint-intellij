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
package org.sonarlint.intellij.trigger;

public enum TriggerType {
  EDITOR_OPEN("Editor open"),
  ACTION("Action"),
  ALL("All files"),
  CHANGED_FILES("Changed files"),
  COMPILATION("Compilation"),
  EDITOR_CHANGE("Editor change"),
  CHECK_IN("Pre-commit check"),
  CONFIG_CHANGE("Config change"),
  BINDING_UPDATE("Binding update");

  private final String name;

  TriggerType(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
