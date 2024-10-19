package org.example.springbootxml_json.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "user")
public class User {
    @Id
    @JacksonXmlProperty(localName = "id") // XML element for id
    private String id;

    @JacksonXmlProperty(localName = "username") // XML element for username
    private String username;
}
