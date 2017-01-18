package info.biosfood.hibernate.audit.services;

import info.biosfood.hibernate.audit.beans.RevisionEntry;
import info.biosfood.hibernate.audit.entities.Company;
import info.biosfood.hibernate.audit.entities.Revision;
import org.hibernate.SessionFactory;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CompanyRevisionService {

    @Autowired
    private SessionFactory sessionFactory;

    public List<RevisionEntry<Company>> get(Long id) {
        AuditReader reader = AuditReaderFactory.get(sessionFactory.getCurrentSession());

        AuditQuery byId = reader.createQuery()
                .forRevisionsOfEntity(Company.class, false, true)
                .add(AuditEntity.id().eq(id));

        List<RevisionEntry<Company>> revisions = new ArrayList<>();

        for (Object row : byId.getResultList()) {
            Object[] array = (Object[]) row;
            revisions.add(new RevisionEntry<>((Company) array[0], (Revision) array[1]));
        }

        return revisions;
    }

}
