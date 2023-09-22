package com.example.petshopback.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.petshopback.entity.Pet;
import com.example.petshopback.entity.PetCategory;
import com.example.petshopback.entity.Product;
import com.example.petshopback.mapper.ProductMapper;
import com.example.petshopback.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.petshopback.utils.Transform;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 产品表（商品，周边） 服务实现类
 * </p>
 *
 * @author hahaha
 * @since 2023-06-09 10:14:35
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private PetService petService;
    @Autowired
    private PetCategoryService petCategoryService;
    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ShopService shopService;


    @Override
    public Page<Product> pageByShopId(Integer pageNum, Integer pageSize, Integer shopId) {
        Page<Product> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("shop_id", shopId);
        return this.page(page, queryWrapper);
    }

    @Override
    public boolean addProduct(Product product) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", product.getName());
        Product isExist = getOne(queryWrapper);
        if (isExist != null) {
            System.out.println("该宠物周边已存在，新增失败");
            return false;
        }
        save(product);
        return true;
    }
    @Override
    public Page<Product> getByCategory(Integer pageNum, Integer pageSize, Integer category) {
//        Page<Product> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        Transform transform = new Transform();
        if (category > 0) {
            queryWrapper.eq("category_id", category);
        }
        //return this.page(page, queryWrapper);
        queryWrapper.gt("stock", 0);
        //按照访问量降序排列
        queryWrapper.orderByDesc("access_count");
        //return this.page(page, queryWrapper);
        return transform.listToPage(this.list(queryWrapper), pageNum, pageSize);
    }

    @Override
    public Page<Product> getByShop(Integer pageNum, Integer pageSize, Integer category) {
//        Page<Product> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        Transform transform = new Transform();
        List<Product> productList = new ArrayList<>();
        if (category == -1) {
            // 添加查询条件，查询stock>0的记录
            queryWrapper.gt("stock", 50);
            productList = this.list(queryWrapper);
            return transform.listToPage(productList, pageNum, pageSize);
        }
//            return this.page(page, queryWrapper);
        queryWrapper.gt("stock", 0).eq("shop_id", category);
//        return this.page(page, queryWrapper);
        productList = this.list(queryWrapper);
        return transform.listToPage(productList, pageNum, pageSize);
    }


    @Override
    public boolean deleteByIds(String ids) {
        List<String> list = new ArrayList<>();
        String[] array = ids.split(",");
        for (String i:array) {
            list.add(i);
        }
        return this.removeByIds(list);
    }

    @Override
    public List<Integer> getShopIds(String ids) {
        List<Integer> list = new ArrayList<>();
        String[] array = ids.split(",");

        for (String id : array) {
            Integer productId = Integer.valueOf(id);
            Product product = this.getById(productId);
            if (product != null) {
                list.add(product.getShopId());
            }
        }

        return list;
    }

    @Override
    public Product getByIdStock(Integer id) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id).gt("stock", 0);
        return this.getOne(queryWrapper);
    }

    @Override
    public boolean modifyStockByIds(String ids, String isPets, String counts) {
        String[] array = ids.split(",");
        String[] arrayIsPet = isPets.split(",");
        String[] arrayCount = counts.split(",");
        // 根据是否是宠物周边，宠物则修改useful,否则修改stock
        for (Integer i = 0; i < array.length; i++) {
            if (arrayIsPet[i].equals("1")) {
                petService.updateUseful(Integer.valueOf(array[i]));
            } else if (arrayIsPet[i].equals("0")){
                Product product = this.getById(Integer.valueOf(array[i]));
                if (product != null) {
                    product.setStock(product.getStock() - Integer.valueOf(arrayCount[i]));
                    this.updateById(product);
                }
            }
        }
//        for (String id : array) {
//            Integer productId = Integer.valueOf(id);
//            Product product = this.getById(productId);
//            if (product != null) {
//                product.setStock(product.getStock() + stock);
//                this.updateById(product);
//            }
//        }
        return true;
    }

    @Override
    public List<Object> getProOrPetByIds(String ids, String isPet) {
        List<Object> list = new ArrayList<>();

        String[] array = ids.split(",");
        String[] arrayIsPet = isPet.split(",");
        // 根据是否是宠物周边，分别查询
        for (Integer i = 0; i < array.length; i++) {
            if (arrayIsPet[i].equals("1")) {
                Pet pet = petService.getById(Integer.valueOf(array[i]));
                if (pet != null) {
                    pet.put("cateName", petCategoryService.getById(pet.getCategoryId()).getName());
                    pet.put("shopName", shopService.getById(pet.getShopId()).getName());
//                    pet.put("address", userAddressService.getDefault());
                    list.add(pet);
                }
            } else {
                Product product = this.getById(Integer.valueOf(array[i]));
                if (product != null) {
                    product.put("cateName", productCategoryService.getById(product.getCategoryId()).getName());
                    product.put("shopName", shopService.getById(product.getShopId()).getName());
//                    product.put("address", userAddressService.getDefault());
                    list.add(product);
                }
            }
//            if (arrayIsPet[Integer.valueOf(i)].equals("1")) {
//                Pet pet = petService.getById(Integer.valueOf(i));
//                if (pet != null) {
//                    list.add(pet);
//                }
//            } else {
//                Product product = this.getById(Integer.valueOf(i));
//                if (product != null) {
//                    list.add(product);
//                }
//            }
//            list.add(this.getById(Integer.valueOf(i)));
        }
        return list;
    }

    @Override

    public List<Product> getByProIds(String ids) {
        List<Product> list = new ArrayList<>();
        String[] array = ids.split(",");
        for (String i:array) {
            QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("id", "name", "price", "img").eq("id", Integer.valueOf(i));
            list.add(this.getOne(queryWrapper));
        }
        return list;
    }

    public boolean checkStock(Integer id) {
        Product product = this.getById(id);
        if (product != null) {
            if (product.getStock() > 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addAccessCount(Integer id) {
        Product product = this.getById(id);
        if (product != null) {
            product.setAccessCount(product.getAccessCount() + 1);
            this.updateById(product);
        }
    }
}
