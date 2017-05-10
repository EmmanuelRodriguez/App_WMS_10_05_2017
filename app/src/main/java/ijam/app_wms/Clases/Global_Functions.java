package ijam.app_wms.Clases;

import android.app.Application;

/**
 * Created by alberto.ramirez on 16/02/2017.
 */
public class Global_Functions extends Application {
    private String sNameDB="IJAM_WMS.sqlite" ;

    public int getiVersion() {
        return iVersion;
    }

    public void setiVersion(int iVersion) {
        this.iVersion = iVersion;
    }

    public String getsNameDB() {
        return sNameDB;
    }

    public void setsNameDB(String sNameDB) {
        this.sNameDB = sNameDB;
    }

    private int iVersion=1;


}
