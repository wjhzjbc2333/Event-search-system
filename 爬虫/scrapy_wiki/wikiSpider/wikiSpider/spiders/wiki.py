import scrapy
import csv
import opencc 
'''
with open('wiki.csv', newline='', encoding='utf-8') as csvfile:
    reader = csv.reader(csvfile, delimiter=' ')
    for row in reader:
        [link,title] = row[0].split(',')
'''

class WikiSpider(scrapy.Spider):
    name = 'wiki'
    allowed_domains = ["zh.wikipedia.org"]
    start_urls = []
    url_title = {}
    converter = opencc.OpenCC('t2s.json')

    with open('link_title.csv', newline='', encoding='utf-8') as csvfile:
        reader = csv.reader(csvfile, delimiter=',')
        for row in reader:
            [link,title] = row
            start_urls.append(link)
            url_title[link] = converter.convert(title)


    def parse(self, response):
        str="results/" + self.url_title[response.request.url] + ".txt"
        file=open(str, "a+", encoding='utf-8')
        for each in response.xpath("//p//text()"):
            #intro = '\n'.join(each).strip()
            content = each.extract()
            str=''.join(content)
            file.write(self.converter.convert(str))
        file.close()
