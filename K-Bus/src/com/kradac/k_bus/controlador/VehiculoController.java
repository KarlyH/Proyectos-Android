package com.kradac.k_bus.controlador;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.kradac.k_bus.modelo.Vehiculo;

public class VehiculoController {

	private static final String url_consulta_regMunicipal = "/K-Bus/webresources/com.kradac.kbus.rest.entities.vehiculos/regmuni=";
	private static final String url_consulta_placa = "/K-Bus/webresources/com.kradac.kbus.rest.entities.vehiculos/placa=";
	// Server Data_Loja http://190.12.61.30:5801
	// Server Data_Zamor http://200.0.29.117
	private static final String tag_nombrePropietario = "nombres";
	private static final String tag_apellidoPropietario = "apellidos";
	private static final String tag_nombreEmpresa = "empresa";
	private static final String tag_placaVehiculo = "placa";
	private static final String tag_regMunicipalVehiculo = "regMunicipal";
	private static final String tag_idEmpresa = "idEmpresa";
	private static final String tag_idPersona = "idPersona";

	public Vehiculo consultaPorRegMunicipal(String urlServer,
			String regMunicipal) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet del = new HttpGet(urlServer + url_consulta_regMunicipal
				+ regMunicipal);
		del.setHeader("content-type", "application/json");
		try {
			HttpResponse resp = httpClient.execute(del);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONArray vehiculosJSON = new JSONArray(respStr);
			JSONObject vehiculoJSON = vehiculosJSON.getJSONObject(0);
			JSONObject personaJSON = vehiculoJSON.getJSONObject(tag_idPersona);
			JSONObject empresaJSON = vehiculoJSON.getJSONObject(tag_idEmpresa);
			Vehiculo vehiculo = new Vehiculo(
					personaJSON.getString(tag_nombrePropietario),
					personaJSON.getString(tag_apellidoPropietario),
					empresaJSON.getString(tag_nombreEmpresa),
					vehiculoJSON.getString(tag_placaVehiculo),
					vehiculoJSON.getString(tag_regMunicipalVehiculo));
			return vehiculo;

		} catch (Exception ex) {
			Log.e("ServicioRest", "Error!", ex);
			return null;
		}
	}

	public Vehiculo consultaPorPlaca(String urlServer, String placa) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet del = new HttpGet(urlServer + url_consulta_placa + placa);
		del.setHeader("content-type", "application/json");
		try {
			HttpResponse resp = httpClient.execute(del);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONArray vehiculosJSON = new JSONArray(respStr);
			JSONObject vehiculoJSON = vehiculosJSON.getJSONObject(0);
			JSONObject personaJSON = vehiculoJSON.getJSONObject(tag_idPersona);
			JSONObject empresaJSON = vehiculoJSON.getJSONObject(tag_idEmpresa);
			Vehiculo vehiculo = new Vehiculo(
					personaJSON.getString(tag_nombrePropietario),
					personaJSON.getString(tag_apellidoPropietario),
					empresaJSON.getString(tag_nombreEmpresa),
					vehiculoJSON.getString(tag_placaVehiculo),
					vehiculoJSON.getString(tag_regMunicipalVehiculo));
			return vehiculo;
		} catch (Exception ex) {
			Log.e("ServicioRest", "Error!", ex);
			return null;
		}
	}
}
