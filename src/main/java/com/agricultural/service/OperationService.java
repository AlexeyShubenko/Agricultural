package com.agricultural.service;

import com.agricultural.domains.main.TechnologicalOperation;

import java.util.ArrayList;

/**
 * Created by Alexey on 12.09.2017.
 */
public interface OperationService {

    void createOperation(String operationName);
    void deleteOperation(TechnologicalOperation operation);
    void editOperation(TechnologicalOperation operation);
    ArrayList<TechnologicalOperation> getOperations();
    String[] getAllOperationsName();
    TechnologicalOperation getOperationByName(String operationName);

}
