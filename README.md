# gradle-github-plugin
[ ![Download](https://api.bintray.com/packages/riiidadmin/maven/gradle-github-plugin/images/download.svg) ](https://bintray.com/riiidadmin/maven/gradle-github-plugin/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-gradle--github--plugin-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1890)

:truck: Gradle plugin for Github releases.  
You can fully automate releases via *gradle-github-plugin*. :rocket:


## Supported features
- Create a release
- Upload your files and/or directories!!! :boom::boom::boom:


## Usage
Edit your `build.gradle` file.  
Add `jcenter()`, `classpath ...` to `repositories`, `dependencies` in `buidlscript` respectively.

```groovy
buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        ...
        classpath 'co.riiid:gradle-github-plugin:0.2.0'
        ...
    }
}
```

And add apply plugin.

```groovy
apply plugin: 'co.riiid.gradle'
```

Then add `github` configuration and set properties.

### Supported Properties
Name | Type | Description
--- | --- | ---
owner | String | *Required.* The id of your Github.
repo | String | *Required.* The name of your Github's repository.
token | String | *Required.* Github access token. Generate yours in [Settings/Tokens][settings_tokens]
tagName | String | *Required.* The name of the tag.
targetCommitish | String | *Default is master.* Specifies the commitish value that determines where the Git tag is created from. Can be any branch or commit SHA. Unused if the Git tag already exists. Default: the repository’s default branch (usually master).
name | String | *Optional.* The name of the release.
body | String | *Optional.* Text describing the contents of the tag.
prerelease | boolean | *Optional.* `true` to create a draft (unpublished) release, `false` to create a published one. Default: `false`
draft | boolean | *Optional.* `true` to identify the release as a prerelease. `false` to identify the release as a full release. Default: `false`

### Example
```groovy
github {
    owner = 'riiid'
    repo = 'gradle-github-plugin'
    token = 'XXXXXXXXXXXXXXXXXXXXX'
    tagName = '0.1.0'
    targetCommitish = 'master'
    name = 'v0.1.0'
    body = """# Project Name
Write `release note` here.
"""
    assets = [
            'app/build/outputs/apk/app-release.apk',
            'app/build/outputs/mapping/release/mapping.txt',
            'app/build/outputs',
            ...
    ]
}
```

If an asset is directory, `gradle-github-plugin` will zip the directory, `<dir-name>.zip` by name.  
For example, `app/build/outputs` is compressed into `app/build/outputs.zip`. The file will be removed after uploaded.

Finally you can see `githubRelease` task

```sh
$ ./gradlew tasks | grep githubRelease
githubRelease
```

Run the task!!!

```sh
$ ./gradlew githubRelease
```

Good luck!!! :trollface::trollface::trollface:


## References
- [Github Releases][github-releases]












[github-releases]: https://developer.github.com/v3/repos/releases/
[settings_tokens]: https://github.com/settings/tokens
