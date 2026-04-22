package ru.mirea.milonov.crypto_loader;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public	class MyLoader extends AsyncTaskLoader<String> {
    private	String encryptedData;
    private String secret;
    public static final	String ARG_WORD_ENCRYPTED = "en";
    public static final	String ARG_WORD_SECRET = "sec";
    public MyLoader(@NonNull Context context, Bundle args)	{
        super(context);
        if	(args != null) {
            encryptedData = args.getString(ARG_WORD_ENCRYPTED);
            secret = args.getString(ARG_WORD_SECRET);
        }
    }
    @Override
    protected void onStartLoading()	{
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public	String	loadInBackground()	{
        try {
            Log.d("Loader", "encrypted: " + encryptedData + " secret: " + secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(secret.getBytes(), "AES"));
            byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decrypted, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}