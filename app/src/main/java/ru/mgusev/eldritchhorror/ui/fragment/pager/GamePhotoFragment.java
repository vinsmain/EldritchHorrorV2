package ru.mgusev.eldritchhorror.ui.fragment.pager;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.androidmaterialgallery.GalleryAdapter;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.GamePhotoPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.GamePhotoView;

import static android.app.Activity.RESULT_OK;
import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class GamePhotoFragment extends MvpAppCompatFragment implements GamePhotoView, OnItemClicked, ImageViewer.OnDismissListener {

    @InjectPresenter
    GamePhotoPresenter gamePhotoPresenter;

    @BindView(R.id.recyclerViewGallery) RecyclerView recyclerViewGallery;

    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int RC_READ_STORAGE = 5;

    private Unbinder unbinder;
    private int columnsCount = 2;
    private GalleryAdapter galleryAdapter;
    private Uri photoURI;
    private List<String> urlList;
    private ImageViewer imageViewer;

    public static GamePhotoFragment newInstance(int page) {
        GamePhotoFragment pageFragment = new GamePhotoFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_PAGE_NUMBER, page);
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //pageNumber = getArguments().getInt(ARGUMENT_PAGE_NUMBER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_photo, null);
        unbinder = ButterKnife.bind(this, view);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) columnsCount = 3;

        recyclerViewGallery.setLayoutManager(new GridLayoutManager(getContext(), columnsCount));
        galleryAdapter = new GalleryAdapter(getContext());
        recyclerViewGallery.setAdapter(galleryAdapter);
        galleryAdapter.setOnClick(this);

        return view;
    }

    @Override
    public void updatePhotoGallery(List<String> imagesUrlList) {
        //check for read storage permission
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            galleryAdapter.addGalleryItems(imagesUrlList);
            urlList = imagesUrlList;
        } else {
            //request permission
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, RC_READ_STORAGE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            gamePhotoPresenter.updateGallery();
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Objects.requireNonNull(getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
    }

    @Override
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getContext()).getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getContext(), "Error!", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getContext(), "com.example.android.provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (imageViewer != null) imageViewer.onDismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                gamePhotoPresenter.updateGallery();
            } else {
                Toast.makeText(getContext(), "Storage Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        if (gamePhotoPresenter.isSelectMode()) gamePhotoPresenter.selectGalleryItem(urlList.get(position), position);
        else gamePhotoPresenter.openFullScreenPhotoViewer(position);
    }

    @Override
    public void onItemLongClick(int position) {
        if (!gamePhotoPresenter.isSelectMode()) {
            gamePhotoPresenter.setSelectMode(true);
            gamePhotoPresenter.selectGalleryItem(urlList.get(position), position);
        }
    }

    @Override
    public void onEditClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {

    }

    @Override
    public void selectGalleryItem(List<String> list, int position) {
        galleryAdapter.selectGalleryItem(list, position);
    }

    @Override
    public void openFullScreenPhotoViewer(int position) {
        imageViewer = new ImageViewer.Builder(getContext(), urlList)
                .setOnDismissListener(this)
                .setStartPosition(position)
                .show();
    }

    @Override
    public void onDismiss() {
        System.out.println("DISSMISS");
    }
}