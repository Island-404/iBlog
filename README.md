# iBlog

## 项目介绍
本项目是一个基于vue+springboot的前后端分离个人博客网站，博客功能包括文章列表、文章分类列表、赞赏功能、查看文章、点赞文章、评论文章、回复评论、留言、可支持代码高亮；还有一个博客管理系统，其功能包括发布文章，编辑文章，添加分类，管理分类，管理用户。

前端页面位于vue-blog下，blog为前台，blogadmin为后台管理系统，后端代码位于myblog下。

在此声明一下，由于本人前端能力有限，所以前端代码是借鉴了其他大佬的，原作者gitee地址如下：

**前台**：https://gitee.com/feng_meiyu/blog

**后台管理系统**：https://gitee.com/lenve/VBlog



## 技术介绍

**前端**：
Vue、Vuex、Vue-router、Axios、ElementUI、Echarts、Markdown、Nginx

**后端**:
SpringBoot、Shiro、MybatisPlus、Mysql、Redis、Swagger2、docker、七牛云

## 开发工具

|开发工具|说明|
|-|-|
|IDEA|Java开发工具IDE|
|Sublime_text|前端开发工具IDE|
|SQLyog Enterprise Trial|MySQL远程连接工具|
|X-shell|Linux远程连接工具|
|Xftp|Linux文件上传工具|

## 项目截图
![QQ图片20210607212315.png](http://qtysfkbdh.hn-bkt.clouddn.com/180509dd-dccc-45cd-99fd-e1ffbdd88987)

![QQ图片20210607212356.png](http://qtysfkbdh.hn-bkt.clouddn.com/a90cb4c1-831e-46ff-8e3e-306fd3100f95)

![QQ图片20210607212608.png](http://qtysfkbdh.hn-bkt.clouddn.com/5978252a-b5d1-47be-bb2e-29820d6cae64)

![QQ图片20210607212612.png](http://qtysfkbdh.hn-bkt.clouddn.com/20baaf0b-0dce-404a-85dc-2a70bbfe1776)


## 项目部署
1.克隆项目到本地
```
git clone https://gitee.com/is_land/iblog.git
```
2.在自己的Mysql图形化工具执行myblog项目中的resource找到vueblog.sql文件.

3.用IDEA打开myblog文件,在application.xml添上你的mysql密码.

4.部署前端

**前台：**
```
npm run serve
```
**后台：**

由于后台前端使用的vue版本比较低，所以需要
```
npm run dev
```
5.在浏览器地址栏输入```http://localhost:8080```即可访问到前台页面；在浏览器地址栏输入```http://localhost:8082```即可访问到后台页面。


