package com.nitindhar.lifesaver;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.nitindhar.lifesaver.data.SessionDao;
import com.nitindhar.lifesaver.data.SessionSharedPreferencesDao;
import com.nitindhar.lifesaver.model.Session;
import com.nitindhar.lifesaver.model.Subway;

public class LifesaverActivity extends Activity implements OnItemSelectedListener {

    private static SessionDao sessionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifesaver);

        SharedPreferences preferences = getSharedPreferences(getResources()
                .getString(R.string.app_prefs), MODE_PRIVATE);

        SessionSharedPreferencesDao.setSharedPreferences(preferences);
        sessionDao = SessionSharedPreferencesDao.instance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Subway[] subways = Subway.values();
        Spinner spinner = (Spinner) findViewById(R.id.subway_spinner);
        spinner.setOnItemSelectedListener(this);
        if(sessionDao.sessionExists()) {
            for(int index = 0; index < subways.length; index++) {
                if(sessionDao.retrieveSession().getMostRecentSubway() == subways[index]) {
                    spinner.setSelection(index);
                    break;
                }
            }
        }
        ArrayAdapter<Subway> adapter = new ArrayAdapter<Subway>(this,
                android.R.layout.simple_spinner_item, Subway.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        sessionDao.storeSession(new Session((Subway)parent.getItemAtPosition(pos)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) { /* no-op */}

}