package ijam.app_wms.SQLite;

/**
 * Created by alberto.ramirez on 15/02/2017.
 */
public class clsOCFG {
    private int iDocEntry;
    private String sNAMESPACE="" ;
    private String sURL="";
    private String sSOAP_ACTION ="";
    private String sBD;
    private String sUserId;
    private String sUsuario;
    private String sCodeUser;
    private String sPassword;
    private String sMsg_Error;

    public String getsPassword() {
        return sPassword;
    }

    public String getsCodeUser() {
        return sCodeUser;
    }

    public void setsCodeUser(String sCodeUser) {
        this.sCodeUser = sCodeUser;
    }

    public void setsPassword(String sPassword) {
        this.sPassword = sPassword;
    }

    public String getsUserId() {
        return sUserId;
    }

    public void setsUserId(String sUserId) {
        this.sUserId = sUserId;
    }

    public String getsUsuario() {
        return sUsuario;
    }

    public void setsUsuario(String sUsuario) {
        this.sUsuario = sUsuario;
    }
    public clsOCFG(){}
    /*public clsOCFG( String sBD, String sMsg_Error, String sSOAP_ACTION, String sNAMESPACE, String sURL) {
        this.sBD = sBD;
        this.sMsg_Error = sMsg_Error;
        this.sSOAP_ACTION = sSOAP_ACTION;
        this.sNAMESPACE = sNAMESPACE;
        this.sURL = sURL;
    }*/

    public int getiDocEntry() {
        return iDocEntry;
    }

    public void setiDocEntry(int iDocEntry) {
        this.iDocEntry = iDocEntry;
    }

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

    public String getsNAMESPACE() {
        return sNAMESPACE;
    }

    public void setsNAMESPACE(String sNAMESPACE) {
        this.sNAMESPACE = sNAMESPACE;
    }

    public String getsSOAP_ACTION() {
        return sSOAP_ACTION;
    }

    public void setsSOAP_ACTION(String sSOAP_ACTION) {
        this.sSOAP_ACTION = sSOAP_ACTION;
    }

    public String getsURL() {
        return sURL;
    }

    public void setsURL(String sURL) {
        this.sURL = sURL;
    }
}
