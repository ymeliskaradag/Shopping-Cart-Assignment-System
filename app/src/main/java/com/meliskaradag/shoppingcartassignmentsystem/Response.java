package com.meliskaradag.shoppingcartassignmentsystem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Response {
    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
    List<Product> products;

    public Response() {}  // JAXB needs a no-arg constructor

    public Response(List<Product> products) {
        this.products = products;
    }
}
