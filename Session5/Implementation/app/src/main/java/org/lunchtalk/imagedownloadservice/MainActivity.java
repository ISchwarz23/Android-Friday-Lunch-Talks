package org.lunchtalk.imagedownloadservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;


/**
 * Simple Activity which communicates with the ImageDownloadService via Intents.
 *
 * @author ISchwarz
 */
public class MainActivity extends AppCompatActivity {

    private static final String IMAGE_URI = "https://pbs.twimg.com/profile_images/1630430857/aaug_500x500logo_transparent.png";

    private ImageView imageView;
    private Button loadImageButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        loadImageButton = (Button) findViewById(R.id.loadImageButton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // The filter's action is BROADCAST_ACTION
        IntentFilter intentFilter = new IntentFilter(ImageDownloadService.IMAGE_DOWNLOAD_BROADCAST_ACTION);
        // Instantiates a new DownloadStateReceiver
        DownloadStateReceiver downloadStateReceiver = new DownloadStateReceiver();
        // Registers the DownloadStateReceiver and its intent filters
        LocalBroadcastManager.getInstance(this).registerReceiver(downloadStateReceiver, intentFilter);

        loadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // show progress indicator
                imageView.setImageBitmap(null);
                progressBar.setVisibility(View.VISIBLE);
                // download image by service
                Intent serviceIntent = new Intent(MainActivity.this, ImageDownloadService.class);
                serviceIntent.setData(Uri.parse(IMAGE_URI));
                startService(serviceIntent);
            }
        });
    }

    private class DownloadStateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // remove progress indicator
            progressBar.setVisibility(View.INVISIBLE);

            // check the status
            if (intent.getIntExtra(ImageDownloadService.IMAGE_DOWNLOAD_STATUS, -1) == ImageDownloadService.STATUS_FINISHED) {
                // set image
                Bitmap image = intent.getParcelableExtra(ImageDownloadService.IMAGE_DATA);
                imageView.setImageBitmap(image);
            } else {
                imageView.setImageResource(R.mipmap.ic_error);
            }
        }
    }

}
