package ijam.app_wms.Clases;

/**
 * Created by alberto.ramirez on 18/02/2017.
 */
public class clsDatos_OnHead {
    int Imagenes;
    private String DocEntry;
    private String DocNum;
    private String DocDate;
    private String Comentarios;
    private String Hora;
    private String Tipo;

    public clsDatos_OnHead(int imagenes, String docEntry,  String docNum,  String docDate, String tipo, String comentarios, String hora) {
        Imagenes = imagenes;
        DocEntry = docEntry;
        DocNum = docNum;
        DocDate = docDate;
        Tipo = tipo;
        Comentarios = comentarios;
        Hora=hora;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
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

