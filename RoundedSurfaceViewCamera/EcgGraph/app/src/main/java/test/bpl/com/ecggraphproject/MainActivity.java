package test.bpl.com.ecggraphproject;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.LinearLayout;

import java.io.IOException;


public class MainActivity extends FragmentActivity implements SurfaceHolder.Callback{

        RoundedSurfaceView surfaceView;
        SurfaceHolder surfaceHolder;


    LinearLayout fragmentContainer;
    Camera camera1;
    boolean previewing=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        surfaceView= (RoundedSurfaceView) findViewById(R.id.rounded_surfaceview);
        fragmentContainer= (LinearLayout) findViewById(R.id.fragment_container);

        surfaceHolder=surfaceView.getHolder();
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        surfaceHolder.addCallback(MainActivity.this);
        if(!previewing){

            camera1 = Camera.open(1);
            if (camera1 != null){
                try {
                    camera1.setDisplayOrientation(90);
                    camera1.setPreviewDisplay(surfaceHolder);
                    camera1.startPreview();
                    previewing = true;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


fragmentContainer.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if(camera1 != null)
        {
            camera1.takePicture(myShutterCallback, myPictureCallback_RAW, myPictureCallback_JPG);

        }
    }
});





        /*FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        EcgGraphFragment ecg_fragment = new EcgGraphFragment();
        fragmentTransaction.add(R.id.fragment_container,ecg_fragment);
        fragmentTransaction.commit();*/
    }


    Camera.ShutterCallback myShutterCallback = new Camera.ShutterCallback(){

        public void onShutter() {
            // TODO Auto-generated method stub
        }};

    Camera.PictureCallback myPictureCallback_RAW = new Camera.PictureCallback(){

        public void onPictureTaken(byte[] arg0, Camera arg1) {
            // TODO Auto-generated method stub
        }};

    Camera.PictureCallback myPictureCallback_JPG = new Camera.PictureCallback(){

        public void onPictureTaken(byte[] arg0, Camera arg1) {
            // TODO Auto-generated method stub
            Bitmap bitmapPicture = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);

            Bitmap correctBmp = Bitmap.createBitmap(bitmapPicture, 0, 0, bitmapPicture.getWidth(), bitmapPicture.getHeight(), null, true);

        }};

    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        if(previewing){
            camera1.stopPreview();
            previewing = false;
        }

        if (camera1 != null){
            try {
                camera1.setPreviewDisplay(surfaceHolder);
                camera1.startPreview();
                previewing = true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub

    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub

        camera1.stopPreview();
        camera1.release();
        camera1 = null;
        previewing = false;

    }
}
