package com.kradac.k_bus.modelo;

public class VehiculoGPS {
	private double latitud;
	private double longitud;
	private String regMunicipal;
	private String placa;
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
	public VehiculoGPS(double latitud, double longitud, String regMunicipal,
			String placa) {
		super();
		this.latitud = latitud;
		this.longitud = longitud;
		this.regMunicipal = regMunicipal;
		this.placa = placa;
	}
}
