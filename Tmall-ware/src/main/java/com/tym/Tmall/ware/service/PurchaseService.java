package com.tym.Tmall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tym.Tmall.common.utils.PageUtils;
import com.tym.Tmall.ware.entity.PurchaseEntity;
import com.tym.Tmall.ware.vo.MergeVO;
import com.tym.Tmall.ware.vo.PurchaseFinishVO;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author AemonT
 * @email 1211148525@qq.com
 * @date 2023-04-30 18:33:37
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceivePurchase(Map<String, Object> params);

    void mergePurchase(MergeVO mergeVO);

    void received(List<Long> ids);

    void done(PurchaseFinishVO purchaseFinishVO);
}

