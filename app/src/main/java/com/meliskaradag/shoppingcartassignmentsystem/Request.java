package com.meliskaradag.shoppingcartassignmentsystem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Request {
    @XmlElement
    double cardLimit;
    @XmlElementWrapper(name = "products")
    @XmlElement(name = "product")
    List<Product> products;
}
