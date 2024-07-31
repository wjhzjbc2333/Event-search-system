'''
import requests as rq
from bs4 import BeautifulSoup
newsUrl = 'http://www.xinhuanet.com/'
newsWeb = rq.get(newsUrl)
newsWeb.encoding = 'utf-8'
soup = BeautifulSoup(newsWeb.text,'lxml')
link_list = []
li_elements = soup.find_all('li')

print(li_elements)
'''
from pyquery import PyQuery as pq
import codecs
import requests
import json

def get_urls(max_page):
    
    #新华网域名
    start_url = 'http://so.news.cn/getNews?keyword=冲突&curPage='
    #start_url = 'http://so.news.cn/getNews?keyWordAll=部署&keyWordOne=导弹+武器+装备&keyWordIg='
    urls = []
    for i in range(1,max_page):
        #searchFields = 0: 全文  = 1: 标题
        spec_url = start_url  + str(i) + '&sortField=0&searchFields=1&lang=cn'
        #spec_url = start_url + '&sortField=0&searchFields=1&url=&senSearch=1&lang=cn&keyword=部署&curPage=' + str(i)
        source = pq(url=spec_url)
        str_src = source.text()
        str_src = str_src.replace("\"\"", "\"")
        try:
            res = json.loads(str_src, strict=False)
            for j in range(0, 10):
                #print(res['content']['results'][j]['url'])
                if res['content']['results']is not None:
                    urls.append(res['content']['results'][j]['url'])
        except:
            print("Error in json")

    return urls   

def get_news(myurl):
    try:
        source = pq(url = myurl)
        title = ''
        content = ''
        for item in source('.title').items():
            title = item.text()
            break
        for item in source('#detail p').items():
            content = content + item.text() + '\n'
            #大部分新闻取到第一个<p>即可 部分需要取完后人工筛选 | break注释:全部p标签 不注释:第一个p标签
            break
        title = title.replace('|', '')
        title = title.replace(':', '')
        title = title.replace('\\', '')
        title = title.replace('/', '')
        title = title.replace('*', '')
        title = title.replace('?', '')
        title = title.replace('<', '')
        title = title.replace('>', '')
        title = title.replace('\"', '')
        title = title.replace('\n', ' ')

        filename = 'result/' + title + '.txt'
        if content != '\n':
            with codecs.open(filename, "w+", encoding='utf-8') as f:
                #f.write(title + '\n')
                f.write(content + '\n')
    except:
        print('Error happened with this url: ' + myurl)
    else:
        print('Correct with this url: ' + myurl)
    finally: 
        print(title)

if __name__ == '__main__':
    urls = get_urls(100)
    for i in range(0, len(urls)):
        try:
            get_news(urls[i])
        except:
            pass
