package com.assigment.core.config;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

public class CustomDialect extends H2Dialect {

    public CustomDialect() {
        super();
        registerFunction("ADD_MINUTES", new SQLFunctionTemplate(StandardBasicTypes.TIMESTAMP, "DATEADD('MINUTE', ?1, ?2)"));
    }
}
