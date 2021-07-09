package net.media.training.live.isp;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 11, 2011
 * Time: 10:26:10 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Door {
    protected boolean isLocked;
    protected boolean isOpened;

    public void lock() {
        this.isLocked = true;
    }

    public void unlock() {
        this.isLocked = false;
    }

    public void open() {
        if (!isLocked)
            isOpened = true;
    }

    public void close() {
        isOpened = false;
    }
}
