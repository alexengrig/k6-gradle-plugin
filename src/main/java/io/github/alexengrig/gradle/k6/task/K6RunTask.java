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

package io.github.alexengrig.gradle.k6.task;

import org.gradle.api.Project;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.tasks.Exec;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.api.tasks.options.Option;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @since 0.1.0
 */
public class K6RunTask extends Exec {

    /**
     * @since 0.1.0
     */
    public static final String NAME = "k6Run";

    private final SourceDirectorySet source;

    private Set<String> pathsOfTests;
    private String flags = "";

    /**
     * @since 0.1.0
     */
    @Inject
    public K6RunTask(SourceDirectorySet source) {
        setGroup("k6");
        setDescription("Start a test.");
        setWorkingDir("src/loadTest/k6");
        this.source = source;
    }

    /**
     * @since 0.1.0
     */
    public static TaskProvider<K6RunTask> register(Project project, SourceDirectorySet source) {
        TaskContainer tasks = project.getTasks();
        return tasks.register(NAME, K6RunTask.class, source);
    }

    @Override
    protected void exec() {
        int skipDirPath = 1 + source.getSrcDirs().iterator().next().getPath().length();
        Stream<String> tests = source.getFiles().stream().map(file -> file.getPath().substring(skipDirPath));
        if (pathsOfTests != null && !pathsOfTests.isEmpty()) {
            tests = tests.filter(file -> pathsOfTests.stream().anyMatch(file::matches));
        }
        tests.forEach(test -> {
            setCommandLine("k6", "run", test, flags);
            super.exec();
        });
    }

    /**
     * @since 0.1.0
     */
    @SuppressWarnings("unused")
    @Option(option = "tests", description = "file paths of tests")
    public void setPaths(List<String> paths) {
        this.pathsOfTests = new HashSet<>(paths);
    }

    /**
     * @since 0.1.0
     */
    @SuppressWarnings("unused")
    @Option(option = "flags", description = "flags of running")
    public void setFlags(String flags) {
        this.flags = flags;
    }

}
