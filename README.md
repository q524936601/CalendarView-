# 移动172《Android应用项目设计》
## 项目题目：传统日历APP的设计与实现
### 成员：梁安铧、林坚滨

### 2019-06-10 周一完成内容：
1、需求分析：一、日历的基本查看功能、周月转换
            二、不同日期所对应的多个附加功能实现，例如万年历、黄历每日吉凶宜忌查询、周公解梦、星座运势、历史上的今天等
            三、……

2、概要设计：一、CalendarView 控件的运用与实现，通过CalendarView展示日历的正常显示
               https://www.jb51.net/article/158010.htm
            二、在主活动中 通过设置setOnDataChangeListener() 来为CalendarView添加监听事件，从而实现其他功能的基本“入口”
            三、TextView控件通过监听事件显示当前点击日期的完整日期（Year年/Month月/Dayofmonth日）
            四、接入API，通过接入具备不同功能的日历相关的API，结合CalendarView的监听事件，通过监听不同的日期实现显示对应日期的万年历、黄历每日吉凶宜             忌查询、周公解梦、星座运势、历史上的今天等完善功能

### 2019-06-11 周二完成内容：
1、功能设计：放置控件，实现界面基础布局，实现日历显示,拖拽页面滑动可跳转至下一个月,点击首月按钮跳转至最低年份的首月,点击今天可自动获取今天的日期跳转至对应的今天,某天可实现查找日历内某一天的效果。
2、界面效果设计：在界面上方对日历控件的引用,显示从1990-2100年的日历,能正常显示每日,每周,星期,节假日,传统节日等等,日历上方中间显示当前选中的时间,时间左右两个箭头能够点击跳转至上下月,日历下方预留空间用于显示选中日期的详细内容。


### 2019-06-12 周三完成内容：
1、添加ListView控件,新增相对性的list_item子布局,对子布局进行规划,将之与ListView关联，用于显示对应日期的详细内容。

### 2019-06-17 周一完成内容：
依赖okhttp 给项目赋予连接网络的功能，确保能够正常获取到接口数。
### 2019-06-18 周二完成内容：
根据就所需要的功能，写一个适配器方法，让接口的数据能够赋予到文件中，并根据选中日期的不同而改变。
### 2019-06-19 周三完成内容：
1、添加了drawable中对TextView边框background的自定义和对Button的background的自定义新图案，优化视觉效果。
2、添加ZiActivity转移MainActivity中的黄历内容并赋值在新的页面，实现点击当日跳转至黄历界面显示更多黄历信息的功能，优化用户体验。
### 2019-06-20 周四完成内容：
1、优化布局界面，赋予子布局数据
2、撰写安卓实训课程文档
### 2019-06-21 周五完成内容：
1、撰写安卓实训课程文档

### 项目已完成
