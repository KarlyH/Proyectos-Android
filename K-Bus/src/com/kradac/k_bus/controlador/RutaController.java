package com.kradac.k_bus.controlador;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.kradac.k_bus.modelo.Ruta;

import android.util.Log;

public class RutaController {

	private static final String url_Rutas = "/K-Bus/webresources/com.kradac.kbus.rest.entities.rutas";
	private static final String tag_CodRuta = "codRuta";
	private static final String tag_Color = "color";
	private static final String tag_Icono = "icono";
	private static final String tag_IdRuta = "idRuta";
	private static final String tag_Linea = "linea";
	private static final String tag_Ruta = "ruta";
	private static final String tag_TipoSancion = "tiempoSancion";

	public ArrayList<Ruta> listarRutas(String server) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet del = new HttpGet(server + url_Rutas);
		del.setHeader("content-type", "application/json");
		try {
			HttpResponse resp = httpClient.execute(del);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONArray respJSON = new JSONArray(respStr);
			ArrayList<Ruta> listaRutas = new ArrayList<Ruta>();
			for (int i = 1; i < respJSON.length(); i++) {
				JSONObject rutaJSON = respJSON.getJSONObject(i);
				Ruta ruta = new Ruta(rutaJSON.getString(tag_CodRuta),
						rutaJSON.getString(tag_Color),
						rutaJSON.getString(tag_Icono),
						rutaJSON.getInt(tag_IdRuta),
						rutaJSON.getInt(tag_Linea),
						rutaJSON.getString(tag_Ruta),
						rutaJSON.getString(tag_TipoSancion));
				listaRutas.add(ruta);
			}
			return listaRutas;
		} catch (Exception ex) {
			Log.e("ServicioRest", "Error!", ex);
			return null;
		}
	}
}
