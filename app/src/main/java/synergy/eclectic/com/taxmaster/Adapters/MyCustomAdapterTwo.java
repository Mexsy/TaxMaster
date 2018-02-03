package synergy.eclectic.com.taxmaster.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import synergy.eclectic.com.taxmaster.PaymentInfo;
import synergy.eclectic.com.taxmaster.R;

/**
 * Created by Emeka Chukumah on 08/03/2017.
 */

public class MyCustomAdapterTwo extends BaseAdapter {
    private static List<PaymentInfo> filingPaymentArrayList;

    private LayoutInflater mInflater;

    public MyCustomAdapterTwo(Context context, List<PaymentInfo> results) {
        filingPaymentArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return filingPaymentArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return filingPaymentArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyCustomAdapterTwo.ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.custom_row_payment, null);
            holder = new MyCustomAdapterTwo.ViewHolder();
            holder.customerID = convertView.findViewById(R.id.customer_idd);
            holder.inVOICENUM = convertView.findViewById(R.id.invoiceNumber);
            holder.amountINFO = convertView.findViewById(R.id.amount_info);
            holder.transDATE = convertView.findViewById(R.id.transDate);
            holder.refID = convertView.findViewById(R.id.ref_id);

            convertView.setTag(holder);
        } else {
            holder = (MyCustomAdapterTwo.ViewHolder) convertView.getTag();
        }

        holder.customerID.setText(filingPaymentArrayList.get(position).getCustomer_id());
        holder.inVOICENUM.setText("Assessment Number : " + filingPaymentArrayList.get(position).getInvoice_number());
        holder.amountINFO.setText("Amount : " + filingPaymentArrayList.get(position).getAmount());
        holder.transDATE.setText("Date : " + filingPaymentArrayList.get(position).getTransaction_date());
        holder.refID.setText("Reference ID : " + filingPaymentArrayList.get(position).getRefID());

        return convertView;

    }

    static class ViewHolder {
        TextView customerID;
        TextView inVOICENUM;
        TextView amountINFO;
        TextView transDATE;
        TextView refID;
    }
}
