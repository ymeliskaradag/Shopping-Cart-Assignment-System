package com.meliskaradag.shoppingcartassignmentsystem;

import java.io.*;
import java.net.Socket;
import java.util.*;
import com.google.gson.Gson;
import javax.xml.bind.*;

class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (InputStream input = socket.getInputStream();
             OutputStream output = socket.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input));
             PrintWriter writer = new PrintWriter(output, true)) {

            StringBuilder requestBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null && !line.isEmpty()) {
                requestBuilder.append(line);
            }

            String requestString = requestBuilder.toString();
            Request request;
            boolean isJson = requestString.trim().startsWith("{");

            if (isJson) {
                Gson gson = new Gson();
                request = gson.fromJson(requestString, Request.class);
            } else {
                JAXBContext jaxbContext = JAXBContext.newInstance(Request.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                StringReader stringReader = new StringReader(requestString);
                request = (Request) jaxbUnmarshaller.unmarshal(stringReader);
            }

            List<Product> products = createCart(request.cardLimit, request.products);

            Response response = new Response(products);
            String responseString;
            if (isJson) {
                Gson gson = new Gson();
                responseString = gson.toJson(response);
            } else {
                JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                StringWriter stringWriter = new StringWriter();
                jaxbMarshaller.marshal(response, stringWriter);
                responseString = stringWriter.toString();
            }

            writer.println(responseString);

        } catch (IOException | JAXBException e) {
            System.out.println("Server exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<Product> createCart(double cardLimit, List<Product> products) {
        List<Product> cart = new ArrayList<>();
        double total = 0;

        for (Product product : products) {
            if (!product.isSold && total + product.price <= cardLimit) {
                total += product.price;
                product.isSold = true;
                cart.add(product);
            } else {
                product.isSold = false;
            }
        }

        return cart;
    }
}
