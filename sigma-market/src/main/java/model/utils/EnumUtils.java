package model.utils;

public class EnumUtils {
    // https://stackoverflow.com/questions/28147348/how-to-create-generic-enum-converter
    public static class EnumConverter<T extends Enum<T>> {
        Class<T> type;

        public EnumConverter(Class<T> type) {
            this.type = type;
        }

        public Enum<T> convert(String text) {
            for (Enum<T> candidate : type.getEnumConstants()) {
                if (candidate.name().equalsIgnoreCase(text)) {
                    return candidate;
                }
            }

            return null;
        }
    }
}
