package com.byteshaper.vcardrangeur.service;

import static com.byteshaper.vcardrangeur.domain.Person.UNKNOWN_PROPERTY;

import com.byteshaper.vcardrangeur.domain.AddressBook;
import com.byteshaper.vcardrangeur.domain.Person;
import com.byteshaper.vcardrangeur.domain.TelephoneNumber;
import com.byteshaper.vcardrangeur.repository.VcardRepository;
import ezvcard.Ezvcard;
import ezvcard.VCard;
import ezvcard.property.Nickname;
import ezvcard.property.StructuredName;
import ezvcard.property.Telephone;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VcardService {
  
  private static final Logger LOGGER = LoggerFactory.getLogger(VcardService.class);
  
  private static final String NAME_FORMAT = "%s %s";

  @Autowired
  private VcardRepository vcardRepository;

  public Optional<AddressBook> getOriginal(String key) {

    Optional<String> content = vcardRepository.find(key);
    LOGGER.info("Trying to get persons for key " + key);

    if (content.isPresent()) {
      List<Person> persons = Ezvcard
          .parse(content.get())
          .all()
          .stream()
          .map(this::toAddress)
          .collect(Collectors.toList());
      return Optional.of(new AddressBook(persons));
    }

    return Optional.empty();
  }
  
  private Person toAddress(VCard vcard) {
    Person address = new Person();
    address.fullName = vcard
        .getStructuredName() != null ? 
            createFullName(vcard.getStructuredName()) : 
              getNickname(vcard.getNickname());
    address.telephonNumbers = vcard
        .getTelephoneNumbers()
        .stream()
        .map(this::toTelephoneNumber)
        .collect(Collectors.toList());
    return address;
  }
  
  private String createFullName(StructuredName structuredName) {
    if(structuredName.getGiven() != null && structuredName.getFamily() != null) {
      return String.format(NAME_FORMAT, structuredName.getGiven(), structuredName.getFamily());
    } else if(structuredName.getGiven() != null) {
      return structuredName.getGiven();
    } else if(structuredName.getFamily() != null) {
      return structuredName.getFamily();
    }
    
    LOGGER.info("Unknown name");
    return UNKNOWN_PROPERTY;
  }
  
  private String getNickname(Nickname nickNameOrNull) {
    if(nickNameOrNull != null) {
      nickNameOrNull.toString();
    }
    
    return UNKNOWN_PROPERTY;
  }
  
  private TelephoneNumber toTelephoneNumber(Telephone telephone) {
    TelephoneNumber telephoneNumber = new TelephoneNumber();
    telephoneNumber.number = telephone.getText();
    telephoneNumber.type = telephone.getTypes().stream().map(t -> t.getValue()).collect(Collectors.joining(";"));
    return telephoneNumber;
  }
}
