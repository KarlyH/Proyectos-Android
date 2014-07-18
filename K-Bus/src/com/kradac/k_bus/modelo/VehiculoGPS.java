package com.kradac.k_bus.modelo;

public class VehiculoGPS {
	private double latitud;
	private double longitud;
	private String regMunicipal;
	private String placa;
	private double velocidad;
	private String fechaHoraUltDato;

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

	public String getRegMunicipal() {
		return regMunicipal;
	}

	public void setRegMunicipal(String regMunicipal) {
		this.regMunicipal = regMunicipal;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}

	public String getFechaHoraUltDato() {
		return fechaHoraUltDato;
	}

	public void setFechaHoraUltDato(String fechaHoraUltDato) {
		this.fechaHoraUltDato = fechaHoraUltDato;
	}

	public VehiculoGPS(double latitud, double longitud, String regMunicipal,
			String placa, double velocidad, String fechaHoraUltDato) {
		super();
		this.latitud = latitud;
		this.longitud = longitud;
		this.regMunicipal = regMunicipal;
		this.placa = placa;
		this.velocidad = velocidad;
		this.fechaHoraUltDato = fechaHoraUltDato;
	}
}
