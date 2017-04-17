package test.bpl.com.ecggraphproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Created by admin on 18-01-2017.
 */

public class RoundedSurfaceView extends SurfaceView {

    Path clipPath;


    public RoundedSurfaceView(Context context) {
        super(context);
        init();
    }

    public RoundedSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundedSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    private void init()
    {
        clipPath=new Path();
        clipPath.addCircle(300,200,200, Path.Direction.CCW);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.clipPath(clipPath);
        super.dispatchDraw(canvas);
    }
}
