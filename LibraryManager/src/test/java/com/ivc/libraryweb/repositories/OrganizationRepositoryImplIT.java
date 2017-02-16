/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivc.libraryweb.repositories;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.ivc.libraryweb.entities.Book;
import com.ivc.libraryweb.entities.Category;
import com.ivc.libraryweb.entities.Organization;
import com.ivc.libraryweb.integration.config.TestConfig;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import static org.assertj.core.api.AssertionsForClassTypes.extractProperty;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

/**
 *
 * @author VitaliyDenisov
 */
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, 
                        classes = {TestConfig.class,OrganizationRepositoryImpl.class})
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
       DirtiesContextTestExecutionListener.class,
       DbUnitTestExecutionListener.class })
public class OrganizationRepositoryImplIT extends AbstractTransactionalJUnit4SpringContextTests {

    private static final String[] ORGANIZATION_NAME = new String[]{"org1", "org2", "newOrg"};
    private static final String[] ALL_ORGANIZATION_NAME = new String[]{"org1", "org2"};
    private static final String[] BOOK_LIST = new String[]{"myBook1","myBook2"};
    
    
    @Autowired
    OrganizationRepository  organizationRepository;

    @PersistenceContext
    private EntityManager em;

    private Organization validOrganization = new Organization("org1", "adr1");
    private Organization deleteOrganization = new Organization("org2", "adr2");
    private Organization newOrganization = new Organization("newOrg", "adr");
    private Book newBook = new Book("newBook", "1", "1", "1", "", "1s");

    /**
     * Test of create method, of class OrganizationRepositoryImpl.
     */
    @Before
    public void before() {
        validOrganization.setId(new Long(1));
        deleteOrganization.setId(new Long(2));
    }

    @Test
    @DatabaseSetup("classpath:organization_data.xml")
    public void testCreate() {
        organizationRepository.create(newOrganization);
        List<Organization> l = em.createNamedQuery("Organization.findAll", Organization.class).getResultList();
        assertThat(extractProperty(Organization.NAME_PROPERTY).from(l))
                .hasSize(ORGANIZATION_NAME.length)
                .containsOnly((Object[]) ORGANIZATION_NAME);
    }

    /**
     * Test of delete method, of class OrganizationRepositoryImpl.
     */
    @Test
    @DatabaseSetup("classpath:organization_data.xml")
    public void testDelete() {
        organizationRepository.delete(deleteOrganization);
        List<Organization> l = em.createNamedQuery("Organization.findAll", Organization.class).getResultList();
        assertTrue((l.size() == 1) && l.get(0).equals(validOrganization));
    }

    /**
     * Test of update method, of class OrganizationRepositoryImpl.
     */
    @Test
    @DatabaseSetup("classpath:organization_data.xml")
    public void testUpdate() {
        validOrganization.setName("newName");
        organizationRepository.update(validOrganization);
        Organization c =  em.find(Organization.class, validOrganization.getId());
        assertEquals(validOrganization, c);
  }

    /**
     * Test of findAll method, of class OrganizationRepositoryImpl.
     */
    @Test
    @DatabaseSetup("classpath:organization_data.xml")
    public void testFindAll() {
        List<Organization> findList = organizationRepository.findAll();
        assertThat(extractProperty(Category.NAME_PROPERTY).from(findList))
                .hasSize(ALL_ORGANIZATION_NAME.length)
                .containsOnly((Object[]) ALL_ORGANIZATION_NAME);
    }

    /**
     * Test of find method, of class OrganizationRepositoryImpl.
     */
    @Test
    @DatabaseSetup("classpath:organization_data.xml")
    public void testFind() {
        Organization organization = organizationRepository.find(validOrganization);
        assertEquals(validOrganization, organization);
    }



}
