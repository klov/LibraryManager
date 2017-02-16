
package com.ivc.libraryweb.services;

import com.ivc.libraryweb.entities.Organization;
import com.ivc.libraryweb.repositories.OrganizationRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static org.springframework.util.Assert.notNull;

/**
 *
 * @author Vitaliy Denisov
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    //-------------------Logger---------------------------------------------------
    //-------------------Constants------------------------------------------------
    public final static String INVALID_PARAMETRE = "The organization contains inadmissible value:";
    //-------------------Fields---------------------------------------------------
    private OrganizationRepository organizationRepository;
  //-------------------Constructors---------------------------------------------

    //-------------------Getters and setters--------------------------------------
    @Autowired(required = false)
    public void setOrganizationRepository(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    //-------------------Methods--------------------------------------------------
    public Organization createOrganization(Organization organization) {
        notNull(organization, INVALID_PARAMETRE + organization);
        return organizationRepository.create(organization);
    }

    public Organization findOrganization(Organization organization) {
        notNull(organization, INVALID_PARAMETRE + organization);
        return organizationRepository.find(organization);
    }

    public List<Organization> findAllOrganization() {
        return organizationRepository.findAll();
    }

    public void deleteOrganization(Organization organization) {
        notNull(organization, INVALID_PARAMETRE + organization);
        organizationRepository.delete(organization);
    }

    public Organization updateOrganization(Organization organization) {
        notNull(organization, INVALID_PARAMETRE + organization);
        return organizationRepository.update(organization);
    }

    public Organization findWithDetailOrganization(Organization organization) {
        notNull(organization, INVALID_PARAMETRE + organization);
        return organizationRepository.findWithDetail(organization);
    }

}
