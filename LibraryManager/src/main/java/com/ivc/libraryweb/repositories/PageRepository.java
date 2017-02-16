
package com.ivc.libraryweb.repositories;

import com.ivc.libraryweb.entities.Page;
import java.util.List;

/**
 *
 * @author Sokolov@ivc.org
 */
public interface PageRepository {

    List<Page> findAll();
    
    List<Page> findPagesForDocument(int documentId);

    Page find(Page page);

    Page findWithDetail(Page page);

    Page create(Page page);

    Page update(Page page);

    void delete(Page page);

}
