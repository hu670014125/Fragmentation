package me.yokeyword.demo2;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;

import com.android.dx.Code;
import com.android.dx.DexMaker;
import com.android.dx.FieldId;
import com.android.dx.Local;
import com.android.dx.MethodId;
import com.android.dx.TypeId;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import dalvik.system.DexClassLoader;

import static java.lang.reflect.Modifier.FINAL;
import static java.lang.reflect.Modifier.PUBLIC;

/**
 * Android 虚拟机字节码生成工具
 *
 * @author huqs
 */
public class DexMakerUtils {

    /**
     * 动态生成Android 虚拟机字节码
     *
     * @param context         Context
     * @param superClass      Class<?>  需要基础的父类
     * @param targetClassName 要生成类的全名如：android.support.v7.app.AppCompatActivity
     */
    public static <T> T generateClass(Context context, Class<?> superClass, String targetClassName) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        DexMaker dexMaker = new DexMaker();
        // 需要继承的父类
        TypeId<?> superClassTypeId = TypeId.get(superClass);
        TypeId<T> targetClassTypeId = TypeId.get(String.format("L%s;", targetClassName.replace(".", "/")));
        // 生成指定类
        dexMaker.declare(targetClassTypeId, "android dex virtual bytecode", Modifier.PUBLIC, superClassTypeId);


//        TypeId<T> generated = TypeId.get("LGenerated;");
        FieldId<T, Integer> fieldId = targetClassTypeId.getField(TypeId.INT, "a");
        FieldId<T, String> fieldIdAbc = targetClassTypeId.getField(TypeId.STRING, "abc");

        // 声明类变量
        dexMaker.declare(fieldId, PUBLIC | FINAL, null);
        dexMaker.declare(fieldIdAbc, PUBLIC | FINAL, null);

        // 生成够着函数
        MethodId<?, Void> constructor = targetClassTypeId.getConstructor(TypeId.INT);
        Code code = dexMaker.declare(constructor, PUBLIC);
        Local<T> thisRef = code.getThis(targetClassTypeId);
        Local<Integer> parameter = code.getParameter(0, TypeId.INT);
        code.invokeDirect(TypeId.OBJECT.getConstructor(), null, thisRef);
        // 给指定的变量赋值，this.a=a
        code.iput(fieldId, thisRef, parameter);
        code.returnVoid();
        File dexOutputDir = context.getDir("dex", Context.MODE_PRIVATE);
        File dexOutputDir2 = context.getDir("dex2", Context.MODE_PRIVATE);
        ClassLoader classLoader = dexMaker.generateAndLoad(context.getClassLoader(), dexOutputDir);
        System.out.println("---------->classLoader:" + classLoader);
        Class<?> aClass = classLoader.loadClass(targetClassTypeId.getClassName());
        System.out.println("---------->字节码加载成功："+ aClass);
        System.out.println("---------->字节码加载成功："+ aClass.getConstructors().length);
        Object instance = aClass.getConstructors()[0].newInstance(123);

        System.out.println("--------->instance:"+instance);
        System.out.println("---------->字节码加载成功："+ aClass);

        return (T) instance;

}
}
