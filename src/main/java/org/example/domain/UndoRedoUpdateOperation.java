package org.example.domain;

import org.example.repository.IRepository;

public class UndoRedoUpdateOperation<T extends Entity> implements IUndoRedoOperation {
    private IRepository<T> repository1;
    private IRepository<T> repository2;
    private T entity;

    public UndoRedoUpdateOperation(IRepository<T> repository1,IRepository<T> repository2, T entity) {
        this.repository1 = repository1;
        this.repository2 = repository2;
        this.entity = entity;
    }

    @Override
    public void doUndo() {
        this.repository2.create(entity);
        this.repository1.delete(entity.getIdEntity());
        this.repository1.create(entity);
    }

    @Override
    public void doRedo() {
        this.repository2.update(entity);

    }
}
