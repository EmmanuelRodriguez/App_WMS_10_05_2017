//Compras, botón ENTRADAS


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
public class frmMenuCmp extends AppCompatActivity {
    Button btnentradas;
    //Button btntraslados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_menu_cmp);
        Toolbar oToolbar = (Toolbar) findViewById(R.id.barra);
        setSupportActionBar(oToolbar);
        ActionBar oActionBar = getSupportActionBar(); // Objeto para instanciar el menu simple
        oActionBar.setHomeButtonEnabled(true); // Activa en el toolbar la opcion de regresar
        oActionBar.setDisplayHomeAsUpEnabled(true);


        btnentradas = (Button) findViewById(R.id.btnEntradas);
        //btntraslados = (Button) findViewById(R.id.btnTraslados);

        btnentradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent oConteoOnline = new Intent(frmMenuCmp.this, frmEntradas.class);
                    startActivity(oConteoOnline);
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
            this.startActivity(new Intent(frmMenuCmp.this,frmMenu.class));
        }
        return true;
    }



}

