### 需求规格说明文档

- [需求规格说明文档](#需求规格说明文档)
  - [0 变更日志](#0-变更日志)
    - [1.1 目的](#11-目的)
    - [1.2 参考文献](#12-参考文献)
  - [2 功能需求](#2-功能需求)
    - [2.1 进货销售人员制定销售单](#21-进货销售人员制定销售单)
      - [2.1.1 特征描述](#211-特征描述)
      - [2.1.2 刺激/响应序列](#212-刺激响应序列)
      - [2.1.3 相关功能需求](#213-相关功能需求)
    - [2.2 进货销售人员制定销售退货单](#22-进货销售人员制定销售退货单)
      - [2.2.1 特征描述](#221-特征描述)
      - [2.2.2 刺激/响应序列](#222-刺激响应序列)
      - [2.2.3 相关功能需求](#223-相关功能需求)
    - [2.3 财务人员查看销售明细表](#23-财务人员查看销售明细表)
      - [2.3.1 特征描述](#231-特征描述)
      - [2.3.2 刺激/响应序列](#232-刺激响应序列)
      - [2.3.3 相关功能需求](#233-相关功能需求)
    - [2.4 总经理制定促销策略](#24-总经理制定促销策略)
      - [2.4.1 特征描述](#241-特征描述)
      - [2.4.2 刺激/响应序列](#242-刺激响应序列)
      - [2.4.3 相关功能需求](#243-相关功能需求)
  - [3 非功能需求](#3-非功能需求)
    - [3.1 安全性](#31-安全性)
    - [3.2 可维护性](#32-可维护性)
    - [3.3 易用性](#33-易用性)
    - [3.4 可靠性](#34-可靠性)
    - [3.5 业务规则](#35-业务规则)
    - [3.6 约束](#36-约束)


#### 0 变更日志

| 变更人 | 变更时间 | 变更内容                       | 版本号 |
| ------ | -------- | ------------------------------ | ------ |
| 刘子扬 | 2022.4.2 | 完成2.3 财务人员查看销售明细表 | v1.0   |
| 李俊彤 | 2022.4.3 | 完成2.1 进货人员制定销售单 | v1.0 |
| 郑寒超 | 2022.4.3 | 完成2.4 总经理制定促销策略     | v1.0   |
| 吴禹廷 | 2022.4.3 | 完成2.2 进货销售人员制定销售退货单 | v1.0 |
|郑寒超|2022.4.25|修改2.4 总经理制定促销策略|v1.1|
|刘子扬|2022.4.30|修改了2.3 财务人员查看销售明细表|v1.1|
|刘子扬|2022.4.30|添加了3 非功能需求|v1.1|
##### 1.1 目的

本文档完成lab4内容，描述了销售模块的需求规格说明。

##### 1.2 参考文献

- 《软件工程与计算（卷二）软件开发的技术基础》骆斌，刘钦，丁二玉编著；
- IEEE标准；
- 《软件⼯程——实践者的研究⽅法》Roger S. Pressman 与 Bruce R. Maxim 著；

#### 2 功能需求

##### 2.1 进货销售人员制定销售单

###### 2.1.1 特征描述

1.在进货销售人员制定销售单时，系统开始处理进货销售人员的请求，生成销售单的表单模板。在得到进货销售人员的提交确认信息后上传给总经理进行审批。销售单通过审批后，系统会更改库存数据和客户的应收应付数据。

2.优先级=高

###### 2.1.2 刺激/响应序列

- 刺激：进货销售人员选择生成销售单
- 响应：系统自动生成一份进货销售单，内容包括单据编号、客户、业务员、操作员、仓库、出货商品清单、折让前总金额、折让、使用代金券金额、折让后总金额、备注、出货商品清单要显示商品的编号、名称、型号、数量、单价、金额、商品备注
- 刺激：进货销售人员依次填入表单各项
- 响应：系统记录进货销售人员的输入
- 刺激：进货销售人员选择生成销售单
- 响应：系统询问进货销售人员是否确认所填信息准确并且继续生成表单
- 刺激：进货销售人员选择确认
- 响应：系统将生成的进货单上传给总经理，并显示审批状态

###### 2.1.3 相关功能需求

| 编号                                               | 功能描述                                                     |
| -------------------------------------------------- | ------------------------------------------------------------ |
| IncomingSalesman.SalesOrder.Input                  | 系统应当允许进货销售人员在制作销售单过程中进行键盘输入       |
| IncomingSalesman.SalesOrder.Input.Client           | 进货销售人员输入客户ID，参见IncomingSalesman.SalesOrder.Client |
| IncomingSalesman.SalesOrder.Input.Salesman         | 进货销售人员输入业务员ID，参见IncomingSalesman.SalesOrder.Salesman |
| IncomingSalesman.SalesOrder.Input.OperationMan     | 进货销售人员输入操作员ID，参见IncomingSalesman.SalesOrder.OperationMan |
| IncomingSalesman.SalesOrder.Input.Warehouse        | 进货销售人员选择仓库                                         |
| IncomingSalesman.SalesOrder.Input.GoodsForm        | 进货销售人员填写销售商品清单，参见IncomingSalesman.SalesOrder.GoodsForm |
| IncomingSalesman.SalesOrder.Input.BeforeDiscount   | 进货销售人员输入折让前总金额，参见IncomingSalesman.SalesOrder.BeforeDiscount |
| IncomingSalesman.SalesOrder.Input.Discount         | 进货销售人员输入折让、使用代金券金额，参见IncomingSalesman.SalesOrder.Discount |
| IncomingSalesman.SalesOrder.Input.AfterDiscount    | 系统根据折让前总金额和折让金额生成折让后总金额               |
| IncomingSalesman.SalesOrder.Input.Note             | 进货销售人员输入备注                                         |
| IncomingSalesman.SalesOrder.Client                 | 系统根据进货销售人员输入的客户ID显示客户                     |
| IncomingSalesman.SalesOrder.Client.Yes             | 进货销售人员确认选择客户，系统添加客户至表单并返回表单填写界面 |
| IncomingSalesman.SalesOrder.Client.Cancel          | 进货销售人员取消选择客户，系统返回表单填写界面且不作任何更改 |
| IncomingSalesman.SalesOrder.Client.Remove          | 进货销售人员删除已选客户，系统删除客户并返回表单填写界面     |
| IncomingSalesman.SalesOrder.Salesman               | 系统根据进货销售人员输入的业务员ID显示业务员                 |
| IncomingSalesman.SalesOrder.Salesman.Yes           | 进货销售人员确认选择业务员，系统添加业务员至表单并返回表单填写界面 |
| IncomingSalesman.SalesOrder.Salesman.Cancel        | 进货销售人员取消选择业务员，系统返回表单填写界面且不作任何更改 |
| IncomingSalesman.SalesOrder.Salesman.Remove        | 进货销售人员删除已选业务员，系统删除业务员并返回表单填写界面 |
| IncomingSalesman.SalesOrder.OperationMan           | 系统根据进货销售人员输入的操作员ID显示操作员                 |
| IncomingSalesman.SalesOrder.OperationMan.Yes       | 进货销售人员确认选择操作员，系统添加操作员至表单并返回表单填写界面 |
| IncomingSalesman.SalesOrder.OperationMan.Cancel    | 进货销售人员取消选择操作员，系统返回表单填写界面且不作任何更改 |
| IncomingSalesman.SalesOrder.OperationMan.Remove    | 进货销售人员删除已选操作员，系统删除操作员并返回表单填写界面 |
| IncomingSalesman.SalesOrder.Warehouse              | 系统显示所有仓库供进货销售人员选择                           |
| IncomingSalesman.SalesOrder.Warehouse.Select       | 进货销售人员选择仓库，系统添加仓库至表单并返回表单填写界面   |
| IncomingSalesman.SalesOrder.Warehouse.Cancel       | 进货销售人员取消选择仓库，系统返回表单填写界面且不作任何更改 |
| IncomingSalesman.SalesOrder.Warehouse.Remove       | 进货销售人员删除已选仓库，系统删除仓库并返回表单填写界面     |
| IncomingSalesman.SalesOrder.GoodsForm              | 系统生成销售商品清单                                         |
| IncomingSalesman.SalesOrder.GoodsForm.Add          | 进货销售人员向销售商品清单中添加商品                         |
| IncomingSalesman.SalesOrder.GoodsForm.Add.SetId    | 进货销售人员填写商品id                                       |
| IncomingSalesman.SalesOrder.GoodsForm.Add.SetName  | 进货销售人员填写商品名称                                     |
| IncomingSalesman.SalesOrder.GoodsForm.Add.SetType  | 进货销售人员填写商品型号                                     |
| IncomingSalesman.SalesOrder.GoodsForm.Add.SetNum   | 进货销售人员填写商品数量                                     |
| IncomingSalesman.SalesOrder.GoodsForm.Add.SetPrice | 进货销售人员填写商品单价                                     |
| IncomingSalesman.SalesOrder.GoodsForm.Add.SetSum   | 进货销售人员填写商品总金额                                   |
| IncomingSalesman.SalesOrder.GoodsForm.Add.SetNote  | 进货销售人员填写商品备注                                     |
| IncomingSalesman.SalesOrder.GoodsForm.Add.Yes      | 进货销售人员确认添加商品，系统添加商品至销售商品清单         |
| IncomingSalesman.SalesOrder.GoodsForm.Add.Cancel   | 进货销售人员取消添加商品，系统不作任何更改                   |
| IncomingSalesman.SalesOrder.GoodsForm.Delete       | 进货销售人员向销售商品清单中删除商品                         |
| IncomingSalesman.SalesOrder.GoodsForm.Yes          | 进货销售人员点击确认，系统生成销售商品清单                   |
| IncomingSalesman.SalesOrder.GoodsForm.Cancel       | 进货销售人员点击取消，系统返回销售商品清单填写界面           |
| IncomingSalesman.SalesOrder.BeforeDiscount         | 系统要求进货销售人员输入折让、使用代金券金额                 |
| IncomingSalesman.SalesOrder.BeforeDiscount.Valid   | 进货销售人员输入折让前总金额符合规范(大于0)                  |
| IncomingSalesman.SalesOrder.BeforeDiscount.Invalid | 进货销售人员输入折让前总金额不符合规范(小于等于0或非数字)，系统要求重新填写 |
| IncomingSalesman.SalesOrder.Discount               | 系统要求进货销售人员输入折让前总金额                         |
| IncomingSalesman.SalesOrder.Discount.Valid         | 进货销售人员输入折让金额符合规范(大于0且小于折让前总金额)    |
| IncomingSalesman.SalesOrder.Discount.Invalid       | 进货销售人员输入折让前总金额不符合规范(小于等于0、大于折让前总金额或非数字)，系统要求重新填写 |
| IncomingSalesman.SalesOrder.SubmitReturning        | 系统根据所填内容自动生成一份销售单                           |
| IncomingSalesman.SalesOrder.SubmitReturning.Yes    | 系统向进货销售人员询问是否确认生成表单，进货销售人员选择确认，系统保留并检查数据，参见IncomingSalesman.SalesOrder.Check |
| IncomingSalesman.SalesOrder.SubmitReturning.Cancel | 系统向进货销售人员询问是否确认生成表单，销售人员选择取消，系统返回表单填写界面 |
| IncomingSalesman.SalesOrder.Check                  | 系统检查表单数据                                             |
| IncomingSalesman.SalesOrder.Check.Empty            | 表单中有未填项，系统提示操作员填写并返回表单填写界面         |
| IncomingSalesman.SalesOrder.Check.Valid            | 操作员填写无误，系统询问操作员是否提交表单，参见IncomingSalesman.SalesOrder.SubmitReturning |
| IncomingSalesman.SalesOrder.SubmitReturning        | 系统询问进货销售人员是否提交表单                             |
| IncomingSalesman.SalesOrder.SubmitReturning.Yes    | 系统提交表单，并跳转至审批界面，参见IncomingSalesman.SalesOrder.Approval |
| IncomingSalesman.SalesOrder.SubmitReturning.Cancel | 系统返回先前界面                                             |
| IncomingSalesman.SalesOrder.Approval               | 系统持续更新审批状态                                         |
| IncomingSalesman.SalesOrder.Approval.Check         | 进货销售人员查看审批状态                                     |
| IncomingSalesman.SalesOrder.Approval.Yes           | 审批通过，系统更改库存数据和客户的应收退款数据               |
| IncomingSalesman.SalesOrder.Approval.Reject        | 审批不通过，系统不作修改                                     |
| IncomingSalesman.SalesOrder.Return                 | 系统允许进货销售人员结束查看并返回。                         |

##### 2.2 进货销售人员制定销售退货单

###### 2.2.1 特征描述
1.在进货销售人员制定销售退货单时，系统开始处理进货销售人员的请求，生成销售退货单的表单模板。在得到进货销售人员的提交确认信息后上传给总经理进行审批。退货单通过审批后，系统会更改库存数据和客户的退款数据。

2.优先级=高
###### 2.2.2 刺激/响应序列
- 刺激：进货销售人员选择生成销售退货单
- 响应：系统自动生成一份销售退货单，内容包括单据编号、客户、业务员、操作员、仓库、退货商品清单、折让前总金额、折让、使用代金券金额、折让后应退款总金额、备注、退货商品清单、要显示商品的编号、名称、型号、数量、单价、金额、商品备注
- 刺激：进货销售人员依次填入表单各项
- 响应：系统记录进货销售人员的输入
- 刺激：进货销售人员选择生成销售退货单
- 响应：系统询问进货销售人员是否确认生成表单
- 刺激：进货销售人员选择确认生成销售退货单
- 响应：系统将生成的进货单上传给总经理，并且显示已上传和审批状态

###### 2.2.3 相关功能需求

| 编号                                                     | 功能描述                                                     |
| -------------------------------------------------------- | ------------------------------------------------------------ |
| IncomingSalesman.SalesOrder.Input                        | 系统应当允许进货销售人员在制作退货单过程中进行键盘输入       |
| IncomingSalesman.SalesOrder.Input.Client                 | 进货销售人员输入客户ID，参见IncomingSalesman.SalesOrder.Client      |
| IncomingSalesman.SalesOrder.Input.Salesman               | 进货销售人员输入业务员ID，参见IncomingSalesman.SalesOrder.Salesman       |
| IncomingSalesman.SalesOrder.Input.OperationMan           | 进货销售人员输入操作员ID，参见IncomingSalesman.SalesOrder.OperationMan      |
| IncomingSalesman.SalesOrder.Input.Warehouse              | 进货销售人员选择仓库   |
| IncomingSalesman.SalesOrder.Input.GoodsForm              | 进货销售人员填写退货商品清单，参见IncomingSalesman.SalesOrder.GoodsForm   |
| IncomingSalesman.SalesOrder.Input.BeforeDiscount         | 进货销售人员输入折让前总金额，参见IncomingSalesman.SalesOrder.BeforeDiscount      |
| IncomingSalesman.SalesOrder.Input.Discount               | 进货销售人员输入折让、使用代金券金额，参见IncomingSalesman.SalesOrder.Discount      |
| IncomingSalesman.SalesOrder.Input.AfterDiscount          | 系统根据折让前总金额和折让金额生成折让后总金额       |
| IncomingSalesman.SalesOrder.Input.Note                   | 进货销售人员输入备注       |
| IncomingSalesman.SalesOrder.Client                       | 系统根据进货销售人员输入的客户ID显示客户       |
| IncomingSalesman.SalesOrder.Client.Yes                   | 进货销售人员确认选择客户，系统添加客户至表单并返回表单填写界面      |
| IncomingSalesman.SalesOrder.Client.Cancel                | 进货销售人员取消选择客户，系统返回表单填写界面且不作任何更改      |
| IncomingSalesman.SalesOrder.Client.Remove                | 进货销售人员删除已选客户，系统删除客户并返回表单填写界面      |
| IncomingSalesman.SalesOrder.Salesman                     | 系统根据进货销售人员输入的业务员ID显示业务员       |
| IncomingSalesman.SalesOrder.Salesman.Yes                 | 进货销售人员确认选择业务员，系统添加业务员至表单并返回表单填写界面      |
| IncomingSalesman.SalesOrder.Salesman.Cancel              | 进货销售人员取消选择业务员，系统返回表单填写界面且不作任何更改      |
| IncomingSalesman.SalesOrder.Salesman.Remove              | 进货销售人员删除已选业务员，系统删除业务员并返回表单填写界面      |
| IncomingSalesman.SalesOrder.OperationMan                 | 系统根据进货销售人员输入的操作员ID显示操作员       |
| IncomingSalesman.SalesOrder.OperationMan.Yes             | 进货销售人员确认选择操作员，系统添加操作员至表单并返回表单填写界面      |
| IncomingSalesman.SalesOrder.OperationMan.Cancel          | 进货销售人员取消选择操作员，系统返回表单填写界面且不作任何更改      |
| IncomingSalesman.SalesOrder.OperationMan.Remove          | 进货销售人员删除已选操作员，系统删除操作员并返回表单填写界面      |
| IncomingSalesman.SalesOrder.Warehouse                    | 系统显示所有仓库供进货销售人员选择       |
| IncomingSalesman.SalesOrder.Warehouse.Select             | 进货销售人员选择仓库，系统添加仓库至表单并返回表单填写界面      |
| IncomingSalesman.SalesOrder.Warehouse.Cancel             | 进货销售人员取消选择仓库，系统返回表单填写界面且不作任何更改      |
| IncomingSalesman.SalesOrder.Warehouse.Remove             | 进货销售人员删除已选仓库，系统删除仓库并返回表单填写界面      |
| IncomingSalesman.SalesOrder.GoodsForm                    | 系统生成退货商品清单      |
| IncomingSalesman.SalesOrder.GoodsForm.Add                | 进货销售人员向退货商品清单中添加商品  |
| IncomingSalesman.SalesOrder.GoodsForm.Add.SetId          | 进货销售人员填写商品id  |
| IncomingSalesman.SalesOrder.GoodsForm.Add.SetName        | 进货销售人员填写商品名称  |
| IncomingSalesman.SalesOrder.GoodsForm.Add.SetType        | 进货销售人员填写商品型号  |
| IncomingSalesman.SalesOrder.GoodsForm.Add.SetNum         | 进货销售人员填写商品数量  |
| IncomingSalesman.SalesOrder.GoodsForm.Add.SetPrice       | 进货销售人员填写商品单价  |
| IncomingSalesman.SalesOrder.GoodsForm.Add.SetSum         | 进货销售人员填写商品总金额  |
| IncomingSalesman.SalesOrder.GoodsForm.Add.SetNote        | 进货销售人员填写商品备注  |
| IncomingSalesman.SalesOrder.GoodsForm.Add.Yes            | 进货销售人员确认添加商品，系统添加商品至退货商品清单  |
| IncomingSalesman.SalesOrder.GoodsForm.Add.Cancel         | 进货销售人员取消添加商品，系统不作任何更改  |
| IncomingSalesman.SalesOrder.GoodsForm.Delete             | 进货销售人员向退货商品清单中删除商品  |
| IncomingSalesman.SalesOrder.GoodsForm.Yes                | 进货销售人员点击确认，系统生成退货商品清单  |
| IncomingSalesman.SalesOrder.GoodsForm.Cancel             | 进货销售人员点击取消，系统返回退货商品清单填写界面  |
| IncomingSalesman.SalesOrder.BeforeDiscount               | 系统要求进货销售人员输入折让、使用代金券金额       |
| IncomingSalesman.SalesOrder.BeforeDiscount.Valid         | 进货销售人员输入折让前总金额符合规范(大于0)      |
| IncomingSalesman.SalesOrder.BeforeDiscount.Invalid       | 进货销售人员输入折让前总金额不符合规范(小于等于0或非数字)，系统要求重新填写  |
| IncomingSalesman.SalesOrder.Discount                     | 系统要求进货销售人员输入折让前总金额      |
| IncomingSalesman.SalesOrder.Discount.Valid               | 进货销售人员输入折让金额符合规范(大于0且小于折让前总金额)      |
| IncomingSalesman.SalesOrder.Discount.Invalid             | 进货销售人员输入折让前总金额不符合规范(小于等于0、大于折让前总金额或非数字)，系统要求重新填写  |
| IncomingSalesman.SalesOrder.SubmitReturning              | 系统根据所填内容自动生成一份销售退货单 |
| IncomingSalesman.SalesOrder.SubmitReturning.Yes          | 系统向进货销售人员询问是否确认生成表单，进货销售人员选择确认，系统保留并检查数据，参见IncomingSalesman.SalesOrder.Check |
| IncomingSalesman.SalesOrder.SubmitReturning.Cancel       | 系统向进货销售人员询问是否确认生成表单，销售人员选择取消，系统返回表单填写界面 |
| IncomingSalesman.SalesOrder.Check                        | 系统检查表单数据 |
| IncomingSalesman.SalesOrder.Check.Empty                  | 表单中有未填项，系统提示操作员填写并返回表单填写界面 |
| IncomingSalesman.SalesOrder.Check.Valid                  | 操作员填写无误，系统询问操作员是否提交表单，参见IncomingSalesman.SalesOrder.SubmitReturning |
| IncomingSalesman.SalesOrder.SubmitReturning              | 系统询问进货销售人员是否提交表单 |
| IncomingSalesman.SalesOrder.SubmitReturning.Yes          | 系统提交表单，并跳转至审批界面，参见IncomingSalesman.SalesOrder.Approval |
| IncomingSalesman.SalesOrder.SubmitReturning.Cancel       | 系统返回先前界面  |
| IncomingSalesman.SalesOrder.Approval                     | 系统持续更新审批状态                     |
| IncomingSalesman.SalesOrder.Approval.Check               | 进货销售人员查看审批状态                     |
| IncomingSalesman.SalesOrder.Approval.Yes                 | 审批通过，系统更改库存数据和客户的应收退款数据   |
| IncomingSalesman.SalesOrder.Approval.Reject              | 审批不通过，系统不作修改               |
| IncomingSalesman.SalesOrder.Return                       | 系统允许进货销售人员结束查看并返回。           |



##### 2.3 财务人员查看销售明细表

###### 2.3.1 特征描述

1. 在财务人员选择查看销售明细表时，系统开始处理财务人员的请求，提供销售明细表的筛选条件、根据财务人员选择的筛选范围进行筛选、以列表形式显示筛选后的信息、支持excel形式导出数据。
2. 优先级 = 高

###### 2.3.2 刺激/响应序列

- 刺激：财务人员选择查看销售明细表
- 响应：系统提供筛选条件的表单
- 刺激：财务人员选择时间区间
- 响应：系统显示可选的时间区间
- 刺激：财务人员选择商品名
- 响应：系统显示可选的商品名
- 刺激：财务人员选择客户
- 响应：系统显示可选的客户
- 刺激：财务人员选择业务员
- 响应：系统显示可选的业务员
- 刺激：财务人员选择仓库
- 响应：系统显示可选的仓库
- 刺激：财务人员确认筛选条件
- 响应：系统根据用户选择的筛选区间或对象进行筛选，并以列表形式显示筛选后的结果，每一条筛选结果包括时间（精确到天）、商品名、型号、数量、单价、总额
- 刺激：财务人员选择导出数据
- 响应：系统将筛选后的信息以excel的形式导出到财务人员选择的地址中

###### 2.3.3 相关功能需求

| 编号                                                    | 功能描述                                                     |
| ------------------------------------------------------- | ------------------------------------------------------------ |
| FinancialStaff.SalesDetails.Choose                      | 财务人员选择销售明细表各项筛选条件，包括时间区间、商品名、客户、业务员、仓库，系统显示可选范围和对象 |
| FinancialStaff.SalesDetails.Choose.TimeRange            | 财务人员选择时间区间，系统显示可选的时间区间，参见FinancialStaff.SalesDetails.TimeRange |
| FinancialStaff.SalesDetails.Choose.Commodity            | 财务人员选择商品名，系统显示可选的商品名，参见FinancialStaff.SalesDetails.Commodity |
| FinancialStaff.SalesDetails.Choose.Client               | 财务人员选择客户，系统显示可选的客户，参见FinancialStaff.SalesDetails.Client |
| FinancialStaff.SalesDetails.Choose.Salesman             | 财务人员选择业务员，系统显示可选的业务员，参见FinancialStaff.SalesDetails.Salesman |
| FinancialStaff.SalseDetails.Choose.Warehouse            | 财务人员选择仓库，系统显示可选的仓库，参见FinancialStaff.SalesDetails.Warehouse |
| FinancialStaff.SalesDetails.Choose.Submit               | 财务人员提交筛选条件，系统筛选出对应的商品销售记录，参见FinancialStaff.SalesDetails.Submit |
|                                                         |                                                              |
| FinancialStaff.SalesDetails.TimeRange                   | 系统显示可选的时间区间                                       |
| FinancialStaff.SalesDetails.TimeRange.Select            | 财务人员选择具体的时间区间，系统记录数据                     |
| FinancialStaff.SalesDetails.TimeRange.Remove            | 财务人员清除已选择的时间区间，系统清楚数据                   |
| FinancialStaff.SalesDetails.TimeRange.Cancel            | 财务人员取消选择时间区间，系统结束时间区间选择任务           |
| FinancialStaff.SalesDetails.TimeRange.Confirm           | 财务人员确认选择的时间区间，系统保存并结束时间区间选择任务   |
|                                                         |                                                              |
| FinancialStaff.SalesDetails.Commodity                   | 系统显示可选的商品名                                         |
| FinancialStaff.SalesDetails.Commodity.Select            | 财务人员选择具体的商品名，系统记录数据                       |
| FinancialStaff.SalesDetails.Commodity.Remove            | 财务人员清除已选择的商品名，系统清除数据                     |
| FinancialStaff.SalesDetails.Commodity.Cancel            | 财务人员取消选择商品名，系统结束商品名选择任务               |
| FinancialStaff.SalesDetails.Commodity.Confirm           | 财务人员确认选择的商品名，系统保存并结束商品名选择任务       |
|                                                         |                                                              |
| FinancialStaff.SalesDetails.Client                      | 系统显示可选的客户                                           |
| FinancialStaff.SalesDetails.Client.Select               | 财务人员选择具体的客户，系统记录数据                         |
| FinancialStaff.SalesDetails.Client.Remove               | 财务人员清除已选择的客户，系统清除数据                       |
| FinancialStaff.SalesDetails.Client.Cancel               | 财务人员取消选择客户，系统结束客户选择任务                   |
| FinancialStaff.SalesDetails.Client.Confirm              | 财务人员确认选择的客户，系统保存并结束客户选择任务           |
|                                                         |                                                              |
| FinancialStaff.SalesDetails.Salesman                    | 系统显示可选的业务员                                         |
| FinancialStaff.SalesDetails.Salesman.Select             | 财务人员选择具体的业务员，系统记录数据                       |
| FinancialStaff.SalesDetails.Salesman.Remove             | 财务人员清除已选择的业务员，系统清除数据                     |
| FinancialStaff.SalesDetails.Salesman.Cancel             | 财务人员取消选择业务员，系统结束业务员选择任务               |
| FinancialStaff.SalesDetails.Salesman.Confirm            | 财务人员确认选择的业务员，系统保存并结束业务员选择任务       |
|                                                         |                                                              |
| FinancialStaff.SalesDetails.Warehouse                   | 系统显示可选的仓库                                           |
| FinancialStaff.SalesDetails.Warehouse.Select            | 财务人员选择具体的仓库，系统记录数据                         |
| FinancialStaff.SalesDetails.Warehouse.Remove            | 财务人员清除已选择的仓库，系统清除数据                       |
| FinancialStaff.SalesDetails.Warehouse.Cancel            | 财务人员取消选择仓库，系统结束仓库选择任务                   |
| FinancialStaff.SalesDetails.Warehouse.Confirm           | 财务人员确认选择的仓库，系统保存并结束仓库选择任务           |
|                                                         |                                                              |
| FinancialStaff.SalesDetails.Submit.Check                | 财务人员提交筛选表单，系统检测表单                           |
| FinancialStaff.SalesDetails.Submit.Check.Empty          | 系统检测发现表单中有未填项，提醒财务人员进行填写             |
| FinancialStaff.SalesDetails.Submit.Check.Full           | 系统检测表单，符合要求，询问财务人员是否提交                 |
| FinancialStaff.SalesDetails.Submit.Confirm              | 财务人员确认提交，系统返回符合上述条件的所有商品销售记录，参见Financial.Staff.SalesDetails.Show |
| FinancialStaff.SalesDetails.Submit.Cancel               | 财务人员取消提交，系统结束提交任务                           |
|                                                         |                                                              |
| FinancialStaff.SalesDetails.Show                        | 系统展示商品销售记录                                         |
| FinancialStaff.SalesDetails.Show.Select                 | 财务人员选中某一条销售记录，系统记录数据                     |
| FinancialStaff.SalesDetails.Show.Remove                 | 财务人员清除某一条销售记录，系统清除数据                     |
| FinancialStaff.SalesDetails.Show.Output                 | 财务人员导出选中的销售记录，系统导出数据，参见FinancialStaff.SalesDetails.Excel |
|                                                         |                                                              |
| FinancialStaff.SalesDetails.Eecel.Check                 | 财务人员导出选中数据，系统检测选中的数据                     |
| FinancialStaff.SalesDetails.Eecel.Check.Empty           | 系统检测并没有选中数据，提醒财务人员进行标记                 |
| FinancialStaff.SalesDetails.Eecel.Check.Exist           | 系统检测，存在至少一条数据，询问财务人员是否导出             |
| FinancialStaff.SalesDetails.Eecel.Confirm               | 财务人员确认导出，系统导出选中的数据，参见FinancialStaff.SalesDetails.Address |
|                                                         |                                                              |
| FinancialStaff.SalesDetails.Address.Input               | 系统要求财务人员选择导出的具体地址，财务人员填写             |
| FinancialStaff.SalesDetails.Address.Input.Check         | 系统检测财务人员填写的地址是否符合规范                       |
| FinancialStaff.SalesDetails.Address.Input.Check.Valid   | 财务人员填写的地址符合规范，系统询问是否确认导出             |
| FinancialStaff.SalesDetails.Address.Input.Check.Invalid | 财务人员填写的地址不符合规范，系统保留选中的数据，不导出，结束地址填写任务 |
| FinancialStaff.SalesDetails.Address.Input.Confirm       | 财务人员确认导出，系统导出数据到制定地址                     |
| FinancialStaff.SalesDetails.Address.Input.Cancel        | 财务人员取消导出，系统结束导出任务                           |



##### 2.4 总经理制定促销策略

###### 2.4.1 特征描述

1. 在总经理选择制定促销策略时，系统开始处理总经理的请求，完成制定促销策略的操作。
2. 优先级：高

###### 2.4.2 刺激/响应序列

- 刺激：总经理请求制定促销策略
- 响应：系统显示三种可选的促销策略的方式：基于用户级别、特价包、基于总价
- 刺激：总经理选择基于用户级别制定促销策略的方式
- 响应：系统显示所有用户级别，及每个用户待填写的促销策略
- 刺激：总经理选择特价包制定促销策略的方式
- 响应：系统显示所有可选择商品
- 刺激：总经理选择基于总价制定促销策略的方式
- 响应：系统显示需要填写的总价划分信息，包括实施促销策略的最低总价、促销档数以及每档的价格区间
- 刺激：总经理填写总价划分方式并确认
- 响应：系统显示各档总价区间，及其对应的待填写的促销策略
- 刺激：总经理点击选择赠品
- 响应：系统显示所有可选择商品
- 刺激：总经理点击选择代金券
- 响应：系统显示所有可选代金券
- 刺激：总经理选择设置促销策略的起始时间
- 响应：系统显示日历表和持续时间
- 刺激：总经理填写完促销策略的信息并提交
- 响应：系统提示制定促销策略成功

###### 2.4.3 相关功能需求

| 编号                                                         | 功能描述                                                     |
| :----------------------------------------------------------- | ------------------------------------------------------------ |
| Manager.MakePromotionStrategy.Choose                         | 总经理选择制定促销策略，系统显示三种可选的促销策略的方式：基于用户级别、特价包、基于总价。 |
| Manager.MakePromotionStrategy.Choose.UserLevelBased          | 总经理选择基于用户级别制定促销策略的方式，系统进入执行制定基于用户级别的促销策略，参见Manager.MakePromotionStrategy.UserLevelBased |
| Manager.MakePromotionStrategy.Choose.DiscountedPackage       | 总经理选择特价包制定促销策略的方式，系统进入执行制定特价包的促销策略，参见Manager.MakePromotionStrategy.DiscountedPackage |
| Manager.MakePromotionStrategy.Choose.TotalPriceBased         | 总经理选择基于总价制定促销策略的方式，系统进入执行基于总价制定促销策略，参见Manager.MakePromotionStrategy.TotalPriceBased |
|                                                              |                                                              |
| Manager.MakePromotionStrategy.UserLevelBased.ShowEmptyStrategy | 总经理选择基于用户级别制定促销策略的方式，系统展示所有用户级别及其待填写的促销策略。 |
| Manager.MakePromotionStrategy.UserLevelBased.Gifts           | 总经理选择促销策略的赠品，系统执行选择商品任务，参见Manager.MakePromotionStrategy.Commodity |
| Manager.MakePromotionStrategy.TotalPriceBased.Discount       | 总经理制定促销策略的折扣，系统执行折扣的任务，参见Manager.MakePromotionStrategy.Discount |
| Manager.MakePromotionStrategy.TotalPriceBased.Voucher        | 总经理选择促销策略的代金券，系统执行选择代金券任务，参见Manager.MakePromotionStrategy.Voucher |
| Manager.MakePromotionStrategy.TotalPriceBased.SetTime        | 总经理选择设置促销策略时间，系统执行选择日期任务，参见Manager.MakePromotionStrategy.SetTime |
| Manager.MakePromotionStrategy.TotalPriceBased.Submit         | 总经理选择提交促销策略，系统执行提交任务，参见Manager.MakePromotionStrategy.Submit |
|                                                              |                                                              |
| Manager.MakePromotionStrategy.DiscountedPackage              | 总经理选择制定特价包，系统展示待填写的促销策略。             |
| Manager.MakePromotionStrategy.DiscountedPackage.SelectCommodity | 总经理选择特价包商品，系统执行选择商品任务，参见Manager.MakePromotionStrategy.Commodity |
| Manager.MakePromotionStrategy.DiscountedPackage.Discount     | 总经理选择填写折扣，系统执行填写折扣任务，参见Manager.MakePromotionStrategy.Discount |
| Manager.MakePromotionStrategy.DiscountedPackage.SetTime      | 总经理选择设置促销策略起始时间，系统执行选择日期任务，参见Manager.MakePromotionStrategy.SetTime |
| Manager.MakePromotionStrategy.DiscountedPackage.Submit       | 总经理选择提交促销策略，系统执行提交任务，参见Manager.MakePromotionStrategy.Submit |
|                                                              |                                                              |
| Manager.MakePromotionStrategy.TotalPriceBased.ShowPriceDividedInfo | 总经理选择基于总价的促销策略，系统显示需要填写总价划分信息，包括实施促销策略的最低总价，促销策略的档数和每档的金额区间。 |
| Manager.MakePromotionStrategy.TotalPriceBased.FillPriceDividedInfo | 总经理填写总价划分策略，系统记录填写的信息，参见Manager.MakePromotionStrategy.Input |
| Manager.MakePromotionStrategy.TotalPriceBased.CheckDividedInfo | 总经理确认总价划分策略，系统检查总经理填写信息               |
| Manager.MakePromotionStrategy.TotalPriceBased.CheckDividedInfo.Invalid | 总经理填写的信息中存在负数，系统提示信息不能为负             |
| Manager.MakePromotionStrategy.TotalPriceBased.CheckDividedInfo.Valid | 总经理填写的信息不存在负数，系统提示总价划分完毕             |
| Manager.MakePromotionStrategy.TotalPriceBased.Gifts          | 总经理选择促销策略的赠品，系统执行选择商品任务，参见Manager.MakePromotionStrategy.Commodity |
| Manager.MakePromotionStrategy.TotalPriceBased.Voucher        | 总经理选择促销策略的代金券，系统执行选择代金券任务，参见Manager.MakePromotionStrategy.Voucher |
| Manager.MakePromotionStrategy.TotalPriceBased.SetTime        | 总经理选择设置促销策略时间，系统执行选择日期任务，参见Manager.MakePromotionStrategy.SetTime |
| Manager.MakePromotionStrategy.TotalPriceBased.Submit         | 总经理选择提交促销策略，系统执行提交任务，参见Manager.MakePromotionStrategy.Submit |
|                                                              |                                                              |
| Manager.MakePromotionStrategy.Commodity                      | 系统展示所有可选商品                                         |
| Manager.MakePromotionStrategy.Commodity.Select               | 总经理选择商品，系统记录所选商品将该商品加入商品列表         |
| Manager.MakePromotionStrategy.Commodity.Remove               | 总经理取消选择商品，系统将该商品移出赠品列表                 |
| Manager.MakePromotionStrategy.Commodity.Cancel               | 总经理取消选择商品，系统结束选择商品任务                     |
| Manager.MakePromotionStrategy.Commodity.Confirm              | 总经理选择商品结束并确认，系统生成商品列表并结束选择商品任务 |
|                                                              |                                                              |
| Manager.MakePromotionStrategy.Discount.Invalid               | 总经理输入的折扣信息不正确（折扣小于0或者大于1），系统提示输入不正确 |
| Manager.MakePromotionStrategy.Discount.Valid                 | 总经理输入的折扣信息正确（0<=折扣<=1），系统提示输入正确，并保存折扣信息 |
|                                                              |                                                              |
| Manager.MakePromotionStrategy.Voucher                        | 系统展示所有可选代金券                                       |
| Manager.MakePromotionStrategy.Voucher.Select                 | 总经理选择代金券，系统记录所选代金券并加入代金券列表         |
| Manager.MakePromotionStrategy.Voucher.Remove                 | 总经理取消所选代金券，系统将该代金券移出代金券列表           |
| Manager.MakePromotionStrategy.Voucher.Cancel                 | 总经理取消选择代金券，系统结束选择代金券任务                 |
| Manager.MakePromotionStrategy.Voucher.Confirm                | 总经理选择代金券结束并确认，系统生成代金券列表并结束选择代金券任务 |
|                                                              |                                                              |
| Manager.MakePromotionStrategy.SetTime                        | 系统展示日历表和待填写的持续时间                             |
| Manager.MakePromotionStrategy.SetTime.Invalid                | 总经理填写的持续时间小于0，系统提示错误                      |
| Manager.MakePromotionStrategy.SetTime.Valid                  | 总经理填写时间信息正确，系统提示设置成功并返回               |
|                                                              |                                                              |
| Manager.MakePromotionStrategy.Submit.Check                   | 总经理选择提交策略，系统对促销策略进行检查                   |
| Manager.MakePromotionStrategy.Submit.Check.Empty             | 总经理未填写完策略，系统提示存在信息为空                     |
| Manager.MakePromotionStrategy.Submit.Check.Full              | 总经理填写完所有信息，系统提示是否提交                       |
| Manager.MakePromotionStrategy.Submit.Confirm                 | 总经理确认提交，系统提示提交成功                             |
| Manager.MakePromotionStrategy.Submit.Cancel                  | 总经理取消提交，系统展示填写的促销策略                       |



#### 3 非功能需求

##### 3.1 安全性

- Safety1：系统应该只允许经过验证和授权的操作者访问
- Safety2：系统应该按照操作者身份验证操作者的访问权限（是否是对应操作者类型和是否有权限进行访问）：
  - 除了库存管理人员、进货销售人员、财务人员、人力资源人员、总经理，其他身份的操作者没有访问权限
- Safety3：总经理可以查看和维护所有信息，其他操作者只能查看和维护自己的信息

##### 3.2 可维护性

- Modifiability1：如果系统需要增加新的VIP会员，要能够在0.25人月内完成
- Modifiability2：如果总经理退出新的促销策略，要能够在0.25人月内完成
- Modifiability3：如果系统的商品表示数据格式发生变化时，系统要能够在3人天内完成

##### 3.3 易用性

- Usability1：库存管理人员可以在30分钟内完成赠品的发放
- Usability2：库存管理人员、总经理可以在30分钟内完成库存查看和库存盘点
- Usability3：进货销售人员、总经理可以在30分钟内完成客户信息管理
- Usability4：财务人员、总经理可以在30分钟内完成销售明细表、经营里程表、经营情况表查看
- Usability5：人力资源人员、总经理可以在30分钟内进行员工信息维护

##### 3.4 可靠性

- Reliability1：如果网页端和服务器之间产生网络故障，系统不能出现故障，能保持稳定状态
  - Reliability1.1：客户端应该检测到故障，并尝试重新连接网络3次，每次15秒
    - Reliability1.1.1：重新连接后，客户端应该继续之前的工作
    - Reliability1.1.2：如果重新连接不成功，客户端应该等待5分钟后再次尝试重新连接
      - Reliability1.1.2.1：重新连接后，客户端应该继续之前的工作
      - Reliability1.1.2.2：如果重新连接仍然不成功，客户端报警

##### 3.5 业务规则

- BR1：适用（商品标识，参照日期）的商品赠送促销策略：
  - （促销商品标识=商品标识）而且（（开始日期早于等于参照日期）并且（结束日期晚于等于参照日期））
- BR2：适用（商品标识，参照日期）的商品特价促销策略：
  - （促销商品标识=商品标识）而且（（开始日期早于等于参照日期）并且（结束日期晚于等于参照日期））
- BR3：积分兑换规则
  - 50积分=1元人民币

##### 3.6 约束

- IC1：在开发过程中缺少可用的打印机，需要使用文件系统模拟打印机
- IC2：系统要在网络上分布为一个服务器和多个客户端
