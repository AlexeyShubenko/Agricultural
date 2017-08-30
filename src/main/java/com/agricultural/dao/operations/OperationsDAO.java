package com.agricultural.dao.operations;

import com.agricultural.domains.main.TechnologicalOperation;

import java.util.ArrayList;

/**
 * Created by Alexey on 14.02.2017.
 */
public interface OperationsDAO {

    void createOperation(String operationName);
    void deleteOperation(TechnologicalOperation operation);
    void editOperation(TechnologicalOperation operation);
    ArrayList<TechnologicalOperation> getOperations();
    String[] getAllOperationsName();
    TechnologicalOperation getOperationByName(String operationName);
}
