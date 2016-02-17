# gradle-github-plugin
[ ![Download](https://api.bintray.com/packages/riiidadmin/maven/gradle-github-plugin/images/download.svg) ](https://bintray.com/riiidadmin/maven/gradle-github-plugin/_latestVersion)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-gradle--github--plugin-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/1890)

:truck: Gradle plugin for Github releases.  
You can fully automate releases via *gradle-github-plugin*. :rocket:


## Supported features
- Create a release
- Upload your files and/or directories!!! :boom::boom::boom:


## Usage
There are two ways.

### 1. jcenter
Edit your `build.gradle` file.  
Add `jcenter()`, `classpath ...` to `repositories`, `dependencies` in `buidlscript` respectively.

```groovy
buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        ...
        classpath 'co.riiid:gradle-github-plugin:X.Y.Z'
        ...
    }
}
```

And add apply plugin.

```groovy
apply plugin: 'co.riiid.gradle'
```

### 2. plugins.gradle.org (easy)
[Plugin mechanism][plugins] introduced in Gradle 2.1+

```groovy
plugins {
  id "co.riiid.gradle" version "X.Y.Z"
}
```

Add `github` configuration and set properties if you've done with 1 or 2.


### Supported Properties
Name | Type | Description
--- | --- | ---
baseUrl | String | *Optional.* The URL of Github. You can change this url as yours if you use Github Enterprise. (Default: https://api.github.com)
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
[plugins]: https://docs.gradle.org/2.4/userguide/plugins.html#sec:plugins_block
[ref]: https://plugins.gradle.org/plugin/co.riiid.gradle
