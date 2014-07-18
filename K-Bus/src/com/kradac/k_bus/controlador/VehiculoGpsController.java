package com.kradac.k_bus.controlador;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.kradac.k_bus.modelo.VehiculoGPS;

import android.util.Log;

public class VehiculoGpsController {

	private static final String url_skyPatrol = "/K-Bus/webresources/com.kradac.kbus.rest.entities.ultimodatoskps/ruta=";
	private static final String url_fastraks = "/K-Bus/webresources/com.kradac.kbus.rest.entities.ultimodatofastracks/ruta=";
	private static final String tag_idEquipo = "idEquipo";
	private static final String tag_latitud = "latitud";
	private static final String tag_longitud = "longitud";
	private static final String tag_placaVehiculo = "placa";
	private static final String tag_regMunicipalVehiculo = "regMunicipal";
	private static final String tag_velocidad = "velocidad";
	private static final String tag_fechaHoraUltDato = "fechaHoraUltDato";

	JSONArray rutasJSON = null;

	public ArrayList<VehiculoGPS> listarSkyPatrol(String urlServer, int idRuta) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpClient httpClient1 = new DefaultHttpClient();
		HttpGet del = new HttpGet(urlServer + url_skyPatrol + idRuta);
		HttpGet del1 = new HttpGet(urlServer + url_fastraks + idRuta);
		del.setHeader("content-type", "application/json");
		del1.setHeader("content-type", "application/json");
		try {
			HttpResponse resp = httpClient.execute(del);
			HttpResponse resp1 = httpClient1.execute(del1);
			String respStr = EntityUtils.toString(resp.getEntity());
			String respStr1 = EntityUtils.toString(resp1.getEntity());
			JSONArray respJSON = new JSONArray(respStr);
			JSONArray respJSON1 = new JSONArray(respStr1);
			ArrayList<VehiculoGPS> listaVehiculoGps = new ArrayList<VehiculoGPS>();
			for (int i = 0; i < respJSON.length(); i++) {
				JSONObject skyPatrolJSON = respJSON.getJSONObject(i);
				VehiculoGPS vehiculoGps = new VehiculoGPS(
						skyPatrolJSON.getDouble(tag_latitud),
						skyPatrolJSON.getDouble(tag_longitud), skyPatrolJSON
								.getJSONObject(tag_idEquipo).getString(
										tag_regMunicipalVehiculo),
						skyPatrolJSON.getJSONObject(tag_idEquipo).getString(
								tag_placaVehiculo),
						skyPatrolJSON.getDouble(tag_velocidad),
						skyPatrolJSON.getString(tag_fechaHoraUltDato));
				listaVehiculoGps.add(vehiculoGps);
			}
			for (int i = 0; i < respJSON1.length(); i++) {
				JSONObject fastracksJSON = respJSON1.getJSONObject(i);
				VehiculoGPS vehiculoGps = new VehiculoGPS(
						fastracksJSON.getDouble(tag_latitud),
						fastracksJSON.getDouble(tag_longitud), fastracksJSON
								.getJSONObject(tag_idEquipo).getString(
										tag_regMunicipalVehiculo),
						fastracksJSON.getJSONObject(tag_idEquipo).getString(
								tag_placaVehiculo),
						fastracksJSON.getDouble(tag_velocidad),
						fastracksJSON.getString(tag_fechaHoraUltDato));
				listaVehiculoGps.add(vehiculoGps);
			}
			return listaVehiculoGps;
		} catch (Exception ex) {
			Log.e("ServicioRest", "Error!", ex);
			return null;
		}
	}
}
