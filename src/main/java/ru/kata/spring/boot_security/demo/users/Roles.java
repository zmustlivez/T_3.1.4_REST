package ru.kata.spring.boot_security.demo.users;

public enum Roles {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");
    private final String description;

    Roles(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
