package com.kradac.k_bus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		// Vamos a declarar un nuevo thread
		Thread timer = new Thread() {
			// El nuevo Thread exige el metodo run
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException e) {
					// Si no puedo ejecutar el sleep muestro el error
					e.printStackTrace();
				} finally {
					Intent i = new Intent(Splash.this, Principal.class);
					startActivity(i);
				}
			}
		};
		// ejecuto el thread
		timer.start();
	}
}
