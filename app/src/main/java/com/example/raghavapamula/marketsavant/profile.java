package com.example.raghavapamula.marketsavant;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicConvolve3x3;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import jp.wasabeef.picasso.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.picasso.transformations.gpu.PixelationFilterTransformation;

import static com.example.raghavapamula.marketsavant.R.id.coin_icon;

public class profile extends AppCompatActivity {

    String[] the_coin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        the_coin = getIntent().getStringArrayExtra("coin");
        //setSupportActionBar(toolbar);

        ImageView graph = (ImageView) findViewById(R.id.coin_icon);
        String url = "https://files.coinmarketcap.com/generated/sparklines/1.png";
        Picasso.with(getApplicationContext()).load(url).into(graph);

        ImageView coinpic = (ImageView) findViewById(R.id.ivContactItem1);
        String url2 = "https://coinmarketcap.com/static/img/coins/16x16/" + the_coin[1].toLowerCase() + ".png";
        Picasso.with(getApplicationContext())
                .load(url2)
                .resize(150, 150)
                .centerCrop()
                //.transform(new Sharpen())
                //.transform(new PixelationFilterTransformation(getApplicationContext()))
                .into(coinpic);

        //Picasso.with(context).load(url).centerCrop().fit().into(iv);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}
