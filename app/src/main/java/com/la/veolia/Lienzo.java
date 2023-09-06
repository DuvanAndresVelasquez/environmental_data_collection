package com.la.veolia;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class Lienzo extends View {

    private android.graphics.Path drawPath;
    private static Paint drawPaint;
    private Paint canvasPaint;
    private int paintColor = 0xFF000000;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    static float TamanyoPunto;

    public Lienzo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }
    private void setupDrawing(){
        drawPath = new android.graphics.Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        //setTamanyoPunto(20);
        drawPaint.setStrokeWidth(10);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    protected void onSizeChanged (int w, int h, int oldw, int oldh){
        super.onSizeChanged(w,  h, oldw ,oldh);
        canvasBitmap = Bitmap.createBitmap(w , h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(canvasBitmap , 0,0, canvasPaint);
        canvas.drawPath(drawPath,drawPaint);
    }

    public boolean onTouchEvent (MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        //drawPaint.setStrokeWidth(7);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.lineTo(touchX, touchY);
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void setColor(String newColor){
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    public void NuevoDibujo() {
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void setTamanyoPunto(float nuevoTamanyo){
        //float pixel= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, nuevoTamanyo,
          //      getResources().getDisplayMetrics());
        //TamanyoPunto = pixel;
        drawPaint.setStrokeWidth(TamanyoPunto);

    }

}
