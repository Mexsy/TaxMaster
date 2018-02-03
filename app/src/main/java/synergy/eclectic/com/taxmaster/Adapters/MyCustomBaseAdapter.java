package synergy.eclectic.com.taxmaster.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import synergy.eclectic.com.taxmaster.DBinserter.FilingHistory;
import synergy.eclectic.com.taxmaster.R;


/**
 * Sourced by geekswithblogs on 06/03/2017.
 */

public class MyCustomBaseAdapter extends BaseAdapter {
    private static List<FilingHistory> filingHistoryArrayList;

    private LayoutInflater mInflater;

    public MyCustomBaseAdapter(Context context, List<FilingHistory> results) {
        filingHistoryArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return filingHistoryArrayList.size();
    }

    public Object getItem(int position) {
        return filingHistoryArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row_view, null);
            holder = new ViewHolder();
            holder.filingNUM = convertView.findViewById(R.id.filing_num);



            holder.filingDATE = convertView.findViewById(R.id.filing_type);
            holder.filingTYPE = convertView.findViewById(R.id.filing_date);
            holder.filingAMOUNT = convertView.findViewById(R.id.filing_amount);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.filingNUM.setText(filingHistoryArrayList.get(position).getFilingNO());
        holder.filingDATE.setText(filingHistoryArrayList.get(position).getFiling_date());
        holder.filingTYPE.setText(filingHistoryArrayList.get(position).getType());
        holder.filingAMOUNT.setText(filingHistoryArrayList.get(position).getAmount());

        return convertView;
    }

    static class ViewHolder {
        TextView filingNUM;
        TextView filingDATE;
        TextView filingTYPE;
        TextView filingAMOUNT;
    }
}