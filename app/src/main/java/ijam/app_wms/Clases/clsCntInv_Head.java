package ijam.app_wms.Clases;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ijam.app_wms.SQLite.clsOCFG;

/**
 * Created by alberto.ramirez on 17/02/2017.
 */
public class clsCntInv_Head {
    private String sDocEntry;
    private String sAbsEntry;
    private String sDocNum;
    private String sDocDate;
    private String sHora;
    private String sComentarios;
    private String Serie;
    private String sStatus;
    private String sCountType;
    private String sRef2;
    private String sRemarks;
    private String sTaker1Type;
    private String sTaker1Id;
    private String sUserSign;
    private String sUpdateDate;
    private String sUpdateTime;
    private String sCardCode;
    private String sMsgError;

    public clsCntInv_Head() {
    }

    public String getsCardCode() {
        return sCardCode;
    }

    public void setsCardCode(String sCardCode) {
        this.sCardCode = sCardCode;
    }

    public String getsAbsEntry() {
        return sAbsEntry;
    }

    public void setsAbsEntry(String sAbsEntry) {
        this.sAbsEntry = sAbsEntry;
    }

    public String getsMsgError() {
        return sMsgError;
    }

    public void setsMsgError(String sMsgError) {
        this.sMsgError = sMsgError;
    }

    public String getsTaker1Id() {
        return sTaker1Id;
    }

    public void setsTaker1Id(String sTaker1Id) {
        this.sTaker1Id = sTaker1Id;
    }

    public String getsUserSign() {
        return sUserSign;
    }

    public void setsUserSign(String sUserSign) {
        this.sUserSign = sUserSign;
    }

    public String getsStatus() {
        return sStatus;
    }

    public void setsStatus(String sStatus) {
        this.sStatus = sStatus;
    }

    public String getsRemarks() {
        return sRemarks;
    }

    public void setsRemarks(String sRemarks) {
        this.sRemarks = sRemarks;
    }

    public String getSerie() {
        return Serie;
    }

    public void setSerie(String serie) {
        Serie = serie;
    }

    public String getsDocNum() {
        return sDocNum;
    }

    public void setsDocNum(String sDocNum) {
        this.sDocNum = sDocNum;
    }

    public String getsDocEntry() {
        return sDocEntry;
    }

    public void setsDocEntry(String sDocEntry) {
        this.sDocEntry = sDocEntry;
    }

    public String getsComentarios() {
        return sComentarios;
    }

    public void setsComentarios(String sComentarios) {
        this.sComentarios = sComentarios;
    }

    public String getsRef2() {
        return sRef2;
    }

    public void setsRef2(String sRef2) {
        this.sRef2 = sRef2;
    }

    public String getsCountType() {
        return sCountType;
    }

    public void setsCountType(String sCountType) {
        this.sCountType = sCountType;
    }

    public String getsDocDate() {
        return sDocDate;
    }

    public void setsDocDate(String sDocDate) {
        this.sDocDate = sDocDate;
    }

    public String getsHora() {
        return sHora;
    }

    public void setsHora(String sHora) {
        this.sHora = sHora;
    }

    public String getsTaker1Type() {
        return sTaker1Type;
    }

    public void setsTaker1Type(String sTaker1Type) {
        this.sTaker1Type = sTaker1Type;
    }

    public String getsUpdateDate() {
        return sUpdateDate;
    }

    public void setsUpdateDate(String sUpdateDate) {
        this.sUpdateDate = sUpdateDate;
    }

    public String getsUpdateTime() {
        return sUpdateTime;
    }

    public void setsUpdateTime(String sUpdateTime) {
        this.sUpdateTime = sUpdateTime;
    }

    public boolean Add_DocumentoCntOnline(clsOCFG oConfiguracionW ) {
        boolean bResp =false;
        String sMETHOD_NAME="Add_Rec_Online_Cab";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapPrimitive  oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("User", oConfiguracionW.getsUsuario());
            oResquest.addProperty("Pwd", oConfiguracionW.getsPassword());
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("Ref2", getsRef2());
            oResquest.addProperty("DocDate", getsDocDate());
            oResquest.addProperty("Hora",getsHora());
            oResquest.addProperty("Comentarios",getsComentarios());

            oEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            oEnvelope.dotNet=true;
            oEnvelope.setOutputSoapObject(oResquest);
            HttpTransportSE oTransporter = new HttpTransportSE(oConfiguracionW.getsURL());
            try {
                oTransporter.call(oConfiguracionW.getsSOAP_ACTION()+ sMETHOD_NAME,oEnvelope );
                oResponse_WS= (SoapPrimitive) oEnvelope.getResponse();
                setsMsgError(oResponse_WS.toString());
                if (Integer.parseInt(oResponse_WS.toString())>0) {
                    setsDocEntry(oResponse_WS.toString());
                    bResp = true;
                }else{
                    bResp= false;
                }
            } catch (IOException ex) {
                throw new Exception( ex.getMessage());
            }catch (XmlPullParserException e) {
                throw new Exception( e.getMessage());
            }

        }catch (Exception ex){
            setsMsgError(ex.getMessage());
        }finally{
            oResponse_WS= null;
            oEnvelope = null;
            oResquest= null;
        }

        return bResp;
    }

    public ArrayList<clsCntInv_Head> Reupera_CntIn_Cab(clsOCFG oConfiguracionW ) {
        ArrayList<clsCntInv_Head> oCabeceras = new ArrayList<clsCntInv_Head>();
       // String sMETHOD_NAME="Recupera_Rec_Online_Cab";
        String sMETHOD_NAME="Recupera_Cnt_Cab";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapObject  oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("User", oConfiguracionW.getsUsuario());
            oResquest.addProperty("Pwd", oConfiguracionW.getsPassword());
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("IdEmp", oConfiguracionW.getsCodeUser());
            oEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            oEnvelope.dotNet=true;
            oEnvelope.setOutputSoapObject(oResquest);
            HttpTransportSE oTransporter = new HttpTransportSE(oConfiguracionW.getsURL());
            try {
                oTransporter.call(oConfiguracionW.getsSOAP_ACTION()+ sMETHOD_NAME,oEnvelope );
                oResponse_WS= (SoapObject) oEnvelope.getResponse();

                for (int i=0; i< oResponse_WS.getPropertyCount();i++){
                    SoapObject ic =(SoapObject)oResponse_WS.getProperty(i);
                    clsCntInv_Head oDoc = new clsCntInv_Head();
                    oDoc.setsDocEntry(ic.getProperty("DocEntry").toString());
                    oDoc.setsDocNum(ic.getProperty("DocNum").toString());
                    oDoc.setsDocDate( ic.getProperty("DocDate").toString());
                    oDoc.setsHora(ic.getProperty("Hora").toString());
                    oDoc.setsCountType(ic.getProperty("Tipo").toString());
                    oDoc.setsComentarios(ic.getProperty("Comentarios").toString());
                    oCabeceras.add(oDoc);
                }

            } catch (IOException ex) {
                throw new Exception( ex.getMessage());
            }catch (XmlPullParserException e) {
                throw new Exception( e.getMessage());
            }

        }catch (Exception ex){
            setsMsgError(ex.getMessage());
        }finally{
            oResponse_WS= null;
            oEnvelope = null;
            oResquest= null;
        }

        return oCabeceras;
    }

    public boolean Close_DocumentoCntOnline(clsOCFG oConfiguracionW ) {
        boolean bResp =false;
        String sMETHOD_NAME="Close_Cnt_Cab";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapPrimitive  oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("User", oConfiguracionW.getsUsuario());
            oResquest.addProperty("Pwd", oConfiguracionW.getsPassword());
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("DocEntry", getsDocEntry());
            oResquest.addProperty("IdEmp", oConfiguracionW.getsCodeUser());


            oEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            oEnvelope.dotNet=true;
            oEnvelope.setOutputSoapObject(oResquest);
            HttpTransportSE oTransporter = new HttpTransportSE(oConfiguracionW.getsURL());
            try {
                oTransporter.call(oConfiguracionW.getsSOAP_ACTION()+ sMETHOD_NAME,oEnvelope );
                oResponse_WS= (SoapPrimitive) oEnvelope.getResponse();

                if (Integer.parseInt(oResponse_WS.toString())>0) {
                    setsDocEntry(oResponse_WS.toString());
                    bResp = true;
                }else{
                    setsMsgError(oResponse_WS.toString());
                    bResp= false;
                }
            } catch (IOException ex) {
                throw new Exception( ex.getMessage());
            }catch (XmlPullParserException e) {
                throw new Exception( e.getMessage());
            }

        }catch (Exception ex){
            setsMsgError(ex.getMessage());
        }finally{
            oResponse_WS= null;
            oEnvelope = null;
            oResquest= null;
        }

        return bResp;
    }

    public ArrayList<clsCntInv_Head> Reupera_Trnsfr_Cab(clsOCFG oConfiguracionW ) {
        ArrayList<clsCntInv_Head> oCabeceras = new ArrayList<clsCntInv_Head>();
        String sMETHOD_NAME="Recupera_Trlds_Cab";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapObject  oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("IdEmp", oConfiguracionW.getsCodeUser());
            oEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            oEnvelope.dotNet=true;
            oEnvelope.setOutputSoapObject(oResquest);
            HttpTransportSE oTransporter = new HttpTransportSE(oConfiguracionW.getsURL());
            try {
                oTransporter.call(oConfiguracionW.getsSOAP_ACTION()+ sMETHOD_NAME,oEnvelope );
                oResponse_WS= (SoapObject) oEnvelope.getResponse();

                for (int i=0; i< oResponse_WS.getPropertyCount();i++){
                    SoapObject ic =(SoapObject)oResponse_WS.getProperty(i);
                    clsCntInv_Head oDoc = new clsCntInv_Head();
                    oDoc.setsDocEntry(ic.getProperty("DocEntry").toString());
                    oDoc.setsAbsEntry(ic.getProperty("AbsEntry").toString()) ;
                    oDoc.setsDocNum(ic.getProperty("DocNum").toString());
                    oDoc.setsDocDate(ic.getProperty("DocDate").toString());
                    oDoc.setsCountType(ic.getProperty("Tipo").toString());
                    oDoc.setsComentarios(ic.getProperty("Comentarios").toString());
                    oDoc.setsCardCode(ic.getProperty("CardCode").toString()) ;
                    oCabeceras.add(oDoc);
                }

            } catch (IOException ex) {
                throw new Exception( ex.getMessage());
            }catch (XmlPullParserException e) {
                throw new Exception( e.getMessage());
            }

        }catch (Exception ex){
            setsMsgError(ex.getMessage());
        }finally{
            oResponse_WS= null;
            oEnvelope = null;
            oResquest= null;
        }

        return oCabeceras;
    }

    public boolean Close_Solicitud_Traslado(clsOCFG oConfiguracionW ) {
        boolean bResp =false;
        String sMETHOD_NAME="Genera_Traslado";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapPrimitive  oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("DocEntry", getsDocEntry());
            oResquest.addProperty("IdEmp", oConfiguracionW.getsCodeUser());

            oEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            oEnvelope.dotNet=true;
            oEnvelope.setOutputSoapObject(oResquest);
            HttpTransportSE oTransporter = new HttpTransportSE(oConfiguracionW.getsURL());
            try {
                oTransporter.call(oConfiguracionW.getsSOAP_ACTION()+ sMETHOD_NAME,oEnvelope );
                oResponse_WS= (SoapPrimitive) oEnvelope.getResponse();

                if (Integer.parseInt(oResponse_WS.toString())>0) {
                    setsDocEntry(oResponse_WS.toString());
                    bResp = true;
                }else{
                    setsMsgError(oResponse_WS.toString());
                    bResp= false;
                }
            } catch (IOException ex) {
                throw new Exception( ex.getMessage());
            }catch (XmlPullParserException e) {
                throw new Exception( e.getMessage());
            }

        }catch (Exception ex){
            setsMsgError(ex.getMessage());
        }finally{
            oResponse_WS= null;
            oEnvelope = null;
            oResquest= null;
        }

        return bResp;
    }

    public ArrayList<clsCntInv_Head> Reupera_Entrada_Cab(clsOCFG oConfiguracionW ) {
        ArrayList<clsCntInv_Head> oCabeceras = new ArrayList<clsCntInv_Head>();
        String sMETHOD_NAME="Recupera_Entrada_Cab";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest = null;
        SoapObject oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("IdEmp", oConfiguracionW.getsCodeUser());
            oEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            oEnvelope.dotNet=true;
            oEnvelope.setOutputSoapObject(oResquest);
            HttpTransportSE oTransporter = new HttpTransportSE(oConfiguracionW.getsURL());
            try {
                oTransporter.call(oConfiguracionW.getsSOAP_ACTION()+ sMETHOD_NAME,oEnvelope);
                oResponse_WS = (SoapObject) oEnvelope.getResponse();

                for (int i=0; i< oResponse_WS.getPropertyCount();i++){
                    SoapObject ic =(SoapObject)oResponse_WS.getProperty(i);
                    clsCntInv_Head oDoc = new clsCntInv_Head();
                    oDoc.setsDocEntry(ic.getProperty("DocEntry").toString());
                    oDoc.setsAbsEntry(ic.getProperty("AbsEntry").toString()) ;
                    oDoc.setsDocNum(ic.getProperty("DocNum").toString());
                    oDoc.setsDocDate(ic.getProperty("DocDate").toString());
                    oDoc.setsCountType(ic.getProperty("Tipo").toString());
                    oDoc.setsComentarios(ic.getProperty("Comentarios").toString());
                    oDoc.setsCardCode(ic.getProperty("CardCode").toString()) ;
                    oCabeceras.add(oDoc);
                }

            } catch (IOException ex) {
                throw new Exception( ex.getMessage());
            }catch (XmlPullParserException e) {
                throw new Exception( e.getMessage());
            }

        }catch (Exception ex){
            setsMsgError(ex.getMessage());
        }finally{
            oResponse_WS= null;
            oEnvelope = null;
            oResquest= null;
        }

        return oCabeceras;
    }

    public boolean Close_Entrada(clsOCFG oConfiguracionW ) {
        boolean bResp =false;
        String sMETHOD_NAME="Genera_Entrada";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapPrimitive  oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("DocEntry", getsDocEntry());
            oResquest.addProperty("IdEmp", oConfiguracionW.getsCodeUser());

            oEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            oEnvelope.dotNet=true;
            oEnvelope.setOutputSoapObject(oResquest);
            HttpTransportSE oTransporter = new HttpTransportSE(oConfiguracionW.getsURL());
            try {
                oTransporter.call(oConfiguracionW.getsSOAP_ACTION()+ sMETHOD_NAME,oEnvelope );
                oResponse_WS= (SoapPrimitive) oEnvelope.getResponse();

                if (Integer.parseInt(oResponse_WS.toString())>0) {
                    setsDocEntry(oResponse_WS.toString());
                    bResp = true;
                }else{
                    setsMsgError(oResponse_WS.toString());
                    bResp= false;
                }
            } catch (IOException ex) {
                throw new Exception( ex.getMessage());
            }catch (XmlPullParserException e) {
                throw new Exception( e.getMessage());
            }

        }catch (Exception ex){
            setsMsgError(ex.getMessage());
        }finally{
            oResponse_WS= null;
            oEnvelope = null;
            oResquest= null;
        }

        return bResp;
    }

}


