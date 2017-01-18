package info.biosfood.hibernate.audit.beans;

import info.biosfood.hibernate.audit.entities.Revision;

public class RevisionEntry<T> {
    private Revision revision;
    private T item;

    public RevisionEntry(T item, Revision revision) {
        this.item = item;
        this.revision = revision;
    }

    public Revision getRevision() {
        return revision;
    }

    public T getItem() {
        return item;
    }
}
