package com.kradac.k_bus.modelo;

public class LineaRuta {
	private double latitud;
	private double longitud;

	public double getLatitud() {
		return latitud;
	}

	public void setLatitud(double latitud) {
		this.latitud = latitud;
	}

	public double getLongitud() {
		return longitud;
	}

	public void setLongitud(double longitud) {
		this.longitud = longitud;
	}

	public LineaRuta(double latitud, double longitud) {
		super();
		this.latitud = latitud;
		this.longitud = longitud;
	}

}
