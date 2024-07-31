### 一、工具与环境

使用 `Anaconda` 虚拟环境，安装 `python3`、`pyquery`、`scrapy` 

~~~
pip install pyquery
pip install scrapy
~~~

暂未使用 `selenium`

浏览器使用 `Microsoft Edge 123.0.2420.65 (正式版本) (64 位) `

### 二、如何爬取

#### 1.`scrapy` 与 `wiki`

##### 1.1 wiki

`scrapy` 提供了较为完整的框架，在爬取 `wiki` 数据的时候使用

`scrapy` 基础使用可参照 [Scrapy 入门教程 | 菜鸟教程 (runoob.com)](https://www.runoob.com/w3cnote/scrapy-detail.html)，更多内容在[Scrapy 2.11 documentation — Scrapy 2.11.1 documentation](https://docs.scrapy.org/en/latest/)

在对项目进行初始化后生成一个自己的类，设置其中的 `allowed_domains`

~~~
allowed_domains = ['zh.wikipedia.org']
~~~

维基有一个单独的搜索页面，不同于普通搜索框在输入关键词后会直接跳转到对应词条，这个页面可以展示所有与关键词相关的词条，在搜索框内无内容时点击“搜索”即可

![image-20240329205318939](C:\Users\86182\AppData\Roaming\Typora\typora-user-images\image-20240329205318939.png)

此时查看域名变为 `https://zh.wikipedia.org/w/index.php?search=&title=Special:搜索`

`search=` 后即为要搜索的关键词

##### 1.2 举例：搜索总统选举

`url = https://zh.wikipedia.org/w/index.php?title=Special%3A搜索&limit=50&offset=0&ns0=1&search=总统选举`

`limit` 属性为一次查询的条目数量，`offset` 为默认查询结果的偏移量（从第几个开始）

有了这个 `url` 后就可以开始编写爬虫

`scrapy` 类中的 `start_urls` 为爬取的起始列表，可以将此 `url` 放入

~~~python
start_urls = ("https://zh.wikipedia.org/w/index.php?title=Special%3A%E6%90%9C%E7%B4%A2&limit=50&offset=0&ns0=1&search=%E6%80%BB%E7%BB%9F%E9%80%89%E4%B8%BE",)
~~~

`%E6%80%BB%E7%BB%9F%E9%80%89%E4%B8%BE` 为粘贴后的编码问题，不影响使用

`parse` 方法拿到的 `response` 数据为网页源代码，在维基页面按 `F12` 即可查看：

![image-20240329211208425](C:\Users\86182\AppData\Roaming\Typora\typora-user-images\image-20240329211208425.png)

维基返回的搜索数据全部放在一个 `ul` 里，每个 `li` 都对应一个条目

在下面的 `a` 标签中，这个链接就是我们想要的详情页链接：

![image-20240329211321423](C:\Users\86182\AppData\Roaming\Typora\typora-user-images\image-20240329211321423.png)

用 `xpath` 解析网页内容可以得到所有的目标 `url`，这里将其以 `link,title` 的格式写入文件（**注：爬取的 `url` 为相对路径**）

~~~python
for each in response.xpath("//div[@class='mw-search-result-heading']"):
    link = each.xpath("a/@href").extract()
    title = each.xpath("a/@title").extract()
    file.write("https://zh.wikipedia.org" + link[0] + ',' + title[0] + '\n')
~~~

现在这个文件 `a.csv` 中是关于“总统选举”的所有目标网址和标题，再使用一个爬虫依次获取网页内容

由于只获取文本信息，可以再看看维基详情页的结构：

![image-20240329212432821](C:\Users\86182\AppData\Roaming\Typora\typora-user-images\image-20240329212432821.png)

所有文本都包含在 `p` 标签下，使用 `response.xpath("//p//text()")` 便可拿到所有文本。如果想同时拿到标题，增加 `//h2//text()` 等等

直接获取的维基数据简体繁体混杂，推荐使用 `opencc` 进行繁转简：[BYVoid/OpenCC: Conversion between Traditional and Simplified Chinese (github.com)](https://github.com/BYVoid/OpenCC)

最后以标题作为文件名，全部 `p` 标签下的文本作为内容输出即可

#### 2.`pyquery`与新华网

`scrapy` 同样能胜任这份工作，只不过在使用中发现 `pyquery + request` 更简单

有关 `pyquery` 的简单使用，可以参考[Python pyquery 教程|极客教程 (geek-docs.com)](https://geek-docs.com/python/python-tutorial/python-pyquery.html)，全部内容在[pyquery – PyQuery complete API — pyquery 2.0.x documentation](https://pyquery.readthedocs.io/en/latest/api.html)

`pyquery` 是用来解析网页的工具，是`jQuery` 的 `python` 实现，网页的获取需要使用 `request` 库

##### 2.1 新华网

新华网的好处：大量新闻，搜索功能不错，大部分新闻第一段内容可作为结果，不需要动态加载（处理简单）

新华网搜索域名：`http://so.news.cn`

##### 2.2 举例：搜索演习

新华网无法直接从 `url` 获取到网页数据，直接爬取 `https://so.news.cn/#search/0/演习/1/` 会发现什么都没有

按 `F12` 打开控制台，`ctrl + R` 刷新网页，在 `Fetch/XHR` 或 `文档-发起程序` 下可以找到真正的请求：

![image-20240330001650650](C:\Users\86182\AppData\Roaming\Typora\typora-user-images\image-20240330001650650.png)

其中 `curPage` 表示当前页号，`searchFields` 代表 `0：在新闻全文中 1：仅在标题中`，如果使用高级搜索则会有更多字段

如果直接进入这个链接，即`http://so.news.cn/getNews?keyword=演习&curPage=1&sortField=0&searchFields=1&lang=cn`，会发现服务器直接返回了一段 `json`：

![image-20240330002201027](C:\Users\86182\AppData\Roaming\Typora\typora-user-images\image-20240330002201027.png)

使用 `python` 自带 `json` 库即可解析出每个新闻下的 `url`

**注：`json` 格式并不一定完全正确，使用 `loads` 方法时需要设置 `strict=False`，且有时会出现额外引号和意外的解析错误，需额外处理**

~~~
source = pq(url=spec_url)
str_src = source.text()
#额外的引号
str_src = str_src.replace("\"\"", "\"")
#这里可能出现json解析的意外bug
try:
    res = json.loads(str_src, strict=False)
    for j in range(0, 10):
        if res['content']['results']is not None:
            urls.append(res['content']['results'][j]['url'])
except:
    print("Error in json")
~~~

取到所有 `url` 后，如法炮制查看页面标题及内容：

![image-20240330002847785](C:\Users\86182\AppData\Roaming\Typora\typora-user-images\image-20240330002847785.png)

取出标题和内容也很简单： 

~~~
for item in source('.title').items():
    title = item.text()
    break
for item in source('#detail p').items():
    content = content + item.text() + '\n'
    #break
~~~

对于上面代码中的第二个 `break`，由于**大部分**新闻在第一个 `p` 标签下会用很简洁的话做为概括，所以只需要概括的时候可以取消其注释

当然这样写会带来一些问题，比如在第一段话前有空的 `p` 标签，或是新华网链接到外部网站导致 `('.title')` 和 `('#detail p')` 什么都取不到。由于新闻数量足够多，这些暂时是可以忽略的













**另：一些事件数据源**

##### 1.`GDELT` 数据库

[The GDELT Project](https://www.gdeltproject.org/)

同个文件夹下的 `csv` 文件即为一份 `GDELT` 示例

`GDELT` 是一个全面记录事件和事件参与者的时空数据集，其核心是对事件及其参与者信息的自动化识别、概化、分类和编码。2013 年 `GDELT` 数据库公布的第一个版本包含自 1979 年以后的所有事件，更新频率为 1 天。2015 年 `GDELT` 的第二个版本更新频率提高到了 15 分钟，但时间范围仅从 2015年 2月19日开始。截止 2018年底，`GDELT` 记录的事件总数超过 7亿 条。每条 `GDELT` 数据记录主要由 5 个部分组成，分别是事件编号和日期、事件参与者、事件动作、事件地理信息以及数据管理。

`GDELT` 的数据格式为包含活动参与者、国家和地区信息、组织形式及各种代码在内的几十列处理过的信息，想得到原文本可以查看`SOURCE_URL` 字段提供的网址——它的主要缺点也在这里：源网址是多语言的，可以用来训练的中文文本很少。

官网提供的查询方式现在已失效，提供的发邮件查询方式也不可行，除此之外还有收费非常高的 `Google BigQuery` 服务。所以如果不要求事件类型可以使用官网提供的压缩包下载获取事件，这些事件只按照发生时间排列，否则没有很好的方法。



**北师大**

北师大似乎搞了一个以 `GDELT` 为数据源的项目，但并未尝试过爬取这个网站。

[全球新闻事件数据共享平台 (bnu.edu.cn)](https://gde.bnu.edu.cn/share?type=introduction)

##### 2.`ICEWS` 数据库

[Integrated Crisis Early Warning System | Lockheed Martin](https://www.lockheedmartin.com/en-us/capabilities/research-labs/advanced-technology-labs/icews.html)

文件形式类似于`GDELT` ，缺点还是无中文语料

##### 3.百度 `DuEE`、`DuIE` 数据集

[DuEE1.0中文事件抽取数据集](https://aistudio.baidu.com/datasetdetail/186935)

[DuEE-fin金融领域篇章级事件抽取数据集](https://aistudio.baidu.com/datasetdetail/186939)

[DuIE2.0中文关系抽取数据集](https://aistudio.baidu.com/datasetdetail/180082)

中文事件库，分别有`11w`和`17w` 数据量；缺点是事件通常只有一句话，且涵盖类型过多
