package ijam.app_wms.Adapters;

import ijam.app_wms.R;
import ijam.app_wms.R.id;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by alberto.ramirez on 16/02/2017.
 */
public class adpMenu extends BaseAdapter {
    Context context;
    String[] titulos;
    int[] imagenes;
    LayoutInflater inflanter;

    public adpMenu(Context context, int[] imagenes, String[] titulos) {
        this.context = context;
        this.imagenes = imagenes;
        this.titulos = titulos;
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return titulos.length;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        TextView txtTitle;
        ImageView imgRow;

        inflanter= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflanter.inflate(R.layout.lst_frm_menu,parent, false);

//        txtTitle= (TextView)itemView.findViewById(id.lblTitle );
//        imgRow=(ImageView)itemView.findViewById(id.imgRow);

//        txtTitle.setText(titulos[position]);
//        imgRow.setImageResource(imagenes[position]);

        return itemView;
    }

}

