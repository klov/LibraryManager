/*
   Информационно-вычислительный центр космодрома Байконур
*/

package com.ivc.libraryweb.repositories;

import com.ivc.libraryweb.entities.Notice;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("jpaNoticeRepository")
public class NoticeRepositoryImpl implements  NoticeRepository{
 @PersistenceContext
    private EntityManager em;

    //-------------------Constructors---------------------------------------------
    //-------------------Getters and setters--------------------------------------
    //-------------------Methods--------------------------------------------------
    public List<Notice> findAll() {
        return em.createNamedQuery("Notice.findAll", Notice.class).getResultList();
    }

    public Notice find(Notice notice) {
        return em.find(Notice.class, notice.getId());
    }


    public Notice create(Notice notice) {
        em.persist(notice);
        return notice;
    }

    public Notice update(Notice notice) {
        int version = find(notice).getVersion();
        notice.setVersion(version);
        return em.merge(notice);
    }

    public void delete(Notice notice) {
        Notice p = find(notice);
        p.getDocument().getComment().remove(p);
        em.remove(p);
    }


}