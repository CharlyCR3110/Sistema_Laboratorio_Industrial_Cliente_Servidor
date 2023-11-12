package com.cliente.instrumentos.logic.async;

import com.compartidos.elementosCompartidos.MensajeAsincrono;

public interface ITarget {
    public void deliver(MensajeAsincrono message);
}
