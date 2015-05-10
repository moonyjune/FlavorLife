package moony.vn.flavorlife.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.edmodo.cropper.CropImageView;


/**
 * Created by moony on 5/7/15.
 */
public class CircleCropImageView extends CropImageView {
    public CircleCropImageView(Context context) {
        super(context);
    }

    public CircleCropImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Gets the cropped circle image based on the current crop selection.
     *
     * @return a new Circular Bitmap representing the cropped image
     */
    public Bitmap getCroppedCircleImage() {
        Bitmap bitmap = getCroppedImage();

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }
}
