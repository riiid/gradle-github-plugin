package co.riiid.gradle

import groovy.json.JsonSlurper
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.ParserRegistry

class HttpBuilder extends HTTPBuilder {

    public HttpBuilder(String url) {
        super(url)
        this.parser.'text/json' = { resp ->
            def bufferedText = resp.entity.content.getText(ParserRegistry.getCharset(resp)).trim()
            return new JsonSlurper().parseText(bufferedText)
        }
        this.parser.'application/json' = this.parser.'text/json'
    }
}
