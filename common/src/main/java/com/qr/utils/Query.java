package com.qr.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qr.base.PageParams;
import com.qr.utils.xss.SQLFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询参数
 *
 */
public class Query<T> extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
    /**
     * mybatis-plus分页参数
     */
    private Page<T> page;
    /**
     * 当前页码
     */
    private int currPage = 1;
    /**
     * 每页条数
     */
    private int limit = 10;

    public Query(PageParams pageParams){
        Map params = JSONObject.parseObject(JSON.toJSONString(pageParams), Map.class);
        this.putAll(params);

        //分页参数
        if(params.get("page") != null){
            currPage = Integer.parseInt(String.valueOf(params.get("page")));
        }
        if(params.get("limit") != null){
            limit = Integer.parseInt(String.valueOf(params.get("limit")));
        }

        this.put("offset", (currPage - 1) * limit);
        this.put("page", currPage);
        this.put("limit", limit);

        //防止SQL注入（因为sidx、order是通过拼接SQL实现排序的，会有SQL注入风险）
        String sidx = SQLFilter.sqlInject((String)params.get("sidx"));
        String order = SQLFilter.sqlInject((String)params.get("order"));
        this.put("sidx", sidx);
        this.put("order", order);

        //mybatis-plus分页
        this.page = new Page<>(currPage, limit);

        //排序
        if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(order)){
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(sidx);
            orderItem.setAsc("ASC".equalsIgnoreCase(order));
            List<OrderItem> orderItemList = new ArrayList();
            orderItemList.add(orderItem);
            this.page.setOrders(orderItemList);
        }

    }

    public Page<T> getPage() {
        return page;
    }

    public int getCurrPage() {
        return currPage;
    }

    public int getLimit() {
        return limit;
    }
}
