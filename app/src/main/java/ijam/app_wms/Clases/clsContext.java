package ijam.app_wms.Clases;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ijam.app_wms.SQLite.AdminSQLite;
import ijam.app_wms.Clases.Global_Functions;
import ijam.app_wms.SQLite.clsOCFG;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by alberto.ramirez on 16/02/2017.
 */
public class clsContext {
    private String sUser;
    private String sPwd;
    private String sBD;
    private String sMsg_Error;
    private int iDocEntry;

    public String getsBD() {
        return sBD;
    }

    public void setsBD(String sBD) {
        this.sBD = sBD;
    }

    public String getsMsg_Error() {
        return sMsg_Error;
    }

    public void setsMsg_Error(String sMsg_Error) {
        this.sMsg_Error = sMsg_Error;
    }

    public String getsPwd() {
        return sPwd;
    }

    public void setsPwd(String sPwd) {
        this.sPwd = sPwd;
    }

    public String getsUser() {
        return sUser;
    }

    public void setsUser(String sUser) {
        this.sUser = sUser;
    }

    public int getiDocEntry() {
        return iDocEntry;
    }

    public void setiDocEntry(int iDocEntry) {
        this.iDocEntry = iDocEntry;
    }

    /* public clsContext( String sUser, String sPwd) {
            this.sBD = sBD;
            this.sMsg_Error = sMsg_Error;
            this.sPwd = sPwd;
            this.sUser = sUser;
        }*/
   public clsContext(){}

    public  boolean  Login_WS(clsOCFG oConfiguracionW ) {
        boolean bRep = false;
        SoapPrimitive oSOAP_WS=null;
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        String sMETHOD_NAME="Login";

        try{
                oResquest = new SoapObject( oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
                oResquest.addProperty("User", getsUser().toString());
                oResquest.addProperty("Pwd", getsPwd().toString());
                oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
                oEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                oEnvelope.dotNet=true;
                oEnvelope.setOutputSoapObject(oResquest);
                HttpTransportSE  oTransporter = new HttpTransportSE(oConfiguracionW.getsURL());
                try {
                    oTransporter.call(oConfiguracionW.getsSOAP_ACTION()+ sMETHOD_NAME,oEnvelope );
                    oSOAP_WS= (SoapPrimitive) oEnvelope.getResponse();

                    try {
                        setiDocEntry(Integer.parseInt(oSOAP_WS.toString()));
                        bRep = true;
                    }catch (Exception ex){
                        setsMsg_Error(oSOAP_WS.toString());
                        bRep = false;
                    }

                } catch (IOException ex) {
                   throw new Exception( ex.getMessage());
                }catch (XmlPullParserException e) {
                    throw new Exception( e.getMessage());
                }

        }catch (Exception ex){
            setsMsg_Error(ex.getMessage());
        }finally {
            oSOAP_WS = null;
            oEnvelope = null;
            oResquest= null;
        }

        return bRep;
    }

}
