package info.biosfood.hibernate.audit.services;

import info.biosfood.hibernate.audit.entities.Company;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompanyService {

    @Autowired
    private SessionFactory sessionFactory;

    public Company get(Long id) {
        Session session = sessionFactory.getCurrentSession();

        return (Company)session.get(Company.class, id);
    }

    public void update(Company company) {
        sessionFactory.getCurrentSession().update(company);
    }

}
