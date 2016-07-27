package com.xiaoke.spider.qixinbao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * Created by fengpj on 2016/7/6.
 * 企业名录抓取
 */
public class GithubRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setTimeOut(10000).setRetryTimes(3).setSleepTime(2000)//.setHttpProxyPool(new HttpProxyGetter().getProxyList(0))
            .addHeader("User-Agent","Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    
    private Downloader downloader;
	
	
	public GithubRepoPageProcessor(Downloader downloader) {
	      this.downloader = downloader;
	}
	

    @Override
    public void process(Page page) {
    	
    	page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }
    
    public static void main(String[] args) {
    	
    	List<Site> sites = new ArrayList<>();
//        Site site1 = Site.me().setRetryTimes(3).setSleepTime(2000)
//                .addHeader("Content-Type", "application/json")
//                .addCookie("sid", "s%3AHcSHsSS6CYXUwDTFRBmJFMzgLtCUFZMW.zYnE5XwhRueaclRROQIcC%2FY7mGtvoZnShdCiLBlr6ZE");
//        Site site2 = Site.me().setRetryTimes(3).setSleepTime(2000)
//                .addHeader("Content-Type", "application/json")
//                .addCookie("sid", "s%3AbItV0jAZfPr2XyBcKqPyHbJPSKibcovr.0O1XPnJ10OI%2Fc8uq3yD5X6Sjpufo%2BlGUdebbkPG%2FJww");
//        sites.add(site1);
//        sites.add(site2);
        
        HttpClientDownloaderPool httpClientDownloaderPool = new HttpClientDownloaderPool(sites);
    	
        Spider.create(new GithubRepoPageProcessor(httpClientDownloaderPool)).addUrl("https://github.com/code4craft").thread(5).run();
    }
}
