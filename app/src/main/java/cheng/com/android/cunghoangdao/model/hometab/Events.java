package cheng.com.android.cunghoangdao.model.hometab;

import android.support.annotation.NonNull;

import com.alorma.timeline.TimelineView;

public class Events {
    private String name;
    private int type;
    private int alignment;

    public Events(@NonNull String name) {
        this(name, TimelineView.TYPE_DEFAULT);
    }

    public Events(@NonNull String name, int type) {
        this(name, type, TimelineView.ALIGNMENT_DEFAULT);
    }

    public Events(@NonNull String name, int type, int alignment) {
        this.name = name;
        this.type = type;
        this.alignment = alignment;
    }

    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public @TimelineView.TimelineType int getType() {
        return type;
    }

    public void setType(@TimelineView.TimelineType int type) {
        this.type = type;
    }

    public @TimelineView.TimelineAlignment int getAlignment() {
        return alignment;
    }

    public void setAlignment(@TimelineView.TimelineAlignment int alignment) {
        this.alignment = alignment;
    }

    @Override public String toString() {
        return "Events{" +
            "name='" + name + '\'' +
            ", type=" + type +
            ", alignment=" + alignment +
            '}';
    }
}