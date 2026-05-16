package com.gestionapp.backend.util;

import org.springframework.dao.DataAccessException;

public final class Utils {

    private Utils() {
    }

    public static RuntimeException wrapDataAccessException(DataAccessException exception, String message) {
        return new RuntimeException(message, exception);
    }
}
