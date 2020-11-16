package com.yz.config;

/**
 * @author andrew
 * @date 2020-11-16
 */
public interface TokenStoreType {

    class InMemory implements TokenStoreType {
    }

    class JDBC implements TokenStoreType {
    }
}
