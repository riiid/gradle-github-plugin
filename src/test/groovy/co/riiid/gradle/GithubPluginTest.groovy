package co.riiid.gradle

import org.gradle.testfixtures.ProjectBuilder
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GithubPluginTest {

    def project

    @Before
    void setUp() {
        project = ProjectBuilder.builder().build()
        project.apply plugin: 'co.riiid.gradle'

    }

    @Test
    void shouldHasGithubReleaseTask() {
        Assert.assertTrue(project.tasks.githubRelease instanceof ReleaseTask)
    }
}

