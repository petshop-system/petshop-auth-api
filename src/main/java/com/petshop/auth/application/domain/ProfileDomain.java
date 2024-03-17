package com.petshop.auth.application.domain;

public class ProfileDomain {

    public enum Profile {

        ADMINISTRATOR("ADMINISTRATOR"),

        API("API"),

        CUSTOMER("CUSTOMER"),

        EMPLOYEE("EMPLOYEE");

        private final String name;

        private Profile(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

}