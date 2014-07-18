package com.kradac.k_bus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Principal extends Activity {
	private Context mycontext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);

		mycontext = this;

		Button btnRutas, btnInformacion, btnParadas, btnCompartir, btnDenuncias, btnEncuestas;

		btnRutas = (Button) findViewById(R.id.btnMapaRutas);
		btnInformacion = (Button) findViewById(R.id.btnInformacionBus);
		btnParadas = (Button) findViewById(R.id.btnMapaParadas);
		btnCompartir = (Button) findViewById(R.id.btnCompartir);
		btnDenuncias = (Button) findViewById(R.id.btnDenuncia);
		btnEncuestas = (Button) findViewById(R.id.btnEncuesta);
		
		btnRutas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Principal.this, MapaRutas.class));
			}
		});

		btnInformacion.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Principal.this, ConsultaVehiculo.class));
			}
		});

		btnParadas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Principal.this, MapaParadas.class));
			}
		});

		btnCompartir.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				compartirApp();
			}
		});
		btnEncuestas.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Principal.this, Encuestas.class));
			}
		});
		btnDenuncias.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(Principal.this, Denuncias.class));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.MnuItmAcerca:
			mnsjAcerca();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			showConfirmExit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void compartirApp() {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "K-Bus");
		sharingIntent
				.putExtra(
						Intent.EXTRA_TEXT,
						"Descargue la aplicación móvil K-Bus desde el Google Play - https://play.google.com/ ");
		startActivity(Intent.createChooser(sharingIntent, "Compartir via"));
	}

	public void showConfirmExit() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mycontext);

		// Setting Dialog Title
		alertDialog.setTitle("Mensaje");
		alertDialog.setIcon(R.drawable.ic_kbus);
		// Setting Dialog Message
		alertDialog
				.setMessage("¿Esta seguro que desea Salir de la Aplicación?");

		// On pressing Settings button
		alertDialog.setPositiveButton("Si",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});

		// on pressing cancel button
		alertDialog.setNegativeButton("No",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

	public void mnsjAcerca() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mycontext);
		alertDialog.setIcon(R.drawable.ic_kbus);
		// Setting Dialog Title
		alertDialog.setTitle("Acerca de");

		// Setting Dialog Message
		alertDialog
				.setMessage("Bienvenidos a K-Bus una aplicación diseñada para visualizar las rutas de transporte publico, obtener la ubicación de los buses en tiempo real e información sobre horarios y las rutas de cada uno de los buses. Gracias por confiar en nosotros. Somos KRADAC.CIA.LTDA. Todos los derechos reservados.");

		// On pressing Settings button
		alertDialog.setPositiveButton("Aceptar",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});

		// Showing Alert Message
		alertDialog.show();
	}

}
