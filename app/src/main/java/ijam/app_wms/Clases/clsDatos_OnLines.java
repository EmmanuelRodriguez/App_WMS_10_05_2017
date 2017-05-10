package ijam.app_wms.Clases;

/**
 * Created by alberto.ramirez on 20/02/2017.
 */
public class clsDatos_OnLines {
    int Imagen;
    int imgStatus;
    private String DocEntry;
    private int lineNum;
    private String ItemCode;
    private String ItemName;
    private String CodeBars;
    private String Quantity;
    private String TipoCnt;
    private String Ubicacion; //******Agregado******
    private String CantSur; //******Agregado******

    public clsDatos_OnLines(int imagen, int imgStatus, String docEntry, int lineNum, String itemCode, String codeBars, String itemName, String quantity,  String tipoCnt, String ubicacion, String cantSur) { //******Agregados últimos 2 campos******
        Imagen = imagen;
        this.imgStatus = imgStatus;
        DocEntry = docEntry;
        this.lineNum = lineNum;
        ItemCode = itemCode;
        CodeBars = codeBars;
        ItemName = itemName;
        Quantity = quantity;
        TipoCnt = tipoCnt;
        Ubicacion = ubicacion; //******Agregado******
        CantSur = cantSur; //******Agregado******
    }

    public String getTipoCnt() {
        return TipoCnt;
    }

    public void setTipoCnt(String tipoCnt) {
        TipoCnt = tipoCnt;
    }

    public int getImagen() {
        return Imagen;
    }

    public void setImagen(int imagen) {
        Imagen = imagen;
    }

    public int getImgStatus() {
        return imgStatus;
    }

    public void setImgStatus(int imgStatus) {
        this.imgStatus = imgStatus;
    }

    public String getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(String docEntry) {
        DocEntry = docEntry;
    }

    public int getLineNum() {
        return lineNum;
    }

    public void setLineNum(int lineNum) {
        this.lineNum = lineNum;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getCodeBars() {
        return CodeBars;
    }

    public void setCodeBars(String codeBars) {
        CodeBars = codeBars;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        Ubicacion = ubicacion;
    }

    public String getCantSur() {
        return CantSur;
    }

    public void setCantSur(String cantSur) {
        CantSur = cantSur;
    }
}
