package synergy.eclectic.com.taxmaster.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import synergy.eclectic.com.taxmaster.R;
import synergy.eclectic.com.taxmaster.Results;

/**
 * Created by Emeka Chukumah on 04/04/2017.
 */

public class CustomerAdapter extends BaseAdapter {
    Context context;

    private static ArrayList<Results> activityArrayList;
    private LayoutInflater mInflater;

    public CustomerAdapter(Context context, ArrayList<Results> results) {
        this.context = context;
        activityArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return activityArrayList.size();
    }

    public Object getItem(int position) {
        return activityArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row_cust, null);

            holder = new ViewHolder();
            holder.customerNAME = convertView.findViewById(R.id.customer_name);
            holder.status = convertView.findViewById(R.id.statusCom);
            holder.deleteBUTTON = convertView.findViewById(R.id.imageButtonDelete);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.customerNAME.setText(activityArrayList.get(position).getSurname());
        holder.status.setText(activityArrayList.get(position).getStatus());
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/quicksandbold.otf");
        holder.customerNAME.setTypeface(face);
        holder.deleteBUTTON.setFocusable(false);
        holder.deleteBUTTON.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                int index = (int)v.getTag();
                System.out.println(position);
                //  System.out.println(index);
                activityArrayList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Customer has been removed",
                        Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }


    static class ViewHolder {
        TextView customerNAME;
        TextView status;
        ImageButton deleteBUTTON;
    }
}
