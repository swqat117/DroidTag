package com.quascenta.petersroad.droidtag;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by AKSHAY on 3/2/2017.
 */

public class SharefileProvider extends ContentProvider {
    public static final Uri CONTENT_URI =
            Uri.parse("content://com.quascenta.petersroad.droidtag.sharefileprovider");

    private static final String TAG = "SharefileProvider";

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {

        return "application/pdf";


    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) {
        String path = uri.getPath();
        if (mode.equals("r") &&
                (path.equals("/foo.txt") || path.equals("/bar.txt"))) {
            try {
                return ParcelFileDescriptor.open(
                        new File(getContext().getFilesDir() + path),
                        ParcelFileDescriptor.MODE_READ_ONLY);
            } catch (FileNotFoundException e) {
                Log.e(TAG, "Bad file " + uri);
            }
        }
        return null;
    }

}
