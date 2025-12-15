package ru.astondevs.data;

import ru.astondevs.model.DataClass;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static ru.astondevs.data.User.Fields.*;

public class User implements DataClass {
    enum Fields {
        NAME("name"),
        PASSWORD("password"),
        AGE("age");

        private final String name;

        Fields(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private String name;
    private String password;
    private Integer age;

    static class Builder implements DataClass.Builder<User> {
        private String name;
        private String password;
        private Integer age;

        @Override
        public Builder addField(String field, String value) {
            switch (field) {
                case "name":
                    name = value;
                    break;
                case "password":
                    password = value;
                    break;
                case "age":
                    age = Integer.parseInt(value);
                    break;
            }
            return this;
        }

        public Builder addName(String name) {
            this.name = name;
            return this;
        }
        public Builder addPassword(String password) {
            this.password = password;
            return this;
        }
        public Builder addAge(Integer age) {
            this.age = age;
            return this;
        }

        @Override
        public User build() {
            return new User(name, password, age);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private User(String name, String password, Integer age) {
        this.name = name;
        this.password = password;
        this.age = age;
    }

    @Override
    public boolean isValid() {
        return name != null && password != null && age != null;
    }

    @Override
    public Map<String, String> toCsvRecord() {
        return Map.of(AGE.name, age.toString(), NAME.name, name, PASSWORD.name, password);
    }

    @Override
    public String getField(String name) {
        return switch (name) {
            case "name" -> name;
            case "password" -> password;
            case "age" -> age.toString();
            default -> null;
        };
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public List<String> getFieldNames() {
        return List.of(AGE.name, NAME.name, PASSWORD.name);
    }
}
