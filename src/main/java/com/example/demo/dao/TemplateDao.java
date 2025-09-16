package com.example.demo.dao;

import java.util.List;

public interface TemplateDao<T> {
    List<T> retrieveList(String tmp, T reqMap) throws Exception;
    boolean register(String tmp,T model) throws Exception;
}
