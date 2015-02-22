package com.gdgdublin.gradle.internal

import org.gradle.api.Project

import javax.inject.Inject

class RenameExtension {

    final Project project;
    FileOutputOptions outputOptions

    @Inject
    RenameExtension(Project project) {
        this.project = project
        outputOptions = new FileOutputOptions()
    }


    void outputOptions(Closure c) {
        project.configure(outputOptions, c)
    }
}
