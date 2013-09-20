package org.dbdoclet.tidbit.common;

import java.util.ArrayList;

import org.dbdoclet.io.FileSet;

public class MediumTargetParams {

    private String overview;
    private String destinationEncoding;
    private String sourceEncoding;
    private Visibility visibility;
    private String source;
    private String memory;
    private ArrayList<FileSet> sourcepath;
    private boolean isDocBook5 = false;
    private boolean linkSourceEnabled;

    public String getDestinationEncoding() {
        return destinationEncoding;
    }

    public String getMemory() {
        return memory;
    }

    public String getOverview() {
        return overview;
    }

    public String getSource() {
        return source;
    }

    public String getSourceEncoding() {
        return sourceEncoding;
    }

    public ArrayList<FileSet> getSourcepath() {
        return sourcepath;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public boolean isDocBook5() {
        return isDocBook5;
    }

    public boolean isLinkSourceEnabled() {
        return linkSourceEnabled;
    }

    public void setDestinationEncoding(String destinationEncoding) {
        this.destinationEncoding = destinationEncoding;
    }

    public void setDocBook5(boolean isDocBook5) {
        this.isDocBook5 = isDocBook5;
    }

    public void setLinkSourceEnabled(boolean linkSourceEnabled) {
        this.linkSourceEnabled = linkSourceEnabled;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setSourceEncoding(String sourceEncoding) {
        this.sourceEncoding = sourceEncoding;
    }

    public void setSourcepath(ArrayList<FileSet> sourcepath) {
        this.sourcepath = sourcepath;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}
