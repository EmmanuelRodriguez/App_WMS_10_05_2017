//Código Agregado


package ijam.app_wms.Clases;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import ijam.app_wms.SQLite.clsOCFG;

import static org.ksoap2.SoapEnvelope.VER11;

/**
 * Created by alberto.ramirez on 20/02/2017.
 */
public class clsCntInv_Lines {
    private String sDocEntry;
    private String sOrderEntry;
    private int iLineNum;
    private String sLineStatus;
    private String sItemCode;
    private String sItemName;
    private String sWhsHouse;
    private String sCodeBars;
    private String sQuantity;
    private String sQuantityTotal;
    private String sComentarios;
    private String sUbicacion;
    private String sCountDate;
    private String sCoutTime;
    private String sUpdateDate;
    private String sUpdateTime;
    private String sUserSign;
    private String sUserUpdate;
    private String sTypeCount;
    private String sMsgError;

    public clsCntInv_Lines() {
    }

    public String getsOrderEntry() {
        return sOrderEntry;
    }

    public void setsOrderEntry(String sOrderEntry) {
        this.sOrderEntry = sOrderEntry;
    }

    public String getsTypeCount() {
        return sTypeCount;
    }

    public void setsTypeCount(String sTypeCount) {
        this.sTypeCount = sTypeCount;
    }

    public int getiLineNum() {
        return iLineNum;
    }

    public void setiLineNum(int iLineNum) {
        this.iLineNum = iLineNum;
    }

    public String getsComentarios() {
        return sComentarios;
    }

    public void setsComentarios(String sComentarios) {
        this.sComentarios = sComentarios;
    }

    public String getsCodeBars() {
        return sCodeBars;
    }

    public void setsCodeBars(String sCodeBars) {
        this.sCodeBars = sCodeBars;
    }

    public String getsCountDate() {
        return sCountDate;
    }

    public void setsCountDate(String sCountDate) {
        this.sCountDate = sCountDate;
    }

    public String getsLineStatus() {
        return sLineStatus;
    }

    public void setsLineStatus(String sLineStatus) {
        this.sLineStatus = sLineStatus;
    }

    public String getsUpdateDate() {
        return sUpdateDate;
    }

    public void setsUpdateDate(String sUpdateDate) {
        this.sUpdateDate = sUpdateDate;
    }

    public String getsUserUpdate() {
        return sUserUpdate;
    }

    public void setsUserUpdate(String sUserUpdate) {
        this.sUserUpdate = sUserUpdate;
    }

    public String getsUserSign() {
        return sUserSign;
    }

    public void setsUserSign(String sUserSign) {
        this.sUserSign = sUserSign;
    }

    public String getsUpdateTime() {
        return sUpdateTime;
    }

    public void setsUpdateTime(String sUpdateTime) {
        this.sUpdateTime = sUpdateTime;
    }

    public String getsWhsHouse() {
        return sWhsHouse;
    }

    public void setsWhsHouse(String sWhsHouse) {
        this.sWhsHouse = sWhsHouse;
    }

    public String getsUbicacion() {
        return sUbicacion;
    }

    public void setsUbicacion(String sUbicacion) {
        this.sUbicacion = sUbicacion;
    }

    public String getsQuantity() {
        return sQuantity;
    }

    public void setsQuantity(String sQuantity) {
        this.sQuantity = sQuantity;
    }

    public String getsItemName() {
        return sItemName;
    }

    public void setsItemName(String sItemName) {
        this.sItemName = sItemName;
    }

    public String getsItemCode() {
        return sItemCode;
    }

    public void setsItemCode(String sItemCode) {
        this.sItemCode = sItemCode;
    }

    public String getsDocEntry() {
        return sDocEntry;
    }

    public void setsDocEntry(String sDocEntry) {
        this.sDocEntry = sDocEntry;
    }

    public String getsCoutTime() {
        return sCoutTime;
    }

    public void setsCoutTime(String sCoutTime) {
        this.sCoutTime = sCoutTime;
    }

    public String getsQuantityTotal() {
        return sQuantityTotal;
    }

    public void setsQuantityTotal(String sQuantityTotal) {
        this.sQuantityTotal = sQuantityTotal;
    }

    public String getsMsgError() {
        return sMsgError;
    }

    public void setsMsgError(String sMsgError) {
        this.sMsgError = sMsgError;
    }

    public boolean Get_Item(clsOCFG oConfiguracionW){
        boolean bResp_WS=false;

        String sMETHOD_NAME="Get_CntItem";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapObject  oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("DocEntry", getsDocEntry());
            oResquest.addProperty("CodeBars", getsCodeBars());
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("IdEmp", oConfiguracionW.getsCodeUser());
            //oResquest.addProperty("User", oConfiguracionW.getsUsuario());
         //   oResquest.addProperty("Pwd", oConfiguracionW.getsPassword());
            oEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            oEnvelope.dotNet=true;
            oEnvelope.setOutputSoapObject(oResquest);
            HttpTransportSE oTransporter = new HttpTransportSE(oConfiguracionW.getsURL());
            try {
                oTransporter.call(oConfiguracionW.getsSOAP_ACTION()+ sMETHOD_NAME,oEnvelope );
                oResponse_WS= (SoapObject) oEnvelope.getResponse();

                if (oResponse_WS.getPropertyCount()>0) {
                    for (int i = 0; i < oResponse_WS.getPropertyCount(); i++) {
                        SoapObject ic = (SoapObject) oResponse_WS.getProperty(i);
                        if(Integer.parseInt(ic.getProperty("CountQty").toString())>=0){
                            setsItemCode(ic.getProperty("ItemCode").toString());
                            setsItemName(ic.getProperty("ItemDesc").toString());
                            setsCodeBars(ic.getProperty("CodeBars").toString());
                            setsWhsHouse(ic.getProperty("WhsCode").toString());
                            setsQuantity(ic.getProperty("CountQty").toString());
                            setsUbicacion(ic.getProperty("Ubicacion").toString());

                            setiLineNum(Integer.parseInt(ic.getProperty("LineNum").toString()));
                        }
                    }
                    bResp_WS =true;
                }else{
                    throw new Exception("El artículo no esta dado de alta");
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

        return bResp_WS;
    }

    public boolean Get_TrflItem(clsOCFG oConfiguracionW){
        boolean bResp_WS=false;

        String sMETHOD_NAME="Get_TrldsItem";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapObject  oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("DocEntry", getsDocEntry());
            oResquest.addProperty("CodeBars", getsCodeBars());
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("IdEmp", oConfiguracionW.getsCodeUser());
            //oResquest.addProperty("User", oConfiguracionW.getsUsuario());
            //   oResquest.addProperty("Pwd", oConfiguracionW.getsPassword());
            oEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            oEnvelope.dotNet=true;
            oEnvelope.setOutputSoapObject(oResquest);
            HttpTransportSE oTransporter = new HttpTransportSE(oConfiguracionW.getsURL());
            try {
                oTransporter.call(oConfiguracionW.getsSOAP_ACTION()+ sMETHOD_NAME,oEnvelope );
                oResponse_WS= (SoapObject) oEnvelope.getResponse();

                if (oResponse_WS.getPropertyCount()>0) {
                    for (int i = 0; i < oResponse_WS.getPropertyCount(); i++) {
                        SoapObject ic = (SoapObject) oResponse_WS.getProperty(i);
                        if( !ic.getProperty("MsgError").toString().equals("-2")){
                            setsItemCode(ic.getProperty("ItemCode").toString());
                            setsItemName(ic.getProperty("ItemDesc").toString());
                            setsCodeBars(ic.getProperty("CodeBars").toString());
                            setsWhsHouse(ic.getProperty("WhsCode").toString());
                            setsQuantity(ic.getProperty("CountQty").toString());
                            setsQuantityTotal(ic.getProperty("QtyTotal").toString()) ;
                            setsUbicacion(ic.getProperty("Ubicacion").toString());
                            setiLineNum(Integer.parseInt(ic.getProperty("LineNum").toString()));
                            setsOrderEntry(ic.getProperty("DocEntry").toString());
                            setsDocEntry(ic.getProperty("AbsEntry").toString());
                        }else
                        {
                            throw new Exception("El artículo no esta dado de alta");
                        }
                    }
                    bResp_WS =true;
                }else{
                    throw new Exception("El artículo no esta dado de alta");
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

        return bResp_WS;
    }

    public boolean Add_ItemCntOnline(clsOCFG oConfiguracionW ) {
        boolean bResp =false;
        String sMETHOD_NAME="Add_Rec_Online_Cab";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapPrimitive oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("User", oConfiguracionW.getsUsuario());
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("ItemCode",getsItemCode());
            oResquest.addProperty("ItemName",getsItemName());
            oResquest.addProperty("DocEntry", getsDocEntry());
            oResquest.addProperty("CodeBars", getsCodeBars());
            oResquest.addProperty("Ubicacion", getsUbicacion());
            oResquest.addProperty("WhsHouse", getsWhsHouse());
            oResquest.addProperty("Quantity", getsQuantity());
            oResquest.addProperty("DocDate", getsCountDate());
            oResquest.addProperty("Hora",getsCoutTime());
            oResquest.addProperty("LineStatus",getsLineStatus());

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

    public  boolean  Valida_Cnt_Item(clsOCFG oConfiguracionW ) {
        boolean bRep = false;
        String sMETHOD_NAME="Valida_Cnt_Item";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapPrimitive oResponse_WS = null;

        try{
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("User", oConfiguracionW.getsUsuario());
            oResquest.addProperty("Pwd",  oConfiguracionW.getsPassword());
            oResquest.addProperty("DocEntry",  getsDocEntry());
            oResquest.addProperty("CodeBars", getsCodeBars());
            oResquest.addProperty("IdEmp", oConfiguracionW.getsCodeUser());

            oEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            oEnvelope.dotNet=true;
            oEnvelope.setOutputSoapObject(oResquest);
            HttpTransportSE oTransporter = new HttpTransportSE(oConfiguracionW.getsURL());
            try {
                oTransporter.call(oConfiguracionW.getsSOAP_ACTION()+ sMETHOD_NAME,oEnvelope );
                oResponse_WS= (SoapPrimitive) oEnvelope.getResponse();

                if (isNumeric(oResponse_WS.toString())) {
                    switch (oResponse_WS.toString()){
                        case "-2":
                            bRep= false;
                            throw new Exception("El artículo no existe en la sociedad, por favor darlo de alta");
                        default:
                            setsMsgError("");
                            setsQuantity(oResponse_WS.toString());
                            bRep= true;
                            break;
                    }
                }else{
                    throw new Exception( oResponse_WS.toString());
                }
            } catch (IOException ex) {
                throw new Exception( ex.getMessage());
            }catch (XmlPullParserException e) {
                throw new Exception( e.getMessage());
            }

        }catch (Exception x){
            setsMsgError(x.getMessage());
        }

        return bRep;
    }

    public ArrayList<clsCntInv_Lines> Reupera_CntOn_Lineas(clsOCFG oConfiguracionW ) {
        ArrayList<clsCntInv_Lines> oLineas = new ArrayList<clsCntInv_Lines>();
        // String sMETHOD_NAME="Recupera_Rec_Online_Cab";
        String sMETHOD_NAME="Recupera_Cnt_Lines";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapObject  oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("DocEntry", getsDocEntry());
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
                    clsCntInv_Lines oDoc = new clsCntInv_Lines();
                    oDoc.setsDocEntry(ic.getProperty("DocEntry").toString());
                    oDoc.setsItemCode(ic.getProperty("ItemCode").toString());
                    oDoc.setsItemName(ic.getProperty("ItemDesc").toString());
                    oDoc.setsWhsHouse(ic.getProperty("WhsCode").toString());
                    oDoc.setsCodeBars(ic.getProperty("CodeBars").toString());
                    oDoc.setsQuantity(ic.getProperty("CountQty").toString());
                    oDoc.setsTypeCount(ic.getProperty("CountType").toString());
                    oDoc.setiLineNum(Integer.parseInt( ic.getProperty("LineNum").toString()));
                    oDoc.setsMsgError("Success");
                    oLineas.add(oDoc);
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

        return oLineas;
    }

    private static boolean isNumeric(String sCadena){
        try {
            Integer.parseInt(sCadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }

    public boolean Update_ItemCntOnline(clsOCFG oConfiguracionW ) {
        boolean bResp =false;
        String sMETHOD_NAME="Update_Cnt_Item";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapPrimitive oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("User", oConfiguracionW.getsUsuario());
            oResquest.addProperty("Pwd", oConfiguracionW.getsPassword());
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("DocEntry",getsDocEntry());
            oResquest.addProperty("LineNum",getiLineNum());
            oResquest.addProperty("IdEmp", oConfiguracionW.getsCodeUser());
            oResquest.addProperty("CodeBars", getsCodeBars());
            oResquest.addProperty("Quantity", getsQuantity());
            oResquest.addProperty("Ubicacion", getsUbicacion());


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

    public boolean Update_ItemTrlfline(clsOCFG oConfiguracionW ) {
        boolean bResp =false;
        String sMETHOD_NAME="Update_Trlds_Item";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapPrimitive oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
//            oResquest.addProperty("User", oConfiguracionW.getsUsuario());
//            oResquest.addProperty("Pwd", oConfiguracionW.getsPassword());
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("OrderEntry", getsOrderEntry());
            oResquest.addProperty("AbsEntry", getsDocEntry());
            oResquest.addProperty("LineNum",getiLineNum());
            oResquest.addProperty("IdEmp", oConfiguracionW.getsCodeUser());
            oResquest.addProperty("CodeBars", getsCodeBars());
            oResquest.addProperty("Quantity", getsQuantity());
            oResquest.addProperty("Ubicacion", getsUbicacion());

            oEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            oEnvelope.dotNet=true;
            oEnvelope.setOutputSoapObject(oResquest);
            HttpTransportSE oTransporter = new HttpTransportSE(oConfiguracionW.getsURL());
            try {
                oTransporter.call(oConfiguracionW.getsSOAP_ACTION()+ sMETHOD_NAME,oEnvelope );
                oResponse_WS= (SoapPrimitive) oEnvelope.getResponse();

                if (Integer.parseInt(oResponse_WS.toString())>=0) {
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

    public ArrayList<clsCntInv_Lines> Reupera_Trlf_Lineas(clsOCFG oConfiguracionW ) {
        ArrayList<clsCntInv_Lines> oLineas = new ArrayList<clsCntInv_Lines>();
        // String sMETHOD_NAME="Recupera_Rec_Online_Cab";
        String sMETHOD_NAME="Recupera_Trlds_Lines";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapObject  oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("DocEntry", getsDocEntry());
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
                    clsCntInv_Lines oDoc = new clsCntInv_Lines();
                    oDoc.setsDocEntry(ic.getProperty("AbsEntry").toString());
                    oDoc.setsOrderEntry(ic.getProperty("DocEntry").toString());
                    oDoc.setsItemCode(ic.getProperty("ItemCode").toString());
                    oDoc.setsItemName(ic.getProperty("ItemDesc").toString());
                    oDoc.setsWhsHouse(ic.getProperty("WhsCode").toString());
                    oDoc.setsCodeBars(ic.getProperty("CodeBars").toString());
                    oDoc.setsQuantity(ic.getProperty("CountQty").toString());
                    oDoc.setsTypeCount(ic.getProperty("AbsEntry").toString());
                    oDoc.setsUbicacion(ic.getProperty("Ubicacion").toString()); //******Agregado*****
                    oDoc.setsQuantityTotal(ic.getProperty("QtyTotal").toString()) ; //******Agregado*****
                    oDoc.setiLineNum(Integer.parseInt( ic.getProperty("LineNum").toString()));
                    oDoc.setsMsgError("Success");
                    oLineas.add(oDoc);
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

        return oLineas;
    }

    public ArrayList<clsCntInv_Lines> Reupera_Entradas_Lineas(clsOCFG oConfiguracionW ) {
        ArrayList<clsCntInv_Lines> oLineas = new ArrayList<clsCntInv_Lines>();
        // String sMETHOD_NAME="Recupera_Rec_Online_Cab";
        String sMETHOD_NAME="Recupera_Entrada_Lines";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapObject  oResponse_WS = null;

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
                oResponse_WS= (SoapObject) oEnvelope.getResponse();

                for (int i=0; i< oResponse_WS.getPropertyCount();i++){
                    SoapObject ic =(SoapObject)oResponse_WS.getProperty(i);
                    clsCntInv_Lines oDoc = new clsCntInv_Lines();
                    oDoc.setsDocEntry(ic.getProperty("DocEntry").toString());
                    oDoc.setsOrderEntry(ic.getProperty("DocEntry").toString()) ;
                    oDoc.setsItemCode(ic.getProperty("ItemCode").toString());
                    oDoc.setsItemName(ic.getProperty("ItemDesc").toString());
                    oDoc.setsWhsHouse(ic.getProperty("WhsCode").toString());
                    oDoc.setsCodeBars(ic.getProperty("CodeBars").toString());
                    oDoc.setsQuantity(ic.getProperty("CountQty").toString());
                    //oDoc.setsTypeCount(ic.getProperty("AbsEntry").toString());
                    oDoc.setiLineNum(Integer.parseInt( ic.getProperty("LineNum").toString()));
                    oDoc.setsQuantityTotal(ic.getProperty("QtyTotal").toString());
                    oDoc.setsMsgError("Success");
                    oLineas.add(oDoc);
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

        return oLineas;
    }

    public boolean Get_EntradasItem(clsOCFG oConfiguracionW){
        boolean bResp_WS=false;

        String sMETHOD_NAME="Get_Entrada_Item";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapObject  oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("DocEntry", getsDocEntry());
            oResquest.addProperty("CodeBars", getsCodeBars());
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("IdEmp", oConfiguracionW.getsCodeUser());
            //oResquest.addProperty("User", oConfiguracionW.getsUsuario());
            //   oResquest.addProperty("Pwd", oConfiguracionW.getsPassword());
            oEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            oEnvelope.dotNet=true;
            oEnvelope.setOutputSoapObject(oResquest);
            HttpTransportSE oTransporter = new HttpTransportSE(oConfiguracionW.getsURL());
            try {
                oTransporter.call(oConfiguracionW.getsSOAP_ACTION()+ sMETHOD_NAME,oEnvelope );
                oResponse_WS= (SoapObject) oEnvelope.getResponse();

                if (oResponse_WS.getPropertyCount()>0) {
                    for (int i = 0; i < oResponse_WS.getPropertyCount(); i++) {
                        SoapObject ic = (SoapObject) oResponse_WS.getProperty(i);
                        if( !ic.getProperty("MsgError").toString().equals("-2")){
                            setsItemCode(ic.getProperty("ItemCode").toString());
                            setsItemName(ic.getProperty("ItemDesc").toString());
                            setsCodeBars(ic.getProperty("CodeBars").toString());
                            setsWhsHouse(ic.getProperty("WhsCode").toString());
                            setsQuantity(ic.getProperty("CountQty").toString());
                            setsQuantityTotal(ic.getProperty("QtyTotal").toString()) ;
                            setsUbicacion(ic.getProperty("Ubicacion").toString());
                            setiLineNum(Integer.parseInt(ic.getProperty("LineNum").toString()));
                            setsOrderEntry(ic.getProperty("DocEntry").toString());
                            setsDocEntry(ic.getProperty("DocEntry").toString());
                        }else
                        {
                            throw new Exception("El artículo no esta dado de alta");
                        }
                    }
                    bResp_WS =true;
                }else{
                    throw new Exception("El artículo no esta dado de alta");
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

        return bResp_WS;
    }

    public boolean Update_ItemEntradasline(clsOCFG oConfiguracionW ) {
        boolean bResp =false;
        String sMETHOD_NAME="Update_Entrada_Item";
        SoapSerializationEnvelope oEnvelope= null;
        SoapObject oResquest= null;
        SoapPrimitive oResponse_WS = null;

        try {
            oResquest = new SoapObject(oConfiguracionW.getsNAMESPACE(), sMETHOD_NAME);
            oResquest.addProperty("Sociedad", oConfiguracionW.getsBD());
            oResquest.addProperty("DocEntry", getsDocEntry());
            oResquest.addProperty("LineNum",getiLineNum());
            oResquest.addProperty("IdEmp", oConfiguracionW.getsCodeUser());
            oResquest.addProperty("CodeBars", getsCodeBars());
            oResquest.addProperty("Quantity", getsQuantity());

            oEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            oEnvelope.dotNet=true;
            oEnvelope.setOutputSoapObject(oResquest);
            HttpTransportSE oTransporter = new HttpTransportSE(oConfiguracionW.getsURL());
            try {
                oTransporter.call(oConfiguracionW.getsSOAP_ACTION()+ sMETHOD_NAME,oEnvelope );
                oResponse_WS= (SoapPrimitive) oEnvelope.getResponse();

                if (Integer.parseInt(oResponse_WS.toString())>=0) {
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


