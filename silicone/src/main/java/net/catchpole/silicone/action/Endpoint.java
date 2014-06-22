package net.catchpole.silicone.action;

public interface Endpoint<I,O> {
    public O handle(I input);
}
