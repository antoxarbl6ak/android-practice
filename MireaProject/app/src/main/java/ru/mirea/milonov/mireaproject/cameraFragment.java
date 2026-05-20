package ru.mirea.milonov.mireaproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.mirea.milonov.mireaproject.databinding.FragmentCameraBinding;


public class cameraFragment extends Fragment {
    private static final int REQUEST_CODE_PERMISSION = 100;
    private boolean isWork = false;
    private Uri imageUri;
    private File photo;
    private OnPhotoTakenListener listener;
    private FragmentCameraBinding binding;

    public interface OnPhotoTakenListener {
        void onPhotoTaken(String photoPath);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnPhotoTakenListener) {
            listener = (OnPhotoTakenListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCameraBinding.inflate(inflater, container, false);


        int cameraPermissionStatus = ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA);

        if (cameraPermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[] {Manifest.permission.CAMERA}, REQUEST_CODE_PERMISSION);
        }
        ActivityResultCallback<ActivityResult> callback = new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent intent = result.getData();
                    binding.photo.setImageURI(imageUri);
                }
            }
        };
        ActivityResultLauncher<Intent> cameraActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), callback);

        Button button = binding.photoButton;
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("QueryPermissionsNeeded")
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (isWork && intent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    try {
                        photo = createImageFile();
                        imageUri = FileProvider.getUriForFile(requireContext(),
                                requireContext().getPackageName() + ".fileprovider",
                                photo);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        cameraActivityResultLauncher.launch(intent);
                    } catch (IOException ex) {
                        Toast.makeText(getContext(), "Ошибка создания файла", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return binding.getRoot();
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ENGLISH).format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";

        File storageDirectory = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDirectory);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                isWork = true;
                Toast.makeText(binding.photo.getContext(), "Разрешение получено", Toast.LENGTH_SHORT).show();
            } else {
                isWork = false;
                Toast.makeText(binding.photo.getContext(), "Разрешение не получено", Toast.LENGTH_SHORT).show();
            }
        }
    }
}