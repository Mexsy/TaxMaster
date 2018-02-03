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

/**
 * Created by Emeka Chukumah on 10/03/2017.
 */

public class CustomerMultipleUser extends BaseAdapter {
    Context context;

    private static ArrayList<String> membersArrayList;

    private LayoutInflater mInflater;

    public CustomerMultipleUser(Context context, ArrayList<String> results) {
        this.context = context;
        membersArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return membersArrayList.size();
    }

    public Object getItem(int position) {
        return membersArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_add_user_row, null);

            holder = new ViewHolder();
            holder.memberNAME = convertView.findViewById(R.id.team_member);
            holder.deleteBUTTON = convertView.findViewById(R.id.imageButtonDel);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.memberNAME.setText(membersArrayList.get(position));
        Typeface face= Typeface.createFromAsset(context.getAssets(), "fonts/quicksandbold.otf");
        holder.memberNAME.setTypeface(face);
        holder.deleteBUTTON.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
//                int index = (int)v.getTag();
                System.out.println(position);
              //  System.out.println(index);
                membersArrayList.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Member has been removed",
                        Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }


    static class ViewHolder {
        TextView memberNAME;
        ImageButton deleteBUTTON;
    }
}
