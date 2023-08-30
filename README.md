项目的Readme见fork的[原仓库](https://github.com/graphql-java-kickstart/graphql-java-tools)
---

## 此fork仓库目前情况
分支：6.0.2.OnePiece.x 管理基于6.0.2的版本变更，x为每次变更的版本号，从1开始。
开发流程：
1. 将版本号设置为快照（SNAPSHOT）后缀
2. 变更后本地install，测试通过后，将快照版本deploy到私服
3. 测试环境测试通过后，将版本更新为OnePiece.x+1，deploy到私服
4. 依赖方更新版本号

## 版本变更信息
### 6.0.2.Onepiece.1
RelayConnectionFactory拓展:增加PageInfo字段 totalCount
### 6.0.2.Onepiece.2
SchemaParserDictionary拓展:拓展初始化流程中按名称加载Class的能力 可配置classPath作为额外加载途径
### 6.0.2.Onepiece.3
MissingFieldResolver.scanForMatches()尝试通过加载Class的手段提供引擎所需javaType
### 6.0.2.Onepiece.4
MissingFieldResolver.createDataFetcher()去掉抛错改为返回null值
### 6.0.2.Onepiece.5
RelayConnectionFactory拓展:增加PageInfo字段 cursorResetDuration
### 6.0.2.Onepiece.6
之前发布到私服没有源码包，这个版本加上。注意install/deploy时，加上-P nexus会带上sources包。

## todo
* 将6.1.0的变更合入6.1.0.OnePiece.x
* 更新到最新的稳定版
