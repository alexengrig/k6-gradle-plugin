/*
 * Copyright 2024 Alexengrig Dev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.alexengrig.gradle.k6;

import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;

/**
 * @since 0.1.0
 */
public interface K6Extension {

    /**
     * @since 0.1.0
     */
    String NAME = "k6";

    /**
     * @since 0.1.0
     */
    static K6Extension create(Project project) {
        ExtensionContainer extensions = project.getExtensions();
        return extensions.create(NAME, K6Extension.class);
    }

}
