/*
 Информационно-вычислительный центр космодрома Байконур
 */
package com.ivc.libraryweb.services;

import com.ivc.libraryweb.entities.Document;
import com.ivc.libraryweb.entities.Notice;
import com.ivc.libraryweb.repositories.DocumentRepository;
import com.ivc.libraryweb.repositories.NoticeRepository;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author VitaliyDenisom
 */
@Service
public class NiticeServiceImpl implements NoticeService {

    //-------------------Logger---------------------------------------------------
    //-------------------Constants------------------------------------------------
    public NiticeServiceImpl() {
    }

    //-------------------Fields---------------------------------------------------
    private DocumentRepository documentRepository;
    private NoticeRepository noticeRepository;

    //-------------------Constructors---------------------------------------------
    //-------------------Getters and setters--------------------------------------
    @Autowired(required = false)
    public void setNoticeRepository(NoticeRepository noticeRepository) {
        this.noticeRepository = noticeRepository;
    }

    @Autowired(required = false)
    public void setDocumentRepository(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    //-------------------Methods--------------------------------------------------
    
    public Notice createNotice(Notice notice, Document document) {
        Document oldDocument =  documentRepository.find(document);
        notice.setDocument(oldDocument);
        notice.setDate(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        return noticeRepository.create(notice);
    }

    @Override
    public void deleteNotice(Notice notice) {
        noticeRepository.delete(noticeRepository.find(notice));
    }
}
