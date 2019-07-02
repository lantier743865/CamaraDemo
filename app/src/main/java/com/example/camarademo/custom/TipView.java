package com.example.camarademo.custom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import com.example.camarademo.R;

public class TipView extends View {
    private static final String TAG = "TipView";

    private int widthPixels; // 屏幕宽度
    private Paint mPaint; // 画笔
    private Point mPoint;// 左上角的坐标


    private float routateDegrees; // 旋转角度
    private float mWidth; // 矩形的宽
    private float mHeight;  // 矩形的高

    private Bitmap verticaline;
    private Bitmap horizontalline;
    private Bitmap leftTopBitmap;
    private Bitmap rightTopBitmap;
    private Bitmap leftBottomBitmap;
    private Bitmap rightBottomBitmap;


    private Bitmap verticalineWhite;
    private Bitmap horizontallineWhite;
    private Bitmap leftTopBitmapWhite;
    private Bitmap rightTopBitmapWhite;
    private Bitmap leftBottomBitmapWhite;
    private Bitmap rightBottomBitmapWhite;

    private Bitmap verticalineYellow;
    private Bitmap horizontallineYellow;
    private Bitmap leftTopBitmapYellow;
    private Bitmap rightTopBitmapYellow;
    private Bitmap leftBottomBitmapYellow;
    private Bitmap rightBottomBitmapYellow;

    private Bitmap verticalineYellowThin;
    private Bitmap horizontallineYellowThin;

    private int vertialThinLineX;
    private int horizontalThinLineY;


    public TipView(Context context) {
        super(context);
        init(context);
    }

    public TipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        Resources resources = getResources();
        // 屏幕宽度
        widthPixels = resources.getDisplayMetrics().widthPixels;
        // 左上角的坐标
        mPoint = new Point(widthPixels / 4, widthPixels / 4);
        mWidth = widthPixels / 2;
        mHeight = widthPixels / 2;
        // 画笔
        mPaint = new Paint();
        // 绘制用到的图
        initBitmap(resources);
    }

    private void initBitmap(Resources resources) {
        Matrix matrix = new Matrix();
        matrix.setRotate(90);
        // 白粗线
        initLineBitmap(resources, matrix);
        initCornerBitmap(resources, matrix);
        setWhiteTheme();
    }

    public void setWhiteTheme() {
        verticaline = verticalineWhite;
        horizontalline = horizontallineWhite;
        leftTopBitmap = leftTopBitmapWhite;
        rightTopBitmap = rightTopBitmapWhite;
        leftBottomBitmap = leftBottomBitmapWhite;
        rightBottomBitmap = rightBottomBitmapWhite;
    }

    public void setYellowTheme() {
        verticaline = verticalineYellow;
        horizontalline = horizontallineYellow;
        leftTopBitmap = leftTopBitmapYellow;
        rightTopBitmap = rightTopBitmapYellow;
        leftBottomBitmap = leftBottomBitmapYellow;
        rightBottomBitmap = rightBottomBitmapYellow;
    }

    private void initCornerBitmap(Resources resources, Matrix matrix) {
        // 白转角
        leftTopBitmapWhite = BitmapFactory.decodeResource(resources, R.mipmap.ic_photo_tip_corner_white);
        int height = leftTopBitmapWhite.getHeight();
        int width = leftTopBitmapWhite.getWidth();
        rightTopBitmapWhite = Bitmap.createBitmap(leftTopBitmapWhite,0,0,width,height,matrix,true);
        rightBottomBitmapWhite = Bitmap.createBitmap(rightTopBitmapWhite,0,0,width,height,matrix,true);
        leftBottomBitmapWhite = Bitmap.createBitmap(rightBottomBitmapWhite,0,0,width,height,matrix,true);
        // 黄转角
        leftTopBitmapYellow = BitmapFactory.decodeResource(resources, R.mipmap.ic_photo_tip_corner_yellow);
        height = leftTopBitmapWhite.getHeight();
        width = leftTopBitmapWhite.getWidth();
        rightTopBitmapYellow= Bitmap.createBitmap(leftTopBitmapYellow,0,0,width,height,matrix,true);
        rightBottomBitmapYellow = Bitmap.createBitmap(rightTopBitmapYellow,0,0,width,height,matrix,true);
        leftBottomBitmapYellow = Bitmap.createBitmap(rightBottomBitmapYellow,0,0,width,height,matrix,true);
    }

    private void initLineBitmap(Resources resources, Matrix matrix) {
        verticalineWhite = BitmapFactory.decodeResource(resources, R.mipmap.ic_photo_tip_line_white);
        int lineHeight = verticalineWhite.getHeight();
        int lineWidth = verticalineWhite.getWidth();
        horizontallineWhite = Bitmap.createBitmap(verticalineWhite,0,0,lineWidth,lineHeight,matrix,true);

        // 黄粗线
        verticalineYellow = BitmapFactory.decodeResource(resources, R.mipmap.ic_photo_tip_line_yellow);
        lineHeight = verticalineYellow.getHeight();
        lineWidth = verticalineYellow.getWidth();
        horizontallineYellow = Bitmap.createBitmap(verticalineYellow,0,0,lineWidth,lineHeight,matrix,true);
        // 黄细线
        verticalineYellowThin = BitmapFactory.decodeResource(resources, R.mipmap.ic_photo_tip_line_yellow_thin);
        lineHeight = verticalineYellowThin.getHeight();
        lineWidth = verticalineYellowThin.getWidth();
        horizontallineYellowThin = Bitmap.createBitmap(verticalineYellowThin,0,0,lineWidth,lineHeight,matrix,true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthPixels,widthPixels);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        test01(canvas);

        canvas.save();
        routateView(canvas);
        drawRect(canvas);
        drawLine(canvas);
        drawThinLine(canvas);
        canvas.restore();

    }

    private void drawThinLine(Canvas canvas) {
        if (vertialThinLineX != 0) {
            canvas.drawBitmap(verticalineYellowThin,mPoint.x + mWidth /2 - verticalineYellowThin.getWidth() /2 + vertialThinLineX,
                    mPoint.y - verticalineYellowThin.getHeight(),mPaint);
            canvas.drawBitmap(verticalineYellowThin,mPoint.x + mWidth /2 - verticalineYellowThin.getWidth() /2 + vertialThinLineX,
                    mPoint.y  + mHeight,mPaint);
        }
        if (horizontalThinLineY != 0) {
            canvas.drawBitmap(horizontallineYellowThin,mPoint.x - horizontallineYellowThin.getWidth(),
                    mPoint.y + mHeight/2  - horizontallineYellowThin.getHeight()/2  + horizontalThinLineY,mPaint);
            canvas.drawBitmap(horizontallineYellowThin,mPoint.x + mWidth,
                    mPoint.y +   mHeight/2 - horizontallineYellowThin.getHeight()/2  + horizontalThinLineY,mPaint);
        }
    }

    private void test01(Canvas canvas) {
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(mPoint.x,mPoint.y,mPoint.x+mWidth,mPoint.y+mHeight,mPaint);
        mPaint.setColor(Color.WHITE);
        canvas.drawLine(mPoint.x+mWidth/2,0,mPoint.x+mWidth/2,widthPixels,mPaint);
        canvas.drawLine(0,mPoint.y+mHeight/2,widthPixels,mPoint.y+mHeight/2,mPaint);
    }

    private void routateView(Canvas canvas) {
        canvas.rotate(routateDegrees,mPoint.x + mWidth /2,mPoint.y + + mHeight /2);
    }

    private void drawLine(Canvas canvas) {
        canvas.drawBitmap(verticaline,mPoint.x + mWidth /2 - verticaline.getWidth() /2 ,mPoint.y - verticaline.getHeight(),mPaint);
        canvas.drawBitmap(verticaline,mPoint.x + mWidth /2 - verticaline.getWidth() /2,mPoint.y + mHeight,mPaint);
        canvas.drawBitmap(horizontalline,mPoint.x - horizontalline.getWidth(),mPoint.y + mHeight /2 - horizontalline.getHeight()/2,mPaint);
        canvas.drawBitmap(horizontalline,mPoint.x + mWidth ,mPoint.y + mHeight /2 - horizontalline.getHeight()/2,mPaint);
    }

    private void drawRect(Canvas canvas) {
        canvas.drawBitmap(leftTopBitmap,mPoint.x,mPoint.y,mPaint);
        canvas.drawBitmap(rightTopBitmap,mPoint.x + mWidth - rightTopBitmap.getWidth(),mPoint.y,mPaint);
        canvas.drawBitmap(rightBottomBitmap,mPoint.x + mWidth - rightBottomBitmap.getWidth(),
                mPoint.y + mHeight - rightBottomBitmap.getHeight(),mPaint);
        canvas.drawBitmap(leftBottomBitmap,mPoint.x ,mPoint.y + mHeight - leftBottomBitmap.getHeight(),mPaint);
    }



    public void setStartPoint(Point point) {
        this.mPoint = point;
    }


    public void setWidth(float width) {
        this.mWidth = width;
        this.mHeight = width;
    }

    public void setRoutateDegrees(float degrees) {
        this.routateDegrees = degrees;
    }


    public void setVertialThinLineX(int x) {
        this.vertialThinLineX = x;
    }

    public void setHorizontalThinLineY(int y) {
        this.horizontalThinLineY = y;
    }


    public void reDraw() {
        invalidate();
    }

}
