package com.shopping.service;

import com.shopping.common.pojo.SearchResult;

public interface SearchService {
    SearchResult search(String keyword,int page,int rows) throws  Exception;
}
