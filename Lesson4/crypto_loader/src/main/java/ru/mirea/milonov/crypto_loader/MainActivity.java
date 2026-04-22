package ru.mirea.milonov.crypto_loader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import ru.mirea.milonov.crypto_loader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String>{

    private ActivityMainBinding binding;
    
    public	final	String	TAG	=	this.getClass().getSimpleName();
    private	final	int	LoaderID	=	1234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    public void onClickButton(View view) throws Exception {
        Bundle bundle = new Bundle();
        String data = binding.textView.getText().toString();
        String secret = "burmaldaburmalda";
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secret.getBytes(), "AES"));
        byte[] encrypted = cipher.doFinal(data.getBytes("UTF-8"));
        bundle.putString(MyLoader.ARG_WORD_ENCRYPTED, Base64.getEncoder().encodeToString(encrypted));
        bundle.putString(MyLoader.ARG_WORD_SECRET, secret);
        LoaderManager.getInstance(this).restartLoader(LoaderID, bundle, this);
    }

    @NonNull
    @Override
    public	Loader<String>	onCreateLoader(int	i,	@Nullable	Bundle	bundle)	{
        if	(i == LoaderID)	{
            Log.d("MainActivity", "Loader created");
            return	new	MyLoader(this, bundle);
        }
        throw	new InvalidParameterException("Invalid	loader	id");
    }
    @Override
    public void onLoadFinished(@NonNull	Loader<String>	loader,	String	s)	{
        if	(loader.getId()	== LoaderID) {
            Log.d(TAG,	"onLoadFinished: " + s);
            Toast.makeText(this,"onLoadFinished: "	+ s, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        Log.d(TAG,	"onLoaderReset");
    }
}