package cz.nkgroup.view;

import cz.nkgroup.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class ScoreTubeView extends SurfaceView implements
		SurfaceHolder.Callback {
	private int i = 0;
	private Bitmap bitmap;
	private int ncolor;
	private FillingThread thread;
	private int score = 0;

	public ScoreTubeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		getHolder().addCallback(this);
		setFocusable(true);
		// this.setBackgroundColor(Color.TRANSPARENT);
		getHolder().setFormat(PixelFormat.TRANSPARENT);
		// setFormat(PixelFormat.TRANSPARENT);
		setZOrderOnTop(true);
		TypedArray customAttrib = context.obtainStyledAttributes(attrs,
				R.styleable.ScoreTubeView);
		String color = customAttrib.getString(R.styleable.ScoreTubeView_color);
		if ("green".equals(color)) {
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.tube_green);
			ncolor = Color.GREEN;
		} else if ("red".equals(color)) {
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.tube_red);
			ncolor = Color.RED;
		} else {
			bitmap = BitmapFactory.decodeResource(getResources(),
					R.drawable.tube_yellow);
			ncolor = Color.YELLOW;
		}
		// getHolder().setFixedSize(bitmap.getWidth(), bitmap.getHeight());
		// TODO Auto-generated constructor stub
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// canvas.drawColor(Color.TRANSPARENT);n

		canvas.drawBitmap(bitmap, 0, 0, null);
		Paint myPaint = new Paint();
		myPaint.setColor(ncolor);
		myPaint.setStyle(Paint.Style.STROKE);
		myPaint.setStrokeWidth(3);
		myPaint.setStyle(Style.FILL);
		int skip = (int) ((float) bitmap.getHeight() * ((float )((float)20/ (float) 177)));
		int width_skip = (int) ((float) bitmap.getWidth() * ((float )((float) 7/ (float) 47)));
		canvas.drawRect(width_skip, bitmap.getHeight() - skip - (2 * i++),
				bitmap.getWidth() - width_skip, bitmap.getHeight() - skip, myPaint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		i = 0;

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		i = 0;
		thread = new FillingThread(this, score);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		boolean retry = true;
		thread.setStop(true);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

	}

	class FillingThread extends Thread {
		private ScoreTubeView view;
		private boolean stop = false;
		private int maxValue;

		public FillingThread(SurfaceView view, int maxValue) {
			this.view = (ScoreTubeView) view;
			this.maxValue = maxValue;
		}

		public void setStop(boolean stop) {
			this.stop = stop;
		}

		public void run() {
			Canvas c;
			int i = -1;
			while ((i < 5*maxValue) && (!stop)) {
				c = null;
				try {
					c = view.getHolder().lockCanvas(null);
					synchronized (view.getHolder()) {
						view.onDraw(c);
						if (i < 0)
							i = 0;
						i++;
					}

				} finally {
					// do this in a finally so that if an exception is thrown
					// during the above, we don't leave the Surface in an
					// inconsistent state
					if (c != null) {
						view.getHolder().unlockCanvasAndPost(c);
					}
				}
			}
		}
	}

}
