package com.example.spyapp;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.util.Log;
import android.view.SurfaceView;

public class Picture {
	


public Picture() {
		super();
	}

public void takePictureNoPreview(Context context) {
    // open back facing camera by default
    Camera myCamera=Camera.open();

    if(myCamera!=null){
      try{
        //set camera parameters if you want to
        //...

        // here, the unused surface view and holder
        SurfaceView dummy=new SurfaceView(context);
      //  myCamera.setPreviewDisplay(dummy.getHolder());    
        myCamera.startPreview(); 

        myCamera.takePicture(null, null, getJpegCallback());

      }finally{}}
       
            

    }


    private PictureCallback getJpegCallback(){
      PictureCallback jpeg=new PictureCallback() {   
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
          FileOutputStream fos;
          try {
            fos = new FileOutputStream("test.jpeg");
            fos.write(data);
            fos.close();
          }  catch (IOException e) {
            //do something about it
          }
        }
      };
	return jpeg;
    
  }


//Selecting front facing camera.

private Camera openFrontFacingCameraGingerbread() {
int cameraCount = 0;
Camera cam = null;
Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
cameraCount = Camera.getNumberOfCameras();
for ( int camIdx = 0; camIdx < cameraCount; camIdx++ ) {
  Camera.getCameraInfo( camIdx, cameraInfo );
  if ( cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT  ) {
      try {
          cam = Camera.open( camIdx );
      } catch (RuntimeException e) {
          Log.d("ok", "Camera failed to open: " + e.getLocalizedMessage());
      }
  }
}

return cam;
}
}
