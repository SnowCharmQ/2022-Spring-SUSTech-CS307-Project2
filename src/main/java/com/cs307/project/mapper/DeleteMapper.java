package com.cs307.project.mapper;

public interface DeleteMapper {
    void deleteOrder(String contract, int salesmanID, int seq);

    void deleteStock();
}
