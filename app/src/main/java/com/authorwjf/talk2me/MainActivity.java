package com.authorwjf.talk2me;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    protected static final int REQUEST_OK = 1;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button1).setOnClickListener(this);
        listview = (ListView) findViewById(R.id.resultList);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
        try {
            startActivityForResult(i, REQUEST_OK);
        } catch (Exception e) {
            Toast.makeText(this, "Error initializing speech to text engine.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OK && resultCode == RESULT_OK) {
            ArrayList<String> thingsYouSaid = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            float[] myconfidence = data.getFloatArrayExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES);
            String[] newvar = thingsYouSaid.toArray(new String[thingsYouSaid.size()]);
            for (int i = 0; i < newvar.length; i++) {
                newvar[i] = newvar[i] + " " + myconfidence[i];
            }
            ((TextView) findViewById(R.id.text1)).setText((Float.toString(myconfidence[0])));
            listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, newvar));
            listview.setOnItemClickListener((parent, view, position, id) -> Toast.makeText(getApplicationContext(),
                            "Click ListItem Number " + position, Toast.LENGTH_LONG)
                            .show()
            );
            Log.d("test", "onActivityResult ");
        }
    }

}
