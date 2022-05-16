package com.bobocode.homework;

import java.lang.reflect.Field;
import java.util.*;

/**
 * A generic comparator that is comparing a random field of the given class. The field is either primitive or
 * {@link Comparable}. It is chosen during comparator instance creation and is used for all comparisons.
 * <p>
 * By default it compares only accessible fields, but this can be configured via a constructor property. If no field is
 * available to compare, the constructor throws {@link IllegalArgumentException}
 *
 * @param <T> the type of the objects that may be compared by this comparator
 */
public class RandomFieldComparator<T> implements Comparator<T> {

    private final Field fieldToCompare;
    private final Class<T> targetType;

    public RandomFieldComparator(Class<T> targetType) {
        this(targetType, true);
    }

    /**
     * A constructor that accepts a class and a property indicating which fields can be used for comparison. If property
     * value is true, then only public fields or fields with public getters can be used.
     *
     * @param targetType                  a type of objects that may be compared
     * @param compareOnlyAccessibleFields config property indicating if only publicly accessible fields can be used
     */
    public RandomFieldComparator(Class<T> targetType, boolean compareOnlyAccessibleFields) {
        this.targetType = targetType;
        this.fieldToCompare = selectRandomField(targetType, compareOnlyAccessibleFields);
    }

    /**
     * Compares two objects of the class T by the value of the field that was randomly chosen. It allows null values
     * for the fields, and it treats null value grater than a non-null value (nulls last).
     */
    @Override
    @SuppressWarnings({"uncheked", "rawtypes", "unchecked"})
    public int compare(T o1, T o2) {
        try {
            Comparable first = (Comparable) fieldToCompare.get(o1);
            Object second = fieldToCompare.get(o2);

            if (first == null && second == null) {
                return 0;
            } else if (first == null) {
                return 1;
            } else if (second == null) {
                return -1;
            }

            return first.compareTo(second);
        } catch (IllegalAccessException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Returns a statement "Random field comparator of class '%s' is comparing '%s'" where the first param is the name
     * of the type T, and the second parameter is the comparing field name.
     *
     * @return a predefined statement
     */
    @Override
    public String toString() {
        return String.format("Random field comparator of class '%s' is comparing '%s'",
                targetType.getTypeName(), fieldToCompare.getName());
    }

    private Field selectRandomField(Class<T> targetType, boolean compareOnlyAccessibleFields) {
        var fields = getAllFields(targetType, compareOnlyAccessibleFields);

        if (fields.length == 0) throw new IllegalArgumentException();

        var filteredFields = filterFields(fields);
        var selectedField = getRandomField(filteredFields);

        if (!compareOnlyAccessibleFields) {
            selectedField.setAccessible(true);
        }

        return selectedField;
    }

    private Field getRandomField(List<Field> filteredFields) {
        var random = new Random();
        return filteredFields
                .stream()
                .skip(random.nextInt(filteredFields.size()))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    private List<Field> filterFields(Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> Comparable.class.isAssignableFrom(field.getType()) || field.getType().isPrimitive())
                .toList();
    }

    private Field[] getAllFields(Class<T> targetType, boolean compareOnlyAccessibleFields) {
        Field[] fields;
        if (compareOnlyAccessibleFields) {
            fields = targetType.getFields();
        } else {
            fields = targetType.getDeclaredFields();
        }
        return fields;
    }
}
