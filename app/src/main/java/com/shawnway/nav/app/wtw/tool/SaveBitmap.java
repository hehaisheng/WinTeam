package com.shawnway.nav.app.wtw.tool;

import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/2/8.
 */

public class SaveBitmap {
    public static Bitmap convertViewToBitmap(ImageView view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.buildDrawingCache();

        return view.getDrawingCache();
    }

    public void saveImage(ImageView view,String strFileName) {
        Bitmap bitmap = convertViewToBitmap(view);
        String strPath = getSDPath();

        try {
            File destDir = new File(strPath);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }

            File imageFile = new File(strPath + "/" + strFileName);
            imageFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSDPath() {
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) {
            return Environment.getExternalStorageDirectory().toString() + "/saving_picture";
        } else
            return "/data/data/com.shawnway.nav.app.wtw/saving_picture";
    }

}
