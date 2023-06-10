## 软件体系结构文档

[软件体系结构文档](# 软件体系结构文档)

- [0 变更日志](# 0 变更日志)
- [1.1 目的](# 1.1 目的)
- [1.2 词汇表](# 1.2 词汇表)
- [1.3 参考文献](# 1.3 参考文献)
- [2 产品概述](# 2 产品概述)
- [3 逻辑视图](# 3逻辑视图)
- [4 组合视角](# 4组合视角)
  - [4.1 开发包图](# 4.1开发包图)
  - [4.2 运行时进程](# 4.2运行时进程)
  - [4.3 物理部署](# 4.3物理部署)
- [5 架构设计](# 5 架构设计)
  - [5.1 模块职责](# 5.1模块职责)
  - [5.2 用户界面层分解](# 5.2用户界面层分解)
  - [5.3 业务逻辑层分解](# 5.3业务逻辑层分解)
  - [5.4 数据层分解](# 5.4数据层分解)
  - [5.5 信息视角](# 5.5信息视角)
  - [5.6 数据库表](#5.6数据库表)



### 0 变更日志

| 变更人 | 变更时间   | 变更内容                                                     | 版本号 |
| ------ | ---------- | ------------------------------------------------------------ | ------ |
| 刘子扬 | 2022.5.1   | 完成数据库表、信息视角、模块职责、运行时进程图、物理部署图   | v1.0   |
| 郑寒超 | 2022.5.2   | 完成系统整体的服务器端开发包图、库存模块和销售模块的业务逻辑层分解 | v1.0   |
| 李俊彤 | 2022.5.2   | 完成用户界面层分解和客户端开发包图                           | v1.0   |
| 吴禹廷 | 2022.5.2   | 完成逻辑视图、开发包图、数据层分解                           | v1.0   |
| 郑寒超 | 2022.05.22 | 修改服务器端开发包图                                         | v1.1   |



### 1.1 目的

1.本文档完成lab5内容，描述了ERP系统的软件体系结构。

2.本报告面向开发人员、测试人员以及最终用户而编写，是了解系统的导航。



### **1.2 词汇表**

| 词汇名称 | 词汇含义      | 备注  |
| -------- | ----------- | ---- |
|  ERP    |  企业资源规划  |      |



### 1.3 参考文献

- 《软件工程与计算（卷二）软件开发的技术基础》骆斌，刘钦，丁二玉编著；
- IEEE标准；
- 《软件⼯程——实践者的研究⽅法》Roger S. Pressman 与 Bruce R. Maxim 著；
- ERP系统用例描述文档
- ERP系统软件需求规格说明文档



### 2. 产品概述

1.参考ERP系统用例描述文档和ERP系统需求规格说明文档中对产品的概括描述。



### 3.逻辑视图

- 在ERP系统中，选择了分层体系结构风格，将系统分为3层(展示层、业务逻辑层、数据层)能够很好地示意整个高层抽象。展示层包含GUI页面的实现，业务逻辑层包含业务逻辑处理的实现，数据层负责数据的持久化和访问。分层体系结构的了逻辑视图和逻辑设计方案如下图所示
  
-  参照体系结构风格的包图表达逻辑视角

![](https://seec-homework.oss-cn-shanghai.aliyuncs.com/201250031-lab5-1.png)

- 软件体系结构逻辑设计方案
  

![](https://seec-homework.oss-cn-shanghai.aliyuncs.com/201250031-lab5-2.png)



### 4.组合视角

#### 4.1开发包图

| 开发包                 | 依赖的其他开发包            |
| ---------------------- | -------------------------------------------------------------------------------------------------------------------------------------------- |
| mainui                 | UserUi,InventoryManegerUi,SaleStuffUi,FinancialStuffUi,HRUi,GMUi,util                                        |
| InventoryManegerUi                | store,util                                                                                                                                   |
| SaleStuffUi           | store,util                                                                                                                                   |
| FinancialStuffUi         | store,util                                                                                                                                   |
| HRUi               | store,util                                                                                                                                   |
| GMUi                 | store,util                                                                                                                                   |
| UserUi                 | store,util                                                                                                                                   |
| util                   |                                                                                                                                              |
| store                  | util,UserModule,InventoryManegerModule,SaleStuffModule,FinancialStuffModule,HRModule,GMModule,WarehouseModule,CouponModule               |
| UserModule            | UserApi        |
| InventoryManegerModule       | InventoryManegerApi           |
| SaleStuffModule             | SaleStuffApi             |
| FinancialStuffModule           | FinancialStuffApi              |
| HRModule             | HRApi                        |
| GMModule               | GMApi                        |
| WarehouseModule            | WarehouseApi         |
| CouponModule           | CouponApi           |
| UserApi               | HTTP                                                                                                                                         |
| InventoryManegerApi          | HTTP                                                                                                                                         |
| SaleStuffApi              | HTTP                                                                                                                                         |
| FinancialStuffApi               | HTTP                                                                                                                                         |
| HRApi              | HTTP                                                                                                                                         |
| GMApi             | HTTP                                                                                                                                         |
| WarehouseApi                | HTTP                                                                                                                                         |
| CouponApi                | HTTP                                                                                                                                         |
| HTTP                   |                                                                                                                                              |
| Categorycontroller        | HTTP,CategoryService                                                                                                                            |
| ProductController       | HTTP,ProductService                                                                                                                           |
| WarehouseController      | HTTP,WarehouseService                                                                                                                     |
| ClientController       | HTTP,ClientService                                                                                                                           |
| GoodsController | HTTP,GoodsService                                                                                                                     |
| SalesController   | HTTP,SalesService                                                                                                                       |
| PromotionStrategyController         | HTTP,PromotionStrategyService                                                                                                                             |
| AccountController         | HTTP,AccountService                                                                                                                             |
| Billscontroller        | HTTP,BillsService                                                                                                                            |
| SalaryController       | HTTP,SalaryService                                                                                                                           |
| BusinessController      | HTTP,BusinessService                                                                                                                     |
| EmployeeController       | HTTP,EmployeeService                                                                                                                           |
| YearEndAwardController | HTTP,YearEndAwardService                                                                                                                     |
| ReceiptApprovalController   | HTTP,ReceiptApprovalService                                                                                                                       |
| CategoryService        | CategoryDao                                                                                                                           |
| ProductService       | ProductDao                                                                                                                          |
| WarehouseService      | ProductDao,WarehouseInputSheetDao,WarehouseDao,WarehouseOutputDao                                                                                                                 |
| ClientService       | ClientDao                                                                                                                          |
| GoodsService | GoodsPurchaseSheetDao,GoodsReturnSheetDao                                                                                                                    |
| SalesService   | SalesOutSheetDao,SalesSheetDao,SalesReturnSheetDao                                                                                                                      |
| PromotionStrategyService        | TotalPriceBasedDao,ClientLevelBasedDao,DiscountPacketDao                                                                                                                            |
| AccountService        | AccountDao                                                                                                                            |
| BillsService        | ReceiveMoneyBillDao,PayMoneyBillDao                                                                                                                           |
| SalaryService       | PayrollDao                                                                                                                     |
| BusinessService      | BusinessConditionDao,InitAccountDao                                                                                                                    |
| EmployeeService       | EmployeeDao                                                                                                                          |
| YearEndAwardService   | YearEndAwardDao                                                                                                                     |
| ReceiptApprovalService  | ReceiptDao                                                                                                                       |
| CategoryDao             | DataUsability,po    |
| ProductDao             | DataUsability,po         |
| WarehouseInputSheetDao     | DataUsability,po         |
| WarehouseDao             | DataUsability,po       |
| WarehouseOutputSheetDao    | DataUsability,po    |
| ClientDao             | DataUsability,po         |
| GoodsPurchaseSheetDao     | DataUsability,po         |
| GoodsReturnSheetDao      | DataUsability,po       |
| SaleOutSheetDao             | DataUsability,po    |
| SaleSheetDao             | DataUsability,po         |
| SaleReturnSheetDao     | DataUsability,po         |
| TotalPriceBasedDao       | DataUsability,po       |
| ClientLevelBasedDao    | DataUsability,po    |
| DiscountPocketDao             | DataUsability,po         |
| AccountDao           | DataUsability,po         |
| ReceiveMoneyBillDao      | DataUsability,po       |
| PayMoneyBillDao             | DataUsability,po         |
| PayRollDao            | DataUsability,po         |
| BusinessConditionDao       | DataUsability,po       |
| InitAccountDao         | DataUsability,po    |
| EmployeeDao             | DataUsability,po         |
| YearEndAwardDao           | DataUsability,po         |
| ReceiptDao              | DataUsability,po       |
| vo                    |                          |
| po                    |                          |
| Database               |                      |

- 客户端开发包图 


![](https://seec-homework.oss-cn-shanghai.aliyuncs.com/201250082 lab5.png)

- 服务器端开发包图

![服务器端开发包图](https://seec-homework.oss-cn-shanghai.aliyuncs.com/201250150-lab5.png)

#### 4.2运行时进程

- ERP系统中，会有多个网页端进程和一个服务器端进程，其进程图如下图。结合部署图，客户端进程在客户端机器运行，服务器端进程是在服务器端机器运行。


![](https://seec-homework.oss-cn-shanghai.aliyuncs.com/201250080_lab5_运行时进程)

#### 4.3物理部署

- ERP系统中的客户端组件放在客户端机器上，服务器端组件放在服务器端机器上。

 ![](https://seec-homework.oss-cn-shanghai.aliyuncs.com/201250080_lab5_物理部署)



### 5. 架构设计

#### 5.1模块职责

- 网页端模块和服务器端模块视图分别如下两张图所示（依次对应上下两张）

  - 网页端模块视图

  ![](https://seec-homework.oss-cn-shanghai.aliyuncs.com/201250080_lab5_网页端模块视图.png)

  - 服务器端模块视图

![](https://seec-homework.oss-cn-shanghai.aliyuncs.com/201250080_lab5_服务器端模块视图)

- 客户端各层职责

| 层             | 职责                                 |
| -------------- | ------------------------------------ |
| 启动模块       | 负责初始化网络通信机制，启动用户界面 |
| 用户界面层     | 基于Web界面的ERP界面                 |
| 客户端网络模块 | 利用Rest API机制发起HTTP请求         |

- 服务器端各层职责

| 层               | 职责                                         |
| ---------------- | -------------------------------------------- |
| 启动模块         | 负责初始化网络通信机制，启动用户界面         |
| 业务逻辑层       | 对于用户界面的输入进行响应并进行业务逻辑处理 |
| 数据层           | 负责数据的持久化和数据访问接口               |
| 服务器端网络模块 | 接收客户端的HTTP请求                         |

- 层之间调用接口

| 接口                                                         | 服务调用方       | 服务提供方       |
| ------------------------------------------------------------ | ---------------- | ---------------- |
| CategoryController<br>ProductController<br>ClientController<br>WarehouseController<br>GoodsController<br>SalesController<br>PromotionStrategyController<br>AccountController<br>BillsController<br>SalaryController<br>BusinessController<br>EmployeeController<br>YearEndAwardsController | 客户端展示层     | 客户端业务逻辑层 |
| CategoryService<br/>ProductService<br/>ClientService<br/>WarehouseService<br/>GoodsService<br/>SalesService<br/>PromotionStrategyService<br/>AccountService<br/>BillsService<br/>SalaryService<br/>BusinessService<br/>EmployeeService<br/>YearEndAwardsService | 客户端业务逻辑层 | 服务端数据层     |

#### 5.2用户界面层分解

#### 5.2.1 职责

| 模块      | 职责                         |
| --------- | ---------------------------- |
| auth      | 负责展示验证界面（有待商榷） |
| commodity | 负责展示商品界面             |
| product   | 负责展示产品界面             |
| test      | 负责展示测试界面             |
| warehouse | 负责展示仓库界面             |

#### 5.2.2 接口规范

- 验证模块

| 接口名称    | 请求方法 | 路径               | 内容         |
  | ----------- | -------- | ------------------ | ------------ |
  | authAPI     | GET      | /api/auth/auth     | （有待商榷） |
  | loginAPI    | POST     | /api/auth/login    | （有待商榷） |
  | registerAPI | POST     | /api/auth/register | （有待商榷） |

- 商品模块

| 接口名称                         | 请求方法 | 路径                                         | 内容             |
  | -------------------------------- | -------- | -------------------------------------------- | ---------------- |
  | getAllCommodityClassificationAPI | GET      | /api/commodity/getAllCommodityClassification | 获取所有商品分类 |
  | createCommodityClassificationAPI | GET      | /api/commodity/createCommodityClassification | 创建商品分类     |
  | deleteCommodityClassificationAPI | GET      | /api/commodity/deleteCommodityClassification | 删除商品分类     |
  | updateCommodityClassificationAPI | GET      | /api/commodity/updateCommodityClassification | 更新所有商品分类 |
  | getAllCommodityAPI               | GET      | /api/commodity/getAllCommodity               | 获取所有商品     |
  | createCommodityAPI               | POST     | /api/commodity/createAllCommodity            | 创建商品         |
  | updateCommodityAPI               | POST     | /api/commodity/updateAllCommodity            | 更新商品         |
  | deleteCommodityAPI               | GET      | /api/commodity/deleteAllCommodity            | 删除商品         |

- 产品模块

| 接口名称          | 请求方法 | 路径                        | 内容         |
  | ----------------- | -------- | --------------------------- | ------------ |
  | findAllProductAPI | GET      | /api/product/findAllProduct | 查找所有产品 |

- 测试模块

| 接口名称    | 请求方法 | 路径               | 内容     |
  | ----------- | -------- | ------------------ | -------- |
  | testGetAPI  | GET      | /api/test/testGet  | 获取测试 |
  | testPostAPI | POST     | /api/test/testPost | 投递测试 |

- 仓库模块

| 接口名称              | 请求方法 | 路径                              | 内容           |
| --------------------- | -------- | --------------------------------- | -------------- |
| warehouseInputAPI     | POST     | /api/warehouse/warehouseInput     | 往仓库输入     |
| warehouseOutputPreAPI | POST     | /api/warehouse/warehouseOutputPre | 预备从仓库输出 |
| warehouseOutputAPI    | POST     | /api/warehouse/warehouseOutput    | 从仓库输出     |

#### 5.3业务逻辑层分解

#### 5.3.1 库存模块

##### 5.3.1.1 职责

| 模块             | 职责                           |
| ---------------- | ------------------------------ |
| CategoryService  | 负责实现商品分类相关的服务     |
| ProductService   | 负责实现商品管理相关的服务     |
| WarehouseService | 负责实现商品出库入库相关的服务 |

##### 5.3.1.2 接口规范

- CategoryService接口规范

<table>
    <tr>
        <td colspan="3" align="center">提供的服务（供接口）</td>
    </tr>
    <tr>
        <td rowspan="3">CategoryService.createCategory</td>
        <td>语法</td>
        <td>CategoryVO createCategory(Integer parentId, String name);</td>
    </tr>
    <tr>
        <td>前置条件</td>
        <td>无</td>
    </tr>
    <tr>
        <td>后置条件</td>
        <td>创建分类成功，并返回VO</td>
    </tr>
    <tr>
        <td rowspan="3">CategoryService.queryAllCategory</td>
        <td>语法</td>
        <td>List<CategoryVO> queryAllCategory();</td>
    </tr>
    <tr>
        <td>前置条件</td>
        <td>无</td>
    </tr>
    <tr>
        <td>后置条件</td>
        <td>返回所有的分类信息</td>
    </tr>
    <tr>
        <td rowspan="3">CategoryService.updateCategory</td>
        <td>语法</td>
        <td>CategoryVO updateCategory(Integer id, String name);</td>
    </tr>
    <tr>
        <td>前置条件</td>
        <td>无</td>
    </tr>
    <tr>
        <td>后置条件</td>
        <td>更新分类信息</td>
    </tr>
    <tr>
        <td rowspan="3">CategoryService.deleteCategory</td>
        <td>语法</td>
        <td>void deleteCategory(Integer id);</td>
    </tr>
    <tr>
        <td>前置条件</td>
        <td>无</td>
    </tr>
    <tr>
        <td>后置条件</td>
        <td>删除指定的分类信息</td>
    </tr>
    <tr>
        <td colspan="3" align="center">需要的服务（需接口）</td>
    </tr>
    <tr>
        <td>服务名</td>
        <td colspan="2">服务</td>
    </tr>
    <tr>
        <td>CategoryDao.createCategory</td>
        <td colspan="2">在数据库中插入CategoryPo对象</td>
    </tr>
    <tr>
        <td>CategoryDao.findByCategoryId</td>
        <td colspan="2">根据分类id在数据中查找相应的CategoryPO对象</td>
    </tr>
    <tr>
        <td>CategoryDao.findAll</td>
        <td colspan="2">查询数据库中所有的CategotyPO对象</td>
    </tr>
    <tr>
        <td>CategoryDao.updateById</td>
        <td colspan="2">更新数据库中的Category对象</td>
    </tr>
    <tr>
        <td>CategoryDao.deleteById</td>
        <td colspan="2">删除数据库中指定的CategoryPO对象</td>
    </tr>
</table>
- ProductService接口规范

<table>
    <tr>
        <td colspan="3" align="center">提供的服务（供接口）</td>
    </tr>
    <tr>
        <td rowspan="3">ProductService.createproduct</td>
        <td>语法</td>
        <td>ProductInfoVO createProduct(CreateProductVO inputVO);</td>
    </tr>
    <tr>
        <td>前置条件</td>
        <td>无</td>
    </tr>
    <tr>
        <td>后置条件</td>
        <td>创建商品成功，并返回VO</td>
    </tr>
    <tr>
        <td rowspan="3">ProductService.updateProduct</td>
        <td>语法</td>
        <td>ProductInfoVO updateProduct(ProductInfoVO productInfoVO);</td>
    </tr>
    <tr>
        <td>前置条件</td>
        <td>无</td>
    </tr>
    <tr>
        <td>后置条件</td>
        <td>更新商品信息</td>
    </tr>
    <tr>
        <td rowspan="3">ProductService.queryAllProduct</td>
        <td>语法</td>
        <td>List<ProductInfoVO> queryAllProduct();</td>
    </tr>
    <tr>
        <td>前置条件</td>
        <td>无</td>
    </tr>
    <tr>
        <td>后置条件</td>
        <td>查看所有商品</td>
    </tr>
    <tr>
        <td rowspan="3">ProductService.deleteById</td>
        <td>语法</td>
        <td>void deleteById(String id);</td>
    </tr>
    <tr>
        <td>前置条件</td>
        <td>无</td>
    </tr>
    <tr>
        <td>后置条件</td>
        <td>删除指定的商品</td>
    </tr>
    <tr>
        <td colspan="3" align="center">需要的服务（需接口）</td>
    </tr>
    <tr>
        <td>服务名</td>
        <td colspan="2">服务</td>
    </tr>
    <tr>
        <td>ProductDao.createProduct</td>
        <td colspan="2">在数据库中插入ProductPo对象</td>
    </tr>
    <tr>
        <td>ProductDao.findById</td>
        <td colspan="2">根据商品id在数据中查找相应的ProductPO对象</td>
    </tr>
    <tr>
        <td>ProductDao.findAll</td>
        <td colspan="2">查询数据库中所有的ProductPO对象</td>
    </tr>
    <tr>
        <td>ProductDao.updateById</td>
        <td colspan="2">更新数据库中的ProductPO对象</td>
    </tr>
    <tr>
        <td>ProductDao.deleteById</td>
        <td colspan="2">删除数据库中指定的ProductPO对象</td>
    </tr>
</table>
- WarehouseService接口规范

<table>
    <tr>
        <td colspan="3" align="center">提供的服务（供接口）</td>
    </tr>
    <tr>
        <td rowspan="3">WarehouseService.productWarehousing</td>
        <td>语法</td>
        <td>void productWarehousing(WarehouseInputFormVO warehouseInputFormVO);</td>
    </tr>
    <tr>
        <td>前置条件</td>
        <td>无</td>
    </tr>
    <tr>
        <td>后置条件</td>
        <td>商品入库</td>
    </tr>
    <tr>
        <td rowspan="3">WarehouseService.productOutOfWarehouse</td>
        <td>语法</td>
        <td>void productOutOfWarehouse(WarehouseOutputFormVO warehouseOutputFormListVO);</td>
    </tr>
    <tr>
        <td>前置条件</td>
        <td>无</td>
    </tr>
    <tr>
        <td>后置条件</td>
        <td>商品出库</td>
    </tr>
    <tr>
        <td rowspan="3">WarehouseService.getWareProductInfo</td>
        <td>语法</td>
        <td>List<WarehouseOneProductInfoVO> getWareProductInfo(GetWareProductInfoParamsVO params);</td>
    </tr>
    <tr>
        <td>前置条件</td>
        <td>无</td>
    </tr>
    <tr>
        <td>后置条件</td>
        <td>从库存中获取指定信息的物品</td>
    </tr>
    <tr>
        <td colspan="3" align="center">需要的服务（需接口）</td>
    </tr>
    <tr>
        <td>服务名</td>
        <td colspan="2">服务</td>
    </tr>
    <tr>
        <td>ProductDao.findById</td>
        <td colspan="2">根据商品id在数据库中查询相应的ProductPO</td>
    </tr>
    <tr>
        <td>ProductDao.updateById</td>
        <td colspan="2">更新数据库中指定ProductPO对象</td>
    </tr>
    <tr>
        <td>CategoryDao.findAll</td>
        <td colspan="2">查询数据库中所有的ProductPO对象</td>
    </tr>
    <tr>
        <td>WarehouseDao.saveBatch</td>
        <td colspan="2">将入库商品存入数据库</td>
    </tr>
    <tr>
        <td>WarehouseDao.deductQuantity</td>
        <td colspan="2">商品出库后更新数据库对应商品数量</td>
    </tr>
    <tr>
        <td>WarehouseDao.findAllNotZeroByPidSortedByBatchId</td>
        <td colspan="2">获取按照批次号排序且商品编号不为0的商品列表</td>
    </tr>
    <tr>
        <td>WarehouseInputSheetDao.getLastest</td>
        <td colspan="2">获取最近一条入库单</td>
    </tr>
    <tr>
        <td>WarehouseInputSheetDao.save</td>
        <td colspan="2">存入一条入库单记录</td>
    </tr>
    <tr>
        <td>WarehouseInputSheetDao.saveBatch</td>
        <td colspan="2">把入库单上的具体内容存入数据库</td>
    </tr>
    <tr>
        <td>WarehouseOutputSheetDao.getLastest</td>
        <td colspan="2">获取最近一条出库单</td>
    </tr>
    <tr>
        <td>WarehouseOutputSheetDao.save</td>
        <td colspan="2">存入一条出库单记录</td>
    </tr>
    <tr>
        <td>WarehouseOutputSheetDao.saveBatch</td>
        <td colspan="2">把出库单上的具体内容存入数据库</td>
    </tr>
</table>
#### 5.3.2 销售模块

##### 5.3.1.1 职责

| 模块          | 职责                                     |
| ------------- | ---------------------------------------- |
| ClientService | 负责实现客户管理所需要的服务             |
| GoodsService  | 负责实现制定出货单和退货单所需要的服务   |
| SaleService   | 负责实现制定销售出货单和销售进货单的服务 |

##### 5.3.1.2 接口规范

- ClientService的接口规范

  <table>
      <tr>
          <td colspan="3" align="center">提供的服务（供接口）</td>
      </tr>
      <tr>
          <td rowspan="3">ClientService.createClient</td>
          <td>语法</td>
          <td>ClinetInfoVO createClient(CreateClientVO inputVO);</td>
      </tr>
      <tr>
          <td>前置条件</td>
          <td>无</td>
      </tr>
      <tr>
          <td>后置条件</td>
          <td>创建用户成功，并返回VO</td>
      </tr>
      <tr>
          <td rowspan="3">ClientService.updateClient</td>
          <td>语法</td>
          <td>ClientInfoVO updateClient(ClientInfoVO clientInfoVO);</td>
      </tr>
      <tr>
          <td>前置条件</td>
          <td>无</td>
      </tr>
      <tr>
          <td>后置条件</td>
          <td>修改用户信息</td>
      </tr>
      <tr>
          <td rowspan="3">ClientService.queryAllClient</td>
          <td>语法</td>
          <td>List<ClientInfoVO> queryAllClient();</td>
      </tr>
      <tr>
          <td>前置条件</td>
          <td>无</td>
      </tr>
      <tr>
          <td>后置条件</td>
          <td>查看所有用户</td>
      </tr>
      <tr>
          <td rowspan="3">ClientService.deleteById</td>
          <td>语法</td>
          <td>void deleteById(String id);</td>
      </tr>
      <tr>
          <td>前置条件</td>
          <td>无</td>
      </tr>
      <tr>
          <td>后置条件</td>
          <td>删除指定的客户</td>
      </tr>
      <tr>
          <td colspan="3" align="center">需要的服务（需接口）</td>
      </tr>
      <tr>
          <td>服务名</td>
          <td colspan="2">服务</td>
      </tr>
      <tr>
          <td>ClientDao.createClient</td>
          <td colspan="2">在数据库中插入ClientPo对象</td>
      </tr>
      <tr>
          <td>ClientDao.findById</td>
          <td colspan="2">根据客户id在数据中查找相应的ClientPO对象</td>
      </tr>
      <tr>
          <td>ClientDao.findAll</td>
          <td colspan="2">查询数据库中所有的ClientPO对象</td>
      </tr>
      <tr>
          <td>ClientDao.updateById</td>
          <td colspan="2">更新数据库中的ClientPO对象</td>
      </tr>
      <tr>
          <td>ClientDao.deleteById</td>
          <td colspan="2">删除数据库中指定的ClientPO对象</td>
      </tr>
  </table>

- GoodsService的接口规范

  <table>
      <tr>
          <td colspan="3" align="center">提供的服务（供接口）</td>
      </tr>
      <tr>
          <td rowspan="3">GoodsService.goodsPurchase</td>
          <td>语法</td>
          <td>void goodsPurchase(goodsPurchaseFormVO goodspurchaseFormVO);</td>
      </tr>
      <tr>
          <td>前置条件</td>
          <td>无</td>
      </tr>
      <tr>
          <td>后置条件</td>
          <td>商品入库</td>
      </tr>
      <tr>
          <td rowspan="3">GoodsService.goodsReturn</td>
          <td>语法</td>
          <td>void goodsReturn(goodsReturnFormVO goodsreturnFormListVO);</td>
      </tr>
      <tr>
          <td>前置条件</td>
          <td>无</td>
      </tr>
      <tr>
          <td>后置条件</td>
          <td>商品出库</td>
      </tr>
      <tr>
          <td colspan="3" align="center">需要的服务（需接口）</td>
      </tr>
      <tr>
          <td>服务名</td>
          <td colspan="2">服务</td>
      </tr>
      <tr>
          <td>GoodsPurchaseSheetDao.save</td>
          <td colspan="2">存入一条进货单记录</td>
      </tr>
      <tr>
          <td>GoodsPurchaseSheetDao.saveBatch</td>
          <td colspan="2">把进货单上的具体内容存入数据库</td>
      </tr>
      <tr>
          <td>GoodsReturnSheetDao.save</td>
          <td colspan="2">存入一条进货退货单记录</td>
      </tr>
      <tr>
          <td>GoodsReturnSheetDao.saveBatch</td>
          <td colspan="2">把进货退货单上的具体内容存入数据库</td>
      </tr>
  </table>

- SaleService的接口规范

  <table>
      <tr>
          <td colspan="3" align="center">提供的服务（供接口）</td>
      </tr>
      <tr>
          <td rowspan="3">SaleService.SaleOut</td>
          <td>语法</td>
          <td>void SaleOut(SaleOutFormVO saleoutFormVO);</td>
      </tr>
      <tr>
          <td>前置条件</td>
          <td>无</td>
      </tr>
      <tr>
          <td>后置条件</td>
          <td>销售出货</td>
      </tr>
      <tr>
          <td rowspan="3">SaleService.SaleReturn</td>
          <td>语法</td>
          <td>void SaleReturn(SaleReturnFormVO salereturnFormListVO);</td>
      </tr>
      <tr>
          <td>前置条件</td>
          <td>无</td>
      </tr>
      <tr>
          <td>后置条件</td>
          <td>销售退货</td>
      </tr>
      <tr>
          <td rowspan="3">SaleService.queryAllSheets</td>
          <td>语法</td>
          <td>List queryAllSheets();</td>
      </tr>
      <tr>
          <td>前置条件</td>
          <td>无</td>
      </tr>
      <tr>
          <td>后置条件</td>
          <td>查看所有的有关销售的单据</td>
      </tr>
      <tr>
          <td colspan="3" align="center">需要的服务（需接口）</td>
      </tr>
      <tr>
          <td>服务名</td>
          <td colspan="2">服务</td>
      </tr>
      <tr>
          <td>SaleOutSheetDao.save</td>
          <td colspan="2">存入一条销售出货单记录</td>
      </tr>
      <tr>
          <td>SaleOutSheetDao.saveBatch</td>
          <td colspan="2">把销售出货单上的具体内容存入数据库</td>
      </tr>
      <tr>
          <td>SaleReturnSheetDao.save</td>
          <td colspan="2">存入一条销售退货单记录</td>
      </tr>
      <tr>
          <td>SaleReturnSheetDao.saveBatch</td>
          <td colspan="2">把销售退货单上的具体内容存入数据库</td>
      </tr>
      <tr>
          <td>SalesSheetDao.queryAllSalesSheets</td>
          <td colspan="2">查询所有的销售出货和销售退货单</td>
      </tr>
  </table>

#### 5.4数据层分解

![](https://seec-homework.oss-cn-shanghai.aliyuncs.com/201250031-lab5-klee数据层分解 (2).png)

#### 5.4.1. 职责

| 模块                           | 职责                                          |
| ------------------------------ | -------------------------------------------- |
| CategoryMapper                 | 持久化数据库的接口，提供产品分类查询服务         |
| ProductMapper                  | 持久化数据库的接口，提供产品查询服务             |
| UserMapper                     | 持久化数据库的接口，提供用户查询服务             |
| WarehouseMapper                | 持久化数据库的接口，提供仓库库存查询服务         |
| WarehouseInputSheetMapper      | 持久化数据库的接口，提供仓库入库单查询服务       |
| WarehouseOutputSheetMapper     | 持久化数据库的接口，提供仓库出库单查询服务       |

#### 5.4.2. 接口规范

#### 5.4.2.1. CategoryMapper模块
<table>
	<tr><th colspan = "3">提供的服务(供接口)</th></tr>
    <td rowspan="4">CategoryMapper.add</td>
	<tr><td>语法</td><td>Integer save(category CategoryPO);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统添加商品分类信息</td></tr>
    <td rowspan="4">CategoryMapper.findByCategoryId</td>
	<tr><td>语法</td><td>CategoryPO findByCategoryId(Integer id);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统返回对应编号的商品分类信息</td></tr>
    <td rowspan="4">CategoryMapper.deleteByid</td>
	<tr><td>语法</td><td>boolean deleteByid(Integer id);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统根据id删除对应的商品分类信息</td></tr>
    <td rowspan="4">CategoryMapper.findAll</td>
	<tr><td>语法</td><td>List< CategoryPO> findAll();</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统返回所有的商品分类信息</td></tr>
    <td rowspan="4">CategoryMapper.updateCategory</td>
	<tr><td>语法</td><td>boolean updateCategory(CategoryPO category);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统更新对应商品分类信息</td></tr>
</table> 


#### 5.4.2.2. ProductMapper模块
<table>
	<tr><th colspan = "3">提供的服务(供接口)</th></tr>
    <td rowspan="4">ProductMapper.delete</td>
	<tr><td>语法</td><td>boolean delete(String id);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统根据id删除对应产品信息</td></tr>
    <td rowspan="4">ProductMapper.findByProductId</td>
	<tr><td>语法</td><td>List< ProductPO> findByProductId(Integer id);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统返回id对应的产品信息</td></tr>
    <td rowspan="4">ProductMapper.findAll</td>
	<tr><td>语法</td><td>List< ProductPO> findAll();</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统返回所有产品信息</td></tr>
    <td rowspan="4">ProductMapper.updateProduct</td>
	<tr><td>语法</td><td>boolean updateProduct(ProductPO product);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统更新产品信息</td></tr>
</table>

#### 5.4.2.3. UserMapper模块
<table>
	<tr><th colspan = "3">提供的服务(供接口)</th></tr>
    <td rowspan="4">UserMapper.save</td>
	<tr><td>语法</td><td>int save(User user);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统添加新用户</td></tr>
    <td rowspan="4">UserMapper.delete</td>
	<tr><td>语法</td><td>boolean delete(String id);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统根据用户id删除用户</td></tr>
    <td rowspan="4">UserMapper.findByUserName</td>
	<tr><td>语法</td><td>User findByUserName(String userName);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统根据用户名查找用户</td></tr>
    <td rowspan="4">UserMapper.findByUserNameAndPassword</td>
	<tr><td>语法</td><td>User findByUserNameAndPassword(String userName, String password);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统根据用户名和密码查找用户</td></tr>
    <td rowspan="4">UserMapper.findByRole</td>
	<tr><td>语法</td><td>List< User> findByRole(String role);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统根据用户类型查找用户</td></tr>
    <td rowspan="4">UserMapper.findAll</td>
	<tr><td>语法</td><td>List< User> findAll();</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统返回所有用户信息</td></tr>
    <td rowspan="4">UserMapper.updateUser</td>
	<tr><td>语法</td><td>boolean updateUser(User user);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统更新用户信息</td></tr>
</table>

#### 5.4.2.4. WarehouseMapper模块

<table>
	<tr><th colspan = "3">提供的服务(供接口)</th></tr>
    <td rowspan="4">WarehouseMapper.saveBatch</td>
	<tr><td>语法</td><td>void saveBatch(List< WarehousePO> warehousePOList);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统根据货单向仓库更新库存信息</td></tr>
    <td rowspan="4">WarehouseMapper.deductQuantity</td>
	<tr><td>语法</td><td>void deductQuantity(WarehousePO warehousePO);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统减少库存数量</td></tr>
    <td rowspan="4">WarehouseMapper.findAllNotZeroByPidSortedByBatchId</td>
	<tr><td>语法</td><td>List< ProductPO> findAllNotZeroByPidSortedByBatchId(String pid);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统根据pid查找并返回所有非0库存</td></tr>
    <td rowspan="4">WarehouseMapper.findAll</td>
	<tr><td>语法</td><td>List< WarehousePO> findAll();</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统返回所有库存信息</td></tr>
    <td rowspan="4">WarehouseMapper.updateWarehouse</td>
	<tr><td>语法</td><td>boolean updateWarehouse(WarehousePO warehouse);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统更新库存信息</td></tr>
</table>

#### 5.4.2.5. WarehouseInputSheetMapper模块
<table>
	<tr><th colspan = "3">提供的服务(供接口)</th></tr>
    <td rowspan="4">WarehouseInputSheetMapper.save</td>
	<tr><td>语法</td><td>int save(WarehouseInputSheetPO warehouseInputSheet);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统添加入库信息</td></tr>
    <td rowspan="4">WarehouseInputSheetMapper.saveBatch</td>
	<tr><td>语法</td><td>void saveBatch(List< WarehouseInputSheetPO> warehouseInputSheetList);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统根据入库单添加入库信息</td></tr>
    <td rowspan="4">WarehouseInputSheetMapper.getLatest</td>
	<tr><td>语法</td><td>WarehouseInputSheetPO();</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统返回最新一条入库信息</td></tr>
    <td rowspan="4">WarehouseInputSheetMapper.findAll</td>
	<tr><td>语法</td><td>List< WarehouseInputSheetPO> findAll();</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统返回所有入库信息</td></tr>
    <td rowspan="4">WarehouseInputSheetMapper.findById</td>
	<tr><td>语法</td><td>boolean findById(int id);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统根据单号返回相应入库信息</td></tr>
</table>

#### 5.4.2.6. WarehouseOutputSheetMapper模块
<table>
	<tr><th colspan = "3">提供的服务(供接口)</th></tr>
    <td rowspan="4">WarehouseOutputSheetMapper.save</td>
	<tr><td>语法</td><td>int save(WarehouseOutputSheetPO warehouseOutputSheet);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统添加出库信息</td></tr>
    <td rowspan="4">WarehouseOutputSheetMapper.saveBatch</td>
	<tr><td>语法</td><td>void saveBatch(List< WarehouseOutputSheetPO> warehouseOutputSheetList);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统根据出库单添加出库信息</td></tr>
    <td rowspan="4">WarehouseOutputSheetMapper.getLatest</td>
	<tr><td>语法</td><td>WarehouseOutputSheetPO();</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统返回最新一条出库信息</td></tr>
    <td rowspan="4">WarehouseOutputSheetMapper.findAll</td>
	<tr><td>语法</td><td>List< WarehouseOutputSheetPO> findAll();</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统返回所有出库信息</td></tr>
    <td rowspan="4">WarehouseOutputSheetMapper.findById</td>
	<tr><td>语法</td><td>boolean findById(int id);</td></tr>
	<tr><td>前置条件</td><td>无</td></tr>
	<tr><td>后置条件</td><td>系统根据单号返回相应出库信息</td></tr>
</table>

#### 5.5信息视角

- 系统的PO类就是对应的相关的实体类

##### 5.5.1Category类

- 包含分类id，分类名，父分类id，是否为叶节点，商品数量，下一个商品index
- Category类的属性如下

```java
public class Category{
    private Integer id;
    private String name;
    private Integer parentId;
    private boolean isLeaf;
    private Integer itemCount;
    private Integer itemIndex;
}
```

##### 5.5.2Product类

- 包含商品id，商品名，分类id，商品型号，商品数量，进价，零售价，最近进价，最近零售价
- Product类的属性如下

```java
public class Product{
    private String id;
    private String name;
    private Integer categoryId;
    private String type;
    private Integer quantity;
    private BigDecimal purchasePrice;
    private BigDecimal retailPrice;
    private BigDecimal recentPp;
    private BigDecimal recentRp;
}
```

##### 5.5.3User类

- 包含用户id，用户姓名，用户密码，用户身份
- User类的属性如下

```java
public class User{
    private Integer id;
    private String name;
    private String password;
    private String role;
}
```

##### 5.5.4WarehouseInputSheetContent类

- 包含入库商品列表id，入库单编号，商品id，商品数量，单价，出厂日期，备注
- WarehouseInputSheetContent类的属性如下

```java
public class WarehouserInputSheetContent{
    private Integer id;
    private String wiId;
    private String pid;
    private String quantity;
    private BigDecimal purchasePrice;
    private Date productionDate;
    private String remark;
}
```

##### 5.5.5WarehouseInputSheet类

- 包含入库单编号，批次，操作员，操作时间
- WarehouseInputSheet类的属性如下

```java
public class WarehouseInputSheet{
    private String id;
    private Integer batchId;
    private String operator;
    private Date updateTime;
}
```

##### 5.5.6WarehouseOutputSheetContent类

- 包含出库商品列表id，商品id，出库单编号，批次，商品数量，单价，备注
- WarehouseOutputSheetContent类的属性如下

```java
public class WarehouseOutputSheetContent{
    private Integer id;
    private String pid;
    private String woId;
    private Integer batchId;
    private Integer quantity;
    private BigDecimal purchasePrice;
    private String remark;
}
```

##### 5.5.7WarehouseOutputSheet类

- 包含入库单编号，操作员，操作时间 
- WarehouseOutputSheet类的属性如下

```java
public class WarehouseOutputSheet{
    private String id;
    private String operator;
    private Date updateTime;
}
```

##### 5.5.8Warehouse类

- 包含库存id，商品编号，数量，进价，批次，出厂日期
- Warehouse类的属性如下

```java
public class Warehouse{
    private Integer id;
    private String pid;
    private Integer quantity;
    private BigDecimal purchasePrice;
    private Integer batchId;
    private Date productionDate;
}
```

#### 5.6数据库表

​	数据库中包含category表、product表、user表、warehouse表、warehouse_input_sheet表、warehouse_input_sheet_content表、warehouse_output_sheet表、warehouse_output_sheet_content表
