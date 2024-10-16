package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpa-persistence");
        // toda vez que for acessar o banco de dados
        EntityManager entityManager = factory.createEntityManager();


        entityManager.close();
        factory.close();
    }
}