package com.pvpdojo.animation;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Animation;
import net.runelite.api.AnimationController;
import net.runelite.api.Client;

public class CKAnimationController extends AnimationController
{
    @Getter
    @Setter
    private boolean loop;

    @Getter
    @Setter
    private boolean finished;

    public boolean isLoop() {
        return loop;
    }

    public boolean isFinished() {
        return finished;
    }

    public CKAnimationController(Client client, int animationID, boolean loop)
    {
        super(client, animationID);
        this.loop = loop;
        this.finished = false;
    }

    public CKAnimationController(Client client, Animation animation, boolean loop)
    {
        super(client, animation);
        this.loop = loop;
        this.finished = false;

    }

    public void setLoop(boolean loop)
    {
        this.loop = loop;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}