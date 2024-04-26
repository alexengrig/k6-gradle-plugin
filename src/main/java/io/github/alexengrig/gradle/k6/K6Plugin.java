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

import io.github.alexengrig.gradle.k6.task.K6RunTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.model.ObjectFactory;

import javax.inject.Inject;

/**
 * @since 0.1.0
 */
public class K6Plugin implements Plugin<Project> {

    private final ObjectFactory objectFactory;

    @Inject
    public K6Plugin(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    /**
     * @since 0.1.0
     */
    @Override
    public void apply(Project project) {
        var source = createSource();
        var extension = K6Extension.create(project);
        var runTask = K6RunTask.register(project, source);
    }

    private SourceDirectorySet createSource() {
        var k6 = objectFactory.sourceDirectorySet("k6", "k6 source");
        k6.srcDir("src/loadTest/k6");
        k6.getFilter().include("**/*.js");
        return k6;
    }

}
