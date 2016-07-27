package com.xiaoke.spider.qixinbao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.AbstractDownloader;


public class HttpClientDownloaderPool extends AbstractDownloader {

    //private List<Site> sites;
    private int seconds = 3;
    private AtomicInteger currentDownloaderIndex = new AtomicInteger();

    private List<MyHttpClientDownloader> downloaderList;
    private List<Site> sites;

    public HttpClientDownloaderPool(List<Site> sites) {
        this.sites = sites;
        downloaderList = new ArrayList<>();
        downloaderList.addAll(sites.stream().map(MyHttpClientDownloader::new).collect(Collectors.toList()));
    }

    public Site getCurrentSite() {
        int index = currentDownloaderIndex.get();
        return sites.get(index);
    }

    @Override
    public Page download(Request request, Task task) {
        int index = currentDownloaderIndex.addAndGet(1);
        MyHttpClientDownloader downloader = downloaderList.get(index % downloaderList.size());
        synchronized (this) {
            while (System.currentTimeMillis() - downloader.getLastEndSpiderTime() < seconds * 1000) {
                try {
                    Thread.sleep(System.currentTimeMillis() - downloader.getLastEndSpiderTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return downloader.download(request, task);
        }
    }

    @Override
    public void setThread(int threadNum) {
        // do nothing
    }
}
