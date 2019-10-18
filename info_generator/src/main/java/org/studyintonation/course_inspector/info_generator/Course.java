package org.studyintonation.course_inspector.info_generator;

import java.util.List;

class Course {
    private String id;
    private String title;
    private String description;
    private String logoUrl;
    private int size;
    private int difficulty;
    private String category;
    private String version;
    private String sha2Hash;
    private String url;
    private List<String> authors;
    private String releaseDate;
    private String logo;

    String getLogo() {
        return logo;
    }

    void setLogo(String logo) {
        this.logo = logo;
    }

    void setLogoUrl(String logoUrl) {

        this.logoUrl = logoUrl;
    }

    void setSize(int size) {

        this.size = size;
    }

    void setVersion(String version) {
        this.version = version;
    }

    void setSha2Hash(String sha2Hash) {
        this.sha2Hash = sha2Hash;
    }

    void setUrl(String url) {
        this.url = url;
    }
}
