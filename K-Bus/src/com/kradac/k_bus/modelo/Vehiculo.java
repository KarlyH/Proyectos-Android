package com.kradac.k_bus.modelo;

public class Vehiculo {

	private String nombrePropietario;
	private String apellidoPropietario;
	private String nombreEmpresa;
	private String placaVehiculo;
	private String regMunicipalVehiculo;

	public String getNombrePropietario() {
		return nombrePropietario;
	}

	public void setNombrePropietario(String nombrePropietario) {
		this.nombrePropietario = nombrePropietario;
	}

	public String getApellidoPropietario() {
		return apellidoPropietario;
	}

	public void setApellidoPropietario(String apellidoPropietario) {
		this.apellidoPropietario = apellidoPropietario;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public String getPlacaVehiculo() {
		return placaVehiculo;
	}

	public void setPlacaVehiculo(String placaVehiculo) {
		this.placaVehiculo = placaVehiculo;
	}

	public String getRegMunicipalVehiculo() {
		return regMunicipalVehiculo;
	}

	public void setRegMunicipalVehiculo(String regMunicipalVehiculo) {
		this.regMunicipalVehiculo = regMunicipalVehiculo;
	}

	public Vehiculo(String nombrePropietario, String apellidoPropietario,
			String nombreEmpresa, String placaVehiculo,
			String regMunicipalVehiculo) {
		super();
		this.nombrePropietario = nombrePropietario;
		this.apellidoPropietario = apellidoPropietario;
		this.nombreEmpresa = nombreEmpresa;
		this.placaVehiculo = placaVehiculo;
		this.regMunicipalVehiculo = regMunicipalVehiculo;
	}

}
