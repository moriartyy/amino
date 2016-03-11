package darklight.amino;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.List;

/**
 * Created by hongmiao.yu on 2016/3/8.
 */
public class PageProcessorChain implements Iterable<PageProcessor> {

    private List<PageProcessor> processors = Lists.newArrayList();

    public Iterator<PageProcessor> iterator() {
        return processors.iterator();
    }

    public class Builder {
        private ImmutableList.Builder<PageProcessor> listBuilder = ImmutableList.builder();

        public Builder add(PageProcessor processor) {
            listBuilder.add(processor);
            return this;
        }

    }
}
