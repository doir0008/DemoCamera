package com.algonquincollege.doir0008.democamera;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Demonstrate how to take a photo using Android's Camera.
 *
 * Features:
 *      a) Implicit style of an Intent.
 *      b) How to start another activity, which returns a result.
 *
 * Assumption:
 *      a) Emulator and/or real device must support a Camera.
 *
 * Limitations:
 *      a) Emulator takes photo on its side
 */

public class MainActivity extends AppCompatActivity {

    private final int CAMERA_REQUEST_CODE = 100;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                startActivityForResult( intent, CAMERA_REQUEST_CODE );
            }
        });

        imageView = (ImageView) findViewById(R.id.imageView);
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

    /**
     * This method is called after the user finishes using the Camera.
     *
     * @param requestCode The integer request code originally supplied to startActivityForResults(),
     *                    allowing you to identity who this result came from.
     * @param resultCode The integer result code returned by the child activity through its
     *                   setResult();
     * @param resultIntent An Intent, which can return result data to caller (various data can
     *                     be attached to Intent "extras".
     */
    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent resultIntent ) {
        Bundle extras;
        Bitmap imageBitmap;

        // Did the user cancel the Camera?
        // Abort
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText( getApplicationContext(), "Cancelled!", Toast.LENGTH_SHORT).show();
            return;
        }

        // We need a switch statement because this method could be called if our app called other
        // activities. For example, we could extend the app to take a Video. Then we would have a
        // case like: case VIDEO_REQUEST_CODE: // code to handle the video capture.
        switch ( requestCode ) {
            case CAMERA_REQUEST_CODE:
                // the photo is returned as an extra to the Intent
                extras = resultIntent.getExtras();
                // the photo is referred to by "data", and is returned as a Bitmap.
                imageBitmap = (Bitmap) extras.get( "data" );

                // display the photo
                // set the <ImageView> to the photo
                if ( imageBitmap != null ) {
                    imageView.setImageBitmap( imageBitmap );
                }
                break;
        }
    }
}
