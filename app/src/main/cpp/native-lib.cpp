#include <jni.h>
#include <string>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>

extern "C"
jstring
Java_com_example_ethan_rccontroller_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello world from C++";
    return env->NewStringUTF(hello.c_str());
}
