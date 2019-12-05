package me.yokeyword.demo2;


import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import leo.android.cglib.dx.Code;
import leo.android.cglib.dx.DexMaker;
import leo.android.cglib.dx.MethodId;
import leo.android.cglib.dx.TypeId;
import leo.android.cglib.dx.TypeList;

import static java.lang.reflect.Modifier.PUBLIC;

/**
 * Android 虚拟机字节码生成工具
 *
 * @author huqs
 */
public class DexMakerUtils2 {

    /**
     * 动态生成Android 虚拟机字节码
     *
     * @param context         Context
     * @param superClass      Class<?>  需要基础的父类
     * @param targetClassName 要生成类的全名如：android.support.v7.app.AppCompatActivity
     */
    public static <T> T generateClass(Context context, Class<?> superClass, String targetClassName) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        DexMaker dexMaker = new DexMaker();
        // 需要继承的父类
        TypeId<?> superClassTypeId = TypeId.get(superClass);
        TypeId<?> targetClassTypeId = TypeId.get(String.format("L%s;", targetClassName.replace(".", "/")));
        // 生成指定类
        dexMaker.declare(targetClassTypeId, "android dex virtual bytecode", Modifier.PUBLIC, superClassTypeId);

        // 生成一个无参数的构造函数
        MethodId<?, Void> constructor = targetClassTypeId.getConstructor(TypeId.INT);
        Code declare = dexMaker.declare(constructor, PUBLIC);
        declare.returnVoid();
        System.out.println("----------->constructor:"+constructor + "isConstructor:"+constructor.isConstructor());
        // TypeId<D> declaringType, TypeId<R> returnType, String name, TypeList parameters

//        Code code = dexMaker.declare(constructor, PUBLIC);

        File dexOutputDir = context.getDir("dex", 0);
        System.out.println("---------父类：" + superClassTypeId.getClassName());
        System.out.println("---------目标类：" + targetClassTypeId.getClassName());
        ClassLoader loader = dexMaker.generateAndLoad(context.getClassLoader(), dexOutputDir);
        System.out.println("---------->loader:" + loader);
        Class<?> aClass = loader.loadClass(targetClassTypeId.getClassName());
        System.out.println("---------->字节码加载成功："+ aClass);

        System.out.println("---------->方法数量："+aClass.getMethods().length);
//        for (Method method : aClass.getMethods()) {
//            System.out.println("--------->method:"+method.getName());
//        }
        System.out.println("---------->构造函数数量："+aClass.getConstructors().length);
        System.out.println("---------->构造函数："+aClass.getConstructors()[0]);
        Constructor<?> constructor1 = aClass.getConstructor(int.class);
        System.out.println("");
//        Object instance = aClass.getConstructors()[0].newInstance(0);

      return null;

}



}
