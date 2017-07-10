package com.murrayc.bigoquiz.shared;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

/**
 * Copyright (c) 2016 Murray Cumming
 *
 * Created by murrayc on 23.06.16.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HasIdAndTitle {
    protected String link;

    public HasIdAndTitle() {
    }

    public HasIdAndTitle(final String id, final String title, final String link) {
        this.id = id;
        this.title = title;
        this.link = link;
    }

    @NotNull
    public static Comparator<HasIdAndTitle> generateTitleSortComparator() {
        return (o1, o2) -> {
            if ((o1 == null) && (o2 == null)) {
                return 0;
            } else if (o1 == null) {
                return -1;
            }

            if ((o1.title == null) && (o2.title == null)) {
                return 0;
            } else if (o1.title == null) {
                return -1;
            }

            return o1.title.compareTo(o2.title);
        };
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    private String id;
    private String title;

    public String getLink() {
        return link;
    }
}
