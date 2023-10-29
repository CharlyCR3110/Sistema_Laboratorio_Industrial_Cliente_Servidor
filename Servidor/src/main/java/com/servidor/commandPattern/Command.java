package com.servidor.commandPattern;

import java.util.List;

public interface Command<T> {
	public void setDatos(Object datos);
	public int execute();
	public T getReturn();
	public List<T> getListReturn();
}