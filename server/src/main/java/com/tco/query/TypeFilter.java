package com.tco.query;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class TypeFilter {

    private static final List<String> allTypes = Arrays.asList("airport", "heliport", "balloonport");

    static String generateTypeString(List<String> type) {
        boolean includeOthers = type.contains("other");
        List<String> includeTypes = new ArrayList<>();
        List<String> excludeTypes = new ArrayList<>(allTypes);

        for (String t : type) {
            if (allTypes.contains(t)) {
                includeTypes.add(t);
                excludeTypes.remove(t);
            }
        }

        if (includeOthers && excludeTypes.isEmpty()) {
            return "";
        }

        return buildTypeString(includeOthers, includeTypes, excludeTypes);
    }

    static String buildTypeString(boolean includeOthers, List<String> includeTypes, List<String> excludeTypes) {
        StringBuilder typeString = new StringBuilder(" AND (");
        if (includeOthers) {
            if (!includeTypes.isEmpty()) {
                typeString.append(generateIncludeString(includeTypes));
                if (!excludeTypes.isEmpty()) {
                    typeString.append(" OR ");
                }
            }
            if (!excludeTypes.isEmpty()) {
                typeString.append("(");
                typeString.append(generateExcludeString(excludeTypes));
                typeString.append(")");
            }
        } else {
            typeString.append(generateIncludeString(includeTypes));
        }
        typeString.append(") ");
        return typeString.toString();
    }

    static String generateIncludeString(List<String> includeTypes) {
        StringBuilder includeString = new StringBuilder();
        for (int i = 0; i < includeTypes.size(); i++) {
            if (i > 0) includeString.append(" OR ");
            includeString.append("world.type LIKE \"%").append(includeTypes.get(i)).append("%\"");
        }
        return includeString.toString();
    }

    static String generateExcludeString(List<String> excludeTypes) {
        StringBuilder excludeString = new StringBuilder();
        for (int i = 0; i < excludeTypes.size(); i++) {
            if (i > 0) excludeString.append(" AND ");
            excludeString.append("world.type NOT LIKE \"%").append(excludeTypes.get(i)).append("%\"");
        }
        return excludeString.toString();
    }
}
