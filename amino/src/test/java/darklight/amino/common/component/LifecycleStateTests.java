package darklight.amino.common.component;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by hongmiao.yu on 2016/3/15.
 */


public class LifecycleStateTests {

    @Test
    public void testChangeState() {

        LifecycleState state;

        state = LifecycleState.UnStarted;
        Assert.assertTrue(state.canMoveToStart());

        state = LifecycleState.Starting;
        Assert.assertFalse(state.canMoveToStart());

        state = LifecycleState.Started;
        Assert.assertFalse(state.canMoveToStart());

        state = LifecycleState.Stopping;
        Assert.assertFalse(state.canMoveToStart());

        state = LifecycleState.Stopped;
        Assert.assertTrue(state.canMoveToStart());

        state = LifecycleState.UnStarted;
        Assert.assertFalse(state.canMoveToStop());

        state = LifecycleState.Starting;
        Assert.assertFalse(state.canMoveToStop());

        state = LifecycleState.Started;
        Assert.assertTrue(state.canMoveToStop());

        state = LifecycleState.Stopping;
        Assert.assertFalse(state.canMoveToStop());

        state = LifecycleState.Stopped;
        Assert.assertFalse(state.canMoveToStop());
    }
}
