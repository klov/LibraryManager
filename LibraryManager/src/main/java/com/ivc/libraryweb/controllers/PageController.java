package com.ivc.libraryweb.controllers;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_USER = "ROLE_USER";
    public static final String BOOK_TYPES_PAGE_PATH = "/book_types_page";
    public static final String CATEGORY_PAGE = "/category_page";
    public static final String POSTS_PAGE_PATH = "/posts_page";
    public static final String DEPARTMENTS_PAGE_PATH = "/departments_page";
    public static final String DOCUMENT_PAGE_PATH = "/document_page";
    public static final String BOOK_PAGE = "/book_page";
    public static final String ORGANIZATION_PAGE = "/organization_page";
    public static final String DELIVERY_PAGE = "/delivery_page";

    @RequestMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @RequestMapping("/")
    public String getStartPage() {
        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        List<? extends GrantedAuthority> d = new ArrayList(userDetails.getAuthorities());
        if (d.get(0).getAuthority().equals(ROLE_ADMIN)) {
            return "book_page";                                                                     // надо немного потом переделать
        }
        return "user_book_page";
    }

    @RequestMapping("/test")
    public String getTestPage() {
        return "test";
    }

    @RequestMapping(BOOK_TYPES_PAGE_PATH)
    public String getBookTypePage() {
        return "book_types_page";
    }

    @RequestMapping(CATEGORY_PAGE)
    public String getCategoryPage() {
        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        List<? extends GrantedAuthority> d = new ArrayList(userDetails.getAuthorities());
        if (d.get(0).getAuthority().equals(ROLE_ADMIN)) {
            return "categories_page";
        }
        return "user_categories_page";
    }

    @RequestMapping(DOCUMENT_PAGE_PATH)
    public String getDocumentPage() {
        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        List<? extends GrantedAuthority> d = new ArrayList(userDetails.getAuthorities());
        if (d.get(0).getAuthority().equals(ROLE_ADMIN)) {
            return "document_page";
        } else {
            return "user_document_page";
        }
    }

    @RequestMapping(BOOK_PAGE)
    public String getBookPage() {
   Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        List<? extends GrantedAuthority> d = new ArrayList(userDetails.getAuthorities());
        if (d.get(0).getAuthority().equals(ROLE_ADMIN)) {
            return "book_page";                                                                     // надо немного потом переделать
        }
        return "user_book_page";
    }

    @RequestMapping(ORGANIZATION_PAGE)
    public String getOrganizationPage() {
        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        List<? extends GrantedAuthority> d = new ArrayList(userDetails.getAuthorities());
        if (d.get(0).getAuthority().equals(ROLE_ADMIN)) {
            return "organization_page";
        }
        return "user_organization_page";
    }

    @RequestMapping(DELIVERY_PAGE)
    public String getDeliveryPage() {
        return "delivery_page";
    }
}
