package com.example.user.filepicker;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URISyntaxException;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {
    private static final int FILE_SELECT_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b = (Button) this.findViewById(R.id.button);

        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                fileBrowserIntent();
            }
        });

    }

    private void fileBrowserIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // 設定 MIME Type 但這裡是沒用的 加個心安而已
        intent.setType("application/font-sfnt");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(Intent.createChooser(intent, "選擇字型"), FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // 若使用者沒有安裝檔案瀏覽器的 App 則顯示提示訊息
            Toast.makeText(this, "沒有檔案瀏覽器 是沒辦法選擇字型的", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // 檔案選擇代碼
            case FILE_SELECT_CODE:
                // 請求確認返回
                if (resultCode == RESULT_OK) {
                    // 取得檔案路徑 Uri
                    Uri uri = data.getData();
                    // 取得路徑
                    String path = null;
                    try {
                        path = FileUtils.getPath(this, uri);
                        Log.d("path", path);
                    } catch (URISyntaxException e) {
                        Toast.makeText(this, "檔案不對勁!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // 檢查檔案類型
                    String filename = FileUtils.typefaceChecker(path);

                    if( filename.isEmpty() ){
                        Toast.makeText(this, "檔案不對勁!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Do something here...
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
