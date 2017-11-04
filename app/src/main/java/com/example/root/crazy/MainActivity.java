package com.example.root.crazy;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.atzcx.appverupdater.AppVerUpdater;
import com.github.atzcx.appverupdater.UpdateErrors;
import com.github.atzcx.appverupdater.callback.Callback;


public class MainActivity extends AppCompatActivity {

    AppVerUpdater appVerUpdater = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        update();
    }


    /* AppVerUpdater code */

    private void update(){

        appVerUpdater = new AppVerUpdater()
                .setUpdateJSONUrl("http://192.168.1.12/update.json") //Todo change address for json location : in json file give apk location
                .setShowNotUpdated(true)
                .setViewNotes(false)
                .setCallback(new Callback() {
                    @Override
                    public void onFailure(UpdateErrors error) {

                        if (error == UpdateErrors.NETWORK_NOT_AVAILABLE) {
                            Toast.makeText(MainActivity.this, "No internet connection.", Toast.LENGTH_LONG).show();
                        }
                        else if (error == UpdateErrors.ERROR_CHECKING_UPDATES) {
                            Toast.makeText(MainActivity.this, "An error occurred while checking for updates.", Toast.LENGTH_LONG).show();
                        }
                        else if (error == UpdateErrors.ERROR_DOWNLOADING_UPDATES) {
                            Toast.makeText(MainActivity.this, "An error occurred when downloading updates.", Toast.LENGTH_LONG).show();
                        }
                        else if (error == UpdateErrors.JSON_FILE_IS_MISSING) {
                            Toast.makeText(MainActivity.this, "Json file is missing.", Toast.LENGTH_LONG).show();
                        }
                        else if (error == UpdateErrors.FILE_JSON_NO_DATA) {
                            Toast.makeText(MainActivity.this, "The file containing information about the updates are empty.", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onDownloadSuccess() {
                        // for example, record/reset license
                    }

                    @Override
                    public void onUpdateChecked(boolean downloading) {
                        // Happens after an update check, immediately after if update check was successful and there
                        // were no dialogs, or, when an update dialog is presented and user explicitly dismissed the dialog.
                        // "downloading" is true if user accepted the update
                        // Typically used for resetting next update check time
                    }
                })
                .setAlertDialogCancelable(true)
                .build(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        appVerUpdater.onResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        appVerUpdater.onStop(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
