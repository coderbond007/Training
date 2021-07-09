package net.media.training.live.ocp;

/**
 * Created by autoopt/mayank.k
 */
public interface Resource {
    void markSlotFree(int resourceId);

    void markSlotBusy(int resourceId);

    int findFreeSlot();
}
