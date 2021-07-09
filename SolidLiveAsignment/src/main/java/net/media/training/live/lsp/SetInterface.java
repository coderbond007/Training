package net.media.training.live.lsp;

public interface SetInterface<T> {
    void add(final T element);
    boolean isMember(final T element);
}
