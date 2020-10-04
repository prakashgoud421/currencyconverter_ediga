package com.ediga.currencyconverter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.HashMap;

public class currencyDisPlayAdapter extends BaseAdapter {

    Context context;
    Double targetedPrice;
    Double selectedPrice;
    private HashMap<String, String> mData = new HashMap<String, String>();
    private String[] mKeys;
    private Double rate;
    public currencyDisPlayAdapter(Context mContext, HashMap<String, String> data, String targetedCurrency ,Double selectedPriced,Double targetedPriced){
        context = mContext;
        mData  = data;
        mKeys = mData.keySet().toArray(new String[data.size()]);
        targetedPrice = targetedPriced;
        selectedPrice = selectedPriced;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(mKeys[position]);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        String key = mKeys[pos];
        rate = Double.valueOf(getItem(pos).toString());

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.currencies_list_data, null, true);
        }

        TextView Key = (TextView) convertView.findViewById(R.id.currency);
        TextView value = (TextView) convertView.findViewById(R.id.value);

        DecimalFormat decimalFormat = new DecimalFormat("0.00");

        //Updating currencies and its values
        if(key!=null) { Key.setText(key.substring(3)); }
        if(value!=null) { value.setText("$ "+decimalFormat.format(calculatePrice())); }

        return convertView;
    }

    //Calculate the Price for Targeted currency
    private Double calculatePrice() {
        return rate * targetedPrice/selectedPrice;
    }
}
