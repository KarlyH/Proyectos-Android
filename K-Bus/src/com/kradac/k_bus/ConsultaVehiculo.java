package com.kradac.k_bus;

import com.kradac.k_bus.controlador.ConexionController;
import com.kradac.k_bus.controlador.VehiculoController;
import com.kradac.k_bus.modelo.Vehiculo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ConsultaVehiculo extends Activity {
	private Context mycontext;
	private RadioGroup opciones;
	private RadioButton registro;
	private RadioButton placa;
	private EditText buscar;
	private Button btnConsultar;
	private int seleccionado;
	private Vehiculo informacionVehiculo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consulta_bus);
		mycontext = this;
		opciones = (RadioGroup) findViewById(R.id.radioGroup1);
		registro = (RadioButton) findViewById(R.id.rbRegistro);
		placa = (RadioButton) findViewById(R.id.rbPlaca);
		buscar = (EditText) findViewById(R.id.txtBuscar);
		btnConsultar = (Button) findViewById(R.id.btnAceptarBuses);
		btnConsultar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!buscar.getText().toString().equals("")) {
					new BuscarVehiculo().execute();
				} else {
					Toast.makeText(getApplicationContext(),
							"No hay ningun dato a buscar", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});
		buscar.setInputType(InputType.TYPE_CLASS_NUMBER);
		registro.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buscar.setHint("Ej. 1170");
				buscar.setInputType(InputType.TYPE_CLASS_NUMBER);
				buscar.setText("");

			}
		});

		placa.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				buscar.setHint("Ej. LOJ01234");
				buscar.setInputType(InputType.TYPE_CLASS_TEXT);
				buscar.setText("");
			}
		});
	}

	class BuscarVehiculo extends AsyncTask<Void, Void, Void> {

		private ProgressDialog pDialog;
		private Boolean isOnline;
		private String server;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(mycontext);
			pDialog.setTitle("Buscando...");
			pDialog.setMessage("Espere por favor.");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected Void doInBackground(Void... arg0) {
			isOnline = new ConexionController(mycontext)
					.isOnline("http://www.google.com");
			server = "http://190.12.61.30:5801";
			
			seleccionado = opciones.getCheckedRadioButtonId();
			switch (seleccionado) {
			case R.id.rbRegistro:
				informacionVehiculo = new VehiculoController()
						.consultaPorRegMunicipal(server, buscar.getText()
								.toString());
				break;
			case R.id.rbPlaca:
				informacionVehiculo = new VehiculoController()
						.consultaPorPlaca(server, buscar.getText().toString());
				break;
			}
			if (informacionVehiculo == null) {
				server = "http://200.0.29.117";
		
				seleccionado = opciones.getCheckedRadioButtonId();
				switch (seleccionado) {
				case R.id.rbRegistro:
					informacionVehiculo = new VehiculoController()
							.consultaPorRegMunicipal(server, buscar.getText()
									.toString());
					break;
				case R.id.rbPlaca:
					informacionVehiculo = new VehiculoController()
							.consultaPorPlaca(server, buscar.getText()
									.toString());
					break;
				}
			}
			return null;
		}

		protected void onPostExecute(Void arg1) {
			// dismiss the dialog once done
			pDialog.dismiss();
			if (isOnline) {
				if (informacionVehiculo == null) {
					Toast.makeText(
							mycontext,
							"Los datos ingresados no generaron ninguna busqueda intente de nuevo por favor.",
							Toast.LENGTH_LONG).show();
				} else {
					mostrarDatos();
				}

			} else {
				Toast.makeText(getApplicationContext(),
						"Problemas con su conexión a internet",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	public void mostrarDatos() {
		Intent i = new Intent(mycontext, InformacionBus.class);
		i.putExtra("propietario", informacionVehiculo.getNombrePropietario()
				+ " " + informacionVehiculo.getApellidoPropietario());
		i.putExtra("operadora", informacionVehiculo.getNombreEmpresa());
		i.putExtra("regMunicipal",
				informacionVehiculo.getRegMunicipalVehiculo());
		i.putExtra("placa", informacionVehiculo.getPlacaVehiculo());
		startActivity(i);
	}
}
