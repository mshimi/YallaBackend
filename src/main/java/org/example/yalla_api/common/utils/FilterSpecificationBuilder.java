package org.example.yalla_api.common.utils;

import jakarta.persistence.criteria.*;
import org.apache.el.parser.AstGreaterThanEqual;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class FilterSpecificationBuilder<T> {


    public enum FilterLogic {
        AND,
        OR
    }

    public Specification<T> buildSpecification(Map<String, String> filters) {
        // Extract logic from filters
        FilterLogic logic = extractLogic(filters);

        Map<String,String> fil = filters;
        fil.remove("page");
        fil.remove("size");

        return (root, query, criteriaBuilder) -> {
            // Build filtering predicates
            List<Predicate> predicates = fil.entrySet().stream()
                    .filter(entry -> !entry.getKey().equalsIgnoreCase("logic") && !entry.getKey().equalsIgnoreCase("sort"))
                    .map(entry -> createPredicate(entry.getKey(), entry.getValue(), root, criteriaBuilder))
                    .collect(Collectors.toList());

            // Apply filtering logic (AND/OR)
            Predicate filterPredicate = predicates.isEmpty() ? null :
                    (logic == FilterLogic.AND) ? criteriaBuilder.and(predicates.toArray(new Predicate[0])) :
                            criteriaBuilder.or(predicates.toArray(new Predicate[0]));

            // Apply sorting
            String sort = fil.get("sort");
            if (sort != null && !sort.isEmpty()) {
                List<Order> orders = parseSortParameter(sort, root, criteriaBuilder);
                query.orderBy(orders);
            }

            return filterPredicate;
        };
    }

    private Predicate createPredicate(String fieldName, String fieldValue, Root<T> root, CriteriaBuilder criteriaBuilder) {
        if (fieldName.contains(".")) {
            // Handle nested fields (e.g., country.name)
            String[] parts = fieldName.split("\\.");
            Join<Object, Object> join = root.join(parts[0]); // Join on the parent field
            Path<Object> childPath = join.get(parts[1]);     // Access the child field
            return handleDataTypeFilter(childPath, fieldValue, criteriaBuilder);
        } else {
            // Handle top-level fields
            Path<Object> path = root.get(fieldName);
            return handleDataTypeFilter(path, fieldValue, criteriaBuilder);
        }
    }

    private Predicate handleDataTypeFilter(Path<Object> path, String fieldValue, CriteriaBuilder criteriaBuilder) {
        Class<?> fieldType = path.getJavaType();

        Boolean isNot = false;

        if ("null".equalsIgnoreCase(fieldValue)) {
            return criteriaBuilder.isNull(path);
        }
        if ("!null".equalsIgnoreCase(fieldValue)) {
            return criteriaBuilder.isNotNull(path);
        }

        // Handle NOT for other values (e.g., !France)
        if (fieldValue.startsWith("!")) {
            isNot= true;
            String actualValue = fieldValue.substring(1).trim();
            if (fieldType.equals(String.class)) {
                return criteriaBuilder.notLike(criteriaBuilder.lower(path.as(String.class)), "%" + actualValue.toLowerCase() + "%");
            }
            if (fieldType.equals(Boolean.class)) {
                return criteriaBuilder.notEqual(path.as(Boolean.class), Boolean.parseBoolean(actualValue));
            }

        }

        if (fieldType.equals(String.class)) {
            return criteriaBuilder.like(criteriaBuilder.lower(path.as(String.class)), "%" + fieldValue.toLowerCase() + "%");
        }

        if (fieldType.equals(Long.class)) {

            return handleNumericFilter(criteriaBuilder, path.as(Long.class), fieldValue, Long::valueOf,isNot);
        }

        if (fieldType.equals(Integer.class)) {
            return handleNumericFilter(criteriaBuilder, path.as(Integer.class), fieldValue, Integer::valueOf, isNot);
        }

        if (fieldType.equals(Double.class)) {
            return handleNumericFilter(criteriaBuilder, path.as(Double.class), fieldValue, Double::valueOf,isNot);
        }

        if (fieldType.equals(Boolean.class)) {
            return criteriaBuilder.equal(path.as(Boolean.class), Boolean.parseBoolean(fieldValue));
        }

        throw new IllegalArgumentException("Unsupported field type for filtering: " + fieldType.getName());
    }

    /**
     * Handles numeric filters (e.g., >, <, and ranges).
     *
     * @param criteriaBuilder The CriteriaBuilder.
     * @param field           The field being filtered.
     * @param fieldValue      The filter value.
     * @param parser          A parser to convert the String fieldValue into the correct numeric type.
     * @return A Predicate for the numeric filter.
     */
    private <N extends Number & Comparable<N>> Predicate handleNumericFilter(
            CriteriaBuilder criteriaBuilder,
            Expression<N> field,
            String fieldValue,
            java.util.function.Function<String, N> parser,
            Boolean isNot
    ) {
        // Remove "!" prefix if present
        String value = isNot ? fieldValue.substring(1).trim() : fieldValue.trim();

        Predicate predicate;

        if (value.startsWith(">=")) {
            predicate = criteriaBuilder.greaterThanOrEqualTo(field, parser.apply(value.substring(2).trim()));
        } else if (value.startsWith(">")) {
            predicate = criteriaBuilder.greaterThan(field, parser.apply(value.substring(1).trim()));
        } else if (value.startsWith("<=")) {
            predicate = criteriaBuilder.lessThanOrEqualTo(field, parser.apply(value.substring(2).trim()));
        } else if (value.startsWith("<")) {
            predicate = criteriaBuilder.lessThan(field, parser.apply(value.substring(1).trim()));
        } else if (value.contains("-")) {
            String[] range = value.split("-");
            predicate = criteriaBuilder.between(
                    field,
                    parser.apply(range[0].trim()),
                    parser.apply(range[1].trim())
            );
        } else {
            predicate = criteriaBuilder.equal(field, parser.apply(value));
        }

        // Negate the predicate if "!" is used
        return isNot ? criteriaBuilder.not(predicate) : predicate;
    }


    /**
     * Extracts the filter logic (AND/OR) from the filters map.
     * If "logic" key is not present, defaults to AND.
     *
     * @param filters The filters map.
     * @return The FilterLogic value.
     */
    private FilterLogic extractLogic(Map<String, String> filters) {
        String logicValue = filters.getOrDefault("logic", "AND").toUpperCase();
        try {
            return FilterLogic.valueOf(logicValue);
        } catch (IllegalArgumentException ex) {
            // Default to AND if the value is invalid
            return FilterLogic.AND;
        }
    }

    private List<Order> parseSortParameter(String sort, Root<T> root, CriteriaBuilder criteriaBuilder) {
        return List.of(sort.split(",")).stream().map(order -> {
            boolean isDesc = order.startsWith("-");
            String fieldName = isDesc ? order.substring(1) : order;
            Path<Object> path = root.get(fieldName);
            return isDesc ? criteriaBuilder.desc(path) : criteriaBuilder.asc(path);
        }).collect(Collectors.toList());
    }

}