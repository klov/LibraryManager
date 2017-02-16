/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ivc.libraryweb.services;

import com.ivc.libraryweb.entities.Organization;
import java.util.List;

/**
 *
 * @author Vitaliy Denisov
 */
public interface OrganizationService {

    Organization createOrganization(Organization organization);

    Organization findOrganization(Organization organization);

    List<Organization> findAllOrganization();

    void deleteOrganization(Organization organization);

    Organization updateOrganization(Organization organization);

    Organization findWithDetailOrganization(Organization organization);
}
