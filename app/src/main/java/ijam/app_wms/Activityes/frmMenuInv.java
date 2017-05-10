//Menú inventario recuentro, traslados


package ijam.app_wms.Activityes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import ijam.app_wms.R;

/**
 * Created by alberto.ramirez on 29/03/2017.
 */
public class frmMenuInv extends AppCompatActivity {
    Button btnrecuento;
    Button btntraslados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_menu_inv);
        Toolbar oToolbar = (Toolbar) findViewById(R.id.barra);
        setSupportActionBar(oToolbar);
        ActionBar oActionBar = getSupportActionBar(); // Objeto para instanciar el menu simple
        oActionBar.setHomeButtonEnabled(true); // Activa en el toolbar la opcion de regresar
        oActionBar.setDisplayHomeAsUpEnabled(true);


        btnrecuento = (Button) findViewById(R.id.btnRecuento);
        btntraslados = (Button) findViewById(R.id.btnTraslados);

        btnrecuento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent oConteoOnline = new Intent(frmMenuInv.this, frmConteoOnline.class);
                    startActivity(oConteoOnline);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btntraslados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent oTraslados = new Intent(frmMenuInv.this, frmTransfer.class);
                    startActivity(oTraslados);
                    finish();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int iId = item.getItemId();
        if (iId == android.R.id.home) { // Valida la opcion seleccionada en el menu y  si es el boton regreso este ira al login
            Intent oIntent = new Intent(getApplicationContext(), frmMenu.class);
            startActivity(oIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            this.startActivity(new Intent(frmMenuInv.this,frmMenu.class));
        }
        return true;
    }



}

