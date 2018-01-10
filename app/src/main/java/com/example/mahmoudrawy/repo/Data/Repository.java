
package com.example.mahmoudrawy.repo.Data;

/**
 * Created by mahmoud rawy™ 01221240053 on 09/01/2018.
 */

/*
 this class for Repository element
 */
public class Repository {

    private String name;


    private Owner owner;


    private String htmlUrl;

    private String description;

    private Boolean fork;


    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFork() {
        return fork;
    }

    public void setFork(Boolean fork) {
        this.fork = fork;
    }


}
