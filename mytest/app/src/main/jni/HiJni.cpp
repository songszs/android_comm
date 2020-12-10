//
// Created by Zang,Song on 2020-12-08.
//
#include "com_zs_test_jni_HiJni.h"
#include <jni.h>
#include <string.h>

#ifdef __cplusplus
extern "C" {
#endif
JNIEXPORT jstring JNICALL Java_com_zs_test_jni_HiJni_sayHello
        (JNIEnv *env, jobject obj) {
    return env->NewStringUTF("hello i am c++");
}

// 动态注册
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    //函数映射关系
    const JNINativeMethod method[] = {
            {"setNum", "(Ljava/lang/String;)I", (void *) setNum},
    };

    // 映射方法
    JNIEnv *env = NULL;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }
    const char *classSign = "com/zs/test/jni/HiJni";
    jclass jClassName = env->FindClass(classSign);
    jint ret = env->RegisterNatives(jClassName, method, 1);
    if (ret != JNI_OK) {
        return -1;
    }
    return JNI_VERSION_1_6;
}
#ifdef __cplusplus
}
#endif

jint setNum(JNIEnv *env, jobject javaObj, jstring num) {
    callJavaMethod(env, javaObj, num);
    return 0;
}

int callJavaMethod(JNIEnv *env, jobject javaObj, jstring javaName) {
    if (env == nullptr) {
        return -1;
    }
    // 找到类中的方法
    jclass targetClass = env->GetObjectClass(javaObj);
    const char *methodSign = "(Ljava/lang/String;)V";
    jmethodID methodId = env->GetMethodID(targetClass, "setName", methodSign);

    // 调用方法
    env->CallVoidMethod(javaObj, methodId, javaName);
    return 0;
}




