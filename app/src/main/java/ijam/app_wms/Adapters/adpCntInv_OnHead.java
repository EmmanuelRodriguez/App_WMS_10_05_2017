package ijam.app_wms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ijam.app_wms.Clases.clsDatos_OnHead;
import ijam.app_wms.R;

/**
 * Created by alberto.ramirez on 17/02/2017.
 */
public class adpCntInv_OnHead extends BaseAdapter {
    Context oContext=null;
    LayoutInflater oInflanter;
    ArrayList<clsDatos_OnHead> oDts_OnHead;
    List<clsDatos_OnHead> oLstDts_OnHead;

    public adpCntInv_OnHead(Context oContext, List<clsDatos_OnHead> oLstDts_OnHead) {
        this.oContext = oContext;
        this.oLstDts_OnHead = oLstDts_OnHead;
        oInflanter=LayoutInflater.from(oContext);
        this.oDts_OnHead= new ArrayList<clsDatos_OnHead>();
        this.oDts_OnHead.addAll(oLstDts_OnHead);
    }

    public class ViewHolder {
        TextView txtDocEntry;
        TextView txtDocNum;
        TextView txtDocDate;
        TextView txtHora;
        TextView txtComentarios;
        TextView txtTipo;
        ImageView imgOnHead;
    }

    @Override
    public int getCount() {
        return oLstDts_OnHead.size();
    }

    @Override
    public Object getItem(int position) {
        return oLstDts_OnHead.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder oHolder;
        if (convertView == null) {
            oHolder = new ViewHolder();
            convertView = oInflanter.inflate(R.layout.lst_frm_conteoonline, null);
            oHolder.imgOnHead = (ImageView) convertView.findViewById(R.id.imgOnHead);
            oHolder.txtDocEntry = (TextView) convertView.findViewById(R.id.lblOn_DocEntry);
            oHolder.txtDocNum = (TextView) convertView.findViewById(R.id.lblOn_DocNum);
            oHolder.txtComentarios = (TextView) convertView.findViewById(R.id.lblOn_Cmnts);
            oHolder.txtTipo = (TextView) convertView.findViewById(R.id.lblOn_Tipo);
            oHolder.txtDocDate = (TextView) convertView.findViewById(R.id.lblOn_DocDate);
            oHolder.txtHora = (TextView) convertView.findViewById(R.id.lblOn_Hora);
            convertView.setTag(oHolder);
        } else {
            oHolder = (ViewHolder) convertView.getTag();
        }
        oHolder.imgOnHead.setImageResource(oLstDts_OnHead.get(position).getImagenes());
        oHolder.txtDocEntry.setText(oLstDts_OnHead.get(position).getDocEntry());
        oHolder.txtDocNum.setText(oLstDts_OnHead.get(position).getDocNum());
        if (Integer.parseInt(oLstDts_OnHead.get(position).getTipo()) == 1){
            oHolder.txtTipo.setText("Simple");
        }
        else if(Integer.parseInt(oLstDts_OnHead.get(position).getTipo()) == 2){
            oHolder.txtTipo.setText("Múltiple");
        }
        if(oLstDts_OnHead.get(position).getComentarios().contains("anyType{}")){
            oHolder.txtComentarios.setText("");
        }
        else
        {
            oHolder.txtComentarios.setText(oLstDts_OnHead.get(position).getComentarios());
        }

        oHolder.txtDocDate.setText(oLstDts_OnHead.get(position).getDocDate());
        oHolder.txtHora.setText(oLstDts_OnHead.get(position).getHora());

        return convertView;
    }

    public void Filtro(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        oLstDts_OnHead.clear();
        if (charText.length()==0 ){
            oLstDts_OnHead.addAll(oDts_OnHead);
        }else{
            for (clsDatos_OnHead oclD: oDts_OnHead){
                if(oclD.getDocEntry().toLowerCase(Locale.getDefault()).contains(charText)){
                    oLstDts_OnHead.add(oclD);
                }
            }
        }
        notifyDataSetChanged();

    }


}
