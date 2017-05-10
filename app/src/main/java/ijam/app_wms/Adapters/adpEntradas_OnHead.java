package ijam.app_wms.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ijam.app_wms.Clases.clsDatos_EntradasHead;
import ijam.app_wms.R;

/**
 * Created by alberto.ramirez on 17/02/2017.
 */
public class adpEntradas_OnHead extends BaseAdapter {
    Context oContext=null;
    LayoutInflater oInflanter;
    ArrayList<clsDatos_EntradasHead> oDts_EntrHead;
    List<clsDatos_EntradasHead> oLstDts_EntrHead;

    public adpEntradas_OnHead(Context oContext, List<clsDatos_EntradasHead> oLstDts_EntrHead) {
        this.oContext = oContext;
        this.oLstDts_EntrHead = oLstDts_EntrHead;
        oInflanter=LayoutInflater.from(oContext);
        this.oDts_EntrHead= new ArrayList<clsDatos_EntradasHead>();
        this.oDts_EntrHead.addAll(oLstDts_EntrHead);
    }

    public class ViewHolder {
        TextView txtDocEntry;
        TextView txtAbsEntry;
        TextView txtDocNum;
        TextView txtDocDate;
        TextView txtCardCode;
        TextView txtComentarios;
        TextView txtTipo;
        ImageView imgOnHead;
    }

    @Override
    public int getCount() {
        return oLstDts_EntrHead.size();
    }

    @Override
    public Object getItem(int position) {
        return oLstDts_EntrHead.get(position);
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
            convertView = oInflanter.inflate(R.layout.lst_frm_entradas, null);
            oHolder.imgOnHead = (ImageView) convertView.findViewById(R.id.imgEntrHead);
            oHolder.txtDocEntry = (TextView) convertView.findViewById(R.id.lblEntr_DocEntry);
            oHolder.txtAbsEntry = (TextView) convertView.findViewById(R.id.lblEntr_AbsEntry);
            oHolder.txtDocNum = (TextView) convertView.findViewById(R.id.lblEntr_DocNum);
            oHolder.txtComentarios = (TextView) convertView.findViewById(R.id.lblEntr_Cmnts);
            oHolder.txtTipo = (TextView) convertView.findViewById(R.id.lblEntr_Tipo);
            oHolder.txtDocDate = (TextView) convertView.findViewById(R.id.lblEntr_DocDate);
            oHolder.txtCardCode = (TextView) convertView.findViewById(R.id.lblEntr_CardCode);
            convertView.setTag(oHolder);
        } else {
            oHolder = (ViewHolder) convertView.getTag();
        }
        oHolder.imgOnHead.setImageResource(oLstDts_EntrHead.get(position).getImagenes());
        oHolder.txtDocEntry.setText(oLstDts_EntrHead.get(position).getDocEntry());
        oHolder.txtAbsEntry.setText(oLstDts_EntrHead.get(position).getAbsEntry());
        oHolder.txtDocNum.setText(oLstDts_EntrHead.get(position).getDocNum());
        oHolder.txtTipo.setText(oLstDts_EntrHead.get(position).getTipo());
        if(oLstDts_EntrHead.get(position).getComentarios().contains("anyType{}")){
            oHolder.txtComentarios.setText("");
        }
        else
        {
            oHolder.txtComentarios.setText(oLstDts_EntrHead.get(position).getComentarios());
        }

        oHolder.txtDocDate.setText(oLstDts_EntrHead.get(position).getDocDate());
        oHolder.txtCardCode .setText(oLstDts_EntrHead.get(position).getCardCode());

        return convertView;
    }

    public void Filtro(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        oLstDts_EntrHead.clear();
        if (charText.length()==0 ){
            oLstDts_EntrHead.addAll(oDts_EntrHead );
        }else{
            for (clsDatos_EntradasHead oclD: oDts_EntrHead ){
                if(oclD.getDocEntry().toLowerCase(Locale.getDefault()).contains(charText)){
                    oLstDts_EntrHead.add(oclD);
                }
            }
        }
        notifyDataSetChanged();

    }


}
