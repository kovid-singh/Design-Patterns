package org.Practice.creational.ProtoType;

public class UserDetails implements Cloneable {
    private String firstName;
    private String lastName;
    private String middleName;
    private String mobileNumber;
    private String email;
    private String emergencyContact;
    private String address;


    UserDetails(Builder builder) {
        this.address = builder.address;
        this.email = builder.email;
        this.emergencyContact = builder.emergencyContact;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.middleName = builder.middleName;
        this.mobileNumber = builder.mobileNumber;
    }


    public static class Builder {

        private String firstName;
        private String lastName;
        private String middleName;
        private String mobileNumber;
        private String email;
        private String emergencyContact;
        private String address;


        //Madatory Fields
        public Builder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public Builder setMiddleName(String middleName) {
            this.middleName = middleName;
            return this;
        }

        public Builder setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setEmergencyContact(String emergencyContact) {
            this.emergencyContact = emergencyContact;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public UserDetails Build() {

            //Here we can validate the fields before creating the object
            if (firstName == null || firstName.isEmpty()) {
                throw new IllegalArgumentException("firstName is required");
            }
            if (lastName == null || lastName.isEmpty()) {
                throw new IllegalArgumentException("lastName is required");
            }

            return new UserDetails(this);
        }


    }


    @Override
    public String toString() {
        return "UserDetails{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", emergencyContact='" + emergencyContact + '\'' +
                ", address='" + address + '\'' +
                '}';

    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported", e);
        }
    }
}