LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)
#需要修改输出.so文件的名字字段——本字段和上文的设置相对应。
LOCAL_MODULE := hi_jni
#需要修改src文件名
LOCAL_SRC_FILES := HiJni.cpp
include $(BUILD_SHARED_LIBRARY)
