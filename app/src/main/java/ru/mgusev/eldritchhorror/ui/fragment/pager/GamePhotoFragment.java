package ru.mgusev.eldritchhorror.ui.fragment.pager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.stfalcon.frescoimageviewer.ImageViewer;

import java.io.File;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.mgusev.eldritchhorror.BuildConfig;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.androidmaterialgallery.GalleryAdapter;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.GamePhotoPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.GamePhotoView;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class GamePhotoFragment extends MvpAppCompatFragment implements GamePhotoView, OnItemClicked, ImageViewer.OnDismissListener, ImageViewer.OnImageChangeListener {

    @InjectPresenter
    GamePhotoPresenter gamePhotoPresenter;

    @BindView(R.id.recyclerViewGallery) RecyclerView recyclerViewGallery;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_TAKE_PHOTO_FROM_GALLERY = 553;
    private static final int RC_READ_STORAGE = 5;

    private Unbinder unbinder;
    private int columnsCount = 2;
    private GalleryAdapter galleryAdapter;
    private List<String> urlList;
    private ImageViewer imageViewer;
    private boolean imageViewerIsOpen;
    private AlertDialog deleteDialog;

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

        if (!MainActivity.initialized) {
            Intent firstIntent = new Intent(getContext(), MainActivity.class);
            firstIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // So all other activities will be dumped
            startActivity(firstIntent);
        }

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
        galleryAdapter.addGalleryItems(imagesUrlList);
        urlList = imagesUrlList;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            gamePhotoPresenter.changeGallery();
            gamePhotoPresenter.addImageFile();
        } else if (requestCode == REQUEST_TAKE_PHOTO_FROM_GALLERY && resultCode == RESULT_OK) {
            gamePhotoPresenter.onSelectImagesFromImagePicker(ImagePicker.getImages(data));
        } else if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_CANCELED) {
            gamePhotoPresenter.deleteFile(new File(gamePhotoPresenter.getCurrentPhotoURI().getPath()));
        }
    }

    @Override
    public void showDeleteDialog() {
        if (deleteDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
            builder.setCancelable(false);
            builder.setTitle(R.string.dialogAlert);
            builder.setMessage(R.string.delete_photo_dialog_message);
            builder.setIcon(R.drawable.delete);
            builder.setPositiveButton(R.string.messageOK, (DialogInterface dialog, int which) -> {
                gamePhotoPresenter.deleteSelectedFiles();
                gamePhotoPresenter.dismissDeleteDialog();
            });
            builder.setNegativeButton(R.string.messageCancel, (DialogInterface dialog, int which) -> gamePhotoPresenter.dismissDeleteDialog());
            deleteDialog = builder.show();
        }
    }

    @Override
    public void hideDeleteDialog() {
        deleteDialog = null;
        //Delete showDeleteDialog() from currentState with DismissDialogStrategy
    }

    @Override
    public void dispatchTakePictureIntent() {
        //check for read storage permission
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCameraIntent();
        } else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, RC_READ_STORAGE);
        }
    }

    private void startCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getContext()).getPackageManager()) != null) {
            // Continue only if the File was successfully created
            File file = gamePhotoPresenter.getNewImageFile();
            if (file != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID  + ".provider", file));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                gamePhotoPresenter.setCurrentPhotoURI(Uri.fromFile(file));
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (imageViewer != null) {
            imageViewerIsOpen = true;
            imageViewer.onDismiss();
        }
        if(deleteDialog != null && deleteDialog.isShowing()) deleteDialog.dismiss();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == RC_READ_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                startCameraIntent();
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        if (gamePhotoPresenter.isSelectMode()) gamePhotoPresenter.selectGalleryItem(urlList.get(position), position);
        else {
            gamePhotoPresenter.setCurrentPosition(position);
            gamePhotoPresenter.openFullScreenPhotoViewer();
        }
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
    public void openFullScreenPhotoViewer() {
        imageViewer = new ImageViewer.Builder(getContext(), urlList)
                .setImageChangeListener(this)
                .setOnDismissListener(this)
                .setStartPosition(gamePhotoPresenter.getCurrentPosition())
                .show();
    }

    @Override
    public void closeFullScreenPhotoViewer() {
        if (imageViewer != null) imageViewer.onDismiss();
    }

    @Override
    public void onDismiss() {
        if (!imageViewerIsOpen) gamePhotoPresenter.closeFullScreenPhotoViewer();
    }

    @Override
    public void onImageChange(int position) {
        gamePhotoPresenter.setCurrentPosition(position);
    }


    @Override
    public void openImagePicker() {
        ImagePicker.create(this) // Activity or Fragment
                .returnMode(ReturnMode.NONE) // set whether pick and / or camera action should return immediate result or not.
                .folderMode(true) // folder mode (false by default)
                .toolbarFolderTitle(getString(R.string.folders_header)) // folder selection title
                .toolbarImageTitle(getString(R.string.select_image_header)) // image selection title
                .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
                .includeVideo(false) // Show video on image picker
                //.single() // single mode
                //.multi() // multi mode (default mode)
                .limit(10) // max images can be selected (99 by default)
                .showCamera(false) // show camera or not (true by default)
                //.imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                //.origin(images) // original selected images, used in multi mode
                //.exclude(images) // exclude anything that in image.getPath()
                //.excludeFiles(files) // same as exclude but using ArrayList<File>
                .theme(R.style.AppTheme) // must inherit ef_BaseTheme. please refer to sample
                .enableLog(false) // disabling log
                //.imageLoader(new GrayscaleImageLoder()) // custom image loader, must be serializeable
                .start(); // start image picker activity with request code
    }
}