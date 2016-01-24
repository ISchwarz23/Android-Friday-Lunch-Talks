package org.lunchtalk.imagedownloadservice;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;

import java.net.URL;


/**
 * An IntentService specialization which is able to download the image of a given URI.
 *
 * @author ISchwarz
 */
public class ImageDownloadService extends IntentService {

    // Defines a custom Intent action
    public static final String IMAGE_DOWNLOAD_BROADCAST_ACTION = "org.lunchtalk.imagedownloadservice.ImageDownloadService.BROADCAST";
    // Defines the key for the status "extra" in an Intent
    public static final String IMAGE_DOWNLOAD_STATUS = "org.lunchtalk.imagedownloadservice.ImageDownloadService.STATUS";
    // Defines the key for the bitmap image "extra" in an Intent
    public static final String IMAGE_DATA = "org.lunchtalk.imagedownloadservice.ImageDownloadService.DATA";

    public static final int STATUS_FINISHED = 0;
    public static final int STATUS_FAILED = -1;


    public ImageDownloadService() {
        super(ImageDownloadService.getName());
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String imageUrlString = workIntent.getDataString();

        // Do work here
        Bitmap image = downloadBitmap(imageUrlString);

        // Create the result intent
        Intent resultIntent = createResultIntent(image);

        // Publish the result
        LocalBroadcastManager.getInstance(this).sendBroadcast(resultIntent);
    }

    private static Intent createResultIntent(Bitmap image) {
        Intent resultIntent = new Intent(IMAGE_DOWNLOAD_BROADCAST_ACTION);

        if (image == null) {
            resultIntent.putExtra(IMAGE_DOWNLOAD_STATUS, STATUS_FAILED);
        } else {
            resultIntent.putExtra(IMAGE_DOWNLOAD_STATUS, STATUS_FINISHED);
            resultIntent.putExtra(IMAGE_DATA, image);
        }

        return resultIntent;
    }

    private static Bitmap downloadBitmap(String url) {
        Bitmap image = null;
        try {
            URL imageUrl = new URL(url);
            image = BitmapFactory.decodeStream(imageUrl.openStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }

    private static String getName() {
        return "ImageDownloadService";
    }
}
