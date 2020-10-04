package com.ediga.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.ediga.currencyconverter.API.CurrencyAPI;
import com.ediga.currencyconverter.API.RetrofitInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    private currencyDisPlayAdapter adapter;
    private CurrencyAPI apiInterface;
    private Spinner spinner;
    private EditText enterAmount;
    private ProgressBar progressBar;
    private Double targetPrice = 01.00;
    private Double selectedPrice = 00.00;
    private String selectedCurrency = "default";
    private HashMap<String, String> map = new HashMap<String, String>();
    private List<String> list = new ArrayList<String>();
    private ArrayAdapter<String> spinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gridView = (GridView) findViewById(R.id.grid_view);
        spinner = (Spinner) findViewById(R.id.currency_selector);
        enterAmount = (EditText) findViewById(R.id.enterAmount);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        //get Currency Rates
        getCurrencyRates(true);

        //Spinner onItemSelection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Appended with "USD" , user to sync with "map" keys format
                selectedCurrency = "USD"+parent.getItemAtPosition(position).toString();
                spinner.setSelection(position);
                selectedPrice = null;
                Log.e("onItemSelected", String.valueOf(selectedPrice) + parent.getItemAtPosition(position).toString());
                if (map.containsKey(selectedCurrency)) {
                    selectedPrice = Double.valueOf(map.get(selectedCurrency));
                    adapter = new currencyDisPlayAdapter(getApplicationContext(), map, selectedCurrency, selectedPrice, targetPrice);
                    gridView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    Log.e("TPonItemSelected", String.valueOf(targetPrice));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Edit Text input from user for targetedPrice
        enterAmount.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                try {
                    targetPrice = Double.valueOf(s.toString());
                    getCurrencyRates(false);

                } catch (Exception e) {
                    targetPrice = 01.00;
                }
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
            }
        });
    }

    //Fetch All currency rates
    private void getCurrencyRates(final Boolean firstTime) {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface = RetrofitInstance.getRetrofitInstance().create(CurrencyAPI.class);
        Call<ResponseBody> call = apiInterface.getCurrencyRates();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    //Clear OLD data
                    list.clear();
                    map.clear();
                    //json response
                    String result = response.body().string();
                    JSONObject ob = new JSONObject(result);
                    JSONObject quotes = ob.getJSONObject("quotes");
                    JSONArray keys = quotes.names();
                    //To iterate over "quotes" object and map currency & its values
                    for (int i = 0; i < keys.length(); i++) {
                        String key = keys.getString(i);
                        String value = quotes.getString(key);
                        map.put(key, value);
                        list.add(keys.getString(i).substring(3));
                    }
                    progressBar.setVisibility(View.GONE);

                } catch (JSONException | IOException e) {
                    progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
                //Load Spinner data with Currencies
                setSpinnerAdapter(list, firstTime);
                //Set Adapter to display Currencies
                setAdapterAndNotify();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Failure", t.toString());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    //Set Data to Adapter and notify when data is updated
    private void setAdapterAndNotify() {
        if (targetPrice != null) {
            adapter = new currencyDisPlayAdapter(getApplicationContext(), map, selectedCurrency, selectedPrice, targetPrice);
            gridView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    //Load data to spinner and set adapter
    private void setSpinnerAdapter(List<String> list, Boolean firstTime) {
        if (list != null && firstTime) {
            spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);
        }
    }
}
