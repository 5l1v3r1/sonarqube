/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2014 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.api.batch.fs;

import org.sonar.api.batch.fs.internal.RelativePathPredicate;

/**
 * Determines if a file must be kept in search results. See {@link org.sonar.api.batch.fs.FileSystem}
 * and {@link org.sonar.api.batch.fs.FilePredicates}.
 * @since 4.2
 */
public interface FilePredicate extends Comparable<FilePredicate> {
  /**
   * Test if provided file is valid for this predicate
   */
  boolean apply(InputFile inputFile);

  /**
   * Filter provided files to keep only the ones that are valid for this predicate
   */
  Iterable<InputFile> filter(Iterable<InputFile> inputFiles);

  /**
   * Get all files that are valid for this predicate.
   */
  Iterable<InputFile> get(FileSystem.Index index);

  /**
   * For optimization. FilePredicates will be applied in priority order. For example when doing
   * p.and(p1, p2, p3) then p1, p2 and p3 will be applied according to their priority value. Higher priority value
   * are applied first.
   * Assign a high priority when the predicate will likely highly reduce the set of InputFiles to filter. Also
   * {@link RelativePathPredicate} and AbsolutePathPredicate have a high priority since they are using cache index.
   */
  int priority();
}
