package logger.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ReflexiveFactory {
    public static <T> T createObject(Class<? extends T> instanceClass, Object... constructorParams)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Class[] paramsClasses = new Class[constructorParams.length];
        for (int i = 0; i < constructorParams.length; i++)
            paramsClasses[i] = constructorParams[i].getClass();

        Constructor constructor = instanceClass.getConstructor(paramsClasses);
        return  (T) constructor.newInstance(constructorParams);
    }
}