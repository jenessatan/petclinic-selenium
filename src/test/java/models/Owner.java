package models;

import net.bytebuddy.utility.RandomString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Owner {

    public Owner(String firstName, String lastName,
                 String address, String city, String telephone, List<Pet> pets) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.telephone = telephone;
        if (pets == null) {
            this.pets = new ArrayList <Pet>();
        } else {
            this.pets = pets;
        }
    }
    /**
     * Constructor: Creates an Owner with randomly generated values
     * */
    public Owner() {
        firstName = RandomString.make(getRandomStrLength());
        lastName = RandomString.make(getRandomStrLength());
        address = RandomString.make(getRandomStrLength());
        city = RandomString.make(getRandomStrLength());
        telephone = getRandomTelephoneNum();
    }

    private Random random = new Random();

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String telephone;
    private List <Pet> pets;

    public Boolean hasSameInfo(Owner owner) {
        return firstName.equals(owner.getFirstName())
                && lastName.equals(owner.getLastName())
                && address.equals(owner.getAddress())
                && city.equals(owner.getCity())
                && telephone.equals(owner.getTelephone());
    }

    public String getFirstName(){return firstName;}
    public String getLastName(){return lastName;}
    public String getAddress() {return address;}
    public String getCity() {return city;}
    public String getTelephone() {return telephone;}
    public List <Pet> getPets() {return pets;}

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addPet(Pet pet) {
        pets.add(pet);
    }

    /**
     * HELPER METHOD
     * Purpose: provides a random length between 1 to 10
     * @return int between 1 to 10
     * */
    private int getRandomStrLength() {
        int strLength = random.nextInt(10) + 1;
        return strLength;
    }

    private String getRandomTelephoneNum() {
        int i1 = random.nextInt(8);
        int i2 = random.nextInt(8);
        int i3 = random.nextInt(8);
        int i4 = random.nextInt(742);
        int i5 = random.nextInt(9999);

        String phoneNum = String.format("%d%d%d%03d%04d", i1, i2, i3, i4, i5);
        return phoneNum;
    }
}
