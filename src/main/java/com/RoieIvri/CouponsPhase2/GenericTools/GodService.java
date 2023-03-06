package com.RoieIvri.CouponsPhase2.GenericTools;

import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public interface GodService<T> {

    boolean isObjectExist(String email,String password) throws Exception;
    public T addObject(T t) throws Exception;
    T updateObject(T t,Long objectId) throws Exception;
    void deleteObject(Long objectId) throws Exception;
    List<T> getAllObjects() throws Exception;
    T getOneObject(Long objectId) throws Exception;


}
