package com.kradac.k_bus;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.kradac.k_bus.controlador.ConexionController;
import com.kradac.k_bus.controlador.LineaRutaController;
import com.kradac.k_bus.controlador.RutaController;
import com.kradac.k_bus.controlador.VehiculoGpsController;
import com.kradac.k_bus.modelo.LineaRuta;
import com.kradac.k_bus.modelo.Ruta;
import com.kradac.k_bus.modelo.VehiculoGPS;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MapaRutas extends FragmentActivity implements LocationListener {

	private Spinner cmbCiudades;
	private Spinner cmbRutas;
	private ArrayAdapter<String> adaptador_ruta;
	private ArrayAdapter<String> adaptador_ciudad;
	private String[] rutas;
	private int idRutaSeleccionada;
	private BitmapDescriptor markerBus;
	private Context mycontext;

	private GoogleMap mapaRutas;
	private String server;
	private Boolean isOnline;
	private Boolean isServerUp;
	private ArrayList<Ruta> listadoRutas;
	private ArrayList<VehiculoGPS> listadoVehiculosGps;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rutas);
		mycontext = this;
		// Getting Google Play availability status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		// Showing status
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else { // Google Play Services are available

			// Getting reference to the SupportMapFragment of activity_main.xml
			SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.mapaRutas);

			// Getting GoogleMap object from the fragment
			mapaRutas = fm.getMap();

			// Enabling MyLocation Layer of Google Map
			mapaRutas.setMyLocationEnabled(true);

			// Getting LocationManager object from System Service
			// LOCATION_SERVICE
			LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

			// Creating a criteria object to retrieve provider
			Criteria criteria = new Criteria();

			// Getting the name of the best provider
			String provider = locationManager.getBestProvider(criteria, true);

			// Getting Current Location
			Location location = locationManager.getLastKnownLocation(provider);

			if (location != null) {
				onLocationChanged(location);
			}

			locationManager.requestLocationUpdates(provider, 20000, 0, this);
			mapaRutas.setOnMarkerClickListener(new OnMarkerClickListener() {
				public boolean onMarkerClick(Marker marker) {
					if (!marker.getTitle().equalsIgnoreCase("inicio")
							&& !marker.getTitle().equalsIgnoreCase("fin")) {
						dialogoInformacion(marker.getTitle());
					}
					return false;
				}
			});
			// Cargar Datos Lista Desplegable
			cmbCiudades = (Spinner) findViewById(R.id.CmbCiudad);
			cmbRutas = (Spinner) findViewById(R.id.CmbRuta);

			final String[] datos = new String[] { "Loja", "Zamora" };

			adaptador_ciudad = new ArrayAdapter<String>(mycontext,
					android.R.layout.simple_spinner_item, datos);

			adaptador_ciudad
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

			cmbCiudades.setAdapter(adaptador_ciudad);

			cmbCiudades
					.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						public void onItemSelected(AdapterView<?> parent,
								android.view.View v, int position, long id) {
							if (position == 0) {
								zoomMapa(-3.983535, -79.201598, 12);
								server = "http://190.12.61.30:5801";
								new ListarRutas().execute();
							} else if (position == 1) {
								zoomMapa(-4.062981, -78.951745, 14);
								server = "http://200.0.29.117";
								new ListarRutas().execute();
							}
						}

						public void onNothingSelected(AdapterView<?> parent) {
							// lblMensaje.setText("");
						}
					});
			// actualizarRutas(rutasLoja);
			cmbRutas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				public void onItemSelected(AdapterView<?> parent,
						android.view.View v, int position, long id) {
					mapaRutas.clear();
					idRutaSeleccionada = position + 2;
					selectMarcador();
					new ListarVehiculoGps().execute();
				}

				public void onNothingSelected(AdapterView<?> parent) {
					// lblMensaje.setText("");
				}
			});
		}
	}

	public void selectMarcador() {
		for (int i = 0; i < listadoRutas.size(); i++) {
			if (idRutaSeleccionada == listadoRutas.get(i).getIdRuta()) {
				String marcador = listadoRutas.get(i).getIcono();
				if (marcador.equalsIgnoreCase("bus_amarillo.png")) {
					markerBus = BitmapDescriptorFactory
							.fromResource(R.drawable.bus_amarillo);
				} else if (marcador.equalsIgnoreCase("bus_azul_claro.png")) {
					markerBus = BitmapDescriptorFactory
							.fromResource(R.drawable.bus_azul_claro);
				} else if (marcador.equalsIgnoreCase("bus_azul.png")) {
					markerBus = BitmapDescriptorFactory
							.fromResource(R.drawable.bus_azul);
				} else if (marcador.equalsIgnoreCase("bus_cafe.png")) {
					markerBus = BitmapDescriptorFactory
							.fromResource(R.drawable.bus_cafe);
				} else if (marcador.equalsIgnoreCase("bus_celeste.png")) {
					markerBus = BitmapDescriptorFactory
							.fromResource(R.drawable.bus_celeste);
				} else if (marcador.equalsIgnoreCase("bus_gris.png")) {
					markerBus = BitmapDescriptorFactory
							.fromResource(R.drawable.bus_gris);
				} else if (marcador.equalsIgnoreCase("bus_morado.png")) {
					markerBus = BitmapDescriptorFactory
							.fromResource(R.drawable.bus_morado);
				} else if (marcador.equalsIgnoreCase("bus_naranja.png")) {
					markerBus = BitmapDescriptorFactory
							.fromResource(R.drawable.bus_naranja);
				} else if (marcador.equalsIgnoreCase("bus_rojo.png")) {
					markerBus = BitmapDescriptorFactory
							.fromResource(R.drawable.bus_rojo);
				} else if (marcador.equalsIgnoreCase("bus_rosa_claro.png")) {
					markerBus = BitmapDescriptorFactory
							.fromResource(R.drawable.bus_rosa_claro);
				} else if (marcador.equalsIgnoreCase("bus_rosa.png")) {
					markerBus = BitmapDescriptorFactory
							.fromResource(R.drawable.bus_rosa);
				} else if (marcador.equalsIgnoreCase("bus_verde_osc.png")) {
					markerBus = BitmapDescriptorFactory
							.fromResource(R.drawable.bus_verde_osc);
				} else if (marcador.equalsIgnoreCase("bus_verde.png")) {
					markerBus = BitmapDescriptorFactory
							.fromResource(R.drawable.bus_verde);
				}
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.mapa, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.MnuItmTrazarRuta:
			new TrazarRuta().execute();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void actualizarRutas(String[] data) {
		adaptador_ruta = new ArrayAdapter<String>(mycontext,
				android.R.layout.simple_spinner_item, data);

		adaptador_ruta
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cmbRutas.setAdapter(adaptador_ruta);
	}

	public void zoomMapa(double latitud, double longitud, int zoom) {

		LatLng latLng = new LatLng(latitud, longitud);

		// Showing the current location in Google Map
		mapaRutas.moveCamera(CameraUpdateFactory.newLatLng(latLng));

		// Zoom in the Google Map
		mapaRutas.animateCamera(CameraUpdateFactory.zoomTo(zoom));
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	class ListarRutas extends AsyncTask<Void, Void, Void> {
		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(mycontext);
			pDialog.setTitle("Cargando Rutas...");
			pDialog.setMessage("Espere por favor.");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected Void doInBackground(Void... arg0) {
			isOnline = new ConexionController(mycontext)
					.isOnline("http://www.google.com");
			isServerUp = new ConexionController(mycontext).isOnline(server);
			if (isOnline) {
				if (isServerUp) {
					listadoRutas = new RutaController().listarRutas(server);
					rutas = new String[listadoRutas.size()];
					for (int i = 0; i < listadoRutas.size(); i++) {
						rutas[i] = "L" + listadoRutas.get(i).getLinea() + ": "
								+ listadoRutas.get(i).getCodRuta() + ": "
								+ listadoRutas.get(i).getRuta();
					}
				}
			}
			return null;
		}

		protected void onPostExecute(Void arg1) {
			pDialog.dismiss();
			if (isOnline) {
				if (isServerUp) {
					actualizarRutas(rutas);
				} else {
					zoomMapa(-3.983535, -79.201598, 12);
					rutas = new String[] {};
					actualizarRutas(rutas);
					Toast.makeText(getApplicationContext(),
							"Problemas con el servidor, intentelo mas tarde..",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getApplicationContext(),
						"Problemas con su conexión a internet",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	class ListarVehiculoGps extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected Void doInBackground(Void... arg0) {
			listadoVehiculosGps = new VehiculoGpsController().listarSkyPatrol(
					server, idRutaSeleccionada);
			return null;
		}

		protected void onPostExecute(Void arg1) {
			if (listadoVehiculosGps.size() == 0) {
				Toast.makeText(
						mycontext,
						"Actualmente no se encuentra ningún vehículo en circulación.",
						Toast.LENGTH_SHORT).show();
			} else {
				for (int i = 0; i < listadoVehiculosGps.size(); i++) {
					actualizarMarcador(new LatLng(listadoVehiculosGps.get(i)
							.getLatitud(), listadoVehiculosGps.get(i)
							.getLongitud()), ""
							+ listadoVehiculosGps.get(i).getRegMunicipal()
							+ "," + listadoVehiculosGps.get(i).getPlaca(),
							markerBus);
				}
			}
		}
	}

	class TrazarRuta extends AsyncTask<Void, Void, Void> {

		private ProgressDialog pDialog;
		private ArrayList<LineaRuta> listadoLineaRuta;
		private PolylineOptions lineOptions;
		private String color;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(mycontext);
			pDialog.setTitle("Graficando Ruta...");
			pDialog.setMessage("Espere por favor.");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
			lineOptions = new PolylineOptions();
		}

		protected Void doInBackground(Void... arg0) {
			isOnline = new ConexionController(mycontext)
					.isOnline("http://www.google.com");
			isServerUp = new ConexionController(mycontext).isOnline(server);
			if (isOnline) {
				if (isServerUp) {
					listadoLineaRuta = new LineaRutaController()
							.listarLineaRuta(server, idRutaSeleccionada);

					for (int i = 0; i < listadoLineaRuta.size(); i++) {
						double lat = listadoLineaRuta.get(i).getLatitud();
						double lng = listadoLineaRuta.get(i).getLongitud();
						LatLng position = new LatLng(lat, lng);
						lineOptions.add(position);
					}

					for (int i = 0; i < listadoRutas.size(); i++) {
						if (idRutaSeleccionada == listadoRutas.get(i)
								.getIdRuta()) {
							color = listadoRutas.get(i).getColor();
						}
					}
					lineOptions.width(5);
					lineOptions.color(Color.parseColor(color));
				}
			}
			return null;
		}

		protected void onPostExecute(Void arg1) {
			if (isOnline) {
				if (isServerUp) {
					if (listadoLineaRuta.size() != 0) {
						actualizarMarcador(new LatLng(listadoLineaRuta.get(0)
								.getLatitud(), listadoLineaRuta.get(0)
								.getLongitud()), "Inicio",
								BitmapDescriptorFactory
										.fromResource(R.drawable.inicio_ruta));
						mapaRutas.addPolyline(lineOptions);
						actualizarMarcador(
								new LatLng(listadoLineaRuta.get(
										listadoLineaRuta.size() - 1)
										.getLatitud(), listadoLineaRuta.get(
										listadoLineaRuta.size() - 1)
										.getLongitud()), "Fin",
								BitmapDescriptorFactory
										.fromResource(R.drawable.fin_ruta));
					} else {
						Toast.makeText(getApplicationContext(),
								"Lo sentimos datos no encontrados...",
								Toast.LENGTH_SHORT).show();
					}

				} else {
					Toast.makeText(getApplicationContext(),
							"Problemas con el servidor, intentelo mas tarde..",
							Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getApplicationContext(),
						"Problemas con su conexión a internet",
						Toast.LENGTH_SHORT).show();
			}
			pDialog.dismiss();
		}
	}

	public void actualizarMarcador(LatLng ubicacion, String titulo,
			BitmapDescriptor marker) {
		mapaRutas.addMarker(new MarkerOptions().position(ubicacion)
				.icon(marker).title(titulo));
	}

	public void dialogoInformacion(String titulo) {
		AlertDialog.Builder aDialogo = new AlertDialog.Builder(this);
		aDialogo.setIcon(R.drawable.ic_kbus);
		aDialogo.setTitle("Información");
		String registro = titulo.split(",")[0];
		String placa = titulo.split(",")[1];
		Double velocidad = null;
		String fechaUltimoDato = null;
		for (int i = 0; i < listadoVehiculosGps.size(); i++) {
			if (placa.equalsIgnoreCase(listadoVehiculosGps.get(i).getPlaca())) {
				velocidad = listadoVehiculosGps.get(i).getVelocidad();
				fechaUltimoDato = listadoVehiculosGps.get(i)
						.getFechaHoraUltDato();
			}
		}
		String mensaje = "Registro: " + registro + "\nPlaca: " + placa
				+ "\nVelocidad: " + velocidad + "\nFecha y Hora: "
				+ fechaUltimoDato.replaceAll("T", " ");
		// + fechaUltimoDato;
		aDialogo.setMessage(mensaje);
		aDialogo.setPositiveButton("Aceptar", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		aDialogo.show();
	}
}