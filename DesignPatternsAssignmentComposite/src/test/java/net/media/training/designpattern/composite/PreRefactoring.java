package net.media.training.designpattern.composite;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: joelrosario
 * Date: Jul 19, 2011
 * Time: 9:36:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class PreRefactoring {
    @Test
    public void BasicStuffWorks() {
        List<FileInterface> files = new ArrayList();

        files.add(new File("file1", 10));
        files.add(new File("file2", 10));
        files.add(new File("File3", 10));

        List<FileInterface> directories = new ArrayList();
        FileInterface dataDir = new Directory("data", files);
        directories.add(dataDir);

        Directory src = new Directory("src", directories);
        assertEquals("Size should be 30.", 30, src.getSize());

        assertTrue("File file1 should exist.", src.fileExists("file1"));
        assertTrue("Directory data should exist.", src.fileExists("data"));
        assertTrue("Directory src should exist.", src.fileExists("src"));

        dataDir.delete();

        assertFalse("File file1 should not exist.", src.fileExists("file1"));
        assertFalse("Directory data should not exist.", src.fileExists("data"));
    }
}
