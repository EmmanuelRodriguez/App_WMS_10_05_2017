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

import ijam.app_wms.Clases.clsDatos_TrnsfrHead;
import ijam.app_wms.R;

/**
 * Created by alberto.ramirez on 17/02/2017.
 */
public class adpTransfer_OnHead extends BaseAdapter {
    Context oContext=null;
    LayoutInflater oInflanter;
    ArrayList<clsDatos_TrnsfrHead> oDts_TrsfHead;
    List<clsDatos_TrnsfrHead> oLstDts_TrsfHead;

    public adpTransfer_OnHead(Context oContext, List<clsDatos_TrnsfrHead> oLstDts_TrnsfHead) {
        this.oContext = oContext;
        this.oLstDts_TrsfHead = oLstDts_TrnsfHead;
        oInflanter=LayoutInflater.from(oContext);
        this.oDts_TrsfHead= new ArrayList<clsDatos_TrnsfrHead>();
        this.oDts_TrsfHead.addAll(oLstDts_TrnsfHead);
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
        return oLstDts_TrsfHead.size();
    }

    @Override
    public Object getItem(int position) {
        return oLstDts_TrsfHead.get(position);
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
            convertView = oInflanter.inflate(R.layout.lst_frm_transfer, null);
            oHolder.imgOnHead = (ImageView) convertView.findViewById(R.id.imgTrnsfrHead);
            oHolder.txtDocEntry = (TextView) convertView.findViewById(R.id.lblTrnsfr_DocEntry);
            oHolder.txtAbsEntry = (TextView) convertView.findViewById(R.id.lblTrnsfr_AbsEntry);
            oHolder.txtDocNum = (TextView) convertView.findViewById(R.id.lblTrnsfr_DocNum);
            oHolder.txtComentarios = (TextView) convertView.findViewById(R.id.lblTrnsfr_Cmnts);
            oHolder.txtTipo = (TextView) convertView.findViewById(R.id.lblTrnsfr_Tipo);
            oHolder.txtDocDate = (TextView) convertView.findViewById(R.id.lblTrnsfr_DocDate);
            oHolder.txtCardCode = (TextView) convertView.findViewById(R.id.lblTrnsfr_CardCode);
            convertView.setTag(oHolder);
        } else {
            oHolder = (ViewHolder) convertView.getTag();
        }
        oHolder.imgOnHead.setImageResource(oLstDts_TrsfHead.get(position).getImagenes());
        oHolder.txtDocEntry.setText(oLstDts_TrsfHead.get(position).getDocEntry());
        oHolder.txtAbsEntry.setText(oLstDts_TrsfHead.get(position).getAbsEntry());
        oHolder.txtDocNum.setText(oLstDts_TrsfHead.get(position).getDocNum());
        oHolder.txtTipo.setText(oLstDts_TrsfHead.get(position).getTipo());
        if(oLstDts_TrsfHead.get(position).getComentarios().contains("anyType{}")){
            oHolder.txtComentarios.setText("");
        }
        else
        {
            oHolder.txtComentarios.setText(oLstDts_TrsfHead.get(position).getComentarios());
        }

        oHolder.txtDocDate.setText(oLstDts_TrsfHead.get(position).getDocDate());
        oHolder.txtCardCode .setText(oLstDts_TrsfHead.get(position).getCardCode());

        return convertView;
    }

    public void Filtro(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        oLstDts_TrsfHead.clear();
        if (charText.length()==0 ){
            oLstDts_TrsfHead.addAll(oDts_TrsfHead );
        }else{
            for (clsDatos_TrnsfrHead oclD: oDts_TrsfHead ){
                if(oclD.getDocEntry().toLowerCase(Locale.getDefault()).contains(charText)){
                    oLstDts_TrsfHead.add(oclD);
                }
            }
        }
        notifyDataSetChanged();

    }


}
