package ijam.app_wms.Clases;

/**
 * Created by alberto.ramirez on 18/02/2017.
 */
public class clsDatos_EntradasHead {
    int Imagenes;
    private String DocEntry;
    private String AbsEntry;
    private String DocNum;
    private String DocDate;
    private String Comentarios;
    private String CardCode;
    private String Tipo;

    public clsDatos_EntradasHead(int imagenes, String docEntry, String absentry, String docNum, String docDate, String tipo, String comentarios, String cardcode) {
        Imagenes = imagenes;
        DocEntry = docEntry;
        AbsEntry= absentry;
        DocNum = docNum;
        DocDate = docDate;
        Tipo = tipo;
        Comentarios = comentarios;
        CardCode = cardcode;
    }

    public String getAbsEntry() {
        return AbsEntry;
    }

    public void setAbsEntry(String absEntry) {
        AbsEntry = absEntry;
    }

    public String getCardCode() {
        return CardCode;
    }

    public void setCardCode(String cardCode) {
        CardCode = cardCode;
    }

    public String getComentarios() {
        return Comentarios;
    }

    public void setComentarios(String comentarios) {
        Comentarios = comentarios;
    }

    public String getDocDate() {
        return DocDate;
    }

    public void setDocDate(String docDate) {
        DocDate = docDate;
    }

    public String getDocEntry() {
        return DocEntry;
    }

    public void setDocEntry(String docEntry) {
        DocEntry = docEntry;
    }

    public int getImagenes() {
        return Imagenes;
    }

    public void setImagenes(int imagenes) {
        Imagenes = imagenes;
    }

    public String getDocNum() {
        return DocNum;
    }

    public void setDocNum(String docNum) {
        DocNum = docNum;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String tipo) {
        Tipo = tipo;
    }
}

