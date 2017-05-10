//Menú principal (Inventario, Compras y Salir)


package ijam.app_wms.Activityes;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import ijam.app_wms.Adapters.adpMenu;
import ijam.app_wms.R;

public class frmMenu extends AppCompatActivity {
    Button btninventario;
    Button btnventas;
    Button btncompras;
    Button btnsalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_menu);
        btninventario = (Button) findViewById(R.id.btnInventario);
       // btnventas = (Button) findViewById(R.id.btnVentas);
        btncompras = (Button) findViewById(R.id.btnCompras);
        btnsalir = (Button) findViewById(R.id.btnSalir);
        Toolbar oToolbar = (Toolbar) findViewById(R.id.barra);
        setSupportActionBar(oToolbar);

        btninventario.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent menuInventario = new Intent(frmMenu.this, frmMenuInv.class);
                    startActivity(menuInventario);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

/*
        btnventas.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent menuVentas = new Intent(frmMenu.this, frmMenuVnt.class);
                    startActivity(menuVentas);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
*/

        btncompras.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent menuCompras = new Intent(frmMenu.this, frmMenuCmp.class);
                    startActivity(menuCompras);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        btnsalir.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(frmMenu.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Salir")
                        .setMessage("¿Desea cerrar sesión?")
                        .setPositiveButton("Si", new DialogInterface.OnClickListener()
                        {
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Intent mainActivity = new Intent(frmMenu.this,MainActivity.class);
                                startActivity(mainActivity);
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        //replaces the default 'Back' button action
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            //do whatever you want the 'Back' button to do
            //as an example the 'Back' button is set to start a new Activity named 'NewActivity'
            this.startActivity(new Intent(frmMenu.this,MainActivity.class));
        }
        return true;
    }
}
