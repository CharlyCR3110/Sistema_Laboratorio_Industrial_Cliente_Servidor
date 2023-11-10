package com.cliente.instrumentos.logic.async;

public interface IListener {
    public void addTarget(ITarget t);

    public void start();
    public void stop();
}
