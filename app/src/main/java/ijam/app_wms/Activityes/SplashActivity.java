//Pantalla imagen almacén


package ijam.app_wms.Activityes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import ijam.app_wms.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        Thread oThread = new Thread()
        {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    Intent oIntent = new Intent(getApplicationContext(), MainActivity.class);
                    //Intent oIntent = new Intent(getApplicationContext(), frmLst_CntOnLines.class);
                    startActivity(oIntent);
                    finish();
                }
            }
        };
        oThread.start();
    }
}
