package net.media.training.designpattern.composite;

public interface FileInterface {
    public void setParent(Directory parent);

    public String getName();

    public int getSize();

    public Directory getParent();

    public void delete();

    public boolean fileExists(String name);
}
