package com.decipher.agriculture.dao.util;

import java.io.Serializable;

/**
 * Created by raja on 12/26/15.
 */
public interface HibernateUtils {
    Object getPersistedObject(Class<?> clazz, Serializable primaryKey);
}
