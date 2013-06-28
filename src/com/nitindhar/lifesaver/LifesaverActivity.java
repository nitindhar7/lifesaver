package com.nitindhar.lifesaver;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

import com.nitindhar.lifesaver.data.SessionDao;
import com.nitindhar.lifesaver.data.SessionSharedPreferencesDao;
import com.nitindhar.lifesaver.model.Session;
import com.nitindhar.lifesaver.model.Subway;

public class LifesaverActivity extends Activity implements OnMenuItemClickListener {

    private static SessionDao sessionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createActionBar(this);

        setContentView(R.layout.activity_lifesaver);

        SharedPreferences preferences = getSharedPreferences(getResources()
                .getString(R.string.app_prefs), MODE_PRIVATE);

        sessionDao = new SessionSharedPreferencesDao(preferences);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onStart() {
        super.onStart();
        if(sessionDao.sessionExists()) {
            TextView currentSubway = (TextView) findViewById(R.id.current_subway);
            currentSubway.setText(sessionDao.retrieveSession().getMostRecentSubway().getCode());
            currentSubway.setBackgroundDrawable(
                    this.getResources().getDrawable(
                            getSubwaySplashId(sessionDao.retrieveSession().getMostRecentSubway())));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        PopupMenu popup = new PopupMenu(this, findViewById(item.getItemId()));
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(this);

        switch (item.getItemId()) {
        case R.id.actionbar_settings:
            inflater.inflate(R.menu.actionbar_setting_items, popup.getMenu());
            break;
        default:
            return super.onOptionsItemSelected(item);
        }

        popup.show();
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        TextView currentSubway = (TextView) findViewById(R.id.current_subway);

        Subway subway = null;

        switch (item.getItemId()) {
        case R.id.train_7:
            subway = Subway.TRAIN_7;
            break;
        case R.id.train_n:
            subway = Subway.TRAIN_N;
            break;
        case R.id.train_q:
            subway = Subway.TRAIN_Q;
            break;
        default:
            return false;
        }

        sessionDao.storeSession(new Session(subway));
        currentSubway.setText(subway.getCode());
        currentSubway.setBackgroundDrawable(this.getResources().getDrawable(getSubwaySplashId(subway)));
        return true;
    }

    public static void createActionBar(Activity activity) {
        createActionBar(activity, false);
    }

    public static void createActionBar(Activity activity, boolean backEnabled) {
        ActionBar actionBar = activity.getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setLogo(activity.getResources().getDrawable(
                R.drawable.ic_launcher_lifesaver));
        actionBar.setHomeButtonEnabled(true);
        actionBar.setBackgroundDrawable(activity.getResources().getDrawable(
                R.color.light_gray));
        if(backEnabled) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public static int getSubwaySplashId(Subway subway) {
        switch(subway) {
        case TRAIN_7: return R.drawable.subway_splash_7;
        case TRAIN_N: return R.drawable.subway_splash_nqr;
        case TRAIN_Q: return R.drawable.subway_splash_nqr;
        default:
            throw new IllegalStateException("Unable to select subway splash drawable for " + subway.getCode());
        }
    }

}