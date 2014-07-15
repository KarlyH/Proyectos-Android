package com.kradac.k_bus.modelo;

public class Ruta {
	private String codRuta;
	private String color;
	private String icono;
	private int idRuta;
	private int linea;
	private String ruta;
	private String tiempoSancion;

	public String getCodRuta() {
		return codRuta;
	}

	public void setCodRuta(String codRuta) {
		this.codRuta = codRuta;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getIcono() {
		return icono;
	}

	public void setIcono(String icono) {
		this.icono = icono;
	}

	public int getIdRuta() {
		return idRuta;
	}

	public void setIdRuta(int idRuta) {
		this.idRuta = idRuta;
	}

	public int getLinea() {
		return linea;
	}

	public void setLinea(int linea) {
		this.linea = linea;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

	public String getTiempoSancion() {
		return tiempoSancion;
	}

	public void setTiempoSancion(String tiempoSancion) {
		this.tiempoSancion = tiempoSancion;
	}

	public Ruta(String codRuta, String color, String icono, int idRuta,
			int linea, String ruta, String tiempoSancion) {
		super();
		this.codRuta = codRuta;
		this.color = color;
		this.icono = icono;
		this.idRuta = idRuta;
		this.linea = linea;
		this.ruta = ruta;
		this.tiempoSancion = tiempoSancion;
	}

}
