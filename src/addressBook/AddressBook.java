package addressBook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The class AddressBook represents an address book that contains many contacts.
 * It has one field named contactList.
 * Each contact is a separate document contains personal information such as first name, last name, email address, postal address, note and phone number.
 * The order of the contacts in the address book is based on last name and contact creating time.
 * 
 * The class AddressBook contains methods add, removeAll, removeObject and search.
 * The method add adds an contact object to the address book.
 * The method removeAll removes all the contacts of the address book that matches the target strings (case insensitive) .
 * The method removeObject removes the contacts of the address book that matches the target object .
 * The method search returns all the contacts that matches the target string (case insensitive).
 * 
 * An empty address book can be created by:AddressBook addrBook=new AddressBook();
 * Methods can be called for the address book created. For example: if e is an contact object,
 * addrBook.add(e); addrBook.remove(stringName)
 *
 */

@XmlRootElement(name = "AddressBook")
@XmlAccessorType(XmlAccessType.NONE)
public class AddressBook {
  private ArrayList<Contact> contactList;
  
  public AddressBook() {
    contactList = new ArrayList<Contact>();
    }
  
  @XmlElements(value = {@XmlElement(name = "Contact", type = Contact.class)})
  @XmlElementWrapper
  public ArrayList<Contact> getContactList(){
    return contactList;
    }
  
  /**
   * The method add adds Contact object to the addressBook.
   * The order of the addressBook forms based on the compareTo method implemented in contact.class.
   * The order is based on lastName. First ordered by lastName by alphabetical order (smaller char value goes first, case insensitive), 
   * then by lastName length (shorter lastName goes first), and last by object creation time if the two lastName are identical.
   * For example, if we have last names "Smith" and "King", "K" has smaller char value, so it has smaller index in the arrayList.
   * If we have last name "King" and "Kin", then the first three char values are the same, we compare the length, "Kin" should have smaller index.
   * If we have last name "King" and "King" in two objects, then we compare the time they are created. The one that is created earlier has smaller index.
   * To use the method, create a addressBook object and then call add method. Example is as follows:
   * if AddressBook addrBook is already created, then simply call addrBook.add(contactName).
   * 
   * @param contact contact object to be added to the addressBook.
   * @return true if added, otherwise return false.
   */
  public boolean add(Contact contact) {
    if (contactList.add(contact)) {
      Collections.sort(contactList);
      return true;
      }
    return false;
  }
  
  /**
   * The method removeAll removes all the contact objects that contains a string that match any of the target strings (case insensitive) splitted by space, + or & from target string.
   * Please keep your target string as specific as possible. For example, if you have only a string "a" as target string, then all contacts that contain "a" will be deleted.
   * If you have a target string "Abby Smith", then any contact that has string or substirng of "Abby" or "Smith" will be removed.
   * If the input contains spaces or tabs, the method will get rid of the spaces or tabs. Only the target string matters for the result.
   * 
   * @param target string to be removed.
   * @return returns true if found and removed, otherwise return false.
   */
  public boolean removeAll(String target) {
    int originalSize = contactList.size();
    ArrayList<Contact> toBeRemovedList = search(target);
    for(Contact toBeRemoved : toBeRemovedList) {
      removeObject(toBeRemoved);
      }      
    if (contactList.size() == originalSize) {
      return false;
      }
    return true;
    }
  
  /**
   * The removeObject method removes the same target contact object.
   * Note that for phoneNumber, (233) 890-2345 and 2338902345 would be treated as the same phone number for an contact.
   * All the other informations of the contact are case insensitive. 
   * Hence an contact would be treated as identical if they only differs in terms of cases or contain/not contain "(" ")" "-" in their phoneNumber.
   * 
   * @param target contact object to be removed.
   * @return returns true if found and removed, otherwise return false.
   */
  public boolean removeObject(Contact target) {
    Iterator<Contact> it = contactList.iterator();
    int originalSize = contactList.size();
    while (it.hasNext()) {
      Contact contact = it.next();
      if (contact.equals(target)) {
        it.remove();
        }
      }
    if (contactList.size() == originalSize) {
      return false;
      }
    return true;
  }
  
  /**
   * The method search searches the strings that is contained in an addressBook (case insensitive).
   * The method takes a string, split by (space, & or +) to several strings, and returns all the contacts that contain any of the target strings.
   * These target strings can be a substring/(whole part) of phoneNumber, a firstName, a lastName,an email, an address or a note. 
   * If the target is phone number, then - ( or ) are ignored in searching.
   * @param target string that are to be searched.
   * @return a list of contact object that contains the exact target string if found, otherwise return empty list.
   */
  public ArrayList<Contact> search(String target) {
    String[] targets = target.toLowerCase().replace("-", "").replace("(", "").replace(")","").split(" |\\+|\\&");
    ArrayList<Contact> resultList = new ArrayList<>();
    Iterator<Contact> it = contactList.iterator();
    while (it.hasNext()) {
      Contact contact = it.next();
      String cleanPhoneNum = contact.getPhoneNumber().replace("-", "").replace("(", "").replace(")","").trim();
      for(String targeti : targets) {
        if (contact.getFirstName().toLowerCase().contains(targeti.trim())) {
          resultList.add(contact);
          }
        else if (contact.getLastName().toLowerCase().contains(targeti.trim())) {
          resultList.add(contact);
          }
        else if (cleanPhoneNum.toLowerCase().contains(targeti.trim())) {
          resultList.add(contact);
          }
        else if (contact.getEmailAddress().toLowerCase().contains(targeti.trim())) {
          resultList.add(contact);
          }
        else if (contact.getPostalAddress().toLowerCase().contains(targeti.trim())) {
          resultList.add(contact);
          }
        else if (contact.getNote().toLowerCase().contains(targeti.trim())) {
          resultList.add(contact);
          }
        }
      }
    return resultList;
    }
  
  /* 
   * returns all the fields and their values of all the contacts in an AddressBook object.
   */
  @Override
  public String toString() {
    String addressbook="";
    for (Contact contact : contactList) {
      addressbook += contact.toString()+" ";
      }
    return addressbook;
    }
}