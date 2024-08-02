package com.meliskaradag.shoppingcartassignmentsystem;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Product {
    @XmlElement
    int id;
    @XmlElement
    double price;
    @XmlElement
    boolean isSold;

    public Product() {}

    public Product(int id, double price) {
        this.id = id;
        this.price = price;
        this.isSold = false;
    }
}
