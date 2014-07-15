package com.kradac.k_bus.controlador;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.util.Log;

import com.kradac.k_bus.modelo.LineaRuta;

public class LineaRutaController {
	private static final String url_lineaRuta = "/K-Bus/webresources/com.kradac.kbus.rest.entities.linearutas/ruta=";
	private static final String tag_latitud = "latitud";
	private static final String tag_longitud = "longitud";

	public ArrayList<LineaRuta> listarLineaRuta(String urlServer, int idRuta) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet del = new HttpGet(urlServer + url_lineaRuta + idRuta);
		del.setHeader("content-type", "application/json");
		try {
			HttpResponse resp = httpClient.execute(del);
			String respStr = EntityUtils.toString(resp.getEntity());
			JSONArray respJSON = new JSONArray(respStr);
			ArrayList<LineaRuta> listasLineaRuta = new ArrayList<LineaRuta>();
			for (int i = 0; i < respJSON.length(); i++) {
				JSONObject lineaRutalJSON = respJSON.getJSONObject(i);
				LineaRuta lineaRuta = new LineaRuta(
						lineaRutalJSON.getDouble(tag_latitud),
						lineaRutalJSON.getDouble(tag_longitud));
				listasLineaRuta.add(lineaRuta);
			}
			return listasLineaRuta;
		} catch (Exception ex) {
			Log.e("ServicioRest", "Error!", ex);
			return null;
		}
	}
}
