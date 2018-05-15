package com.zs.annotation;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * @author: ZangSong
 * @email: gnoszsong@gmail.com
 * @date: 18-5-10 下午3:13
 * @description: mytest
 * 参考资料 https://race604.com/annotation-processing/
 */
@SupportedAnnotationTypes({"com.zs.annotation.GenerateTest", "com.zs.annotation.TestMethod", "com.zs.annotation.TestClass", "com.zs.annotation.TestParams"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class GenerateTestProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        //-----------------------------生成一段简单的代码-start-------------------------
        StringBuilder builder = new StringBuilder()
                .append("package com.zs.annotation.test;\n\n")
                .append("public class GeneratedClass {\n\n")
                .append("\tpublic String getMessage() {\n")
                .append("\t\treturn \"");

        // for each javax.lang.model.element.Element annotated with the CustomAnnotation
        for (Element element : roundEnv.getElementsAnnotatedWith(TestMethod.class)) {
            String objectType = element.getSimpleName().toString();
            // this is appending to the return statement
            builder.append(objectType).append(" says hello!\\n");
        }

        builder.append("\";\n")
               .append("\t}\n")
               .append("}\n");

        try { // write the file
            JavaFileObject source = processingEnv.getFiler().createSourceFile("com.zs.annotation.test.GeneratedClass");
            Writer         writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }
        //-----------------------------生成一段简单的代码-end-------------------------


        Messager messager = processingEnv.getMessager();
        Elements       elementUtils   = processingEnv.getElementUtils();
        messager.printMessage(Diagnostic.Kind.NOTE, "-----获取方法上的相应详细的信息-START-----------------------------" );
        for (Element element : roundEnv.getElementsAnnotatedWith(TestMethod.class)) {
            //对于Element直接强转
            ExecutableElement executableElement = (ExecutableElement) element;

            //非对应的Element，通过getEnclosingElement转换获取
            TypeElement classElement = (TypeElement) element
                    .getEnclosingElement();

            //当(ExecutableElement) element成立时，使用(PackageElement) element
            //            .getEnclosingElement();将报错。
            //需要使用elementUtils来获取

            PackageElement packageElement = elementUtils.getPackageOf(classElement);

            //全类名
            String fullClassName = classElement.getQualifiedName().toString();
            //类名
            String className = classElement.getSimpleName().toString();
            //包名
            String packageName = packageElement.getQualifiedName().toString();
            //方法名
            String methodName = executableElement.getSimpleName().toString();

            //取得方法参数列表
            List<? extends VariableElement> methodParameters = executableElement.getParameters();
            //参数类型列表
            List<String> types = new ArrayList<>();
            for (VariableElement variableElement : methodParameters) {
                TypeMirror methodParameterType = variableElement.asType();
                if (methodParameterType instanceof TypeVariable) {
                    TypeVariable typeVariable = (TypeVariable) methodParameterType;
                    methodParameterType = typeVariable.getUpperBound();
                }
                //参数名
                String parameterName = variableElement.getSimpleName().toString();
                //参数类型
                String parameteKind = methodParameterType.toString();
                types.add(methodParameterType.toString());

                messager.printMessage(Diagnostic.Kind.NOTE, "TestMethod parameterName = " + parameterName);
                messager.printMessage(Diagnostic.Kind.NOTE, "TestMethod parameteKind = " + parameteKind);
            }

            messager.printMessage(Diagnostic.Kind.NOTE, "TestMethod methodName = " + methodName);
            messager.printMessage(Diagnostic.Kind.NOTE, "TestMethod className = " + className);
            messager.printMessage(Diagnostic.Kind.NOTE, "TestMethod fullClassName = " + fullClassName);
            messager.printMessage(Diagnostic.Kind.NOTE, "TestMethod packageName = " + packageName);
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "-----获取方法上的相应详细的信息-END------------------------------" );

        messager.printMessage(Diagnostic.Kind.NOTE, "-----获取属性上的相应详细的信息-START-----------------------------" );
        for (Element element : roundEnv.getElementsAnnotatedWith(TestField.class)) {
            //ElementType.FIELD注解可以直接强转VariableElement
            VariableElement variableElement = (VariableElement) element;

            TypeElement classElement = (TypeElement) element
                    .getEnclosingElement();
            PackageElement packageElement = elementUtils.getPackageOf(classElement);
            //类名
            String className = classElement.getSimpleName().toString();
            //包名
            String packageName = packageElement.getQualifiedName().toString();
            //类成员名
            String variableName = variableElement.getSimpleName().toString();

            //类成员类型
            TypeMirror typeMirror = variableElement.asType();
            String type = typeMirror.toString();

            messager.printMessage(Diagnostic.Kind.NOTE, "TestField type = " + type);
            messager.printMessage(Diagnostic.Kind.NOTE, "TestField variableName = " + variableName);
            messager.printMessage(Diagnostic.Kind.NOTE, "TestField className = " + className);
            messager.printMessage(Diagnostic.Kind.NOTE, "TestField packageName = " + packageName);
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "-----获取属性上的相应详细的信息-END-----------------------------" );

        messager.printMessage(Diagnostic.Kind.NOTE, "-----获取类上的相应详细的信息-START-----------------------------" );
        for (Element element : roundEnv.getElementsAnnotatedWith(TestClass.class)) {
            //ElementType.TYPE注解可以直接强转TypeElement
            TypeElement classElement = (TypeElement) element;

            PackageElement packageElement = (PackageElement) element
                    .getEnclosingElement();

            //全类名
            String fullClassName = classElement.getQualifiedName().toString();
            //类名
            String className = classElement.getSimpleName().toString();
            //包名
            String packageName = packageElement.getQualifiedName().toString();
            //父类名
            String superClassName = classElement.getSuperclass().toString();

            messager.printMessage(Diagnostic.Kind.NOTE, "TestClass className = " + className);
            messager.printMessage(Diagnostic.Kind.NOTE, "TestClass fullClassName = " + fullClassName);
            messager.printMessage(Diagnostic.Kind.NOTE, "TestClass packageName = " + packageName);
            messager.printMessage(Diagnostic.Kind.NOTE, "TestClass superClassName = " + superClassName);
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "-----获取类上的相应详细的信息-END-----------------------------" );

        return true;
    }
}
