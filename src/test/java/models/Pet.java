package models;

import java.util.Date;

public class Pet {
    public Pet(String name, Date birthDate, String typeOfAnimal) {
        this.name = name;
        this.birthDate = birthDate;
        this.type = typeOfAnimal;
    }

    private String name;
    private Date birthDate;
    private String type;

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getType() {
        return type;
    }
}
