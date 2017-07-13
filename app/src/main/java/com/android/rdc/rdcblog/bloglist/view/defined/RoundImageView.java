package com.android.rdc.rdcblog.bloglist.view.defined;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by xjhaobang
 * time: 2016/7/25.
 */
public class RoundImageView extends ImageView {

	Bitmap bitmap;
	Bitmap bitmap1;
	Rect rectSrc;
	Rect rectDest;
	Drawable drawable;
	private Paint paint;

	public RoundImageView(Context context) {
		this(context, null);
	}

	private void init() {
		drawable = getDrawable();
		if (null != drawable) {
			bitmap = ((BitmapDrawable) drawable).getBitmap();
			bitmap1 = getCircleBitmap(bitmap);
			rectSrc = new Rect(0, 0, bitmap1.getWidth(), bitmap1.getHeight());
			rectDest = new Rect(0, 0, getWidth(), getHeight());
		}
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		paint = new Paint();
		init();
	}

	/**
	 * 绘制圆形图片
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		if (null != drawable) {
			bitmap = ((BitmapDrawable) drawable).getBitmap();
			bitmap1 = getCircleBitmap(bitmap);
			rectSrc = new Rect(0, 0, bitmap1.getWidth(), bitmap1.getHeight());
			rectDest = new Rect(0, 0, getWidth(), getHeight());
			paint.reset();
			canvas.drawBitmap(bitmap1, rectSrc, rectDest, paint);
		} else {
			drawable = getDrawable();
			super.onDraw(canvas);
		}
	}

	/**
	 * 获取圆形图片
	 */
	private Bitmap getCircleBitmap(Bitmap bitmap) {
		Bitmap bitmap1 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap1);
		final int color = 0xff424242;
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		int x = bitmap.getWidth();
		canvas.drawCircle(x / 2, x / 2, x / 2, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return bitmap1;
	}
}
