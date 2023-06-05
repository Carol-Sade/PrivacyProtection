# PrivacyProtection

## vue搭建步骤

1. 在vue目录下执行npm install安装相关依赖
2. vue目录下执行npm run dev运行开发版
3. vue目录下执行npm run build:prod将代码打包到vue/dist下
4. 修改vue/src/utils/request.js的baseUrl，作为api前缀
5. vue/src/views下的privacy和user下的information的upload操作需要在data里单独设置baseUrl

```
const service = axios.create({
  baseURL: '', // url = base url + request url
  // withCredentials: true, // send cookies when cross-domain requests
  timeout: 30000 // request timeout
})
```

## springboot搭建步骤

1. 修改src/main/java/resources/application.yml的dev和prod切换本地和线上配置

```
spring:
  profiles:
    # active: dev
    active: prod
```

需要修改mysql用户名密码，redis地址以及密码，ssl证书路径及密码，默认ssl证书保存在同yml目录下

2. 修改yml中upload内容，本地和线上部署有路径区别
3. sql结构文件在根目录
4. 通过maven的package命令将后端打包到target目录

## 区块链搭建步骤

1. 使用Fabric 1.4，运行first-network下的byfn.sh,指定couchdb数据库
2. 将运行完成后生成的crypto-config保存（每次运行都需要保存新的），并在com/example/privacyprotection/config/PathConf.java中修改配置，主要修改线上或本地路径，以及密钥
3. 需要在发起区块链操作请求的服务器配置hosts,映射ip地址
```agsl
xxx.xxx.xxx.xxx orderer.example.com peer0.org1.example.com peer1.org1.example.com peer0.org2.example.com peer1.org2.example.com
```
4. 将resources的chaincode在前端区块链智能合约管理中上传，并进行安装，实例化。也可通过在区块链服务器命令行操作

仅供学习参考，其他问题可以联系CarolSade@outlook.com
