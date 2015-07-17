package com.thoughtworks.jieshuquan.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.thoughtworks.jieshuquan.Constants;
import com.thoughtworks.jieshuquan.R;
import com.thoughtworks.jieshuquan.activity.MineBookActivity;
import com.thoughtworks.jieshuquan.activity.SettingsActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MineFragment extends Fragment {

    private static final String ARG_USERNAME = "user_name";
    private static final String ARG_PARAM2 = "param2";

    @InjectView(R.id.head_borrow)
    TextView mBadgeBorrow;
    @InjectView(R.id.head_return)
    TextView mBadgeReturn;
    @InjectView(R.id.head)
    RoundedImageView mHeadView;

    private String mUserName;
    private String mParam2;
    private AVUser currentUser;

    private FragmentCallbacks mListener;

    private final int PICK_IMAGE_FROM_GALLERY_REQUEST = 0;
    private final int PICK_IMAGE_FROM_CAMERA_REQUEST = 1;

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (FragmentCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MainActivityListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserName = getArguments().getString(ARG_USERNAME);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.inject(this, view);

        mHeadView.setScaleType(ImageView.ScaleType.FIT_XY);
        currentUser = AVUser.getCurrentUser();

        initHead();

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void refreshAvatar() {
        mHeadView.setImageResource(R.drawable.ic_tab_books);

        currentUser.fetchInBackground(new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                Object object = currentUser.get(Constants.KAVATAR);
                if (object instanceof AVFile) {
                    if (object != null) {
                        String avatar_url = ((AVFile) object).getUrl();

                        if (!TextUtils.isEmpty(avatar_url)) {
                            Glide.with(getActivity()).load(avatar_url).into(mHeadView);
                        }
                    }
                }


            }
        });

    }

    private void initHead() {
        int borrowCount = 1;
        int returnCount = 29;

        setBadgeView(borrowCount, mBadgeBorrow);
        setBadgeView(returnCount, mBadgeReturn);

        refreshAvatar();
    }

    private void setBadgeView(int borrowCount, TextView badgeView) {
        if (borrowCount <= 0) {
            badgeView.setVisibility(View.GONE);
        } else {
            badgeView.setText(String.valueOf(borrowCount));
            badgeView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.list_my_book)
    void showMyBook() {
        Intent intent = new Intent(getActivity(), MineBookActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.list_my_rent)
    void showMyRent() {
        Toast.makeText(getActivity(), "My Rent", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.head)
    void showHeadIcon() {
        new AlertDialog.Builder(this.getActivity()).setTitle(getResources().getString(R.string.choose_avatar)).
                setItems(new String[]{getResources().getString(R.string.choose_avatar_from_gallery),
                        getResources().getString(R.string.choose_avatar_from_camera)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case PICK_IMAGE_FROM_GALLERY_REQUEST: {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.choose_avatar)), i);
                            }
                            break;

                            case PICK_IMAGE_FROM_CAMERA_REQUEST: {
                                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent, i);
                            }
                            break;
                        }

                    }
                }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case PICK_IMAGE_FROM_GALLERY_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        saveAvatar(saveBitmap(selectedImage));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (AVException e) {
                        e.printStackTrace();
                    }


                }

                break;
            case PICK_IMAGE_FROM_CAMERA_REQUEST:
                if (resultCode == Activity.RESULT_OK) {
                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    try {
                        saveAvatar(saveBitmap(thumbnail));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (AVException e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }

    @OnClick(R.id.list_my_setting)
    void showSetting() {
        Intent intent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(intent);
    }

    private String saveBitmap(Bitmap bitmap) {
        String path = null;
        if (bitmap != null) {

            String filename = new SimpleDateFormat("yyMMddHHmmss")
                    .format(new Date());
            path = getActivity().getFilesDir() + "/" + filename;
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(path);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (!bitmap.isRecycled()) {
                bitmap.recycle();
            }
        }

        return path;
    }

    private String saveBitmap(Uri data) {

        InputStream image_stream = null;
        try {
            image_stream = getActivity().getContentResolver().openInputStream(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(image_stream);

        return saveBitmap(bitmap);
    }

    private void saveAvatar(String path) throws IOException, AVException {


        final AVFile file = AVFile.withAbsoluteLocalPath(currentUser.getObjectId(), path);

        file.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                currentUser.setFetchWhenSave(true);
                currentUser.put(Constants.KAVATAR, file);

                currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        refreshAvatar();
                    }
                });


            }
        });

    }


}
