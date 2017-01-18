package info.biosfood.hibernate.audit.listeners;

import info.biosfood.hibernate.audit.context.ApplicationContextHolder;
import info.biosfood.hibernate.audit.entities.Revision;
import info.biosfood.hibernate.audit.services.UserService;
import org.hibernate.envers.RevisionListener;

public class CustomRevisionListener implements RevisionListener {

    UserService userService = new UserService();

    public CustomRevisionListener() {
        userService = ApplicationContextHolder.get(UserService.class);
    }

    @Override
    public void newRevision(Object o) {
        Revision revision = (Revision)o;

        revision.setUsername(getUserName());
    }

    protected String getUserName() {
        return userService.getCurrentUserName();
    }

}
