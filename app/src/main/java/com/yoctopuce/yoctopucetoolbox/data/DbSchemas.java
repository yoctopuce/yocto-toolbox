package com.yoctopuce.yoctopucetoolbox.data;

/**
 * Created by seb on 20.04.2016.
 */
public class DbSchemas {
    public static final class HubsTable {
        public static final String NAME = "hubs";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String SERIAL = "serial";
            public static final String HOST = "host";
            public static final String PORT = "port";
            public static final String USER = "user";
            public static final String PASS = "pass";
        }
    }
}
