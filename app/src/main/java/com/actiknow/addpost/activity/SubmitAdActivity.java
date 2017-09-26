package com.actiknow.addpost.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actiknow.addpost.R;
import com.actiknow.addpost.utils.AppConfigTags;
import com.actiknow.addpost.utils.AppConfigURL;
import com.actiknow.addpost.utils.NetworkConnection;
import com.actiknow.addpost.utils.SetTypeFace;
import com.actiknow.addpost.utils.Utils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import static com.actiknow.addpost.activity.MultiImageSelect.imageList;
import static com.actiknow.addpost.activity.MultiImageSelect.imageName;

public class SubmitAdActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int SELECT_PICTURE = 100;

    ImageView ivOne;
    ImageView ivTwo;
    ImageView ivThree;
    ImageView ivFour;
    ImageView ivFive;
    EditText etMessage;
    TextView tvTotalCharacter;
    String imageURI;
    TextView tvTotalWords;
    TextView tvSubmitPost;
    CoordinatorLayout clMain;
    ArrayList<String> encodedImageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_ad_activity);
        initView();
        initListener();
    }

    private void initListener() {
        ivOne.setOnClickListener(this);
        ivTwo.setOnClickListener(this);
        ivThree.setOnClickListener(this);
        ivFour.setOnClickListener(this);
        ivFive.setOnClickListener(this);
        etMessage.addTextChangedListener (new TextWatcher () {
            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                if (s.length() > 0 && etMessage.getText ().toString ().length () > 0) {
                    tvTotalWords.setText("Total words : "+String.valueOf(s.length()));
                } else {
                    tvTotalWords.setText("Total words : 0 ");
                }
                String input=etMessage.getText().toString().trim().replaceAll("\n", "");
                String[] wordCount=input.split("\\s");
                tvTotalCharacter.setText("Total words : "+String.valueOf(wordCount.length));
            }

            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged (Editable s) {
              /*  String input=etMessage.getText().toString().trim().replaceAll("\n", "");
                String[] wordCount=input.split("\\s");
                tvTotalCharacter.setText("Total words : "+String.valueOf(wordCount.length));*/
            }
        });
        tvSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UploadImageVolley ();
            }
        });
    }


    private void UploadImageVolley () {

        if (NetworkConnection.isNetworkAvailable (SubmitAdActivity.this)) {

            Utils.showLog(Log.INFO, "" + AppConfigTags.URL, AppConfigURL.URL_UPLOAD_IMAGE, true);
            StringRequest strRequest1 = new StringRequest(Request.Method.POST, AppConfigURL.URL_UPLOAD_IMAGE,
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //encodedImageList.clear ();
                            Utils.showLog(Log.INFO,AppConfigTags.SERVER_RESPONSE, response, true);
                            if (response != null) {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    boolean error = jsonObj.getBoolean(AppConfigTags.ERROR);
                                    String message = jsonObj.getString(AppConfigTags.MESSAGE);
                                    if (!error) {
                                        Toast.makeText(SubmitAdActivity.this, message,Toast.LENGTH_LONG).show();

                                    } else {
                                        Utils.showSnackBar (SubmitAdActivity.this, clMain, message, Snackbar.LENGTH_LONG, null, null);
                                    }
                                } catch (Exception e) {
                                    Utils.showSnackBar (SubmitAdActivity.this, clMain, getResources ().getString (R.string.snackbar_text_exception_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                                    e.printStackTrace();
                                }
                            } else {
                                Utils.showSnackBar (SubmitAdActivity.this, clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                                Utils.showLog(Log.WARN, AppConfigTags.SERVER_RESPONSE, AppConfigTags.DIDNT_RECEIVE_ANY_DATA_FROM_SERVER, true);
                            }

                        }
                    },
                    new com.android.volley.Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Utils.showLog(Log.ERROR, AppConfigTags.VOLLEY_ERROR, error.toString(), true);
                            Utils.showSnackBar (SubmitAdActivity.this, clMain, getResources ().getString (R.string.snackbar_text_error_occurred), Snackbar.LENGTH_LONG, getResources ().getString (R.string.snackbar_action_dismiss), null);
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new Hashtable<String, String>();
                    JSONArray jsonArray = new JSONArray();
                    for (String encoded: encodedImageList){
                        jsonArray.put(encoded);
                    }
                      //encodedImageList.clear();
                    params.put (imageName, "Sudhanshu");
                    params.put (imageList, String.valueOf(jsonArray));

                    Utils.showLog(Log.INFO, AppConfigTags.PARAMETERS_SENT_TO_THE_SERVER, "" + params, true);
                    return params;
                }

            };
            Utils.sendRequest(strRequest1, 60);
        } else {
            Utils.showSnackBar(this, clMain, getResources().getString(R.string.snackbar_text_no_internet_connection_available), Snackbar.LENGTH_LONG, getResources().getString(R.string.snackbar_action_go_to_settings), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent dialogIntent = new Intent(Settings.ACTION_SETTINGS);
                    dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(dialogIntent);
                }
            });
        }
    }

    private void initView() {
        etMessage = (EditText) findViewById(R.id.etMessage);
        tvTotalWords = (TextView) findViewById(R.id.tvTotalWords);
        tvTotalCharacter = (TextView) findViewById(R.id.tvTotalCharacter);
        ivOne = (ImageView) findViewById(R.id.ivOne);
        ivTwo = (ImageView) findViewById(R.id.ivTwo);
        ivThree = (ImageView) findViewById(R.id.ivThree);
        ivFour = (ImageView) findViewById(R.id.ivFour);
        ivFive = (ImageView) findViewById(R.id.ivFive);
        tvSubmitPost = (TextView) findViewById(R.id.tvSubmitPost);
        clMain = (CoordinatorLayout) findViewById(R.id.clMain);

    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ivOne:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
                break;

            case R.id.ivTwo:
                Intent ii = new Intent();
                ii.setType("image/*");
                ii.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(ii, "Select Picture"), 2);
                break;

            case R.id.ivThree:
                Intent iii = new Intent();
                iii.setType("image/*");
                iii.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(iii, "Select Picture"), 3);
                break;

            case R.id.ivFour:
                Intent iiii = new Intent();
                iiii.setType("image/*");
                iiii.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(iiii, "Select Picture"), 4);
                break;

            case R.id.ivFive:
                Intent iiiii = new Intent();
                iiiii.setType("image/*");
                iiiii.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(iiiii, "Select Picture"), 5);
                break;

        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    if (requestCode == 1) {
                        // Get the url from data
                        Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            // Get the path from the Uri
                            mArrayUri.add(selectedImageUri);
                            String path = getPathFromURI(selectedImageUri);
                          //  String selectedPath1 = getPath(selectedImageUri);
                            Log.e("Image Path : ", "" + path);
                            // Set the image in ImageView
                            ivOne.setImageURI(selectedImageUri);
                        }

                    }
                }
            break;

            case 2:
                if (resultCode == RESULT_OK) {
                    if (requestCode == 2) {
                        // Get the url from data
                        Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            // Get the path from the Uri
                            mArrayUri.add(selectedImageUri);
                            String path = getPathFromURI(selectedImageUri);
                            Log.e("Image Path : ", "" + path);
                            // Set the image in ImageView
                            ivTwo.setImageURI(selectedImageUri);
                        }

                    }
                }
                break;

            case 3:
                if (resultCode == RESULT_OK) {
                    if (requestCode == 3) {
                        // Get the url from data
                        Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            // Get the path from the Uri
                            mArrayUri.add(selectedImageUri);
                            String path = getPathFromURI(selectedImageUri);
                            Log.e("Image Path : ", "" + path);
                            // Set the image in ImageView
                            ivThree.setImageURI(selectedImageUri);
                        }

                    }
                }
                break;

            case 4:
                if (resultCode == RESULT_OK) {
                    if (requestCode == 4) {
                        // Get the url from data
                        Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            // Get the path from the Uri
                            mArrayUri.add(selectedImageUri);
                            String path = getPathFromURI(selectedImageUri);
                            Log.e("Image Path : ", "" + path);
                            // Set the image in ImageView
                            ivFour.setImageURI(selectedImageUri);
                        }

                    }
                }
                break;
            case 5:
                if (resultCode == RESULT_OK) {
                    if (requestCode == 5) {
                        // Get the url from data
                        Uri selectedImageUri = data.getData();
                        if (null != selectedImageUri) {
                            // Get the path from the Uri
                            mArrayUri.add(selectedImageUri);
                            String path = getPathFromURI(selectedImageUri);
                            Log.e("Image Path : ", "" + path);
                            // Set the image in ImageView
                            ivFive.setImageURI(selectedImageUri);
                        }


                    }
                }
                break;
        }
        for(int i = 0; i < mArrayUri.size(); i++){
            Cursor cursor = getContentResolver().query(mArrayUri.get(i),
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imageURI  = cursor.getString(columnIndex);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), mArrayUri.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            String encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            encodedImageList.add(encodedImage);
            cursor.close();
            //encodedImageList.add(mArrayUri.get(i));
        }
        Log.e("EncodedList"," "+encodedImageList.size() +" , " + encodedImageList.toString());
    }


    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }




}
