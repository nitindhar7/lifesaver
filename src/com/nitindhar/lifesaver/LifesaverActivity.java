package com.nitindhar.lifesaver;

import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.TextView;

import com.nitindhar.lifesaver.data.SessionDao;
import com.nitindhar.lifesaver.data.SessionSharedPreferencesDao;

public class LifesaverActivity extends Activity implements OnMenuItemClickListener {

    private static SessionDao sessionDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifesaver);

        createActionBar(this);

        SharedPreferences preferences = getSharedPreferences(getResources()
                .getString(R.string.app_prefs), MODE_PRIVATE);

        sessionDao = new SessionSharedPreferencesDao(preferences);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(sessionDao.sessionExists()) {
            TextView currentSubway = (TextView) findViewById(R.id.current_subway);
            currentSubway.setText(sessionDao.retrieveSession().getMostRecentSubway().toString());
        }

        //sessionDao.storeSession(new Session((Subway)parent.getItemAtPosition(pos)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        MenuItem actionbarRefresh = menu.findItem(R.id.actionbar_refresh);
        actionbarRefresh.setActionView(spinner);
        postsTask.setActionbarRefresh(actionbarRefresh);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        PopupMenu popup = new PopupMenu(this, findViewById(item.getItemId()));
//        MenuInflater inflater = popup.getMenuInflater();
//        popup.setOnMenuItemClickListener(this);
//
//        switch (item.getItemId()) {
//        case R.id.actionbar_settings:
//            inflater.inflate(R.menu.actionbar_setting_items, popup.getMenu());
//            break;
//        case R.id.actionbar_refresh:
//            item.setActionView(spinner);
//            postsTask = new PostsTask(this, posts);
//            postsTask.execute("all");
//            postsTask.setActionbarRefresh(item);
//            break;
//        default:
//            return super.onOptionsItemSelected(item);
//        }
//
//        popup.show();
//        return true;
//    }
//
//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        postsTask = new PostsTask(this, posts);
//
//        switch (item.getItemId()) {
//        case R.id.settings_menu_logout:
//            executor.submit(new LogoutTask());
//            Intent kampr = new Intent(PostsActivity.this, KamprActivity.class);
//            startActivity(kampr);
//            return true;
//        default:
//            return false;
//        }
//    }

    public static void createActionBar(Activity activity) {
        createActionBar(activity, false);
    }

    public static void createActionBar(Activity activity, boolean backEnabled) {
        ActionBar actionBar = activity.getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setLogo(activity.getResources().getDrawable(
                R.drawable.ic_launcher));
        actionBar.setHomeButtonEnabled(true);
        if(backEnabled) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}