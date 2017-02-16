
package com.ivc.libraryweb.controllers;

import static com.ivc.libraryweb.controllers.OrganizationRestController.ORGANIZATION_PAGE;
import com.ivc.libraryweb.entities.Organization;
import com.ivc.libraryweb.services.OrganizationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author @author Vitaliy Denisov
 */
@RestController
@RequestMapping(path = ORGANIZATION_PAGE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class OrganizationRestController {

    //-------------------Constants------------------------------------------------
    public static final String ORGANIZATION_PAGE = "/organizations";
    //-------------------Fields---------------------------------------------------
    OrganizationService organizationService;
    //-------------------Constructors---------------------------------------------

    public OrganizationRestController() {
    }

    //-------------------Getters and setters--------------------------------------
    @Autowired(required = false)
    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    //-------------------Methods--------------------------------------------------
    @RequestMapping(method = RequestMethod.GET)
    public List<Organization> getAllOrganization() {
        return organizationService.findAllOrganization();
    }

    @RequestMapping(method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organization createOrganization(@RequestBody Organization organization) {
        return organizationService.createOrganization(organization);
    }

    @RequestMapping(method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public Organization updateOrganization(@RequestBody Organization organization) {
        return organizationService.updateOrganization(organization);
    }

    @RequestMapping(method = RequestMethod.DELETE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void deleteOrganization(@RequestBody Organization organization) {
        organizationService.deleteOrganization(organization);
    }
}
