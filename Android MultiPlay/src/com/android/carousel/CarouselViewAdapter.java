package com.android.carousel;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CarouselViewAdapter extends BaseAdapter {

	int mGalleryItemBackground;
	private Context context;

	private List<CarouselDataItem> mDocus;
	int m_w, m_h;

	public CarouselViewAdapter(Context context, List<CarouselDataItem> docuList,
			int image_max_w, int image_max_h) {
		m_w = image_max_w;
		m_h = image_max_h;
		mDocus = docuList;
		this.context = context;
	}

	private Bitmap createReflectedImage(Bitmap bmp) {
		if (bmp == null)
			return null;
		// The gap we want between the reflection and the original image
		final int reflectionGap = 0;
		int width = bmp.getWidth();
		int height = bmp.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);

		int reflection_part = 4;
		Bitmap reflectionImage = Bitmap.createBitmap(bmp, 0, height - height
				/ reflection_part, width, height / reflection_part, matrix,
				false);
		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / reflection_part), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bmp, 0, 0, null);
		Paint deafaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, height, 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
				0x00ffffff, TileMode.CLAMP);
		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;

	}

	public int getCount() {
		return Integer.MAX_VALUE;
	}

	public Object getItem(int position) {
		if (mDocus.size() == 0)
			return null;

		if (position >= mDocus.size()) {
			position = position % mDocus.size();
		}
		// return position;
		return mDocus.get(position);
	}

	public long getItemId(int position) {
		if (mDocus.size() == 0)
			return 0; // fix divide by zero on filtering
		if (position >= mDocus.size()) {
			position = position % mDocus.size();
		}
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		if (position >= mDocus.size()) {
			position = position % mDocus.size();
		}

		Bitmap originalImage = BitmapFactory.decodeResource(context.getResources(),mDocus.get(position).getIconId());
		Bitmap reflection = createReflectedImage(originalImage);
		if (originalImage != null)
			originalImage.recycle();
		CarouselViewItem scaIv = new CarouselViewItem(
				context, reflection,
				mDocus.get(position).getTitle(),
				Color.WHITE,
				Constants.m_nTextSizeMedium,
				m_w, m_h);

		return scaIv;
	}

	public int checkPosition(int position) {
		if (position >= mDocus.size()) {
			position = position % mDocus.size();
		}
		return position;
	}

	public float getScale(boolean focused, int offset) {
		return Math.max(0, 1.0f / (float) Math.pow(2, Math.abs(offset)));
	}

	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

}
