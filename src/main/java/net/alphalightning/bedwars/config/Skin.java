package net.alphalightning.bedwars.config;

public class Skin {

    private String value;
    private String signature;

    public String value() {
        return value;
    }

    public String signature() {
        return signature;
    }

    public Skin value(String value) {
        this.value = value;
        return this;
    }

    public Skin signature(String signature) {
        this.signature = signature;
        return this;
    }

}
