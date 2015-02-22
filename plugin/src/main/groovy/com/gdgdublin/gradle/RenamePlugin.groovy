package com.gdgdublin.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class RenamePlugin implements Plugin<Project> {

    void apply(Project project) {

        def renamingOptions = project.extensions.create("renamingOptions", com.gdgdublin.gradle.internal.RenameExtension, project)

        project.afterEvaluate {
            if (renamingOptions.outputOptions.renameOutput) {
                project.android.applicationVariants.all {
                    renamingOptions.outputOptions.generateOutputName(project, it)
                }
            }
        }
    }
}
