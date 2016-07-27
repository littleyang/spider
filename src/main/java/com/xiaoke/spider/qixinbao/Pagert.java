package com.xiaoke.spider.qixinbao;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.processor.PageProcessor;

public class Pagert implements PageProcessor{
	
	
	private Site site = Site.me().setRetryTimes(3).setSleepTime(2000)
            .addHeader("Content-Type", "application/json")
            .addCookie("sid", "s%3AQopj2OiDpv9TCwBm4ypgk1sksybuEcXS.qsVdMu8DM4%2F6fZSJQfQ8dy3QgN3da3uV0WyexsZij00");
	
	private Downloader downloader;
	
	
	public Pagert(Downloader downloader) {
	        this.downloader = downloader;
	}
	
	

	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
		 List<String> mainStaffs = page.getHtml().xpath("//*[@id=\"info\"]/div/div[3]/div[2]/ul/li").all();
	       for(int i=0;i<mainStaffs.size();i++){
	    	   Document doc = Jsoup.parseBodyFragment(page.getHtml().xpath("//*[@id=\"info\"]/div/div[3]/div[2]/ul/li").all().get(i));
	           Element body = doc.body();  
	           System.out.println("==== " + doc.select("span").first().text());
	           System.out.println("==== " + doc.select("span").last().text());
	       }
	       
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}

}
