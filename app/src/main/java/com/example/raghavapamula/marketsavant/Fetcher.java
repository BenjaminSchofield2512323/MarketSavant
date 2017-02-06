package com.example.raghavapamula.marketsavant;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static android.R.attr.x;

/**
 * Created by raghavapamula on 12/29/16.
 */

public class Fetcher extends IntentService {

    public static final int STATUS_RUNNING = 0;
    public static final int STATUS_FINISHED = 1;
    public static final int STATUS_ERROR = 2;

    private static final String TAG = "F";

    public Fetcher() {
        super(Fetcher.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.d(TAG, "Service Started!");

        final ResultReceiver receiver = intent.getParcelableExtra("receiverTag");
        String url = (String) intent.getStringExtra("ticker");

        Bundle bundle = new Bundle();
        String[][] coins = new String[710][10];

        try {
            coins = getCoins();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Serializable coins1 = (Serializable) coins;
        String[][] coins2 = (String[][]) coins1;

        //Log.d("hilo", coins2[709][0]);
        //  Log.d(TAG, price);
        bundle.putSerializable("coins", coins);

        //bundle.putString("price", price);

        //bundle.putDouble(url, price);
        receiver.send(STATUS_FINISHED, bundle);

        Log.d(TAG, "Service Stopping!");
        this.stopSelf();
    }

    static String[][] getCoins() throws JSONException {
        JsonArray zipcode;
        try {
            URL coinApi = new URL("http://coinmarketcap.northpole.ro/api/v5/all.json");
            URLConnection connection = coinApi.openConnection();
            connection.getContent();
            InputStreamReader is = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(is);

            connection.connect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) connection.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            zipcode = rootobj.get("markets").getAsJsonArray(); //just grab the zipcode

        } catch (IOException e) {
            Logger log = Logger.getLogger(Fetcher.class.getName());
            log.log(Level.SEVERE, e.toString(), e);
            return null;
        }

        String[][] coins = new String[zipcode.size()][10];

            for (int i = 0; i < zipcode.size(); i++) {
                //Log.d(TAG, zipcode.get(i).toString());
                JSONObject mJsonObject = new JSONObject(zipcode.get(zipcode.size() - 1 - i).toString());
                //System.out.println(mJsonObject.getJSONObject("price").getString("usd"));
                //System.out.println(mJsonObject.getJSONObject("price").getString("btc"));

                //hi.setPrice((mJsonObject.getJSONObject("price").getString("usd")));
                coins[i][0] = (mJsonObject.getJSONObject("price").getString("usd"));

                //hi.setName((mJsonObject.getJSONObject("name").toString()));
                coins[i][1] = ((mJsonObject.get("name").toString()));
                Log.d(TAG, ((mJsonObject.get("name").toString())));

                //hi.setSymbol((mJsonObject.getJSONObject("symbol").toString()));
                coins[i][2] = ((mJsonObject.get("symbol").toString()));

                //hi.setPrice((mJsonObject.getJSONObject("marketCap").getString("usd")));
                coins[i][3] = ((mJsonObject.getJSONObject("marketCap").getString("usd")));

                //hi.setAvailableSupply((mJsonObject.getJSONObject("availableSupplyNumber").toString()));
                coins[i][4] = ((mJsonObject.get("availableSupplyNumber").toString()));

                //hi.setVolume24((mJsonObject.getJSONObject("volume24").getString("usd")));
                coins[i][5] = ((mJsonObject.getJSONObject("volume24").getString("usd")));

                //hi.setChange1h(mJsonObject.getJSONObject("change1h").getString("usd"));
                coins[i][6] = (mJsonObject.getJSONObject("change1h").getString("usd"));

                //hi.setChange7h((mJsonObject.getJSONObject("change7h").getString("usd")));
                coins[i][7] = ((mJsonObject.getJSONObject("change7h").getString("usd")));

                //hi.setChange7d((mJsonObject.getJSONObject("change7d").getString("usd")));
                coins[i][8] = ((mJsonObject.getJSONObject("change7d").getString("usd")));

                //hi.setTimeStamp(((mJsonObject.getJSONObject("timestamp").toString())));
                coins[i][9] = (((mJsonObject.get("timestamp").toString())));

            }

            //Log.d("BTC_Price", coins[709][9]);

        return coins;
    }

}
