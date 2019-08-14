package com.nineteenc.module_annotation;

import android.content.Context;

import com.nineteenc.module_annotation.util.ClassUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Author    zhengchengbin
 * Describe:
 * Data:      2019/8/14 9:43
 * Modify by:
 * Modification date:
 * Modify content:
 */
public class NCRoute {

    private static NCRoute mNCRoute;
    private Map<String, String> routes = new HashMap<>();
    public static final String ROUTES_PACKAGE_NAME = "com.nineteenc.module_annotation.routes";

    private NCRoute() {}

    public static NCRoute getInstance() {
        if (mNCRoute == null) {
            synchronized (NCRoute.class) {
                if (mNCRoute == null) {
                    mNCRoute = new NCRoute();
                }
            }
        }
        return mNCRoute;
    }

    public void init(Context context) {
        Set<String> names = ClassUtils.getFileNameByPackageName(context, ROUTES_PACKAGE_NAME);
        try {
            initRoutes(names);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRoutes(Set<String> names)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        for (String name : names) {
            Class clazz = Class.forName(name);
            Object object = clazz.newInstance();
            if (object instanceof IRoute) {
                IRoute iRoute = (IRoute) object;
                iRoute.loadInto(routes);
            }
        }
    }

    public NavigationUtils build(String path) {
        String component = routes.get(path);
        if (component == null) {
            throw new RuntimeException("could not find route with " + path);
        }
        return new NavigationUtils(component);
    }
}
