package org.example.domain;

import org.example.repository.IRepository;

public class UndoRedoUpdateOperation<T extends Entity> implements IUndoRedoOperation {
    private IRepository<T> repository;
    private T entity;

    @Override
    public void doUndo() {
        this.repository.create(entity);
    }

    @Override
    public void doRedo() {
        this.repository.update(entity);

    }
}
