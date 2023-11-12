package com.cliente.instrumentos.presentation.notificaciones;

import com.compartidos.elementosCompartidos.MensajeAsincrono;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Model extends java.util.Observable{
	List<MensajeAsincrono> mensajeAsincronos;
	int props;

	public Model() {
	}

	public void init(){
		this.setList(new ArrayList<MensajeAsincrono>());
		props=0;
	}

	public void setList(List<MensajeAsincrono> list){
		this.mensajeAsincronos = list;
		props+=LIST;
	}

	public void agregar(MensajeAsincrono e){
		this.mensajeAsincronos.add(e);
		props+=LIST;
	}

	public List<MensajeAsincrono> getList() {
		return mensajeAsincronos;
	}

	@Override
	public void addObserver(Observer o) {
		super.addObserver(o);
		commit();
	}

	public void commit(){
		setChanged();
		notifyObservers(props);
		props=0;
	}

	public static int LIST=1;
}
