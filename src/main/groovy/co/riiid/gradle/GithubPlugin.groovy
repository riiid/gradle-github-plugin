package co.riiid.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class GithubPlugin implements Plugin<Project> {

    private static final String NAME = 'github'

    void apply(Project project) {
        project.extensions.create(NAME, GithubExtension)
        project.task('githubRelease', type: ReleaseTask)
    }
}
