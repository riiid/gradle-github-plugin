package co.riiid.gradle

import groovyx.net.http.ContentType
import groovyx.net.http.Method
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ReleaseTask extends DefaultTask {

    final String BASE_URL = 'https://api.github.com'

    // header
    final String HEADER_ACCEPT = 'application/vnd.github.v3+json'
    final String HEADER_USER_AGENT = 'Mozilla/5.0'

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
                body            : project.github.getBody()
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
            def name = asset.split('/')[-1]
            def upload = uploadUrl.replace('{?name}', "?name=${name}")

            def url = new URL(upload as String)
            def host = url.host + (url.port > 0 ? ":" + url.port + "" : "")
            host = "${url.protocol}://${host}"
            def path = url.path

            def http = new HttpBuilder(host)
            def file = new File(asset as String)

            def map = URLConnection.getFileNameMap()
            def contentType = map.getContentTypeFor(asset as String)

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
                }

                response.failure = { resp, json ->
                    System.err.println json
                }
            }
        }
    }
}

