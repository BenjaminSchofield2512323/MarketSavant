package com.example.raghavapamula.marketsavant;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import de.codecrafters.tableview.SortableTableView;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.SwipeToRefreshListener;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;

import static com.example.raghavapamula.marketsavant.R.id.tableView;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements MyResultReceiver.Receiver {
    MyResultReceiver mReceiver;
    SwipeToRefreshListener.RefreshIndicator fake = null;
    static String[][] coins;

    private static String[] array = {};
    ArrayAdapter<String> adapter;
    AutoCompleteTextView textView;
    MenuItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceiver = new MyResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        Intent i = new Intent(this, Fetcher.class);
        i.putExtra("ticker", "goog");
        i.putExtra("receiverTag", mReceiver);
        this.startService(i);

        LayoutInflater inflater = getLayoutInflater();
        //RelativeLayout l = (RelativeLayout) inflater.inflate(R.layout.activity_main, null, false);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        // actionBar.setDisplayShowTitleEnabled(false);
        // actionBar.setIcon(R.drawable.ic_action_search);

        LayoutInflater inflator = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflator.inflate(R.layout.actionbar, null);

        getSupportActionBar().setCustomView(v);
        ArrayList<String> lst = new ArrayList<String>(Arrays.asList(array));

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, lst);
        textView = (AutoCompleteTextView) v
                .findViewById(R.id.editText1);
        textView.setAdapter(adapter);

        //Define the AutoComplete Threshold
        textView.setThreshold(1);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    item.setVisible(true);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    Log.d("username", SignupActivity.user);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.action_user);
        textView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                item.setVisible(false);

            }
        });

        return true;
    }

    int i = 0;
    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        final String[] DATA_TO_SHOW = { "Price", "Name", "Symbol", "MarketCap"};
        // TODO Auto-generated method stub
        String[][] coinsa = (String[][]) resultData.getSerializable("coins");
        coins = coinsa;
        Log.d("sohail", coins[242][1]);
        SortableTableView<String[]> tableView = (SortableTableView<String[]>) findViewById(R.id.tableView);
        tableView.setColumnComparator(0, new CoinComparator(0));
        tableView.setColumnComparator(1, new CoinComparator(1));
        tableView.setColumnComparator(2, new CoinComparator(2));
        tableView.setColumnComparator(3, new CoinComparator(3));
        tableView.setColumnWeight(0, 2);
        tableView.setColumnWeight(1, 3);
        tableView.setColumnWeight(2, 2);
        tableView.setColumnWeight(3, 3);
        final SimpleTableDataAdapter yay = new SimpleTableDataAdapter(this, coins);
        if (i == 0) {
        tableView.setDataAdapter(yay);
        }
        else {
            tableView.setDataAdapter(yay);
            fake.hide();
        }
        tableView.addDataClickListener(new ProfileClickListener());
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(this, DATA_TO_SHOW));
        int colorEvenRows = getResources().getColor(R.color.silver);
        int colorOddRows = getResources().getColor(R.color.white);
        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));
        tableView.setSwipeToRefreshEnabled(true);
        final Activity b = this;
        adapter.clear();
        ArrayList<String> lst = new ArrayList<String>(Arrays.asList(array));
        for (int i = 0 ; i < coins.length ; i++) {
            for (int j = 0 ; j < coins.length; j++) {
                String col = coins[j][1];
                lst.add(col);
            }
        }
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, lst);

        textView.setAdapter(adapter);

        adapter.getFilter().filter(textView.getText(), null);
        tableView.setSwipeToRefreshListener(new SwipeToRefreshListener() {
            @Override
            public void onRefresh(final RefreshIndicator refreshIndicator) {
                i  += 1;
                // your async refresh action goes here
                mReceiver = new MyResultReceiver(new Handler());
                mReceiver.setReceiver(MainActivity.this);
                Intent i = new Intent(MainActivity.this, Fetcher.class);
                i.putExtra("receiverTag", mReceiver);
                MainActivity.this.startService(i);

                fake = refreshIndicator;
                //stop the indicator later
            }
        });

    }

    private class ProfileClickListener implements TableDataClickListener<String[]> {
        @Override
        public void onDataClicked(int rowIndex, String[] clickedCoin) {
            //String clickedCarString = clickedCar.getProducer().getName() + " " + clickedCar.getName();
            Toast.makeText(getApplicationContext(), clickedCoin[1], Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, profile.class);
            intent.putExtra("coin", clickedCoin);
            MainActivity.this.startActivity(intent);
        }
    }

    private static class CoinComparator implements Comparator<String[]> {
        int attribute = 0;

        CoinComparator(int x) {
            attribute = x;
        }

        Position a = new Position();

        public int compare(String[] coins1, String[] coins2) {
            int p = a.returnPosition(coins1);
            int q = a.returnPosition(coins2);
            try {
                Float l = Float.valueOf(coins[p][attribute]);
                Float m = Float.valueOf(coins[q][attribute]);
                int cmp = l > m ? -1 : l < m ? +1 : 0;
                return cmp;
            } catch (NumberFormatException a) {
                return coins[p][attribute].compareTo(coins[q][attribute]);

            }


        }
    }

    public static class Position {

        public int returnPosition(String[] x) {
            int a = 0;
            for (int i = 0; i < 710; ++i) {
                if (coins[i] == x) {
                    // Found the correct i,j - print them or return them or whatever
                    a = i;
                    break;
                }

            }
            return a;
        }
    }

}
