package com.example.user.filepicker;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;


import java.io.File;
import java.net.URISyntaxException;
/**
 * Created by USER on 2017/9/15.
 */

public class FileUtils {
    /**
     * 由外部 Activity 回傳的資料取得檔案路徑
     * @param context   Activity
     * @param uri       Uri
     * @return
     *  檔案路徑
     * @throws URISyntaxException
     */
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Do nothing
            }
            finally {
                if( cursor != null ) cursor.close();
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * 檢查取回的檔案副檔名
     * @param path  檔案路徑
     * @return
     *  若檔案類型正確則回傳檔案名稱, 反之則回傳空字串
     */
    public static String typefaceChecker(String path){
        if( path == null || path.isEmpty() ) return "";

        File file = new File(path);

        if( !file.exists() || !file.isFile() ) {
            return "";
        }

        String filename = file.getName();
        return filename;
    }
}

