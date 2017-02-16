/*
 */
package com.ivc.libraryweb.repositories;

import com.ivc.libraryweb.entities.Category;
import com.ivc.libraryweb.entities.Organization;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author VitaliyDenisov
 */
@Transactional
@Repository("jpaOrganizationRepository")
public class OrganizationRepositoryImpl implements OrganizationRepository {

    //-------------------Logger---------------------------------------------------
    //-------------------Constants------------------------------------------------
    //-------------------Fields---------------------------------------------------
    @PersistenceContext
    private EntityManager em;
  //-------------------Constructors---------------------------------------------

    //-------------------Getters and setters--------------------------------------
    //-------------------Methods--------------------------------------------------
    public Organization create(Organization organization) {
        em.persist(organization);
        return organization;
    }

    public void delete(Organization organization) {
        em.remove(find(organization));
    }

    public Organization update(Organization organization) {
        int version = find(organization).getVersion();
        organization.setVersion(version);
        return em.merge(organization);
    }

    public List<Organization> findAll() {
        return em.createNamedQuery("Organization.findAll", Organization.class).getResultList();
    }

    public Organization find(Organization organization) {
        return em.find(Organization.class, organization.getId());
    }

    public Organization findWithDetail(Organization organization) {
        return em.createNamedQuery("Organization.findWithDetail", Organization.class).setParameter("id", organization.getId()).getSingleResult();
    }
}
