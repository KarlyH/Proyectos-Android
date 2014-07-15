package com.kradac.k_bus;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class InformacionBus extends Activity {

	private Bundle bExtras;
	private TextView txtPropietario;
	private TextView txtOperadora;
	private TextView txtRegMunicipal;
	private TextView txtPlaca;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_informacion_bus);
		bExtras = getIntent().getExtras();

		txtPropietario = (TextView) findViewById(R.id.tvPropietario);
		txtPropietario.setText(bExtras.getString("propietario"));

		txtOperadora = (TextView) findViewById(R.id.tvOperadora);
		txtOperadora.setText(bExtras.getString("operadora"));

		txtRegMunicipal = (TextView) findViewById(R.id.tvRegistroMunicipal);
		txtRegMunicipal.setText(bExtras.getString("regMunicipal"));

		txtPlaca = (TextView) findViewById(R.id.tvPlaca);
		txtPlaca.setText(bExtras.getString("placa"));
	}
}
