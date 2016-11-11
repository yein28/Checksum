package yein.checksum;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    TextView _appLocation;
    TextView _md5_java;
    TextView _md5_c;

    String md5_java;
    String md5_c=null;
    String appLoc;

    ndkFunc ndk = new ndkFunc();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this._md5_c = (TextView)findViewById(R.id.tv_c);
        this._md5_java = (TextView)findViewById(R.id.tv_java);
        this._appLocation = (TextView)findViewById(R.id.tv_appLocation);

        // app Location
        this.appLoc = getApplicationContext().getPackageCodePath();
        this._appLocation.setText(appLoc);

        // get MD5 in java
        try {
            md5_java = getMd5();
            this._md5_java.setText(md5_java);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // get MD5 in C , it's ndk func
        md5_c = ndk.md5FromC( this.appLoc );
        this._md5_c.setText(md5_c);

    }

    // MD5 Java Code
    private String getMd5() throws NoSuchAlgorithmException, FileNotFoundException {
        String result;
        File file = new File(getApplicationContext().getPackageCodePath());
        MessageDigest md = MessageDigest.getInstance("MD5");
        InputStream input = new DigestInputStream(new BufferedInputStream(new FileInputStream(file)),md);
        try{
            while(input.read() != -1 );
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                input.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
        byte[] hash = md.digest();
        StringBuilder sb = new StringBuilder();
        for(byte b:hash)
            sb.append(String.format("%02X",b));
        result = sb.toString();
        return result;
    }
}
