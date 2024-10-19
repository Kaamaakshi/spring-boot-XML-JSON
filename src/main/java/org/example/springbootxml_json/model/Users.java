package org.example.springbootxml_json.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
public class Users {
    @JacksonXmlProperty(localName = "user") // The wrapper element
    @JacksonXmlElementWrapper(useWrapping = false) // Prevent wrapping in <users>
    private List<User> users; // List of User objects
}