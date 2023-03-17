package com.example.shoesstore.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.shoesstore.MenuSelection;
import com.example.shoesstore.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class UserProfile extends Fragment {

    private View mView;
    private ImageView imgAvatar;
    private EditText edUserName,edEmail;
    private Button btnUpdateUserProfile;
    private Uri seletedImage;
    private ProgressDialog dialog;


    private ActivityResultLauncher<Intent> mActivityResult=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK);
                    Intent i=result.getData();
                    if(i==null){
                        return;
                    }
                    seletedImage=i.getData();
                    imgAvatar.setImageURI(seletedImage);

                }
            });
    MenuSelection menuSelection=(MenuSelection) getActivity();
    public UserProfile() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_user_profile,container,false);
        initUI();

        setUserInformation();
        initListener();

        return mView;
    }

    private void initUI(){
        imgAvatar=(ImageView) mView.findViewById(R.id.imgAvatar);
        edUserName=mView.findViewById(R.id.edUserName);
        edEmail=mView.findViewById(R.id.edEmail);
        btnUpdateUserProfile=mView.findViewById(R.id.btnUpdateProfile);
        dialog=new ProgressDialog(getActivity());

    }
    private void initListener() {
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openClickRequestPermission(); dung update profile nhung da dung ImagePicker thay the.

                ImagePicker.with(UserProfile.this)
//                        .crop()	    			//Crop image(Optional), Check Customization for more option
//                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        //kiểm tra thử xem có ask permission trước không
                        //nếu không thì xem xét dùng lại hàm openClickRequestPermission
                        .createIntent(intent -> {
                            mActivityResult.launch(intent);
                            return null;
                        });

            }
        });
        btnUpdateUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onClickUpdateProfile();
            }
        });
    }

    private void onClickUpdateProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            return;
        }
        dialog.show();
        String strUserName=edUserName.getText().toString().trim();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(strUserName)
                .setPhotoUri(seletedImage)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        dialog.dismiss();
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Update Profile Success.",
                                    Toast.LENGTH_SHORT).show();
//                           //update fragment home( su dung FragmentResultListener)
                            Bundle result = new Bundle();
                            result.putString("bundleKey", "result");
                            getParentFragmentManager().setFragmentResult("requestKey", result);
                        }
                    }
                });
    }

    private void setUserInformation() {
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        if(user ==null){
            return;
        }
        else{
            edEmail.setText(user.getEmail());
            edUserName.setText(user.getDisplayName());
            Glide.with(getActivity()).load(user.getPhotoUrl()).error(R.drawable.ic_light_user).into(imgAvatar);

        }
    }



//    public void setBitmapImage(Bitmap imageView){
//        imgAvatar.setImageBitmap(imageView);
//    }
//    private void openClickRequestPermission() {
//        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
//            openGallery();
//            return;
//        }
//        if(ActivityCompat.checkSelfPermission(getContext(),
//                Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
//            openGallery();
//        }
//        else {
//            String [] permission={Manifest.permission.READ_EXTERNAL_STORAGE};
//            getActivity().requestPermissions(permission,MY_REQUEST_CODE);
//        }
//
//    }
//    public void openGallery() {
//        Intent i=new Intent();
//        i.setType("image/*");
//        i.setAction(Intent.ACTION_GET_CONTENT);
//        mActivityResult.launch(Intent.createChooser(i,"Select Picture"));
//
//    }
}