# RxPractice
### RxJava使用整理

### 前言
> RxJava的热度火（jiang）不（sao）火（hua）我不知道，反正我知道RxJava的star已经3.3W了，Rx1出来的时候其实已经学习了一些，当初年轻，没有深入理解，现在使用也只是用到什么操作符，就去网上查，所以这边相当于给自己写个文档，下次遇到可以使用的直接查。
### 准备
> - 学习时间：学习RxJava的成本是需要一点的，但是只要专心几天就能啃的差不多操作起来（非常值）。
> - 学习操作符之前先把原理基本使用弄懂，不然还是一头雾水，原本Rx在我面前是打了马赛克的，看了这个教程，豁然开朗。[RxJava入门教程学习](https://www.jianshu.com/u/c50b715ccaeb)
#### 创建操作符
- [基本创建-【BasicCreate】](https://github.com/LiangLuDev/RxPractice/blob/b78b5ab5145778c877a9e1820673a0dc34708732/app/src/main/java/com/luliang/rxpractice/operators/create/BasisCreate.java)
- [快速创建-【FastCreate】](https://github.com/LiangLuDev/RxPractice/blob/b78b5ab5145778c877a9e1820673a0dc34708732/app/src/main/java/com/luliang/rxpractice/operators/create/FastCreate.java)
- [延迟创建-【DelayCreate】](https://github.com/LiangLuDev/RxPractice/blob/b78b5ab5145778c877a9e1820673a0dc34708732/app/src/main/java/com/luliang/rxpractice/operators/create/DelayCreate.java)
#### 变换操作符
- [变换操作-【Map】【FlatMap】【ConcatMap】【Buffer】](https://github.com/LiangLuDev/RxPractice/blob/7aeebc20d3ae2d8dd05eb6e87ea145a7f06950f1/app/src/main/java/com/luliang/rxpractice/operators/transform/Transform.java)
#### 组合/合并操作符
- [合并操作-【concat】【concatArray】【merge】【mergeArray】【concatDelayError】【mergeDelayError】](https://github.com/LiangLuDev/RxPractice/blob/eddb9c9a55f4024422f62d4586f277b65989a1a9/app/src/main/java/com/luliang/rxpractice/operators/combine/ObservablesMerge.java)
- [组合操作-【zip】【combineLatest】【combineLatestDelayError】【reduce】【collect】【startWith】【startWithArray】【count】](https://github.com/LiangLuDev/RxPractice/blob/eddb9c9a55f4024422f62d4586f277b65989a1a9/app/src/main/java/com/luliang/rxpractice/operators/combine/ObservablesCombine.java)
#### 功能性操作符
- [常用功能操作符-【subscribe】【subscribeOn】【observeOn】](https://github.com/LiangLuDev/RxPractice/blob/23c8133d58f06fd33da4157528bda561c39045b8/app/src/main/java/com/luliang/rxpractice/operators/utility/AlwaysUtility.java)
- [其他功能操作符-【delay】【repeat】【repeatWhen】【do】](https://github.com/LiangLuDev/RxPractice/blob/23c8133d58f06fd33da4157528bda561c39045b8/app/src/main/java/com/luliang/rxpractice/operators/utility/OtherUtility.java)
- [错误处理功能操作符-【onErrorReturn】【onErrorResumeNext】【onExceptionResumeNext】【retry】【retryUntil】【retryWhen】](https://github.com/LiangLuDev/RxPractice/blob/23c8133d58f06fd33da4157528bda561c39045b8/app/src/main/java/com/luliang/rxpractice/operators/utility/ErrorUtility.java)
#### 过滤操作符
- [数量过滤-【take】【takeLast】]()
- [位置过滤-【firstElement】【lastElement】【elementAt】【elementAtOrError】]()
- [条件过滤-【filter】【ofType】【skip】【skipLast】【distinct】【distinctUntilChanged】]()
- [时间过滤-【throttleFirst】【throttleLast】【throttleWithTimeout】【sample】]()
#### TODO
#### 条件/布尔操作符