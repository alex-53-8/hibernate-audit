package info.biosfood.hibernate.audit.services;

import info.biosfood.hibernate.audit.beans.RevisionEntry;
import info.biosfood.hibernate.audit.entities.Company;
import info.biosfood.hibernate.test.Dataset;
import info.biosfood.hibernate.test.DatasetTestExecutionListener;
import info.biosfood.hibernate.test.FormattingTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.TestCase.assertEquals;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration("/beans.xml")
@Dataset(file = "/data.xml", useImMemoryServer = false)
@TestExecutionListeners(listeners = {
    FormattingTestExecutionListener.class,
    DatasetTestExecutionListener.class
})
public class CompanyServiceTest {

    private static final Logger LOG = Logger.getLogger(CompanyServiceTest.class);

    @Autowired
    CompanyService companyService;

    @Autowired
    CompanyRevisionService companyRevisionService;

    @Test
    public void testAudit() {
        LOG.debug("Fetching a company");
        Long companyId = 1L;

        Company company = companyService.get(companyId);

        assertEquals(companyId, company.getId());

        List<String> assignedBeforeNames = new ArrayList<>();

        assignedBeforeNames.add(assignNewNameAndReturn(company, "New name"));
        assignedBeforeNames.add(assignNewNameAndReturn(company, "New name 2"));
        assignedBeforeNames.add(assignNewNameAndReturn(company, "New name 3"));

        List<RevisionEntry<Company>> revisions = companyRevisionService.get(companyId);

        verifyNames(assignedBeforeNames, revisions);
    }

    protected void verifyNames(List<String> assignedBeforeNames, List<RevisionEntry<Company>> revisions) {
        verifyName(assignedBeforeNames.get(0), revisions.get(0)); // New name
        verifyName(assignedBeforeNames.get(1), revisions.get(1)); // New name 2
        verifyName(assignedBeforeNames.get(2), revisions.get(2)); // New name 3
    }

    protected void verifyName(String name, RevisionEntry<Company> revisionEntry) {
        LOG.debug("--- Verifying name ---");
        LOG.debug("Verified assigned name: " + name);
        LOG.debug("Company name in an object: " + revisionEntry.getItem().getName());
        LOG.debug("User name who made the change: " + revisionEntry.getRevision().getUsername());

        assertEquals(name, revisionEntry.getItem().getName());
    }

    protected String assignNewNameAndReturn(Company company, String newName) {
        company.setName(newName);
        companyService.update(company);

        return newName;
    }

}
