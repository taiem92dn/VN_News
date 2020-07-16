package com.tngdev.vnnews.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(strict = false, name = "item")
public class NewsItem {

    @Element
    private String title;
    @Element
    private String description;
    @Element(name="pubDate")
    private String date;
    @Element
    private String link;
    @Element
    private String guid;

    private static final String PREFIX_IMG = "<img src=\"";
    private static final String SUFFIX_IMG = "\" >";
    private String image = null;

    public String getImage() {
        if (image != null)
            return image;


        int index = description.lastIndexOf(PREFIX_IMG);
        if (index != -1) {
            image = description.substring(index + PREFIX_IMG.length(), description.lastIndexOf(SUFFIX_IMG));
        }
        else {
            image = "";
        }

        return image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }


}
