from pyquery import PyQuery as pq
import codecs
import requests
import json
import os
#words = ['军队交火', '部署武器']
words = ['演习','对话磋商','边境冲突','军队交火','部署武器']  

def get_urls(max_page, word):

    #国际在线网域名
    start_url = 'https://news.cri.cn/search/q?page='
    url_time = {}
    for i in range(1,max_page):
        spec_url = start_url  + str(i) + '&pageSize=25&type=0&qtext=' + word + '&lang=cn'
        source = pq(url=spec_url)
        res = json.loads(source.text(), strict = False)
        for j in range(0, 25):
            if res['data'][j]['urls'] is not None:
                url_time[res['data'][j]['urls']['pc']] = res['data'][j]['publishTimeStr']
 
    return url_time

def get_news(myurl, time, word):
    try:
        # r=requests.get(myurl)
        # r.encoding = 'gbk'
        # source = pq(r.content)
        source = pq(url = myurl)
        title = ''
        content = ''
        imgName= ''
        
        title = source('#atitle').text()
        if title == '':
            title = source('.atitle').text()
        if title == '':
            title = source('.title').text()
        if(title == ''):
            title = source('.article-header h1').text()
        
        #考虑同时爬取图片
        for item in source('#abody p').items():
            if item.text() != "" and item.text() != '':
                content = content + item.text() + '\n'           
            imgSrc = item('img').attr('src')
            if(imgSrc != None):
                if(imgSrc[0] == '/'):
                    imgSrc = "https:" + imgSrc
                img_name = imgSrc.split('/')[-1]
                save_image(imgSrc, img_name)
                if imgName == '':
                    imgName = img_name
                else:
                    imgName = imgName + "," + img_name
        if content == '':
            for item in source('.article-content p').items():
                if item.text() != None:
                    content  = content + item.text() + '\n'

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

        filename = word + '/' + title + '.txt'
        if content != '' and content != '\n':
            with codecs.open(filename, "w+", encoding='utf-8') as f:
                #f.write(title + '\n')
                f.write(time + '\n')
                f.write(imgName + '\n')
                f.write(content + '\n')
    except:
        print('Error happened with this url: ' + myurl)
    else:
        print('Correct with this url: ' + myurl)
    finally: 
        #print(title)
        pass

def save_image(img_src, name, main_path='images'):
    imgResponse = requests.get(img_src)
    if(imgResponse.status_code == 200):
        if not os.path.exists(main_path):#如果路径不存在
            os.makedirs(main_path)        #创建文件夹
        with open(main_path + '/' + name,'ab') as f:#以二进制追加模式打开,不清空
            f.write(imgResponse.content)
            f.close()


if __name__ == '__main__':
    for word in words:
        if not os.path.exists(word):
            os.makedirs(word)
        urls = get_urls(30, word)
        for url, time in urls.items():
            get_news(url, time, word)
