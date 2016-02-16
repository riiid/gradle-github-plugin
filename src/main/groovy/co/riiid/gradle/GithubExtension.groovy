package co.riiid.gradle

class GithubExtension {

    String baseUrl = "https://api.github.com"
    String acceptHeader = 'application/vnd.github.v3+json'
    
    String owner
    String repo
    String token

    String tagName
    String targetCommitish = "master"
    String name
    String body

    boolean prerelease = false
    boolean draft = false

    String[] assets

    String getBaseUrl() {
        return baseUrl
    }

    String getAcceptHeader() {
        return acceptHeader
    }

    String getOwner() {
        return owner
    }

    String getRepo() {
        return repo
    }

    String getToken() {
        return token
    }

    String getTagName() {
        return tagName
    }

    String getTargetCommitish() {
        return targetCommitish
    }

    String getName() {
        return name
    }

    String getBody() {
        return body
    }

    boolean isPrerelease() {
        return prerelease
    }

    boolean isDraft() {
        return draft
    }

    String[] getAssets() {
        return assets
    }

    void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl
    }

    void setAcceptHeader(String acceptHeader) {
        this.acceptHeader = acceptHeader
    }

    void setOwner(String owner) {
        if (owner == null || owner.isEmpty()) {
            throw new IllegalArgumentException("owner")
        }

        this.owner = owner
    }

    void setRepo(String repo) {
        if (repo == null || repo.isEmpty()) {
            throw new IllegalArgumentException("repo")
        }

        this.repo = repo
    }

    void setToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new IllegalArgumentException("token")
        }

        this.token = token
    }

    void setTagName(String tagName) {
        this.tagName = tagName
    }

    void setTargetCommitish(String targetCommitish) {
        this.targetCommitish = targetCommitish
    }

    void setName(String name) {
        this.name = name
    }

    void setBody(String body) {
        this.body = body
    }

    void setAssets(String[] assets) {
        this.assets = assets
    }

    void setPrerelease(boolean prerelease) {
        this.prerelease = prerelease
    }

    void setDraft(boolean draft) {
        this.draft = draft
    }
}
