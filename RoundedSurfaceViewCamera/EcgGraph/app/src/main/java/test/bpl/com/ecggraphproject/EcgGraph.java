package test.bpl.com.ecggraphproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by admin on 15-12-2016.
 */

public class EcgGraph extends View {


    Paint mPaint;
    int pixels_per_cm;

    public EcgGraph(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EcgGraph(Context context) {
        super(context);
        init();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint=new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(android.R.color.holo_green_dark));

       // canvas.drawCircle(getWidth()/2,getHeight()/2,150,paint);

        Rect rect=new Rect(0,0,getWidth(),getHeight());
        Paint p=new Paint();
        p.setAntiAlias(true);
        p.setColor(getResources().getColor(R.color.white));
        canvas.drawRect(rect,p);
    }


    public void init()
    {
        mPaint=new Paint();
        int density=getResources().getDisplayMetrics().densityDpi;  // for mdpi 160 dpi
        Log.i("Get density of phones ",""+density);

        float xdpi = getResources().getDisplayMetrics().xdpi;
        float ydpi = getResources().getDisplayMetrics().ydpi;

        float inches = 0.5f/2.54f;
        // xDots = inches * xdpi;
        // yDots = inches * ydpi;


        pixels_per_cm=(int) (density/2.54f+0.5f)  ;




    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        WindowManager wm = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        setMeasuredDimension(35*pixels_per_cm,42*pixels_per_cm);
    }
}
