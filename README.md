项目的Readme见fork的[原仓库](https://github.com/graphql-java-kickstart/graphql-java-tools)
---

## 此fork仓库目前情况
分支：13.1.1.OnePiece.0 基于分支6.0.2.OnePiece.x合并tag 13.1.1而来

与原13.1.1的区别：
1. 兼容graphql-java中PageInfo的变更
    - 依赖的graphql-java版本为由21.3变更为21.3.OnePiece.10.0.0（不影响服务主要为了单测能过通过）
    - 变更RelayConnectionFactory中的PageInfoDefinition，与6.0.2.OnePiece.x一致
2. 为无resolver接口提供支持
    - 扩展includeUnusedTypes参数的功能，能够同时支持includeUnusedInputs
3. 为SchemaParserDictionary按java路径导入提供支持
    - 删除SchemaClassScanner下init中的部分校验，完整解释见改动处代码注释
    - 删除SchemaClassScanner中validateAndCreateResult中部分报警日志
4. 构建AppliedDirective时，为其赋默认值
    - SchemaParser中buildAppliedDirectives逻辑修改，支持默认值