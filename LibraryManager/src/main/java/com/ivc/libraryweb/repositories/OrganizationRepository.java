
package com.ivc.libraryweb.repositories;

import com.ivc.libraryweb.entities.Organization;
import java.util.List;


public interface OrganizationRepository {

    /**
     * Creates the organization.
     *
     * @param organization the organization.
     * @return created organization.
     */
    Organization create(Organization organization);

    /**
     * Delete the organization.
     *
     * @param organization the organization to delete.
     */
    void delete(Organization organization);

    /**
     * Update the organization.
     *
     * @return updated organization.
     */
    Organization update(Organization organization);

    /**
     * Returns the list of organizations.
     *
     * @return the list of organizations.
     */
    List<Organization> findAll();

    /**
     * Return the organization.
     *
     * @return the organization.
     */
    Organization find(Organization organization);

    Organization findWithDetail(Organization organization);

}
