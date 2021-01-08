package com.qr.dataobject.service.impl;

import com.qr.dataobject.entity.DataNames;
import com.qr.dataobject.mapper.DataNamesMapper;
import com.qr.dataobject.service.IDataNamesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 数据对象名称表 服务实现类
 * </p>
 *
 * @author wd
 * @since 2020-08-28
 */
@Service
public class DataNamesServiceImpl extends ServiceImpl<DataNamesMapper, DataNames> implements IDataNamesService {

}
