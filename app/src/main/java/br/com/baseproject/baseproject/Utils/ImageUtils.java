package br.com.baseproject.baseproject.Utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

/**
 * Created by mvenosa on 12/11/17.
 */

/*

    ImageUtils imageHelper = new ImageUtils();

    Add this code to your onActivityResult to receive the image from the camera!

    if (requestCode == imageHelper.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
        Bundle extras = data.getExtras();
        Bitmap imageBitmap = (Bitmap) extras.get("data");
        mImageView.setImageBitmap(imageBitmap);
    }

    Add this code to your onActivityResult to receive the image from the gallery!

   if (requestCode == imageHelper.REQUEST_LOAD_IMGAGE && resultCode == RESULT_OK) {
        try {
            final Uri imageUri = data.getData();
            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
            mImageView.setImageBitmap(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(PostImage.this, "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }

    Add this code to your onActivityResult to receive the cropped image from the gallery!
    if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (resultCode == RESULT_OK) {
          Uri resultUri = result.getUri();
        } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
          Exception error = result.getError();
        }
      }

 */

public class ImageUtils {

    public final int REQUEST_IMAGE_CAPTURE = 1;
    public final int REQUEST_LOAD_IMGAGE = 2;

    public void dispatchTakePictureIntent(Activity activity) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void dispatchGalleryImageIntent(Activity activity){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        if (photoPickerIntent.resolveActivity(activity.getPackageManager()) != null){
            photoPickerIntent.setType("image/*");
            activity.startActivityForResult(photoPickerIntent, REQUEST_LOAD_IMGAGE);
        }
    }

    // Uses Android-Image-Cropper: https://github.com/ArthurHub/Android-Image-Cropper

    public void cropImage(Activity activity, Uri uri){
        CropImage.activity(uri)
                .start(activity);
    }

    public void getAndCropImage(Activity activity){
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(activity);
    }
}
