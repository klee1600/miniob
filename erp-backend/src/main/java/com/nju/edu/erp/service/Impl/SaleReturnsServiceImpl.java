package com.nju.edu.erp.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.nju.edu.erp.dao.SaleReturnsSheetContentDao;
import com.nju.edu.erp.dao.SaleReturnsSheetDao;
import com.nju.edu.erp.dao.SaleSheetContentDao;
import com.nju.edu.erp.dao.SaleSheetDao;
import com.nju.edu.erp.dao.WarehouseDao;
import com.nju.edu.erp.enums.sheetState.PurchaseReturnsSheetState;
import com.nju.edu.erp.enums.sheetState.SaleReturnsSheetState;
import com.nju.edu.erp.model.po.CustomerPO;
import com.nju.edu.erp.model.po.PurchaseReturnsSheetContentPO;
import com.nju.edu.erp.model.po.PurchaseReturnsSheetPO;
import com.nju.edu.erp.model.po.PurchaseSheetContentPO;
import com.nju.edu.erp.model.po.PurchaseSheetPO;
import com.nju.edu.erp.model.po.SaleReturnsSheetContentPO;
import com.nju.edu.erp.model.po.SaleReturnsSheetPO;
import com.nju.edu.erp.model.po.SaleSheetContentPO;
import com.nju.edu.erp.model.po.SaleSheetPO;
import com.nju.edu.erp.model.po.WarehousePO;
import com.nju.edu.erp.model.vo.ProductInfoVO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetContentVO;
import com.nju.edu.erp.model.vo.Sale.SaleSheetVO;
import com.nju.edu.erp.model.vo.SaleReturns.SaleReturnsSheetContentVO;
import com.nju.edu.erp.model.vo.SaleReturns.SaleReturnsSheetVO;
import com.nju.edu.erp.model.vo.user.UserVO;
import com.nju.edu.erp.model.vo.purchaseReturns.PurchaseReturnsSheetContentVO;
import com.nju.edu.erp.service.CustomerService;
import com.nju.edu.erp.service.ProductService;
import com.nju.edu.erp.service.SaleReturnsService;
import com.nju.edu.erp.service.WarehouseService;
import com.nju.edu.erp.utils.IdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleReturnsServiceImpl implements SaleReturnsService {

    @Autowired
    private SaleReturnsSheetDao saleReturnsSheetDao;
    @Autowired
    private SaleReturnsSheetContentDao saleReturnsSheetContentDao;
    @Autowired
    private SaleSheetDao saleSheetDao;

    @Autowired
    private SaleSheetContentDao saleSheetContentDao;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private WarehouseService warehouseService;
    @Autowired
    private ProductService productService;

    /**
     * 在这里就把应退 代金券计算什么的全部维护好
     */
    @Override
    public void makePurchaseReturnsSheet(UserVO userVO, SaleReturnsSheetVO vo) {
        SaleReturnsSheetPO saleReturnsSheetPO = new SaleReturnsSheetPO();
        BeanUtils.copyProperties(vo, saleReturnsSheetPO);
        // 此处根据制定单据人员确定操作员
        saleReturnsSheetPO.setOperator(userVO.getName());
        saleReturnsSheetPO.setCreateTime(new Date());
        SaleReturnsSheetPO latest = saleReturnsSheetDao.getLatest();
        //生成销售退货单id
        String id = IdGenerator.generateSheetId(latest == null ? null : latest.getId(), "XSTHD");
        saleReturnsSheetPO.setId(id);
        saleReturnsSheetPO.setState(SaleReturnsSheetState.PENDING_LEVEL_1);
        BigDecimal totalAmount = BigDecimal.ZERO;
        //todo 这里他妈到底在干弔
        //todo 弔他妈写的什么几把
        //找到原来的那张销售单的销售列表
        List<SaleSheetContentPO> saleSheetContent = saleSheetContentDao.selectList(new QueryWrapper<>(SaleSheetContentPO.builder().saleSheetId(vo.getSupplier()).build()));
        Map<String, SaleSheetContentPO> map = new HashMap<>();//用来算钱
        for (SaleSheetContentPO item : saleSheetContent) {
            map.put(item.getPid(), item);
        }
        List<SaleReturnsSheetContentPO> pContentPOList = new ArrayList<>();

        //对销售退货清单列表进行操作
        for (SaleReturnsSheetContentVO content : vo.getContentList()) {
            SaleReturnsSheetContentPO pContentPO = new SaleReturnsSheetContentPO();
            BeanUtils.copyProperties(content, pContentPO);
            pContentPO.setSaleReturnsSheetId(id);//关联
            SaleSheetContentPO item = map.get(pContentPO.getPid());
            pContentPO.setUnitPrice(item.getUnitPrice());

            BigDecimal unitPrice = pContentPO.getUnitPrice();
            pContentPO.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(pContentPO.getQuantity())));
            pContentPOList.add(pContentPO);
            totalAmount = totalAmount.add(pContentPO.getTotalPrice());
        }
        for (SaleReturnsSheetContentPO po : pContentPOList) {
            saleReturnsSheetContentDao.insert(po);
        }
        saleReturnsSheetPO.setRawTotalAmount(totalAmount);

        SaleSheetPO saleSheet = saleSheetDao.selectOne(new QueryWrapper<>(SaleSheetPO.builder().id(saleReturnsSheetPO.getSaleSheetId()).build()));
        saleReturnsSheetPO.setCustomer(saleSheet.getSupplier());
        saleReturnsSheetPO.setVoucher(saleSheet.getVoucherAmount());
        saleReturnsSheetPO.setDiscount(saleSheet.getDiscount());
        // 退还最终价格 按比例退还
        saleReturnsSheetPO.setFinalAmount(totalAmount.multiply(saleSheet.getFinalAmount().divide(saleSheet.getRawTotalAmount())));
        saleReturnsSheetDao.insert(saleReturnsSheetPO);
    }

    @Override
    public List<SaleReturnsSheetVO> getSalesReturnsSheetByState(SaleReturnsSheetState state) {
        List<SaleReturnsSheetVO> res = new ArrayList<>();
        List<SaleReturnsSheetPO> all;
        if (state == null) {
            all = saleReturnsSheetDao.selectList(null);
        } else {
            all = saleReturnsSheetDao.selectList(new QueryWrapper<>(SaleReturnsSheetPO.builder().state(state).build()));
        }
        for (SaleReturnsSheetPO po : all) {
            SaleReturnsSheetVO vo = new SaleReturnsSheetVO();
            BeanUtils.copyProperties(po, vo);
            List<SaleReturnsSheetContentPO> alll = saleReturnsSheetContentDao.selectList(new QueryWrapper<>(SaleReturnsSheetContentPO.builder().saleReturnsSheetId(po.getId()).build()));
            List<SaleReturnsSheetContentVO> vos = new ArrayList<>();
            for (SaleReturnsSheetContentPO p : alll) {
                SaleReturnsSheetContentVO v = new SaleReturnsSheetContentVO();
                BeanUtils.copyProperties(p, v);
                vos.add(v);
            }
            vo.setContentList(vos);
            res.add(vo);
        }
        return res;
    }

    @Override
    @Transactional
    public List<SaleReturnsSheetVO> getSaleReturnsSheetByTime(String beginDateStr, String endDateStr) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<SaleReturnsSheetVO> res= new ArrayList<>();
        List<SaleReturnsSheetPO> all;
        try {
            Date beginTime = dateFormat.parse(beginDateStr);
            Date endTime = dateFormat.parse(endDateStr);
            if (beginTime.compareTo(endTime) > 0) {
                return null;
            } else {
                all = saleReturnsSheetDao.findAllSheetByTime(beginTime, endTime);
                for (SaleReturnsSheetPO po : all) {
                    SaleReturnsSheetVO vo = new SaleReturnsSheetVO();
                    BeanUtils.copyProperties(po, vo);
                    List<SaleReturnsSheetContentPO> allContent = saleReturnsSheetDao.findContentBySheetId(po.getId());
                    List<SaleReturnsSheetContentVO> vos = new ArrayList<>();
                    for (SaleReturnsSheetContentPO p : allContent) {
                        SaleReturnsSheetContentVO v = new SaleReturnsSheetContentVO();
                        BeanUtils.copyProperties(p, v);
                        vos.add(v);
                    }
                    vo.setContentList(vos);
                    res.add(vo);
                }
                return res;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据销售退货单id进行审批(state == "待二级审批"/"审批完成"/"审批失败")
     * 在controller层进行权限控制
     *
     * @param SaleReturnsSheetId 销售退货单id
     * @param state              销售退货单要达到的状态
     */
    @Override
    public void approval(String SaleReturnsSheetId, SaleReturnsSheetState state) {
        SaleReturnsSheetPO saleReturnsSheetPO = saleReturnsSheetDao.selectOne(new QueryWrapper<>(SaleReturnsSheetPO.builder().id(SaleReturnsSheetId).build()));
        if (state.equals(SaleReturnsSheetState.FAILURE)) {//拒绝掉
            if (saleReturnsSheetPO.getState() == SaleReturnsSheetState.SUCCESS)
                throw new RuntimeException("状态更新失败");
            LambdaUpdateWrapper<SaleReturnsSheetPO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(SaleReturnsSheetPO::getSaleSheetId, SaleReturnsSheetId)
                    .set(SaleReturnsSheetPO::getState, state);
            int effectLines = saleReturnsSheetDao.update(null, lambdaUpdateWrapper);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");
        } else {
            SaleReturnsSheetState prevState;//前一个状态
            if (state.equals(SaleReturnsSheetState.SUCCESS)) {
                prevState = SaleReturnsSheetState.PENDING_LEVEL_2;
            } else if (state.equals(SaleReturnsSheetState.PENDING_LEVEL_2)) {
                prevState = SaleReturnsSheetState.PENDING_LEVEL_1;
            } else {
                throw new RuntimeException("状态更新失败");
            }
            //先更新状态
            LambdaUpdateWrapper<SaleReturnsSheetPO> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(SaleReturnsSheetPO::getSaleSheetId, SaleReturnsSheetId)
                    .eq(SaleReturnsSheetPO::getState, prevState)
                    .set(SaleReturnsSheetPO::getState, state);
            int effectLines = saleReturnsSheetDao.update(null, lambdaUpdateWrapper);
            if (effectLines == 0) throw new RuntimeException("状态更新失败");

            if (state.equals(SaleReturnsSheetState.SUCCESS)) {
                // TODO 审批完成, 修改一系列状态
                // 进货退货单id， 关联的进货单id 【   进货退货单id->进货单id->入库单id->批次id】

                //- 进货退货单id-pid， quantity 【批次id+pid -> 定位到库存的一个条目->库存减去quantity】
                //- 【 pid -> 定位到单位进价->Σ单位进价*quantity=要收回的钱->客户payable减去要收回的钱】
                //todo 先找出退货列表 入库
                List<SaleReturnsSheetContentPO> contentPO = saleReturnsSheetContentDao.selectList(new QueryWrapper<>(SaleReturnsSheetContentPO.builder().saleReturnsSheetId(SaleReturnsSheetId).build()));
                for (SaleReturnsSheetContentPO content : contentPO) {
                    String pid = content.getPid();//商品id
                    Integer quantity = content.getQuantity();//商品数量
                    WarehousePO warehousePO = warehouseService.findByPidDesc(pid);//找到最便宜的货，退进来
                    if (warehousePO == null)
                        throw new RuntimeException("单据发生错误！请联系管理员！");

                    warehouseService.addQuantity(warehousePO, quantity);//仓库加货

                    ProductInfoVO productInfoVO = productService.getOneProductByPid(pid);
                    productInfoVO.setQuantity(productInfoVO.getQuantity() + quantity);
                    productService.updateProduct(productInfoVO);
                }


                //退还给客户钱
                CustomerPO customer = customerService.findCustomerById(saleReturnsSheetPO.getCustomer());
                customer.setReceivable(customer.getReceivable().add(saleReturnsSheetPO.getFinalAmount()));
                customerService.updateCustomer(customer);
            }
        }
    }
}
