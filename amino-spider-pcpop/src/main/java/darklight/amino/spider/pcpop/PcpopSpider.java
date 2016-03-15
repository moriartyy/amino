//package darklight.amino.spider.pcpop;
//
//import com.google.common.collect.ImmutableList;
//import com.google.common.collect.Lists;
//import com.google.inject.Inject;
//import darklight.amino.engine.Page;
//import darklight.amino.common.Environment;
//import darklight.amino.common.Settings;
//import darklight.amino.spider.AbstractSpider;
//import darklight.amino.spider.SpiderStats;
//import darklight.amino.repository.Repository;
//
//import java.util.LinkedList;
//import java.util.List;
//
///**
// * Created by hongmiao.yu on 2016/3/10.
// */
//public class PcpopSpider extends AbstractSpider {
//
//    private final Repository repository;
//    private final LinkedList<Page> fetchQueue = Lists.newLinkedList();
//
//    @Inject
//    public PcpopSpider(Settings settings, Environment environment) {
//        super(settings, environment);
//        List<Page> seeds = getSeeds();
//        fetchQueue.addAll(seeds);
//        this.repository = null;
//    }
//
//    @Override
//    public SpiderStats stats() {
//        return null;
//    }
//
//    @Override
//    protected void doStart() {
//
//    }
//
//    @Override
//    protected void doStop() {
//
//    }
//
//    @Override
//    public void crawl(Page page) {
//        download(page);
//        List<Page> linkPages = parseLinkPages(page);
//        filterPages(linkPages);
//        fetchQueue.addAll(0, linkPages);
//        this.repository.save(name(), page);
//    }
//
//    private void download(Page page) {
//
//    }
//
//    private void filterPages(List<Page> linkPages) {
//    }
//
//    private List<Page> parseLinkPages(Page page) {
//        return null;
//    }
//
//    @Override
//    public Page next() {
//        Page page = fetchQueue.poll();
//        if (page == null) {
//            this.fetchQueue.addAll(this.repository.acquireDirty(this.name(), 1000));
//            this.fetchQueue.addAll(this.repository.acquireFresh(this.name(), 1000));
//            page = fetchQueue.poll();
//        }
//        return page;
//    }
//
//    @Override
//    public String name() {
//        return "pcpop-spider";
//    }
//
//    public List<Page> getSeeds() {
//        return ImmutableList.of(new Page("http://www.pcpop.com/"));
//    }
//}
