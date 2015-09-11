package co.riiid.gradle

import groovyx.net.http.ContentType
import groovyx.net.http.Method
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.zeroturnaround.zip.ZipUtil

class ReleaseTask extends DefaultTask {

    final String BASE_URL = 'https://api.github.com'

    // header
    final String HEADER_ACCEPT = 'application/vnd.github.v3+json'
    final String HEADER_USER_AGENT = 'gradle-github-plugin'

    @TaskAction
    public release() {
        def http = new HttpBuilder(BASE_URL)

        def path = "/repos/" +
                "${project.github.owner}/" +
                "${project.github.repo}/releases"

        def postBody = [
                tag_name        : project.github.getTagName(),
                target_commitish: project.github.getTargetCommitish(),
                name            : project.github.getName(),
                body            : project.github.getBody(),
                prerelease      : project.github.isPrerelease(),
                draft           : project.github.isDraft()
        ]

        http.request(Method.POST) {
            uri.path = path
            requestContentType = ContentType.JSON
            body = postBody

            headers.'User-Agent' = HEADER_USER_AGENT
            headers.'Authorization' = "token ${project.github.token}"
            headers.'Accept' = HEADER_ACCEPT

            response.success = { resp, json ->
                println json
                if (project.github.assets != null) {
                    postAssets(json.upload_url, project.github.assets)
                }
            }

            response.failure = { resp, json ->
                System.err.println json
            }
        }
    }

    public postAssets(uploadUrl, assets) {
        assets.each { asset ->
            def file = new File(asset as String)
            def name = asset.split('/')[-1]
            if (file.exists() && file.directory) {
                name += ".zip"
            }

            def upload = uploadUrl.replace(
                    '{?name,label}', "?name=${name}&label=${name}")
            println "upload url: ${upload}"

            def url = new URL(upload as String)
            def host = url.host + (url.port > 0 ? ":" + url.port + "" : "")
            host = "${url.protocol}://${host}"
            def path = url.path
            def http = new HttpBuilder(host)

            if (file.exists()) {
                if (file.directory) {
                    def zipFile = new File(file.parentFile, file.name + ".zip")
                    ZipUtil.pack(file, zipFile)
                    file = zipFile
                }

                def map = URLConnection.getFileNameMap()
                def contentType = map.getContentTypeFor(file.absolutePath)

                http.ignoreSSLIssues()
                http.request(Method.POST) { req ->
                    uri.path = path
                    uri.query = [
                            name: name,
                    ]
                    send ContentType.BINARY, file.bytes

                    headers.'User-Agent' = HEADER_USER_AGENT
                    headers.'Authorization' = "token ${project.github.token}"
                    headers.'Accept' = HEADER_ACCEPT
                    headers.'Content-Type' = contentType


                    response.success = { resp, json ->
                        println json
                        if (file.exists() && file.name.endsWith(".zip"))
                            file.delete()
                    }

                    response.failure = { resp, json ->
                        System.err.println json
                        if (file.exists() && file.name.endsWith(".zip"))
                            file.delete()
                    }
                }
            }
        }
    }
}

