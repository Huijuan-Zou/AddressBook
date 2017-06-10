package addressBook;

import java.util.Date;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;

/**
 * The class contact represents an contact in an addressBook.
 * The class contact contains 7 fields. The first 6 fields are the attributes of an contact.
 * Field time, which records the time object is created, is used to help compare the contact objects while adding to the arrayList.
 * Default empty constructor is used solely for unmarshal.
 * A builder pattern is used to help generating new instance.
 * All the fields other than time are assigned values through builder pattern.
 * Any of the six fields used in the builder pattern is optional.
 * To use the builder pattern, one or more field values are required.
 * An example to use builder pattern is, contact e2=new contact.Builder().lastName("King").build(), if one field value is assigned.
 * The maximum fields to be assigned is six if all the six fields are listed.
 * 
 * The class contact contains two override methods toString() and compareTo(). Please refer to their official Javadocs.
 * The class contact also contains public getter method for each field.
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Contact implements Comparable<Contact> {
  @XmlElement
  private final String firstName;
  @XmlElement
  private final String lastName;
  @XmlElement
  private final String phoneNumber;
  @XmlElement
  private final String address;
  @XmlElement
  private final String email;
  @XmlElement
  private final String note;
  private Date time;

  public static class Builder {
    private String lastName = "";
    private String firstName = "";
    private String phoneNumber = "";
    private String address = "";
    private String email = "";
    private String note = "";
    
    public  Builder() {
    }
    
    public Builder firstName(String val) {
      firstName = val;
      return this;
      }
    
    public Builder lastName(String val) {
      lastName = val;
      return this;
      }
    
    public Builder phoneNumber(String val) {
      phoneNumber = val;
      return this;
      }
    
    public Builder address(String val) {
      address = val;
      return this;
      }
    
    public Builder email(String val) {
      email = val;
      return this;
      }
    
    public Builder note(String val) {
      note = val;
      return this;
      }
    
    public Contact build() {
      return new Contact(this);
      }
    }
  
  private Contact() {
    this.firstName = "";
    this.lastName = "";
    this.phoneNumber = "";
    this.address = "";
    this.email = "";
    this.note = "";
    this.time = null;
    }
  
  private Contact(Builder builder) {
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.phoneNumber = builder.phoneNumber;
    this.address = builder.address;
    this.email = builder.email;
    this.note = builder.note;
    this.time = new Date();
    }

  public String getFirstName(){
    return this.firstName;
    }
  
  public String getLastName(){
    return this.lastName;
    }
  
  public String getPhoneNumber(){
    return this.phoneNumber;
    }
  
  public String getPostalAddress(){
    return this.address;
    }
  
  public String getEmailAddress(){
    return this.email;
    }
  
  public String getNote(){
    return this.note;
    }
  
  public Date getTime(){
    return this.time;
    }
  
  /* 
   * returns the first 6 fields of an contact object with their values.
   */
  @Override
  public String toString() {
    return "firstName: " + this.firstName + " , lastName: " + this.lastName + " , phoneNumber: "+ this.phoneNumber+" , address: " + this.address+" , "
        + " email: "+this.email+" , note: "+this.note+" . ";
    }
  
  private String getCleanPhoneNumber(String phoneNumber) {
    return phoneNumber.replace("-", "").replace("(", "").replace(")","").trim();
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((address == null) ? 0 : address.toLowerCase().hashCode());
    result = prime * result + ((email == null) ? 0 : email.toLowerCase().hashCode());
    result = prime * result + ((firstName == null) ? 0 : firstName.toLowerCase().hashCode());
    result = prime * result + ((lastName == null) ? 0 : lastName.toLowerCase().hashCode());
    result = prime * result + ((phoneNumber == null) ? 0 : getCleanPhoneNumber(phoneNumber).hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Contact other = (Contact) obj;
    if (address == null) {
      if (other.address != null)
        return false;
    } else if (!address.toLowerCase().equals(other.address.toLowerCase()))
      return false;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.toLowerCase().equals(other.email.toLowerCase()))
      return false;
    if (firstName == null) {
      if (other.firstName != null)
        return false;
    } else if (!firstName.toLowerCase().equals(other.firstName.toLowerCase()))
      return false;
    if (lastName == null) {
      if (other.lastName != null)
        return false;
    } else if (!lastName.toLowerCase().equals(other.lastName.toLowerCase()))
      return false;
    if (getCleanPhoneNumber(phoneNumber) == null) {
      if (getCleanPhoneNumber(other.phoneNumber) != null)
        return false;
    } else if (!getCleanPhoneNumber(phoneNumber).equals(getCleanPhoneNumber(other.phoneNumber)))
      return false;
    return true;
  }

  /* This is a  method to override the default compareTo method
   * This compare method compares contact objects first by comparing the lastName of the contact objects Alphabetically(case insensitive). 
   * If all the Characters (index 0 to  index of (shorter length of the two strings-1)) are the same, then compare the length of the two last names.
   * If the two last name are the same, then compares the time the objects are created.
   * @param contact object to compare to this contact object
   */
  @Override
  public int compareTo(Contact other) {
    String thisLastName = this.getLastName().toLowerCase();
    String otherLastName = other.getLastName().toLowerCase();
    int thisLength = thisLastName.length();
    int otherLength = otherLastName.length();
    int minLength = Math.min(thisLength, otherLength);
    int res = this.getTime().compareTo(other.getTime());
    
    for (int i = 0;i < minLength;i++) {
      if (thisLastName.charAt(i) < otherLastName.charAt(i)) {
        return -1;
        }
      else if (thisLastName.charAt(i) > otherLastName.charAt(i)) {
        return 1;
        }
      }
    if (thisLength != otherLength) {
      if (otherLength > thisLength) {
        return -1;
        }
      else if ( otherLength < thisLength ) {
        return 1;
        }
      }
    return res;
    }
}
