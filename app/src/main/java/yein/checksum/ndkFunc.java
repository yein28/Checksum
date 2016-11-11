package yein.checksum;

/**
 * Created by Yein on 2016-09-06.
 */
public class ndkFunc {
    public native String md5FromC(String pac);
    static
    {
        System.loadLibrary("module-jni");
    }
}
