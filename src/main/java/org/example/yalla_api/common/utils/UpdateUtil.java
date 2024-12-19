package org.example.yalla_api.common.utils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class UpdateUtil {

    public static void copyNonNullProperties(Object source, Object target) {
        BeanWrapper src = new BeanWrapperImpl(source);
        BeanWrapper trg = new BeanWrapperImpl(target);

        for (PropertyDescriptor propertyDescriptor : src.getPropertyDescriptors()) {
            String propertyName = propertyDescriptor.getName();
            if ("class".equals(propertyName)) {
                continue; // Skip the class property
            }

            Method getter = propertyDescriptor.getReadMethod(); // Get the getter method
            if (getter != null) {
                Object value = ReflectionUtils.invokeMethod(getter, source); // Use the getter to retrieve the value
                if (value != null) {
                    trg.setPropertyValue(propertyName, value); // Set the value in the target object
                }
            }
        }
    }
}
