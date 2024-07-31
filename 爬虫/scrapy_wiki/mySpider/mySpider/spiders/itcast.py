import scrapy
from mySpider.items import ItcastItem
from scrapy.http import Request
from urllib.parse import quote


class ItcastSpider(scrapy.Spider):
    name = 'itcast'
    allowed_domains = ['zh.wikipedia.org']
    start_urls = ("https://zh.wikipedia.org/w/index.php?title=Special%3A%E6%90%9C%E7%B4%A2&limit=50&offset=0&ns0=1&search=%E6%80%BB%E7%BB%9F%E9%80%89%E4%B8%BE")

    
    def parse(self, response):

        items = []
        file=open("../wikiSpider/link_title.csv", "a+", encoding='utf-8')
        for each in response.xpath("//div[@class='mw-search-result-heading']"):
            item = ItcastItem()
            link = each.xpath("a/@href").extract()
            title = each.xpath("a/@title").extract()
        
            item['link'] = link[0]
            item['title'] = title[0]
            file.write("https://zh.wikipedia.org" + link[0] + ',' + title[0] + '\n')
            items.append(item)
        file.close()
        return items
           
