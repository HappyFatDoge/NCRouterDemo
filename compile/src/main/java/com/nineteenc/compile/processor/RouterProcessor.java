package com.nineteenc.compile.processor;

import com.nineteenc.module_annotation.NCRoute;
import com.nineteenc.module_annotation.Route;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Author    zhengchengbin
 * Describe:
 * Data:      2019/8/12 14:53
 * Modify by:
 * Modification date:
 * Modify content:
 */
public class RouterProcessor extends AbstractProcessor {

    private Map<String, String> routes = new TreeMap<>();
    private Filer mFiler;
    private String mModuleName;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        System.out.println("init");
        mFiler = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("process");
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Route.class)) {
            addRoute(element);
        }
        createRouteFile();
        return true;
    }

    private void createRouteFile() {
        TypeSpec.Builder builder = TypeSpec.classBuilder(mModuleName + "NCRouterMap").addModifiers(Modifier.PUBLIC);
        TypeName superInterface = ClassName.bestGuess("com.nineteenc.module_annotation.IRoute");
        builder.addSuperinterface(superInterface);
        TypeName stringType =  ClassName.get(String.class);
        TypeName mapType = ParameterizedTypeName.get(ClassName.get(Map.class), stringType, stringType);

        MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("loadInto")
                .addAnnotation(Override.class)
                .returns(void.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(mapType, "routes");

        for (String key : routes.keySet()) {
            methodBuilder.addStatement("routes.put($S, $S)", key, routes.get(key));
        }

        builder.addMethod(methodBuilder.build());
        JavaFile javaFile = JavaFile.builder(NCRoute.ROUTES_PACKAGE_NAME, builder.build()).build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    private void addRoute(Element element) {
        Route route = element.getAnnotation(Route.class);
        String path = route.path();
        String group = route.group();
        if (group != null && !group.equals("")) {
            mModuleName = group;
            path = group + "/" + path;
        } else {
            mModuleName = path.split("/")[1];
        }
        routes.put(path, element.toString());
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Route.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
