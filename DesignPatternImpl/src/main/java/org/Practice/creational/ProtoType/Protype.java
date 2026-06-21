package org.Practice.creational.ProtoType;

public class Protype {
    public static void main(String[] args) {

        UserDetails userDetails = new UserDetails.Builder("ho", "Doe")
                .setMiddleName("M")
                .setMobileNumber("1234567890")
                .setEmail("kd@gmail.com")
                .setEmergencyContact("0987654321")
                .setAddress("123 Main St")
                .Build();

        Object clonedUser = userDetails.clone();


        System.out.println(userDetails);
        System.out.println(userDetails.hashCode());

        System.out.println(clonedUser);
        System.out.println(clonedUser.hashCode());

    }






}



