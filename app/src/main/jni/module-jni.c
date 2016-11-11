#include <jni.h>
#include <stdio.h>

#include "openssl/base.h"
#include "openssl/md5.h"

JNIEXPORT jstring JNICALL
Java_yein_checksum_ndkFunc_md5FromC(JNIEnv *env, jobject instance, jstring pac_) {
    const char *pac = (*env)->GetStringUTFChars(env, pac_, 0);

    unsigned  char c[MD5_DIGEST_LENGTH];
    int i;
    char dest[32]={0};
    FILE *fp;
    MD5_CTX mdContext;
    size_t bytes;
    unsigned char data[1024];

    fp = fopen(pac, "rb");
    MD5_Init (&mdContext);
    while(( bytes = fread(data, 1, 1024, fp)) != 0 )
        MD5_Update(&mdContext, data, bytes );
    MD5_Final(c, &mdContext );
    fclose(fp);

    for( i = 0 ; i < MD5_DIGEST_LENGTH ; i++ )
        sprintf(dest+i*2,"%02X",c[i]);

    (*env)->ReleaseStringUTFChars(env, pac_, pac);

    return (*env)->NewStringUTF(env, dest);
}