package com.example.petshopback.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import java.util.ArrayList;
import java.util.List;

public class Transform {
    public <T> Page listToPage(List<T> list, int pageNum, int pageSize) {
        List<T> pageList = new ArrayList<>();
        int curIdx = pageNum > 1 ? (pageNum - 1) * pageSize : 0;
        for (int i = 0; i < pageSize && curIdx + i < list.size(); i++) {
            pageList.add(list.get(curIdx + i));
        }
        Page page = new Page<>(pageNum, pageSize);
        page.setRecords(pageList);
        page.setTotal(list.size());
        return page;
    }
}
