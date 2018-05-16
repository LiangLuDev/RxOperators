# RxPractice
### RxJava使用整理

### 前言
> RxJava的热度火（jiang）不（sao）火（hua）我不知道，反正我知道RxJava的star已经3.3W了，Rx1出来的时候其实已经学习了一些，当初年轻，没有深入理解，现在使用也只是用到什么操作符，就去网上查，所以这边相当于给自己写个文档，下次遇到可以使用的直接查。
#### 创建操作符
- [基本创建-【BasicCreate】](https://github.com/LiangLuDev/RxPractice/blob/b78b5ab5145778c877a9e1820673a0dc34708732/app/src/main/java/com/luliang/rxpractice/operators/create/BasisCreate.java)
- [快速创建-【FastCreate】](https://github.com/LiangLuDev/RxPractice/blob/b78b5ab5145778c877a9e1820673a0dc34708732/app/src/main/java/com/luliang/rxpractice/operators/create/FastCreate.java)
- [延迟创建-【DelayCreate】](https://github.com/LiangLuDev/RxPractice/blob/b78b5ab5145778c877a9e1820673a0dc34708732/app/src/main/java/com/luliang/rxpractice/operators/create/DelayCreate.java)
#### 变换操作符
- [变换操作-【Map】【FlatMap】【ConcatMap】【Buffer】](https://github.com/LiangLuDev/RxPractice/blob/7aeebc20d3ae2d8dd05eb6e87ea145a7f06950f1/app/src/main/java/com/luliang/rxpractice/operators/transform/Transform.java)
#### 组合/合并操作符
- [合并操作-【concat】【concatArray】【merge】【mergeArray】【concatDelayError】【mergeDelayError】](https://github.com/LiangLuDev/RxPractice/blob/eddb9c9a55f4024422f62d4586f277b65989a1a9/app/src/main/java/com/luliang/rxpractice/operators/combine/ObservablesMerge.java)
- [组合操作-【zip】【combineLatest】【combineLatestDelayError】【reduce】【collect】【startWith】【startWithArray】【count】](https://github.com/LiangLuDev/RxPractice/blob/eddb9c9a55f4024422f62d4586f277b65989a1a9/app/src/main/java/com/luliang/rxpractice/operators/combine/ObservablesCombine.java)
#### TODO
#### 辅助操作符
#### 过滤操作符
#### 条件/布尔操作符