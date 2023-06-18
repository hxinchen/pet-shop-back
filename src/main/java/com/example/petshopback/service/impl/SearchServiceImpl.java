package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.petshopback.entity.*;
import com.example.petshopback.mapper.SearchMapper;
import com.example.petshopback.service.SearchService;
import com.example.petshopback.utils.Result;
import com.example.petshopback.utils.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.petshopback.mapper.PetMapper;
import com.example.petshopback.mapper.ProductMapper;
import com.example.petshopback.mapper.ShopMapper;


import java.util.List;


@Service
public class SearchServiceImpl extends ServiceImpl<SearchMapper, Search> implements SearchService {

    @Autowired
    private PetMapper petMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ShopMapper shopMapper;

    /**
     * 根据参数进行实体搜索
     *
     * @param currentPage   当前页码
     * @param everyPageSize 每页记录数
     * @param option        选项
     * @param key           关键字
     * @return 搜索结果
     */
    public Result searchEntityByKey(Integer currentPage, Integer everyPageSize, Integer option, String key) {
        Result result = new Result<>();
        if (option == null) {
            result.setStatusCode(ResultType.FAIL.getCode());
            result.setMessage("查询参数option不能为空");
            return result;
        }
        if (StringUtils.isBlank(key)) {
            result.setStatusCode(ResultType.FAIL.getCode());
            result.setMessage("查询参数key不能为空");
            return result;
        }

        try {
            switch (option) {
                case 1:
                    // 如果查询宠物数据，调用 PetMapper 的 selectPage 方法进行分页查询
                    return getPageData(new Page<>(currentPage, everyPageSize), new QueryWrapper<Pet>().like("breed", key).orderByDesc("id"), petMapper, result);
                case 2:
                    // 如果查询商品数据，调用 ProductMapper 的 selectPage 方法进行分页查询
                    return getPageData(new Page<>(currentPage, everyPageSize), new QueryWrapper<Product>().like("name", key).orderByDesc("id"), productMapper, result);
                case 3:
                    // 如果查询商店数据，调用 ShopMapper 的 selectPage 方法进行分页查询
                    return getPageData(new Page<>(currentPage, everyPageSize), new QueryWrapper<Shop>().like("name", key).orderByDesc("id"), shopMapper, result);
                default:
                    result.setStatusCode(ResultType.FAIL.getCode());
                    result.setMessage("无效的查询参数option");
                    return result;
            }
        } catch (Exception e) {
            result.setStatusCode(ResultType.FAIL.getCode());
            result.setMessage(e.getMessage());
            return result;
        }
    }

    /**
     * 将分页数据填充到结果对象中
     *
     * @param iPage  分页对象
     * @param qw     查询包装器
     * @param mapper Mapper对象
     * @param result 结果对象
     * @param <T>    实体类型
     * @return 填充了分页数据的结果对象
     */
    private <T> Result getPageData(IPage<T> iPage, QueryWrapper<T> qw, BaseMapper<T> mapper, Result result) {
        // 使用 mapper 的 selectPage 方法进行分页查询
        List<T> records = mapper.selectPage(iPage, qw).getRecords();
        Long total = mapper.selectCount(qw);

        // 将分页数据都塞到Data对象中
        Data<T> data = new Data<>();
        data.setRecords(records);
        data.setCount(records.size());
        data.setTotal(total);
        data.setPages(iPage.getPages());
        data.setCurrent(iPage.getCurrent());

        // 将 Data 对象和查询结果信息都塞到 Result 对象中，并返回
        result.setData(data);
        result.setStatusCode(ResultType.SUCCESS.getCode());
        result.setMessage(ResultType.SUCCESS.getName());
        return result;
    }

}
