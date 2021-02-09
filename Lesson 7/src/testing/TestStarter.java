package testing;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestStarter {
    private TestStarter() {
    }

    public static void start(String className) {
        try {
            start(Class.forName(className));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    public static void start(Class aClass) {
        List<Method> testMethods = findMethods(aClass, Test.class);

        testMethods.sort(new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                return o1.getAnnotation(Test.class).order() - o2.getAnnotation(Test.class).order();
            }
        });

        if (testMethods.isEmpty()) {
            System.out.println(String.format("%s has no any test methods", aClass.getName()));
            return;
        }

        Object obj = initObject(aClass);

        List<Method> beforeSuitMethods = findMethods(aClass, BeforeSuite.class);
        if (!beforeSuitMethods.isEmpty() && beforeSuitMethods.size() > 1) {
            throw new RuntimeException("BeforeSuite annotation must be only one");
        }

        List<Method> afterSuitMethod = findMethods(aClass, AfterSuite.class);
        if (!afterSuitMethod.isEmpty() && beforeSuitMethods.size() > 1) {
            throw new RuntimeException("AfterSuite annotation must be only one");
        }

        if (!beforeSuitMethods.isEmpty()) {
            executeMethods(beforeSuitMethods.get(0), obj);
        }

        for (Method testMethod : testMethods) {
            executeMethods(testMethod, obj);
        }

        if (!afterSuitMethod.isEmpty()) {
            executeMethods(afterSuitMethod.get(0), obj);
        }

    }

    private static void executeMethods(Method method, Object obj, Object... args) {
        try {
            method.setAccessible(true);
            method.invoke(obj, args);
            method.setAccessible(false);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static List<Method> findMethods(Class aClass, Class<? extends Annotation> annotationClass) {
        List<Method> testMethods = new ArrayList<>();
        for (Method method : aClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotationClass)) {
                testMethods.add(method);
            }
        }
        return testMethods;
    }

    private static Object initObject(Class aClass) {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException |
                IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("SWW", e);
        }
    }

}
