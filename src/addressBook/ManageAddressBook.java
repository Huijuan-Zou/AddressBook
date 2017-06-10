package addressBook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * ManageAddressBook class contains two methods read and save. 
 * 
 * The save method's function is to save a addressBook to a XML file.
 * The input requires the name of the addressBook to be saved. 
 * The Input also takes the path to save the new XML file.
 * The Input can have one more optional fileName to save the addressBook. A new XML file with that fileName will be created. 
 * The save method have no return statement.
 * The save method throws JAXBException Exception and FileNotFoundException. 
 * The method save is public. To use it, call ManageAddressBook.save(NameOfAddressBook,"","") if use default path and fileName ("addressBook").
 * Call ManageAddressBook.save(NameOfAddressBook,pathName,fileName) if use your own path and fileName.
 * Please use default fileName with care. New file will erase old file with same default fileName.
 * 
 * * The function of the method read is to read from a XML file and output an Object addressBook.
 * The input of the method read requires a fileName. A input of filePath is optional.
 * The method read returns the addressBook object given the filePath and fileName.
 * The read method throws JAXBException Exception and FileNotFoundException. 
 * The method read is public. 
 * To use it, call ManageAddressBook.read("",fileName) if read from a file from default filePath (root relative path).
 * call ManageAddressBook.read(pathName,fileName) if you want to read from a file that has a path different from the root relative path.
 * If file not found, FileNotFoundException will be thrown.
 * 
 */
public class ManageAddressBook {
  
  /**
   * The method read a XML file and returns an object addressBook.
   * The input of the fileName is required. It must be a XML file. 
   * The input of the fileName can have no .xml suffix.
   * If the input fileName has no .xml suffix, the method read will add a .xml suffix.
   * Path must be absolute path or "".
   * If path is "". The XML file must exist in the root relative path.
   * 
   * @param path the directory of the file to be read.
   * @param fileName the name of the file to be read.
   * @return the addressBook read from the XML file.
   * @throws JAXBException Exception will be thrown if AddressBook.class or contact.class is not annotated correctly.
   * @throws FileNotFoundException Exception will be thrown if fileName or path not correct.
   */
  public static AddressBook read(String path, String fileName) throws JAXBException, FileNotFoundException {
    File file = null;
    final Unmarshaller jaxbUnmarshaller = JAXBContext.newInstance(AddressBook.class, Contact.class).createUnmarshaller();
    if (fileName.contains(".xml")) {
      file = new File(path + fileName);
      }
    else {
      file = new File(path + fileName + ".xml");
      }
    FileInputStream inputStream = new FileInputStream(file);
    AddressBook addressBook = (AddressBook) jaxbUnmarshaller.unmarshal(inputStream);
    return addressBook;
  }

  /**
   * The Input of save method is an addressBook name and a file path.
   * The addressBook is saved to be XML file.
   * AddressBook name is required for this method.
   * File path or fileName is not required.
   * If file path is set to "", then current folder path will be used;
   * If file path set to "", then a default file name "addressName" will be used;
   * 
   * @param addressBook name of the addressBook to be saved.
   * @param path absolute path for the file if path is not set ""; otherwise it's the path of current folder.
   * @param fileName the name of the new file to be created to save the addressBook.
   * @throws JAXBException Exception will be thrown if AddressBook.class or contact.class is not annotated correctly.
   * @throws FileNotFoundException Exception will be thrown if fileName or path not correct.
   */
  public static void save(AddressBook addressBook, String path,String fileName) throws JAXBException, FileNotFoundException {
    Marshaller MarshallerObj;
    if (fileName.equals("")) {
      fileName = "addressBook";
      }
    MarshallerObj = JAXBContext.newInstance(AddressBook.class,Contact.class).createMarshaller();
    MarshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
    MarshallerObj.marshal(addressBook, new FileOutputStream(path + fileName + ".xml"));
  }
}