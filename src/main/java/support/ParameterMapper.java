package support;

import java.util.Map;

public class ParameterMapper {

    private ParameterMapper() {
    }

    public static String stringFromMap(final Map<String, String> map) {
        final StringBuilder string = new StringBuilder();
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            string.append(entry.getKey());
            string.append("=");
            string.append(entry.getValue());
            string.append("&");
        }
        return string.toString();
    }
}
