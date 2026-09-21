package ru.mgusev.eldritchhorror.ui.fragment.pager;

import android.Manifest;
import android.content.ClipData;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import ru.mgusev.eldritchhorror.BuildConfig;
import ru.mgusev.eldritchhorror.R;
import ru.mgusev.eldritchhorror.databinding.FragmentGamePhotoBinding;
import ru.mgusev.eldritchhorror.utils.PhotoViewerDialog;
import ru.mgusev.eldritchhorror.utils.gallery.GalleryAdapter;
import ru.mgusev.eldritchhorror.interfaces.OnItemClicked;
import ru.mgusev.eldritchhorror.interfaces.OnShareImageClick;
import ru.mgusev.eldritchhorror.presentation.presenter.pager.GamePhotoPresenter;
import ru.mgusev.eldritchhorror.presentation.view.pager.GamePhotoView;
import ru.mgusev.eldritchhorror.ui.activity.main.MainActivity;
import timber.log.Timber;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static ru.mgusev.eldritchhorror.presentation.presenter.pager.StartDataPresenter.ARGUMENT_PAGE_NUMBER;

public class GamePhotoFragment extends MvpAppCompatFragment implements GamePhotoView, OnItemClicked, OnShareImageClick {

    @InjectPresenter
    GamePhotoPresenter gamePhotoPresenter;

    private FragmentGamePhotoBinding binding;

    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_TAKE_PHOTO_FROM_GALLERY = 553;
    static final int REQUEST_CAMERA_PERMISSION = 554;

    private int columnsCount = 2;
    private GalleryAdapter galleryAdapter;
    private List<String> urlList;
    private Dialog imageDialog;
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

        binding = FragmentGamePhotoBinding.inflate(inflater, container, false);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) columnsCount = 3;

        binding.recyclerViewGallery.setLayoutManager(new GridLayoutManager(getContext(), columnsCount));
        galleryAdapter = new GalleryAdapter(getContext());
        binding.recyclerViewGallery.setAdapter(galleryAdapter);
        galleryAdapter.setOnClick(this);

        return binding.getRoot();
    }

    @Override
    public void updatePhotoGallery(List<String> imagesUrlList) {
        galleryAdapter.addGalleryItems(imagesUrlList);
        urlList = imagesUrlList;
    }

    @Override
    public void showPhotoNoneMessage(boolean isShow) {
        binding.gamePhotoEmptyScroll.setVisibility(isShow ? View.VISIBLE : View.GONE);
        binding.recyclerViewGallery.setVisibility(isShow ? View.GONE : View.VISIBLE);
        if (isShow) updatePhotoNoneMessageHeight();
    }

    private void updatePhotoNoneMessageHeight() {
        binding.gamePhotoNone.post(() -> {
            if (binding == null) return;
            ViewGroup.LayoutParams layoutParams = binding.gamePhotoNone.getLayoutParams();
            int itemHeight = binding.gamePhotoNone.getWidth() / columnsCount;
            if (layoutParams.height != itemHeight) {
                layoutParams.height = itemHeight;
                binding.gamePhotoNone.setLayoutParams(layoutParams);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            gamePhotoPresenter.changeGallery();
            gamePhotoPresenter.addImageFile();
        } else if (requestCode == REQUEST_TAKE_PHOTO_FROM_GALLERY && resultCode == RESULT_OK) {
            List<Uri> selectedUris = new ArrayList<>();
            if (data != null && data.getClipData() != null) {
                for (int index = 0; index < data.getClipData().getItemCount(); index++) {
                    selectedUris.add(data.getClipData().getItemAt(index).getUri());
                }
            } else if (data != null && data.getData() != null) {
                selectedUris.add(data.getData());
            }
            gamePhotoPresenter.onSelectImageUris(selectedUris);
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
        Context context = getContext();
        if (context == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            return;
        }
        startCameraIntent();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode != REQUEST_CAMERA_PERMISSION) return;

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCameraIntent();
        } else {
            Context context = getContext();
            if (context != null) {
                Toast.makeText(context, R.string.camera_permission_denied, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void startCameraIntent() {
        Context context = getContext();
        if (context == null) return;

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(context.getPackageManager()) == null) {
            Toast.makeText(context, R.string.camera_app_unavailable, Toast.LENGTH_LONG).show();
            return;
        }

        File file = gamePhotoPresenter.getNewImageFile();
        if (file == null) {
            Toast.makeText(context, R.string.camera_file_unavailable, Toast.LENGTH_LONG).show();
            return;
        }

        try {
            Uri outputUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
            takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            takePictureIntent.setClipData(ClipData.newRawUri("output", outputUri));
            gamePhotoPresenter.setCurrentPhotoURI(Uri.fromFile(file));
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        } catch (ActivityNotFoundException | IllegalArgumentException | SecurityException exception) {
            Timber.e(exception, "Не удалось открыть камеру");
            Toast.makeText(context, R.string.camera_open_error, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        if (imageDialog != null && imageDialog.isShowing()) imageDialog.dismiss();
        if(deleteDialog != null && deleteDialog.isShowing()) deleteDialog.dismiss();
        binding = null;
        super.onDestroyView();
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
    public void selectGalleryItem(List<String> list, int position) {
        galleryAdapter.selectGalleryItem(list, position);
    }

    @Override
    public void openFullScreenPhotoViewer() {
        imageDialog = PhotoViewerDialog.show(
                requireContext(),
                Uri.parse(urlList.get(gamePhotoPresenter.getCurrentPosition())),
                dialog -> gamePhotoPresenter.closeFullScreenPhotoViewer());
    }

    @Override
    public void closeFullScreenPhotoViewer() {
        if (imageDialog != null && imageDialog.isShowing()) imageDialog.dismiss();
    }

    @Override
    public void openImagePicker() {
        Intent selectImagesIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        selectImagesIntent.setType("image/*");
        selectImagesIntent.addCategory(Intent.CATEGORY_OPENABLE);
        selectImagesIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(selectImagesIntent, REQUEST_TAKE_PHOTO_FROM_GALLERY);
    }

    @Override
    public void onClickShare() {
        gamePhotoPresenter.shareImage(true);
    }
}
