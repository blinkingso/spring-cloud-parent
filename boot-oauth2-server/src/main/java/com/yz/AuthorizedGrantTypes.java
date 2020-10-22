package com.yz;

/**
 * @author andrew
 * @date 2020-10-22
 */
public enum AuthorizedGrantTypes {
    AUTHORIZATION_CODE("authorization_code"),
    @Deprecated
    PASSWORD("password"),
    CLIENT_CREDENTIALS("client_credentials"),
    REFRESH_TOKEN("refresh_token"),
    @Deprecated
    IMPLICIT("implicit");

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    AuthorizedGrantTypes(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
