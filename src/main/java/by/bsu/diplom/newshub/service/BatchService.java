package by.bsu.diplom.newshub.service;

import by.bsu.diplom.newshub.exception.BatchServiceException;

import java.util.List;

/**
 * Service allows save batch of entities in one transaction
 */
public interface BatchService<T> {

    /**
     * Save list of entities in one transaction
     *
     * @param list with entities to save
     */
    void saveBatch(List<T> list) throws BatchServiceException;
}
