package com.cg.bankapp.utils;

import javax.persistence.Persistence;

import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntityManagerGenerator {
	private EntityManagerGenerator() {
		throw new IllegalStateException();
	}

	private static EntityManager entityManager = null;
	static {
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bank-application-pu");
		entityManager = emf.createEntityManager();
	}

	public static EntityManager getEntityManager() {
		return entityManager;
	}
}
