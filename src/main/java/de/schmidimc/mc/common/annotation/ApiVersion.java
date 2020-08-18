package de.schmidimc.mc.common.annotation;

public enum ApiVersion {
    NONE(""), VERSION_1_13("1.13"), VERSION_1_14("1.14"), VERSION_1_15("1.15"), VERSION_1_16("1.16");

    private final String version;
    private ApiVersion(final String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return version;
    }
}
