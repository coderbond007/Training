package net.media.training.designpattern.composite;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: joelrosario
 * Date: Jul 19, 2011
 * Time: 9:18:51 PM
 * To change this template use pre_refactoring.File | Settings | pre_refactoring.File Templates.
 */
public class Directory implements FileInterface{
    private final String name;
    private List<FileInterface> fileInterfaces;
    private Directory parent;

    public String getName() {
        return name;
    }

    public Directory(String name, List<FileInterface> fileInterfaces) {
        this.name = name;
        this.fileInterfaces = fileInterfaces;

        for (FileInterface fileInterface : fileInterfaces) {
            fileInterface.setParent(this);
        }
    }

    public int getSize() {
        int sum = 0;
        for (FileInterface fileInterface : fileInterfaces) {
            sum += fileInterface.getSize();
        }
        return sum;
    }

    public void setParent(Directory directory) {
        this.parent = directory;
    }

    public void delete(Directory directoryToDelete) {
        while(this.fileInterfaces.size()>0) {
            FileInterface fileInterface = this.fileInterfaces.get(0);
            fileInterface.delete();
        }
        directoryToDelete.getParent().removeEntry(directoryToDelete);
    }

    public void delete() {
        delete(this);
    }

    public void removeEntry(FileInterface fileInterface) {
        fileInterfaces.remove(fileInterface);
    }

    public void add(FileInterface fileInterface) {
        fileInterfaces.add(fileInterface);
    }

    private boolean fileExists(String name, Directory directoryToSearch) {
        for (FileInterface fileInterface : directoryToSearch.getFileInterfaces()) {
            if (fileInterface.fileExists(name)) {
                return true;
            }
        }
        return this.name.equals(name);
    }

    public boolean fileExists(String name) {
        return fileExists(name, this);
    }


    public List<FileInterface> getFileInterfaces() {
        return fileInterfaces;
    }

    public Directory getParent() {
        return parent;
    }
}
