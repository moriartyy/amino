package darklight.amino.common.component;

/**
 * Created by hongmiao.yu on 2016/3/15.
 */
public enum LifecycleState {

    UnStarted, Starting, Started, Stopping, Stopped;

    public boolean canMoveToStart() {
        return this.equals(UnStarted) || this.equals(Stopped);
    }

    public boolean canMoveToStop() {
        return this.equals(Started);
    }


}
