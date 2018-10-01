package g.cardiact.pkg.com.ecgrealtimegraph;

import android.annotation.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.os.Handler;
import android.support.annotation.*;
import android.util.*;
import android.view.*;

import java.util.*;


public class RealTimeEcgView extends View {

    Paint mPaint;
    int mPixelsPerCm = 0;
    public  String leadArr[]={"I","II","III","aVR","aVL","aVF","V1","V2","V3","V4","V5","V6"};

    float xMargin;
    float yValue=0;
    float xValue=0;

    float  newYValue;
    float newXvalue;


    int mWidth;
    int mHeight;

    Canvas mCanvas;

    Bitmap mGraphImg;
    /** The r1. */
    private Rect r1;

    /** The r2. */
    private Rect r2;

    /** The r3. */
    private Rect r3;

    boolean mDoPaint;
    int count=0;

    int baseXpoint=0;
    List<Integer> floatListpoints;
    private int[] plotPoints;
    boolean mSizechanged;


    float mYplotpoints;
    Path mPath;
     float heightScale;
     float widthScale;



     android.os.Handler handler;

    public RealTimeEcgView(Context context) {
        super(context);
        init();

    }

    public RealTimeEcgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    public RealTimeEcgView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }


    private void init() {
        mPaint = new Paint();

        handler=new Handler();
        final float density = getResources().getDisplayMetrics().densityDpi;

        mPixelsPerCm = (int) (density / Constants.CMS_PER_INCH + 0.5f);

        heightScale = Constants.AMPLITUDE_PER_CM / mPixelsPerCm; //
        widthScale = mPixelsPerCm / Constants.SAMPLES_PER_CM;


        newXvalue= widthScale;

        if(floatListpoints==null){
            floatListpoints=new ArrayList<>();

        }
        }


    @Override
    protected void onDraw(Canvas c) {


        Log.i(":)","Hello OnDraw()");



      if (null == mGraphImg) {

             /** Used Bitmap.Config.ALPHA_8 instead Bitmap.Config.ARGB_8888 to
             * avoid OutOfMemory exception in the application on various phones
             * for example (Samsung Note 2,Micromax Bolt, LG Optimal)
            */
            mGraphImg = Bitmap.createBitmap(this.getWidth(), this.getHeight(),
                    Bitmap.Config.ALPHA_8);
        }
        if (null == mCanvas) {
            mCanvas = new Canvas(mGraphImg);
        }

        if (null == r1) {
            r1 = new Rect(0, 0, getWidth(), getHeight());
        }

        if (null == r2) {
            r2 = new Rect(0, 0, getWidth(), getHeight());

        }

        if (null == r3) {
            r3 = new Rect(0, 0, getWidth(), getHeight());
        }


        if (null != mGraphImg && mDoPaint && !mDrawPoints) {
            c.drawBitmap(mGraphImg, r1, r2, mPaint);
            return;
        }


        //

        mPaint.setColor(0X0082CB00);

        mCanvas.drawRect(r3, mPaint);



        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5f);




        if(mDrawPoints){

           // mCanvas.drawPoint(xValue,yValue,mPaint);
            newYValue=mYplotpoints;
            mCanvas.drawLine(xValue,yValue+baseXpoint,newXvalue,newYValue+baseXpoint,mPaint);
            xValue=newXvalue;
            yValue=newYValue;
            newXvalue= newXvalue+widthScale;



            c.drawBitmap(mGraphImg, r1, r2, mPaint);


            mDoPaint=true;
            mDrawPoints=false;
        }

    }



boolean mDrawPoints;
    boolean mEraser;
int NoOfWidthCrossescount=0;

    public void drawpoints(int yValue)
    {
        mYplotpoints=yValue;
        mDrawPoints=true;
        floatListpoints.add(yValue);
        Log.d("List to plot=", ""+floatListpoints);
        Log.d("newXvallue=", ""+newXvalue +" mWidth="+mWidth);
        mEraser=false;
       if(newXvalue>mWidth) {

            floatListpoints.clear();
            newXvalue = 0;
            xValue=0;
            mEraser=true;

            NoOfWidthCrossescount++;
            topLeft=0;
            blackRect=100;
            Log.d("**********8","Graph reaches to the end ");

        }


        if(NoOfWidthCrossescount>=2){
           erasePartOfCanvas();
        }
        if(mEraser && !startErasing)
        {

            erasePartOfCanvas();
            startRepeatingTask();

            }
        invalidate();

        }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        }

float blackRect=widthScale;
    float topLeft=0;
    boolean startErasing;


    public void eraseFirstPartOfCanvas(){
        // Set eraser paint properties
        Paint eraserPaint=new Paint();
        eraserPaint.setAlpha(0);
        eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        eraserPaint.setAntiAlias(true);
        startErasing=true;
        mCanvas.drawRect(0,0,100,mHeight,eraserPaint);

        //  invalidate();

    }

    public void erasePartOfCanvas(){
            // Set eraser paint properties
            Paint eraserPaint=new Paint();
            eraserPaint.setAlpha(0);
            eraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            eraserPaint.setAntiAlias(true);
            startErasing=true;
            mCanvas.drawRect(topLeft,0,blackRect,mHeight,eraserPaint);

          //  invalidate();

        }


        private void moverRecctangle()
        {
            topLeft=topLeft+(8*widthScale);
            blackRect=blackRect+(8*widthScale);
        }


    public void clearCanvas()
    {
        try {

            mPaint.setColor(Color.TRANSPARENT);
         //   mCanvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR);
            int newWidth=(mCanvas.getWidth()/2);
            mCanvas.drawRect(0,0,newWidth,mHeight,mPaint);
            mDrawPoints=true;

        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("--On size() changed--","width and Height  = "+w+" "+h);
        pxCount++;
        mWidth=w;
        mHeight=h;
        baseXpoint=mHeight/2;
    }


    int pxCount=0;





    // alternate the view's background color
    Runnable mRunnableCode = new Runnable() {
        @Override
        public void run() {
            if (startErasing) {

                moverRecctangle();
                erasePartOfCanvas();

            }

            // repost the code to run again after a delay
            handler.postDelayed(mRunnableCode, 120);
        }
    };


    void startRepeatingTask() {
        mRunnableCode.run();
    }


    void stopRepeatingTask() {
        handler.removeCallbacks(mRunnableCode);
    }


}
