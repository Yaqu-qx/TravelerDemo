### 介绍
这个是我大三上学期做的安卓课程项目，它中文名叫做畅游世界，是一个整合了旅游服务、自主规划、AI问答与个性化管理功能的综合性旅游APP。
主要功能包括以下六点：
1. 对相关类别旅游景点的查询
2. 对景点详细信息(包括相关图片、视频、文字介绍、酒店信息、路线地址)的查看
3. 对感兴趣景点的动态操作
4. 制作旅游规划与攻略
5. 相关事项问答
6. 账号的个性化管理

### 功能模块图
![image](https://github.com/Yaqu-qx/TravelerDemo/blob/master/images/%E5%9B%BE%E7%89%871.png)

### 界面效果
<img src="https://github.com/Yaqu-qx/TravelerDemo/blob/master/images/%E5%9B%BE%E7%89%872.png" width="210px"> <img src="https://github.com/Yaqu-qx/TravelerDemo/blob/master/images/%E5%9B%BE%E7%89%873.png" width="210px"> <img src="https://github.com/Yaqu-qx/TravelerDemo/blob/master/images/%E5%9B%BE%E7%89%874.png" width="210px"> <img src="https://github.com/Yaqu-qx/TravelerDemo/blob/master/images/%E5%9B%BE%E7%89%875.png" width="210px"><img src="https://github.com/Yaqu-qx/TravelerDemo/blob/master/images/%E5%9B%BE%E7%89%876.png" width="210px"> <img src="https://github.com/Yaqu-qx/TravelerDemo/blob/master/images/%E5%9B%BE%E7%89%877.png" width="210px"> <img src="https://github.com/Yaqu-qx/TravelerDemo/blob/master/images/%E5%9B%BE%E7%89%878.png" width="210px"> <img src="https://github.com/Yaqu-qx/TravelerDemo/blob/master/images/%E5%9B%BE%E7%89%879.png" width="210px"><img src="https://github.com/Yaqu-qx/TravelerDemo/blob/master/images/%E5%9B%BE%E7%89%8710.png" width="210px"> <img src="https://github.com/Yaqu-qx/TravelerDemo/blob/master/images/%E5%9B%BE%E7%89%8711.png" width="210px"> <img src="https://github.com/Yaqu-qx/TravelerDemo/blob/master/images/%E5%9B%BE%E7%89%8712.png" width="210px"> <img src="https://github.com/Yaqu-qx/TravelerDemo/blob/master/images/%E5%9B%BE%E7%89%8713.png" width="210px">

### 系统组织架构
系统组织架构类似于MVC，Layout文件定义了视图(View)层的界面布局，Activity和Fragment文件通常充当控制器（Controller）的角色，负责处理用户交互、视图的展示和更新。Adapter文件用于管理ListView或RecyclerView等列表组件的数据绑定SQLiteOpenHelper文件可能用于管理数据库操作，即模型（Model）层的数据存取和处理。

### 环境
经过安卓模拟器测试，畅游世界 APP 可以在 Android 7.0 和 Android 10.0 上兼容。 <br>
但其中有一个问题是里面的ai功能主要接入了讯飞星火大模型的SDK，这个SDK要求的模拟器CUP框架必须在armeabi-v7a或arm64-va8，虽然我在armeabi-v7a和25api的模拟器环境下能够成功运行app,但是armeabi-v7a框架的模拟器运行的速度可以说是十分缓慢QAQ。<br>
而将ai功能的代码注释掉后在x86的设备上是比较顺畅的

### 不足
该项目还有很多问题和不完善的地方还待解决，除了上面ai功能环境问题之外，还有“我的”功能模块的很多小功能还没实现和地图功能未完善。
